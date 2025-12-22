package no.vejmon.dommern.lyttere;

import no.vejmon.dommern.bane.BaneType;
import no.vejmon.dommern.judge.NyLydEvent;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class LydLytter {

    private final Map<BaneType, Integer> baneMapLyd;

    public LydLytter(@Qualifier("baneMapLyd") Map<BaneType, Integer> baneMapLyd) {
        this.baneMapLyd = baneMapLyd;
    }

    @EventListener
    @Async
    public void handleLyd(NyLydEvent lydEvent){
        

    }
}
