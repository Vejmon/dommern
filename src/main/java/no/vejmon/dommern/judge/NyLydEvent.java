package no.vejmon.dommern.judge;

import lombok.Getter;
import no.vejmon.dommern.lyttere.Lyd;
import org.springframework.context.ApplicationEvent;

@Getter
public class NyLydEvent extends ApplicationEvent {

    private final Lyd lyd;

    public NyLydEvent(Object source, Lyd lyd) {
        super(source);
        this.lyd = lyd;
    }
}
