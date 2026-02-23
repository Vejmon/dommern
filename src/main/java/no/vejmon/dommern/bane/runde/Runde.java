package no.vejmon.dommern.bane.runde;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import no.vejmon.dommern.bane.BaneType;
import no.vejmon.dommern.bane.bil.Bil;
import no.vejmon.dommern.bane.kusk.Kusk;
import no.vejmon.dommern.bane.laup.Laup;

@ToString
@RequiredArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(indexes = {
        @Index(columnList = "kusk_id, start"),
        @Index(columnList = "kusk_id, stop")
})
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