package no.vejmon.dommern.judge;

import no.vejmon.dommern.bane.BaneType;
import no.vejmon.dommern.bane.Kusk;
import no.vejmon.dommern.bane.KuskService;
import no.vejmon.dommern.bane.MinimalRunde;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.util.NoSuchElementException;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LinjeDommerTest {

    @Mock
    private KuskService kuskService;
    @Mock
    private ApplicationEventPublisher publisher;

    private LinjeDommer linjeDommer;

    @BeforeEach
    public void init(){
        when(kuskService.initKusk(any(BaneType.class)))
                .thenAnswer(invocation -> {
                    BaneType baneType = invocation.getArgument(0, BaneType.class);
                    return new Kusk(baneType.name(), baneType);
                });

        linjeDommer = new LinjeDommer(kuskService, publisher);
        linjeDommer.init();

    }

    @Test
    public void kuskCantBeInvalidBane() {
        Kusk kusk = new Kusk("Test", BaneType.UTENFOR_BANEN);
        NyKuskEvent event = new NyKuskEvent(this, kusk);
        Assertions.assertThrows(NoSuchElementException.class, () -> linjeDommer.handleNewKusk(event));
    }

    @Test
    public void ableToReplaceKusk(){
        String kuskName = UUID.randomUUID().toString();
        Kusk kusk = new Kusk(kuskName, BaneType.KORTESTE_VEIEN);
        linjeDommer.handleNewKusk(new NyKuskEvent(this, kusk));
        linjeDommer.getKusker().stream().filter(k -> k.getName().equals(kuskName)).findFirst().orElseThrow();
    }

    @Test
    @Disabled
    public void newRoundNeedsValidBane(){

    }

    @Test
    @Disabled
    public void newRoundNeedsValidBil(){

    }

    @Test
    public void addNewRundeToDb() {
        MinimalRunde runde = new MinimalRunde(BaneType.KORTESTE_VEIEN);
        NyRundeEvent event = new NyRundeEvent(this, runde);
        Assertions.assertDoesNotThrow(() -> linjeDommer.handleNewRound(event));
        Mockito.verify(kuskService, times(1))
                .saveLaps(anyList());
    }
    
}
