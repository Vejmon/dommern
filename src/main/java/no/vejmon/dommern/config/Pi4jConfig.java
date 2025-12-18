package no.vejmon.dommern.config;

import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import com.pi4j.plugin.gpiod.provider.gpio.digital.GpioDDigitalInputProvider;
import com.pi4j.plugin.mock.provider.gpio.digital.MockDigitalInputProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class Pi4jConfig {


    @Bean
    @Profile("production")
    public Context pi4j(){
        return Pi4J.newContextBuilder()
                .add(GpioDDigitalInputProvider.newInstance())
                .build();
    }

    @Bean
    @Profile({"test", "local"})
    public Context mockedPi4j(){
        return Pi4J.newContextBuilder()
                .add(MockDigitalInputProvider.newInstance())
                .build();
    }

}
