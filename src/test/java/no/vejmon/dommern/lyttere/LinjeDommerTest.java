package no.vejmon.dommern.lyttere;

import no.vejmon.dommern.bane.BaneType;
import no.vejmon.dommern.bane.Kusk;
import no.vejmon.dommern.bane.KuskService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LinjeDommerTest {

    @Mock
    private KuskService kuskService;

    private LinjeDommer linjeDommer;

    @BeforeEach
    public void init(){
        when(kuskService.initKusk(any(BaneType.class)))
                .thenAnswer(invocation -> {
                    BaneType baneType = invocation.getArgument(0, BaneType.class);
                    return new Kusk(baneType.name(), baneType);
                });

        linjeDommer = new LinjeDommer(kuskService);
        linjeDommer.init();

    }

    @Test
    public void kuskCantBeInvalidBane() {
        Kusk kusk = new Kusk("Test", BaneType.UTENFOR_BANEN);
        NyKuskEvent event = new NyKuskEvent(this, kusk);
        Assertions.assertThrows(NoSuchElementException.class, () -> linjeDommer.newKusk(event));
    }

    @Test
    public void ableToReplaceKusk(){
        String kuskName = UUID.randomUUID().toString();
        Kusk kusk = new Kusk(kuskName, BaneType.KORTESTE_VEIEN);
        linjeDommer.newKusk(new NyKuskEvent(this, kusk));
        linjeDommer.getKusker().stream().filter(k -> k.getName().equals(kuskName)).findFirst().orElseThrow();
    }
    
}
