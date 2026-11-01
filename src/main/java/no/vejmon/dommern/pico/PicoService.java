package no.vejmon.dommern.pico;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PicoService {

    @Value("${pico.picotool:}")
    private String picotoolBin;
    private List<String> picotoolForce;

    @PostConstruct
    public void init() {
        picotoolForce = new ArrayList<>(List.of(picotoolBin, "reboot", "-f"));
    }

    public void start() throws IOException, InterruptedException {
        ArrayList<String> command = new ArrayList<>(picotoolForce);
        command.addLast("-a"); // a for application mode
        ProcessBuilder pb = new ProcessBuilder(command);
        Process p = pb.start();
        int exit = p.waitFor();
        if (exit != 0) {
            throw new IOException("picotool stop failed: " + exit);
        }
    }

    public void stop() throws IOException, InterruptedException {
        ArrayList<String> command = new ArrayList<>(picotoolForce);
        command.addLast("-u"); // u for USB to BOOTSEL mode
        ProcessBuilder pb = new ProcessBuilder(command);
        Process p = pb.start();
        int exit = p.waitFor();
        if (exit != 0) {
            throw new IOException("picotool stop failed: " + exit);
        }
    }
}
