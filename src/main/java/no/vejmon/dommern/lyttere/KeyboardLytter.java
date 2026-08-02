package no.vejmon.dommern.lyttere;

import lombok.extern.slf4j.Slf4j;
import no.vejmon.dommern.bane.BaneType;
import no.vejmon.dommern.bane.runde.MinimalRunde;
import no.vejmon.dommern.judge.NyRundeEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;


@Profile("local")
@Component
@Slf4j
public class KeyboardLytter implements Lytter {

    private final ApplicationEventPublisher publisher;
    private final List<BaneType> baneList;

    public KeyboardLytter(ApplicationEventPublisher publisher,
                          List<BaneType> baneList) {
        this.publisher = publisher;
        this.baneList = baneList;
        init();
    }

    public void init() {
        Thread inputThread = new Thread(() -> {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String line;
            try {
                while ((line = reader.readLine()) != null) {
                    if (line.equals("exit")) System.exit(0);
                    Map<String, BaneType> keyToBaneMap = Map.of(
                            "y", baneList.get(0),
                            "i", baneList.get(1),
                            "u", baneList.get(2),
                            "o", baneList.get(3)
                    );
                    for (Map.Entry<String, BaneType> entry : keyToBaneMap.entrySet()) {
                        if (line.equalsIgnoreCase(entry.getKey())) {
                            MinimalRunde runde = new MinimalRunde(entry.getValue());
                            if (entry.getValue() == BaneType.UTENFOR_BANEN) return;
                            publisher.publishEvent(new NyRundeEvent(this, runde));
                        }
                    }
                }
            } catch (IOException e) {
                System.err.println("Error reading input: " + e.getMessage());
            }
        });

        inputThread.start();

    }
}
