package no.vejmon.dommern.lyttere;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import no.vejmon.dommern.bane.BaneType;
import no.vejmon.dommern.bane.Runde;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@Slf4j
public class KeyboardLytter implements Lytter, NativeKeyListener {

    @PostConstruct
    public void init() throws NativeHookException {
        GlobalScreen.registerNativeHook();
        GlobalScreen.addNativeKeyListener(this);
    }

    @Override
    public Instant lytt(BaneType baneType) {
        return Instant.now();
    }


    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
        BaneType baneType = Runde.hentBaneType(e.getKeyCode());
        Runde runde = new Runde(baneType);
        log.debug("Ny runde: {}", runde);
    }
}

