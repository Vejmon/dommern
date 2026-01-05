package no.vejmon.dommern.lyttere;

import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import no.vejmon.dommern.judge.OnKuskerChangedEvent;
import org.springframework.context.ApplicationEventPublisher;

public class RaceEntityListener {

    private final ApplicationEventPublisher publisher;

    public RaceEntityListener(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @PostPersist
    @PostUpdate
    @PostRemove
    public void onChange(Object obj) {
        publisher.publishEvent(new OnKuskerChangedEvent(this, null));
    }


}
