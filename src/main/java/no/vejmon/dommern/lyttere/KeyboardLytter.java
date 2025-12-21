package no.vejmon.dommern.lyttere;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import no.vejmon.dommern.bane.BaneType;
import no.vejmon.dommern.bane.MinimalRunde;
import no.vejmon.dommern.bane.Runde;
import no.vejmon.dommern.judge.NyRundeEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Map;


@Profile("local")
@Component
@Slf4j
public class KeyboardLytter implements Lytter, NativeKeyListener {

    private final ApplicationEventPublisher publisher;
    private final Map<BaneType, Integer> baneMap;

    public KeyboardLytter(ApplicationEventPublisher publisher,
                          Map<BaneType, Integer> baneMap) {
        this.publisher = publisher;
        this.baneMap = baneMap;
    }

    @PostConstruct
    public void init() throws NativeHookException {
        GlobalScreen.registerNativeHook();
        GlobalScreen.addNativeKeyListener(this);
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
        BaneType baneType = Runde.hentBaneType(e.getKeyCode(), baneMap);
        if (baneType == BaneType.UTENFOR_BANEN) return;
        MinimalRunde runde = new MinimalRunde(baneType);
        publisher.publishEvent(new NyRundeEvent(this, runde));
        log.debug("Ny runde: {}", runde);
    }
}

