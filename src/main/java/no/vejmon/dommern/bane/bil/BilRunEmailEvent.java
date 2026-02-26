package no.vejmon.dommern.bane.bil;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class BilRunEmailEvent extends ApplicationEvent {

    private final Bil bil;

    public BilRunEmailEvent(Object source, Bil bil) {
        super(source);
        this.bil = bil;
    }

}
