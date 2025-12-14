package no.vejmon.dommern;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class DommernApplication {
    public static void main(String[] args) {
        SpringApplication.run(DommernApplication.class, args);
    }

}
