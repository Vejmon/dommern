package no.vejmon.dommern.judge;

import lombok.Getter;
import no.vejmon.dommern.bane.Laup;
import org.springframework.context.ApplicationEvent;

@Getter
public class NyLaupEvent extends ApplicationEvent {

    private final Laup laup;

    public NyLaupEvent(Object source, Laup laup) {
        super(source);
        this.laup = laup;
    }
}
