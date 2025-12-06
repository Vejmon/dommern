package no.vejmon.dommern;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class DommernApplication {
    public static void main(String[] args) {
        SpringApplication.run(DommernApplication.class, args);
    }

}
