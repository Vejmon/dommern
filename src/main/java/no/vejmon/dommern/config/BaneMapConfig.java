package no.vejmon.dommern.config;

import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import lombok.extern.slf4j.Slf4j;
import no.vejmon.dommern.bane.BaneType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import java.util.Map;

import static no.vejmon.dommern.bane.BaneType.*;

@Configuration
@Slf4j
public class BaneMapConfig {

    @Bean
    @Primary
    @Profile("local")
    public Map<BaneType, Integer> baneMapLocal() {
        log.info("Creating local keyboard baneMap");
        return Map.of(
                BESTEFAR_BANEN, NativeKeyEvent.VC_H,
                FRODE_SPESIAL, NativeKeyEvent.VC_J,
                UTEN_NAVN, NativeKeyEvent.VC_K,
                KORTESTE_VEIEN, NativeKeyEvent.VC_L
        );
    }

    @Bean(value = "baneMapLyd")
    public Map<BaneType, Integer> baneMapLyd(){
        log.info("creating lyd banemap");
        return Map.of(
                KORTESTE_VEIEN, 1,
                FRODE_SPESIAL, 2,
                UTEN_NAVN, 3,
                BESTEFAR_BANEN, 4
        );
    }


    @Bean
    @Primary
    @Profile("production")
    public Map<BaneType, Integer> baneMap() {
        log.info("Creating production gpio baneMap");
        return Map.of(
                BESTEFAR_BANEN, 19,
                FRODE_SPESIAL, 21,
                UTEN_NAVN, 24,
                KORTESTE_VEIEN, 26
        );
    }

}
