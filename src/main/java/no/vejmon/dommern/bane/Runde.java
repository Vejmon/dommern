package no.vejmon.dommern.bane;

import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.Map;

import static no.vejmon.dommern.bane.BaneType.*;

@Getter
@Setter
@Embeddable
public class Runde {
    private Instant start = Instant.now();
    private Instant end;
    private BaneType baneType;

    public Map<BaneType, Integer> baneMap = Map.of(
            BESTEFAR_BANEN, NativeKeyEvent.VC_H,
            FRODE_SPESIAL, NativeKeyEvent.VC_J,
            UTEN_NAVN, NativeKeyEvent.VC_K,
            KORTESTE_VEIEN, NativeKeyEvent.VC_L
    );
}