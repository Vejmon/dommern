package no.vejmon.dommern.bane;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;

@ToString
@RequiredArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Runde extends MinimalRunde {

    @NonNull
    @ManyToOne(optional = false)
    private Kusk kusk;
    @NonNull
    @ManyToOne(optional = false)
    private Bil bil;
    @ManyToOne(optional = true)
    private Laup laup;


    public Runde(@NonNull Kusk kusk, @NonNull Bil currentBil, @NonNull BaneType baneType) {
        super(baneType);
        this.kusk = kusk;
        this.bil = currentBil;
    }
}