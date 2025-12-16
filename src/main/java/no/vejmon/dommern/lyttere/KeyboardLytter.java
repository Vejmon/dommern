package no.vejmon.dommern.lyttere;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import no.vejmon.dommern.bane.BaneType;
import no.vejmon.dommern.bane.Runde;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;


@Profile("local")
@Component
@Slf4j
public class KeyboardLytter implements Lytter, NativeKeyListener {

    private final ApplicationEventPublisher publisher;

    public KeyboardLytter(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @PostConstruct
    public void init() throws NativeHookException {
        GlobalScreen.registerNativeHook();
        GlobalScreen.addNativeKeyListener(this);
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
        BaneType baneType = Runde.hentBaneType(e.getKeyCode());
        if (baneType == BaneType.UTENFOR_BANEN) return;
        Runde runde = new Runde(baneType);
        publisher.publishEvent(new NyRundeEvent(this, runde));
        log.debug("Ny runde: {}", runde);
    }
}

