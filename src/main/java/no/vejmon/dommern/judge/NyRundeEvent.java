package no.vejmon.dommern.judge;

import lombok.Getter;
import no.vejmon.dommern.bane.MinimalRunde;
import org.springframework.context.ApplicationEvent;

@Getter
public class NyRundeEvent extends ApplicationEvent {

    private final MinimalRunde runde;

    public NyRundeEvent(Object source, MinimalRunde runde) {
        super(source);
        this.runde = runde;
    }

}
