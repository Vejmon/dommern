package no.vejmon.dommern.lyttere;

import no.vejmon.dommern.bane.BaneType;
import no.vejmon.dommern.config.BaneMapConfig;
import no.vejmon.dommern.config.GpioProperties;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = {BaneMapConfig.class, GpioProperties.class})
public class LydTest {

    @Autowired
    private List<BaneType> baneList;



    @Test
    void hentLydNavnNotFormattedName() {
        Lyd lyd = new Lyd(BaneType.KORTESTE_VEIEN, LydType.RACE_START);

        String filnavn = lyd.hentLydNavn(baneList);
        assertEquals("startlyd.wav", filnavn);
    }

    @Test
    void hentLydNavnFormattedName() {
        Lyd lyd = new Lyd(BaneType.FRODE_SPESIAL, LydType.DEFAULT);

        String filnavn = lyd.hentLydNavn(baneList);
        assertEquals("bane2rundelyd.wav", filnavn);
    }

}
