package no.vejmon.dommern.bane;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.List;
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


    public static BaneType hentBaneType(int keyCode, List<BaneType> baneList) {
        return baneList.stream()
                .filter(baneType -> baneType.getGpioPin() == keyCode)
                .findFirst()
                .orElse(UTENFOR_BANEN);
    }
}
