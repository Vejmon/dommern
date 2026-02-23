package no.vejmon.dommern.bane.kusk;


import no.vejmon.dommern.bane.BaneType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface KuskRepository extends JpaRepository<Kusk, UUID> {

    @RestResource(exported = false)
    Optional<Kusk> findByName(String name);

    @RestResource(exported = false)
    Optional<Kusk> findByCurrentBane(BaneType baneType);

}
