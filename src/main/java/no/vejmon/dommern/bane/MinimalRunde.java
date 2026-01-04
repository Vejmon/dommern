package no.vejmon.dommern.bane;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

import static no.vejmon.dommern.bane.BaneType.UTENFOR_BANEN;

@Getter
@Setter
@MappedSuperclass
@RequiredArgsConstructor
@NoArgsConstructor
public class MinimalRunde {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private Instant start = Instant.now();
    private Instant stop;
    @NonNull
    private BaneType baneType;

    @Transient
    private Long tid;

    public Long getTid(){
        if (stop == null) return start.toEpochMilli() - Instant.EPOCH.toEpochMilli();
        return stop.toEpochMilli() - start.toEpochMilli();
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
