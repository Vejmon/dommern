package no.vejmon.dommern.judge;

import lombok.Getter;
import no.vejmon.dommern.bane.Kusk;
import org.springframework.context.ApplicationEvent;

import java.util.List;

@Getter
public class OnKuskerChangedEvent extends ApplicationEvent {

    private final List<Kusk> kusker;

    public OnKuskerChangedEvent(Object source, List<Kusk> kusker) {
        super(source);
        this.kusker = kusker;
    }
}
