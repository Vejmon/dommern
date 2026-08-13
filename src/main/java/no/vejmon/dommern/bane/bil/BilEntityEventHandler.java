package no.vejmon.dommern.bane.bil;

import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import no.vejmon.dommern.config.BarcodeGen;
import no.vejmon.dommern.config.EmailConfig;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static no.vejmon.dommern.config.BarcodeGen.toByteArrayResource;

@Slf4j
@Component
public class BilEntityEventHandler {

    private final EmailConfig emailConfig;
    private final BarcodeGen barcodeGen;
    private final ChatClient chatClient;

    public BilEntityEventHandler(EmailConfig emailConfig,
                                 BarcodeGen barcodeGen,
                                 ChatClient.Builder chatClientBuilder) {
        this.emailConfig = emailConfig;
        this.barcodeGen = barcodeGen;
        this.chatClient = chatClientBuilder.build();
    }

    @EventListener
    @Async
    protected void onAiEvent(BilRunAiEvent aiEvent) {
        Bil bil = aiEvent.getBil();
        log.info("Ny bil '{}' er registrert kjører Ai agenter ", bil.getName());
        String response = chatClient.prompt()
                .system("You are a helpful assistant for the Dommern slot-car racing game. Your task is to provide a fun and engaging message whenever a new slot-car is registered. The message should include the name of the car, the name of the driver, and any interesting details about the car or driver that you can infer from the information provided. Keep the tone light-hearted and entertaining.")
                .user("A new slot-car has been registered with the following details: " + bil)
                .call()
                .content();
        log.info("AI response for new car '{}': {}", bil.getName(), response);
    }

    @EventListener
    @Async
    protected void onEmailEvent(BilRunEmailEvent emailEvent) {
        Bil bil = emailEvent.getBil();
        String email = bil.getKusk().getEmail();
        if (email == null || email.isBlank() || bil.getId() == null) {
            log.info("Den nye bilen '{}' har ingen epost knyttet til kusken, eller bil id er null, sender ikke epost", bil.getName());
            return;
        }
        log.info("Ny bil '{}' er registrert for kusk '{}', sender epost til {}", bil.getId(), bil.getKusk().getName(), email);
        try {
            emailConfig.sendNewBilImage(bil, toByteArrayResource(barcodeGen.qrCode(bil.getId().toString(), bil.getInitials())));
        } catch (MessagingException|IOException e) {
            log.error("Kunne ikke sende epost for bil id '{}'", bil.getId(), e);
        }
    }

}
