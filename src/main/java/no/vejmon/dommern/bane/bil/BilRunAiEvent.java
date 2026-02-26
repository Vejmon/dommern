package no.vejmon.dommern.bane.bil;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class BilRunAiEvent extends ApplicationEvent {

    private final Bil bil;

    public BilRunAiEvent(Object source, Bil bil) {
        super(source);
        this.bil = bil;
    }

}
