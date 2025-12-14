package no.vejmon.dommern.bane;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface KuskRepository extends JpaRepository<Kusk, UUID> {

    Runde findTopByOrderByRunder_stopDesc();

    Optional<Kusk> findByName(String name);
}
