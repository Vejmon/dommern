package no.vejmon.dommern.bane;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import no.vejmon.dommern.lyttere.RaceEntityListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ToString(exclude = {"runder", "bil", "laup"})
@NoArgsConstructor
@Getter
@Setter
@Entity
@EntityListeners({RaceEntityListener.class})
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

    @OneToMany(mappedBy = "kusk", fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonIgnore
    private List<Bil> bil;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Laup laup;

    @OneToMany(mappedBy = "kusk", orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Runde> runder = new ArrayList<>();

    public Kusk(String name, @NonNull BaneType currentBane) {
        this.name = name;
        this.currentBane = currentBane;
    }

    @OneToOne
    private Runde personalBest;

    public boolean setPersonalBest(Runde personalBest) {
        if (personalBest == null || personalBest.getTid() == null) return false;
        if (this.personalBest == null || (this.personalBest.getTid() > personalBest.getTid())){
            this.personalBest = personalBest;
            return true;
        }
        return false;
    }
}