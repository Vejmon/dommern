package no.vejmon.dommern.pico;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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

    public void start() throws IOException {
        ArrayList<String> command = new ArrayList<>(picotoolForce);
        command.addLast("-a");

        ProcessBuilder pb = new ProcessBuilder(command);
        pb.redirectErrorStream(false); // Merge stderr into stdout
        Process process = pb.start();

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        }

        try {
            int exitCode = process.waitFor();
            System.out.println("Exit code: " + exitCode);
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void stop() throws IOException {

        ArrayList<String> command = new ArrayList<>(picotoolForce);
        command.addLast("-u");

        ProcessBuilder pb = new ProcessBuilder(command);
        pb.redirectErrorStream(true); // Merge stderr into stdout
        Process process = pb.start();

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        }

        try {
            int exitCode = process.waitFor();
            System.out.println("Exit code: " + exitCode);
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
