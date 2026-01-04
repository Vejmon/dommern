package no.vejmon.dommern.bane;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JsonIgnore
    private Kusk kusk;
    @NonNull
    @ManyToOne(fetch = FetchType.EAGER)
    private Bil bil;
    @ManyToOne
    private Laup laup;


    public Runde(@NonNull Kusk kusk, @NonNull BaneType baneType) {
        super(baneType);
        this.kusk = kusk;
    }
}