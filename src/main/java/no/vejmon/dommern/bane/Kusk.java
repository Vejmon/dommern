package no.vejmon.dommern.bane;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ToString
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Kusk {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name =  "ukjent";

    private BaneType currentBane;

    @ElementCollection
    private List<Runde> runder = new ArrayList<>();

    public Kusk(String name, BaneType currentBane) {
        this.name = name;
        this.currentBane = currentBane;
    }


}