package no.vejmon.dommern.judge;

import lombok.Getter;
import no.vejmon.dommern.bane.Runde;
import org.springframework.context.ApplicationEvent;

@Getter
public class NyRundeEvent extends ApplicationEvent {

    private final Runde runde;

    public NyRundeEvent(Object source, Runde runde) {
        super(source);
        this.runde = runde;
    }

}
