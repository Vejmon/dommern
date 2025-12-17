package no.vejmon.dommern.bane;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

import static no.vejmon.dommern.bane.BaneType.UTENFOR_BANEN;

@ToString
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Runde {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(optional = false)
    private Kusk kusk;
    private Instant start = Instant.now();
    private Instant stop;
    private BaneType baneType;

    @Transient
    private Instant tid;

    public Runde(BaneType baneType) {
        this.baneType = baneType;
    }

    public Instant getTid(){
        if (stop == null) return Instant.ofEpochMilli(start.toEpochMilli() - Instant.EPOCH.toEpochMilli());
        return Instant.ofEpochMilli(stop.toEpochMilli() - start.toEpochMilli());
    }


    public static BaneType hentBaneType(int keyCode, Map<BaneType, Integer> baneMap) {
        return baneMap.entrySet()
                .stream()
                .filter(entry -> entry.getValue() == keyCode)
                .findFirst()
                .map(Map.Entry::getKey)
                .orElse(UTENFOR_BANEN);
    }

}