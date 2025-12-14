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
import java.util.Optional;

@Service
public class LinjeDommer {

    private final List<Kusk> kusker = new ArrayList<>();

    private final KuskService kuskService;

    public LinjeDommer(KuskService kuskService) {
        this.kuskService = kuskService;
    }

    @PostConstruct
    public void init() {
        kusker.add(kuskService.initKusk("Korteste veien", BaneType.KORTESTE_VEIEN));
        kusker.add(kuskService.initKusk("Frode spesial", BaneType.FRODE_SPESIAL));
        kusker.add(kuskService.initKusk("Uten navn", BaneType.UTEN_NAVN));
        kusker.add(kuskService.initKusk("Bestefar banen", BaneType.BESTEFAR_BANEN));
    }

    @EventListener
    @Async
    public void handleNewRound(NyRundeEvent nyRundeEvent){
        Kusk kusk = kusker.stream().filter(k ->
                k.getCurrentBane() == nyRundeEvent.getRunde().getBaneType()
        ).findFirst().orElseThrow();

        Optional<Runde> lastRunde = kuskService.findLastRunde(kusk);
        if (lastRunde.isPresent()) {
            lastRunde.get().setStop(nyRundeEvent.getRunde().getStart());
            kusk.getRunder().add(nyRundeEvent.getRunde());
        }
        else kusk.getRunder().add(nyRundeEvent.getRunde());

        if (kusk.getRunder().isEmpty()) {
            kusk.getRunder().add(nyRundeEvent.getRunde());
        } else {
            Runde previousRunde = kusk.getRunder().getLast();
            previousRunde.setStop(nyRundeEvent.getRunde().getStart());
            kusk.getRunder().add(nyRundeEvent.getRunde());
        }

        kuskService.saveKusk(kusk);

    }

}
