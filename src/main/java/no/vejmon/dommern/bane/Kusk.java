package no.vejmon.dommern.bane;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ToString(exclude = "runder")
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Kusk {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true)
    private String name =  "ukjent";

    @NonNull
    private BaneType currentBane;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Bil currentBil;

    @OneToMany(mappedBy = "kusk", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Bil> bil;

    @ManyToOne(fetch = FetchType.LAZY)
    private Laup laup;

    @OneToMany(mappedBy = "kusk", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Runde> runder = new ArrayList<>();

    public Kusk(String name, @NonNull BaneType currentBane) {
        this.name = name;
        this.currentBane = currentBane;
    }

    @OneToOne
    private Runde personalBest;

    public void setPersonalBest(Runde personalBest) {
        if (personalBest == null || personalBest.getTid() == null) return;
        if (this.personalBest == null )
            this.personalBest = personalBest;
        else if (this.personalBest.getTid().toEpochMilli() > personalBest.getTid().toEpochMilli())
            this.personalBest = personalBest;
    }
}