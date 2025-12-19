package no.vejmon.dommern.lyttere;

import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.DigitalInputConfigBuilder;
import com.pi4j.io.gpio.digital.DigitalState;
import com.pi4j.io.gpio.digital.PullResistance;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import no.vejmon.dommern.bane.BaneType;
import no.vejmon.dommern.bane.Runde;
import no.vejmon.dommern.judge.NyRundeEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

@Component
@Slf4j
public class GPIOLytter implements Lytter {

    private final ApplicationEventPublisher publisher;
    private final Map<BaneType, Integer> baneMap;
    private final Context pi4j;

    public GPIOLytter(ApplicationEventPublisher publisher,
                      Map<BaneType, Integer> baneMap,
                      Context pi4j) {
        this.publisher = publisher;
        this.baneMap = baneMap;
        this.pi4j = pi4j;
    }

    @PostConstruct
    void init(){
        baneMap.entrySet().forEach( baneMapEntry -> {
            DigitalInputConfigBuilder buttonConfig = DigitalInput.newConfigBuilder(pi4j)
                    .id(UUID.randomUUID().toString())
                    .name(baneMapEntry.getKey().name())
                    .address(baneMapEntry.getValue())
                    .pull(PullResistance.PULL_DOWN)
                    .debounce(3000L);
            DigitalInput button = pi4j.create(buttonConfig);
            log.info("Created button: {}", button.getName());

            button.addListener(event -> {
                if (event.state() == DigitalState.LOW){
                    Runde runde = new Runde(baneMapEntry.getKey());
                    publisher.publishEvent(new NyRundeEvent(this, runde));
                }
            });

        });
    }

    @PreDestroy
    public void teardown(){
        log.info("Tearing down GPIO Lytter: {}", pi4j.boardInfo().getBoardModel().getName());
        pi4j.shutdown();
    }

}
