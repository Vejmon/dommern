package no.vejmon.dommern.runde;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@Embeddable
public class Runde {

    private Instant start;
    private Instant end;
    private BaneType baneType;

}
