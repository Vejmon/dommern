package no.vejmon.dommern.bane;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RundeRepository  extends JpaRepository<Runde, UUID> {

    @RestResource(exported = false)
    List<Runde> findTopByKuskOrderByStartDesc(Kusk kusk);

    @RestResource(rel = "runder_by_kusk")
    Page<Runde> findByKusk_IdAndStopIsNotNullOrderByStartDesc(UUID kuskId, Pageable pageable);

}
