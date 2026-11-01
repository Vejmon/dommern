package no.vejmon.dommern.lyttere;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("pico") // used when run on a computer in conjunction with a raspberry pico board
public class PicoUSBLytter implements Lytter {

}
