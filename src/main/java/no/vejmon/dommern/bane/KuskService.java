package no.vejmon.dommern.bane;

import org.springframework.stereotype.Service;

@Service
public class KuskService {

    private final KuskRepository kuskRepository;

    public KuskService(KuskRepository kuskRepository) {
        this.kuskRepository = kuskRepository;
    }

    public Runde findLastRunde(Kusk kusk) {
        return kuskRepository.findTopByOrderByRunder_endDesc();
    }

    public Kusk findByName(String name){
        return kuskRepository.findByName(name);
    }
}
