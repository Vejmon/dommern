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
import no.vejmon.dommern.bane.MinimalRunde;
import no.vejmon.dommern.judge.NyRundeEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@Slf4j
public class GPIOLytter implements Lytter {

    private final ApplicationEventPublisher publisher;
    private final List<BaneType> baneList;
    private final Context pi4j;
    @Value("${spring.pi4j.gpio.pull:OFF}")
    private String pull;
    @Value("${spring.pi4j.gpio.debounce:4000000}")
    private Long debounce;

    public GPIOLytter(ApplicationEventPublisher publisher,
                      List<BaneType> baneList,
                      Context pi4j) {
        this.publisher = publisher;
        this.baneList = baneList;
        this.pi4j = pi4j;
    }

    @PostConstruct
    void init(){
        baneList.forEach( baneType -> {
            DigitalInputConfigBuilder buttonConfig = DigitalInput.newConfigBuilder(pi4j)
                    .id(UUID.randomUUID().toString())
                    .name(baneType.name())
                    .address(baneType.getGpioPin())
                    .pull(PullResistance.parse(pull))
                    .debounce(debounce);
            DigitalInput button = pi4j.create(buttonConfig);
            log.info("Created button: {}", button.getName());

            button.addListener(event -> {
                if (event.state() == DigitalState.LOW){
                    MinimalRunde runde = new MinimalRunde(baneType);
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
