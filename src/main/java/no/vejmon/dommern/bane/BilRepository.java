package no.vejmon.dommern.bane;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BilRepository extends JpaRepository<Bil, UUID> {

}
