package no.vejmon.dommern.lyttere;

import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.DigitalInputConfigBuilder;
import com.pi4j.io.gpio.digital.DigitalState;
import com.pi4j.io.gpio.digital.PullResistance;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import no.vejmon.dommern.bane.BaneType;
import no.vejmon.dommern.bane.Runde;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Map;

@Profile("production")
@Component
@Slf4j
public class GPIOLytter implements Lytter {

    private final ApplicationEventPublisher publisher;
    private static Map<BaneType, Integer> baneMap;
    private static Context pi4j;

    public GPIOLytter(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @PostConstruct
    void init(){
        pi4j = Pi4J.newAutoContext();

        baneMap.entrySet().forEach( baneMapEntry -> {
            DigitalInputConfigBuilder buttonConfig = DigitalInput.newConfigBuilder(pi4j)
                    .id("button")
                    .name(baneMapEntry.getKey().name())
                    .address(baneMapEntry.getValue())
                    .pull(PullResistance.PULL_DOWN)
                    .debounce(3000L);
            DigitalInput button = pi4j.create(buttonConfig);

            button.addListener(event -> {
                if (event.state() == DigitalState.LOW){
                    Runde runde = new Runde(baneMapEntry.getKey());
                    publisher.publishEvent(new NyRundeEvent(this, runde));

                }
            });

        });

    }

}
