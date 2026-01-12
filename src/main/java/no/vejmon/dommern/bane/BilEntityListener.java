package no.vejmon.dommern.bane;

import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import no.vejmon.dommern.config.BarcodeGen;
import no.vejmon.dommern.config.EmailConfig;
import org.springframework.data.rest.core.event.AbstractRepositoryEventListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static no.vejmon.dommern.config.BarcodeGen.toByteArrayResource;

@Slf4j
@Component
public class BilEntityListener extends AbstractRepositoryEventListener<Bil> {

    private final EmailConfig emailConfig;
    private final BarcodeGen barcodeGen;

    public BilEntityListener(EmailConfig emailConfig,
                             BarcodeGen barcodeGen) {
        this.emailConfig = emailConfig;
        this.barcodeGen = barcodeGen;
    }

    @Override
    protected void onAfterCreate(Bil bil) {
        String email = bil.getKusk().getEmail();
        if (email == null || email.isBlank() || bil.getId() == null) {
            log.info("Den nye bilen '{}' har ingen epost knyttet til kusken, eller bil id er null, sender ikke epost", bil.getName());
            return;
        }
        log.info("Ny bil '{}' er registrert for kusk '{}', sender epost til {}", bil.getId(), bil.getKusk().getName(), email);
        try {
            emailConfig.sendNewBilImage(bil, toByteArrayResource(barcodeGen.qrCode(bil.getId().toString())));
        } catch (MessagingException|IOException e) {
            log.error("Kunne ikke sende epost for bil id '{}'", bil.getId(), e);
        }
    }

}
