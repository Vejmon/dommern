package no.vejmon.dommern.judge;

import jakarta.annotation.PostConstruct;
import lombok.AccessLevel;
import lombok.Getter;
import no.vejmon.dommern.bane.*;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Getter
@Service
public class LinjeDommer {

    private final List<Kusk> kusker = new ArrayList<>();

    @Getter(AccessLevel.NONE)
    private final KuskService kuskService;

    private Laup laup = null;

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
        MinimalRunde runde = nyRundeEvent.getRunde();
        Kusk kusk = kusker.stream().filter(k ->
                k.getCurrentBane() == nyRundeEvent.getRunde().getBaneType()
        ).findFirst().orElseThrow();

        List<Runde> laps = kuskService.findLastRunde(kusk);
        if (!laps.isEmpty()) {
            laps.getLast().setStop(runde.getStart());
            kusk.setPersonalBest(laps.getLast());
            kuskService.saveKusk(kusk);
        }
        Runde lap = new Runde(kusk, runde.getBaneType());
        laps.add(lap);
        kuskService.saveLaps(laps);
    }

    @EventListener
    @Async
    public void handleNewKusk(NyKuskEvent newKuskEvent){
        Kusk oldKusk = kusker.stream().filter(k ->
                k.getCurrentBane().equals(newKuskEvent.getKusk().getCurrentBane())).findFirst().orElseThrow();
        kusker.remove(oldKusk);
        oldKusk.setCurrentBane(BaneType.I_DEPO);
        kuskService.saveKusk(oldKusk);
        kusker.add(newKuskEvent.getKusk());

    }

    @EventListener
    @Async
    public void handleNewLaup(NyLaupEvent newLaupEvent){
        this.laup = newLaupEvent.getLaup();
        for (Kusk kusk : kusker) {
            kusk.setLaup(laup);
        }
    }

}
