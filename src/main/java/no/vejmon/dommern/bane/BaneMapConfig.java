package no.vejmon.dommern.bane;

import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Map;

import static no.vejmon.dommern.bane.BaneType.*;

@Configuration
public class BaneMapConfig {

    @Bean
    @Profile("local")
    public Map<BaneType, Integer> baneMapLocal() {
        return Map.of(
                BESTEFAR_BANEN, NativeKeyEvent.VC_H,
                FRODE_SPESIAL, NativeKeyEvent.VC_J,
                UTEN_NAVN, NativeKeyEvent.VC_K,
                KORTESTE_VEIEN, NativeKeyEvent.VC_L
        );
    }

    @Bean
    @Profile("production")
    public Map<BaneType, Integer> baneMap() {
        return Map.of(
                BESTEFAR_BANEN, 19,
                FRODE_SPESIAL, 21,
                UTEN_NAVN, 24,
                KORTESTE_VEIEN, 26
        );
    }
}
