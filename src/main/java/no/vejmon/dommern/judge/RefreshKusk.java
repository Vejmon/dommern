package no.vejmon.dommern.judge;

import lombok.Getter;
import no.vejmon.dommern.bane.Kusk;
import org.springframework.context.ApplicationEvent;

@Getter
public class RefreshKusk extends ApplicationEvent {

    private final Kusk kusk;

    public RefreshKusk(Object source, Kusk kusk) {
        super(source);
        this.kusk = kusk;
    }

}
