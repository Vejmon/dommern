package no.vejmon.dommern.lyttere;

import lombok.Getter;
import no.vejmon.dommern.bane.Kusk;
import org.springframework.context.ApplicationEvent;

@Getter
public class NyKuskEvent extends ApplicationEvent {

    private final Kusk kusk;

    public NyKuskEvent(Object source, Kusk kusk) {
        super(source);
        this.kusk = kusk;
    }

}
