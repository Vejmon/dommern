package no.vejmon.dommern.lyttere;

import no.vejmon.dommern.bane.BaneType;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LydTest {

    private final Map<BaneType, Integer> baneLydMap =
            Map.of(BaneType.KORTESTE_VEIEN, 1,
            BaneType.FRODE_SPESIAL, 2,
            BaneType.UTEN_NAVN, 3,
            BaneType.BESTEFAR_BANEN, 4);


    @Test
    void hentLydNavnNotFormattedName() {
        Lyd lyd = new Lyd(BaneType.KORTESTE_VEIEN, LydType.RACE_START);

        String filnavn = lyd.hentLydNavn(baneLydMap);
        assertEquals("startlyd.wav", filnavn);
    }

    @Test
    void hentLydNavnFormattedName() {
        Lyd lyd = new Lyd(BaneType.FRODE_SPESIAL, LydType.DEFAULT);

        String filnavn = lyd.hentLydNavn(baneLydMap);
        assertEquals("bane2rundelyd.wav", filnavn);
    }

}
