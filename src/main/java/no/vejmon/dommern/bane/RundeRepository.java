package no.vejmon.dommern.bane;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RundeRepository  extends JpaRepository<Runde, UUID> {

    List<Runde> findTopByKuskOrderByStartDesc(Kusk kusk);

}
