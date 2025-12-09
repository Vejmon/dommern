package no.vejmon.dommern.runde;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Embeddable
public class Runde {
    private Instant start;
    private Instant end;
    private BaneType baneType;
}