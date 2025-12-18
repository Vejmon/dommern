package no.vejmon.dommern.lyttere;

import jakarta.annotation.PostConstruct;
import no.vejmon.dommern.bane.BaneType;
import no.vejmon.dommern.bane.Kusk;
import no.vejmon.dommern.bane.KuskService;
import no.vejmon.dommern.bane.Runde;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LinjeDommer {

    private final List<Kusk> kusker = new ArrayList<>();

    private final KuskService kuskService;

    public LinjeDommer(KuskService kuskService) {
        this.kuskService = kuskService;
    }

    @PostConstruct
    public void init() {
        kusker.add(kuskService.initKusk(BaneType.KORTESTE_VEIEN));
        kusker.add(kuskService.initKusk(BaneType.FRODE_SPESIAL));
        kusker.add(kuskService.initKusk(BaneType.UTEN_NAVN));
        kusker.add(kuskService.initKusk(BaneType.BESTEFAR_BANEN));
    }

    @EventListener
    @Async
    public void handleNewRound(NyRundeEvent nyRundeEvent){
        Kusk kusk = kusker.stream().filter(k ->
                k.getCurrentBane() == nyRundeEvent.getRunde().getBaneType()
        ).findFirst().orElseThrow();

        List<Runde> laps = kuskService.findLastRunde(kusk);
        if (!laps.isEmpty()) {
            laps.getLast().setStop(nyRundeEvent.getRunde().getStart());
        }
        Runde lap = new Runde(nyRundeEvent.getRunde().getBaneType());
        lap.setKusk(kusk);
        laps.add(lap);
        kuskService.saveLaps(laps);

    }

}
