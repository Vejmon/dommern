package no.vejmon.dommern.config;

import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
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
    private List<Integer> pins = List.of(
            NativeKeyEvent.VC_Y,
            NativeKeyEvent.VC_I,
            NativeKeyEvent.VC_U,
            NativeKeyEvent.VC_O);
}
