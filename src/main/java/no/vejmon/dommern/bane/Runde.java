package no.vejmon.dommern.bane;

import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;
import java.util.Map;

import static no.vejmon.dommern.bane.BaneType.*;

@ToString
@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class Runde {
    private Instant start = Instant.now();
    private Instant end;
    private BaneType baneType;

    public Runde(BaneType baneType) {
        this.baneType = baneType;
    }

    private static Map<BaneType, Integer> baneMap = Map.of(
            BESTEFAR_BANEN, NativeKeyEvent.VC_H,
            FRODE_SPESIAL, NativeKeyEvent.VC_J,
            UTEN_NAVN, NativeKeyEvent.VC_K,
            KORTESTE_VEIEN, NativeKeyEvent.VC_L
    );

    public static BaneType hentBaneType(int keyCode) {
        return baneMap.entrySet()
                .stream()
                .filter(entry -> entry.getValue() == keyCode)
                .findFirst()
                .map(Map.Entry::getKey)
                .orElse(UTENFOR_BANEN);
    }

}