package no.vejmon.dommern.bane;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@ToString(exclude = {"runder", "kusk"})
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
public class Bil {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToMany(mappedBy = "bil", orphanRemoval = true)
    private List<Runde> runder;

    @NonNull
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Kusk kusk;

    @NonNull
    private String name;
    @NonNull
    private String make;
    @NonNull
    private String model;

    private Float gPrCmTorque;
    private Float kRpm;

    private String basedMake;
    private String basedModel;

}
