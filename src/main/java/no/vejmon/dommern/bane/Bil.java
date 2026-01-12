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

    public String getInitials() {
        // If name only has one part, return first two letters
        String[] parts = name.trim().split("\\s+");
        StringBuilder initials = new StringBuilder();
        for (String part : parts) {
            if (!part.isEmpty()) {
                initials.append(Character.toUpperCase(part.charAt(0)));
            }
        }
        if (initials.length() == 1 && parts[0].length() >= 2) {
            initials.append(Character.toUpperCase(parts[0].charAt(1)));
        }

        return initials.toString();
    }

}
