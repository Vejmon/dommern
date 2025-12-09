package no.vejmon.dommern.runde;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FørerRepository extends JpaRepository<UUID, Fører> {


}
