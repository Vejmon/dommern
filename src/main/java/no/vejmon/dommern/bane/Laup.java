package no.vejmon.dommern.bane;


import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
public class Laup {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToMany(mappedBy = "laup", fetch = FetchType.EAGER)
    @Size(max = 4)
    private List<Kusk> participants;

    @OneToOne
    private Runde fastestLap;

    private Instant timeLimit;


}
