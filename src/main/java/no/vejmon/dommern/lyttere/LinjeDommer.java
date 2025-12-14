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
import java.util.Arrays;
import java.util.List;

@Service
public class LinjeDommer {

    public static Kusk kusk1 = new Kusk("Korteste veien", BaneType.KORTESTE_VEIEN);
    public static Kusk kusk2 = new Kusk("Frode spesial", BaneType.FRODE_SPESIAL);
    public static Kusk kusk3 = new Kusk("Uten navn", BaneType.UTEN_NAVN);
    public static Kusk kusk4 = new Kusk("Bestefar banen", BaneType.BESTEFAR_BANEN);
    public static List<Kusk> kusker = new ArrayList<>(Arrays.asList(
            kusk1, kusk2, kusk3, kusk4
    ));

    private final KuskService kuskService;

    public LinjeDommer(KuskService kuskService) {
        this.kuskService = kuskService;
    }

    @PostConstruct
    public void init() {
        for (Kusk kusk : kusker) {
            kusk = kuskService.initKusk(kusk.getName(), kusk.getCurrentBane());
        }

    }

    @EventListener
    @Async
    public void handleNewRound(NyRundeEvent nyRundeEvent){
        Kusk kusk = kusker.stream().filter(k ->
                k.getCurrentBane() == nyRundeEvent.getRunde().getBaneType()
        ).findFirst().orElseThrow();

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
