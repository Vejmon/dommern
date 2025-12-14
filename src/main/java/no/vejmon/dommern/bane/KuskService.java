package no.vejmon.dommern.bane;

import org.springframework.stereotype.Service;

@Service
public class KuskService {

    private final KuskRepository kuskRepository;

    public KuskService(KuskRepository kuskRepository) {
        this.kuskRepository = kuskRepository;
    }

    public Runde findLastRunde(Kusk kusk) {
        return kuskRepository.findTopByOrderByRunder_stopDesc();
    }

    public Kusk initKusk(String name, BaneType baneType){
        Kusk kusk =  kuskRepository.findByName(name).orElseGet(() -> new Kusk(name, baneType));
        return kuskRepository.save(kusk);
    }

    public Kusk saveKusk(Kusk kusk){
        return kuskRepository.save(kusk);
    }

    public Kusk findByName(String name){
        return kuskRepository.findByName(name).orElseThrow();
    }
}
