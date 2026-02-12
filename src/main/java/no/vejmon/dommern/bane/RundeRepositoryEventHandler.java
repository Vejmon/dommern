package no.vejmon.dommern.bane;

import org.springframework.data.rest.core.annotation.HandleBeforeDelete;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

@Component
@RepositoryEventHandler
public class RundeRepositoryEventHandler {
    private final KuskRepository kuskRepository;

    public RundeRepositoryEventHandler(KuskRepository kuskRepository) {
        this.kuskRepository = kuskRepository;
    }

    @HandleBeforeDelete
    public void handleRundeDelete(Runde runde) {
        Kusk kusk = runde.getKusk();
        if (kusk != null && kusk.getPersonalBest() != null
                && kusk.getPersonalBest().getId().equals(runde.getId())) {
            kusk.getRunder().stream()
                    .filter(r -> r.getTid() != null && r.getId() != kusk.getPersonalBest().getId())
                    .min((r1, r2) -> Long.compare(r1.getTid(), r2.getTid()))
                    .ifPresent(kusk::setPersonalBest);
            kuskRepository.save(kusk);
        }
    }

}
