package no.vejmon.dommern.bane;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
public class Kusk {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String navn;

    @ElementCollection
    private List<Runde> runder = new ArrayList<>();


}