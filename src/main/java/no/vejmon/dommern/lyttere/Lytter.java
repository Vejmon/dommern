package no.vejmon.dommern.lyttere;

import no.vejmon.dommern.bane.BaneType;

import java.time.Instant;

public interface Lytter {
    Instant lytt(BaneType baneType);
}
