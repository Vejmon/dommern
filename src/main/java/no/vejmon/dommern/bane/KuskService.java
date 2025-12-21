package no.vejmon.dommern.bane;

import no.vejmon.dommern.judge.NyKuskEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KuskService {

    private final KuskRepository kuskRepository;
    private final RundeRepository rundeRepository;
    private final ApplicationEventPublisher publisher;

    public KuskService(KuskRepository kuskRepository,
                       RundeRepository rundeRepository,
                       ApplicationEventPublisher publisher) {
        this.kuskRepository = kuskRepository;
        this.rundeRepository = rundeRepository;
        this.publisher = publisher;
    }

    public List<Runde> findLastRunde(Kusk kusk) {
        return rundeRepository.findTopByKuskOrderByStartDesc(kusk);
    }

    public Kusk initKusk(BaneType baneType){
        Kusk kusk =  kuskRepository.findByName(baneType.name()).orElseGet(() -> {
            Kusk initKusk = new Kusk(baneType.name(), baneType);
            Bil ukjentBil = new Bil(initKusk,"ukjent merke", "ukjent model");
            initKusk.setCurrentBil(ukjentBil);
            return initKusk;
        });
        return kuskRepository.save(kusk);
    }

    public void saveLaps(List<Runde> runde){
        rundeRepository.saveAll(runde);
    }

    public void replaceKusk(Kusk newKusk){
        publisher.publishEvent(new NyKuskEvent(this, newKusk));
    }

    public Kusk saveKusk(Kusk kusk){
        return kuskRepository.save(kusk);
    }

    public Kusk findByName(String name){
        return kuskRepository.findByName(name).orElseThrow();
    }
}
