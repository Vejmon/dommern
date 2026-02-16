package no.vejmon.dommern.judge;

import jakarta.annotation.PostConstruct;
import lombok.AccessLevel;
import lombok.Getter;
import no.vejmon.dommern.bane.*;
import no.vejmon.dommern.lyttere.Lyd;
import no.vejmon.dommern.lyttere.LydType;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Service
public class LinjeDommer {

    private final List<Kusk> kusker = new ArrayList<>();

    @Getter(AccessLevel.NONE)
    private final KuskService kuskService;
    @Getter(AccessLevel.NONE)
    private final ApplicationEventPublisher publisher;

    private Laup laup = null;

    public LinjeDommer(KuskService kuskService,
                       ApplicationEventPublisher publisher) {
        this.kuskService = kuskService;
        this.publisher = publisher;
    }

    @PostConstruct
    public void init() {
        kuskService.placeAllKusksInDepo();
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
        boolean newPB = false;
        if (!laps.isEmpty()) {
            laps.getLast().setStop(runde.getStart());
            newPB = kusk.setPersonalBest(laps.getLast());
            kusk.setLatest(laps.getLast());
            kuskService.saveKusk(kusk);
        }
        Runde lap = new Runde(kusk, runde.getBaneType());
        laps.add(lap);
        kuskService.saveLaps(laps);
        publishLydEvent(newPB, runde.getBaneType());
        notifyKuskerChanged();

    }

    @EventListener
    @Async
    public void handleNewKusk(NyKuskEvent newKuskEvent){
        Kusk oldKusk = kusker.stream().filter(k ->
                k.getCurrentBane().equals(newKuskEvent.getKusk().getCurrentBane())).findFirst().orElseThrow();
        kusker.set(kusker.indexOf(oldKusk), newKuskEvent.getKusk());
        oldKusk.setCurrentBane(BaneType.I_DEPO);
        kuskService.saveKusk(oldKusk);
        notifyKuskerChanged();
    }

    @EventListener
    @Async
    public void handleNewLaup(NyLaupEvent newLaupEvent){
        this.laup = newLaupEvent.getLaup();
        for (Kusk kusk : kusker) {
            kusk.setLaup(laup);
        }
    }

    private void publishLydEvent(boolean newPB, BaneType baneType){
        LydType lydType = newPB ? LydType.RECORD : LydType.DEFAULT;
        NyLydEvent lydEvent = new NyLydEvent(this, new Lyd(baneType, lydType));
        publisher.publishEvent(lydEvent);
    }

    @EventListener
    @Async
    public void handleRefreshKusk(RefreshKusk refreshEvent){
        Kusk refreshedKusk = kuskService.findById(refreshEvent.getKusk().getId());
        Optional<Kusk> oldKusk = kusker.stream()
                .filter(kusk -> kusk.getId().equals(refreshEvent.getKusk().getId()))
                        .findFirst();
        if (oldKusk.isEmpty())
            return;
        kusker.set(getKusker().indexOf(oldKusk.get()), refreshedKusk);
        notifyKuskerChanged();
    }


    private void notifyKuskerChanged() {
    publisher.publishEvent(new OnKuskerChangedEvent(this, List.copyOf(kusker)));
    }

}
