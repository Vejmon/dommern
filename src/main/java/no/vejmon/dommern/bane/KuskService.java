package no.vejmon.dommern.bane;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KuskService {

    private final KuskRepository kuskRepository;
    private final RundeRepository rundeRepository;

    public KuskService(KuskRepository kuskRepository,
                       RundeRepository rundeRepository) {
        this.kuskRepository = kuskRepository;
        this.rundeRepository = rundeRepository;
    }

    public List<Runde> findLastRunde(Kusk kusk) {
        return rundeRepository.findTopByKuskOrderByStartDesc(kusk);
    }

    public Kusk initKusk(String name, BaneType baneType){
        Kusk kusk =  kuskRepository.findByName(name).orElseGet(() -> new Kusk(name, baneType));
        return kuskRepository.save(kusk);
    }

    public void saveLaps(List<Runde> runde){
        rundeRepository.saveAll(runde);
    }


    public Kusk saveKusk(Kusk kusk){
        return kuskRepository.save(kusk);
    }

    public Kusk findByName(String name){
        return kuskRepository.findByName(name).orElseThrow();
    }
}
