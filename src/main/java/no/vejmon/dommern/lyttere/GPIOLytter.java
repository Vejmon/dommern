package no.vejmon.dommern.lyttere;

import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("production")
@Component
@Slf4j
public class GPIOLytter implements Lytter {

    private final ApplicationEventPublisher publisher;
    private static Context pi4j;

    public GPIOLytter(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @PostConstruct
    void init(){
        pi4j = Pi4J.newAutoContext();


        
    }

}
