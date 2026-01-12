package no.vejmon.dommern.bane;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
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

    @NotEmpty
    private String name;
    private String make;
    private String model;

    private Float gPrCmTorque;
    private Float kRpm;

    private String basedMake;
    private String basedModel;

    public String getInitials() {

        String[] parts = name.trim().split("\\s+");
        StringBuilder initials = new StringBuilder();
        for (String part : parts) {
            if (!part.isEmpty()) {
                initials.append(Character.toUpperCase(part.charAt(0)));
            }
        }
        if (initials.length() == 1 && parts[0].length() >= 3) {
            initials.append(Character.toLowerCase(parts[0].charAt(1)));
            initials.append(Character.toLowerCase(parts[0].charAt(2)));
        }

        return initials.toString();
    }

}
