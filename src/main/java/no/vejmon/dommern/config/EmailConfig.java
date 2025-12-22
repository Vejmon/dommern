package no.vejmon.dommern.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import java.io.PrintWriter;
import java.io.StringWriter;

@Profile("production")
@Slf4j
@Aspect
@Component
public class EmailConfig {

    @Value("${spring.mail.username}")
    private String mail;
    private final MailSender sender;

    public EmailConfig(MailSender sender) {
        this.sender = sender;
    }

    @AfterThrowing(
            pointcut = "execution(* no.vejmon..*(..))  && !within(no.vejmon.dommern.config.EmailConfig)",
            throwing = "ex"
    )
    public void sendEmail(Throwable ex){
        log.warn("Sending email notification for exception");
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(mail);
        message.setTo(mail);
        message.setSubject("🔥 Dommern har ett problem! 🔥");
        message.setText(buildEmailBody(ex));
        sender.send(message);
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
