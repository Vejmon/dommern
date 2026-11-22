package no.vejmon.dommern.pico;

import com.fazecast.jSerialComm.SerialPort;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
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
        Process p = new ProcessBuilder(command)
                .inheritIO()
                .start();

        int exit = p.waitFor();
        if (exit != 0) {
            throw new IOException("picotool stop failed: " + exit);
        }
    }

    public void stop() throws IOException, InterruptedException {
        ArrayList<String> command = new ArrayList<>(picotoolForce);
        command.addLast("-u"); // u for USB to BOOTSEL mode
        Process p = new ProcessBuilder(command)
                .inheritIO()
                .start();
        int exit = p.waitFor();
        if (exit != 0) {
            throw new IOException("picotool stop failed: " + exit);
        }
    }

    public void listen(){
        SerialPort port = findPicoPort();
        port.setBaudRate(115200);          // Pico default
        port.setNumDataBits(8);
        port.setNumStopBits(SerialPort.ONE_STOP_BIT);
        port.setParity(SerialPort.NO_PARITY);
        port.openPort();

        // Read continuously
        byte[] buffer = new byte[1024];
        while (port.isOpen()) {
            int available = port.bytesAvailable();
            if (available > 0) {
                int read = port.readBytes(buffer, buffer.length);
                System.out.print(new String(buffer, 0, read));
            }
            try { Thread.sleep(10); } catch (InterruptedException ignored) {}
        }
        port.closePort();
    }

    public ClockSetResponse setClock() throws IOException, InterruptedException {
        long epoch = System.currentTimeMillis();
        ArrayList<String> picotoolSetClock = new ArrayList<>(List.of(
                picotoolBin, "config", "-s", "current_epoch_millis" , "%d".formatted(epoch)));
        ArrayList<String> command = new ArrayList<>(picotoolSetClock);
        command.addLast("-a"); // a for application mode

        Process p = new ProcessBuilder(command)
                .inheritIO()
                .start();

        int exit = p.waitFor();
        if (exit != 0) {
                throw new IOException("picotool stop failed: " + exit);
        }
        return new ClockSetResponse("clock_set", epoch);
    }

    SerialPort findPicoPort() {
        SerialPort[] ports = SerialPort.getCommPorts();
        try {
            int retries = 5;
            while (ports.length == 0 && retries-- > 0) {
                Thread.sleep(500); // give the OS time to register the port
                ports = SerialPort.getCommPorts();
            }
            for (SerialPort port : ports) {
                if ("pico".equalsIgnoreCase(port.getDescriptivePortName())) {
                    log.info("Found Pico: systemName: {}, descriptivePortName: {}", port.getSystemPortName(), port.getDescriptivePortName());
                    return port;
                }
            }
        }
        catch (InterruptedException e){
            throw new RuntimeException("No Pico found. len ports = %s".formatted(ports.length));
        }
        throw new RuntimeException("No Pico found, list of ports empty.");
    }

    public record ClockSetResponse(String status, Long currentmilis){}
}
