package no.vejmon.dommern.config;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import no.vejmon.dommern.bane.Bil;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;

import java.io.PrintWriter;
import java.io.StringWriter;

@Slf4j
@Aspect
@Configuration
@ConditionalOnExpression(
        "T(org.springframework.util.StringUtils).hasText('${spring.mail.username:}')"
)
public class EmailConfig {

    @Value("${spring.mail.username}")
    private String mail;
    private Integer counter = 0;
    private final JavaMailSender sender;

    public EmailConfig(JavaMailSender sender) {
        this.sender = sender;
    }

    @AfterThrowing(
            pointcut = "execution(* no.vejmon..*(..))  && !within(no.vejmon.dommern.config.EmailConfig)",
            throwing = "ex"
    )
    private void sendThrownEmail(Throwable ex){
        log.error(ex.getMessage());
        if (counter > 15) return;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(mail);
        message.setTo(mail);
        message.setSubject("🔥 Dommern har ett problem! 🔥");
        message.setText(buildEmailBody(ex));
        log.info("Sending email notification for exception to {}", mail);
        sender.send(message);
        log.info("email sendt successfully");

        counter++;
    }

    @Async
    public void sendNewBilImage(Bil bil, InputStreamSource qrImage) throws MessagingException {
        String text ="""
        Hei!
        
        Bilen din "%s" er nå klar til bruk. QR-koden for din bil ligger vedlagt.
        Hilsen,
        Dommern Teamet""".formatted(bil.getName());
        String subject = "%s er klar hos Dommern!".formatted(bil.getName());
        sendEmailWithImage(bil.getKusk().getEmail(), text, subject, qrImage);
    }

    public void sendEmailWithImage(String to, String text, String subject, InputStreamSource qrImage) throws MessagingException {
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setTo(to);
        helper.setFrom(mail);
        helper.setSubject(subject);
        helper.setText(text);

        helper.addAttachment("img.png", qrImage);
        log.debug("Sending email with image to {}", to);
        sender.send(message);
        log.debug("Mail with image sendt to {}", to);
    }

    private String buildEmailBody(Throwable ex) {
        StringWriter sw = new StringWriter();
        ex.printStackTrace(new PrintWriter(sw));

        return """
               Dommern har støtt på en feil.

               Message:
               %s

               Stack trace:
               %s
               """.formatted(ex.getMessage(), sw.toString());
    }

}
