package no.vejmon.dommern.lyttere;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import jakarta.annotation.PostConstruct;
import no.vejmon.dommern.bane.BaneType;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class KeyboardLytter implements Lytter, NativeKeyListener{

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
        if (e.getKeyCode() == NativeKeyEvent.VC_H) {
            System.out.println("H pressed!");
        }
    }
}

