package no.vejmon.dommern.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "spring.pi4j.gpio")
@Getter
@Setter
public class GpioProperties {
private List<Integer> pins = List.of(4, 17, 24, 25);
}
