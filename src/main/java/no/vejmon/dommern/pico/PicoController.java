package no.vejmon.dommern.pico;


import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Profile({"pico", "local"})
@Controller
@RequestMapping("/pico")
public class PicoController {

    public final PicoService picoService;

    public PicoController(PicoService picoService) {
        this.picoService = picoService;
    }

    @GetMapping("/stop")
    public @ResponseBody String stop() throws IOException, InterruptedException {
        picoService.stop();
        return "stopping";
    }

    @GetMapping("/start")
    public @ResponseBody String start() throws IOException, InterruptedException {
        picoService.start();
        return "starting";
    }

    @GetMapping("/clock")
    public @ResponseBody ResponseEntity<PicoService.ClockSetResponse> clock() throws IOException, InterruptedException {
        return ResponseEntity.ok(picoService.setClock());
    }
}
