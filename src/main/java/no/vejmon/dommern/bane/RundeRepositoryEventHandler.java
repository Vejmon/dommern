package no.vejmon.dommern.bane;

import jakarta.transaction.Transactional;
import no.vejmon.dommern.judge.RefreshKusk;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.rest.core.annotation.HandleAfterDelete;
import org.springframework.data.rest.core.annotation.HandleBeforeDelete;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

import java.util.Comparator;

@Component
@RepositoryEventHandler
public class RundeRepositoryEventHandler {
    private final KuskRepository kuskRepository;
    private final ApplicationEventPublisher eventPublisher;

    public RundeRepositoryEventHandler(KuskRepository kuskRepository,
                                       ApplicationEventPublisher eventPublisher) {
        this.kuskRepository = kuskRepository;
        this.eventPublisher = eventPublisher;
    }

    @HandleBeforeDelete
    @Transactional
    public void handleRundeDelete(Runde runde) {
        Kusk kusk = runde.getKusk();
        if (kusk != null && kusk.getPersonalBest() != null
                && kusk.getPersonalBest().getId().equals(runde.getId())) {
            kusk.getRunder().stream()
                    .filter(r ->
                            r.getTid() != null && !r.getId().equals(kusk.getPersonalBest().getId())
                    )
                    .min(Comparator.comparingLong(MinimalRunde::getTid))
                    .ifPresentOrElse(kusk::declarePersonalBest, () -> kusk.declarePersonalBest(null));
            kuskRepository.saveAndFlush(kusk);
        }
    }

    @HandleAfterDelete
    public void handleRundeAfterDelete(Runde runde) {
        eventPublisher.publishEvent(new RefreshKusk(this, runde.getKusk()));
    }

}
