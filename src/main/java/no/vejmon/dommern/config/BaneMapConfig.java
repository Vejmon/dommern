package no.vejmon.dommern.config;

import lombok.extern.slf4j.Slf4j;
import no.vejmon.dommern.bane.BaneType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.Map;

import static no.vejmon.dommern.bane.BaneType.*;

@Configuration
@Slf4j
public class BaneMapConfig {


    private final GpioProperties gpioProperties;

    public BaneMapConfig(GpioProperties gpioProperties) {
        this.gpioProperties = gpioProperties;
    }

    @Bean
    @Primary
    public Map<BaneType, Integer> baneMapLocal() {
        return Map.of(
                KORTESTE_VEIEN, gpioProperties.getPins().get(0),
                FRODE_SPESIAL, gpioProperties.getPins().get(1),
                UTEN_NAVN, gpioProperties.getPins().get(2),
                BESTEFAR_BANEN, gpioProperties.getPins().get(3)
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

}
