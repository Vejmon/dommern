package no.vejmon.dommern.config;

import lombok.extern.slf4j.Slf4j;
import no.vejmon.dommern.bane.BaneType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@Slf4j
public class BaneMapConfig {


    private final GpioProperties gpioProperties;

    public BaneMapConfig(GpioProperties gpioProperties) {
        this.gpioProperties = gpioProperties;
    }

    @Bean
    public List<BaneType> baneList() {
        List<BaneType> baner = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            BaneType bane = BaneType.values()[i];
            bane.setGpioPin(gpioProperties.getPins().get(i));
            bane.setSoundNumber(i + 1);
            baner.add(bane);
        }
        return baner;
    }

}
