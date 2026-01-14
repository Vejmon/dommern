package no.vejmon.dommern.bane;

import jakarta.persistence.PostPersist;
import no.vejmon.dommern.judge.NyKuskEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KuskEntityListener {

    private final ApplicationEventPublisher publisher;

    public KuskEntityListener(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @PostPersist
    public void handleKuskAfterCreate(Kusk kusk) {
        if (kusk.getCurrentBane().isEnabled()){
            publisher.publishEvent(new NyKuskEvent(this, kusk));
        }
    }
}
