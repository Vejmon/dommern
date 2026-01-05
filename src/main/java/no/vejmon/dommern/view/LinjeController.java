package no.vejmon.dommern.view;


import no.vejmon.dommern.bane.Kusk;
import no.vejmon.dommern.judge.LinjeDommer;
import no.vejmon.dommern.judge.OnKuskerChangedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/linje")
public class LinjeController {

    private final LinjeDommer linjeDommer;
    private SseEmitter emitter;

    @Autowired
    public LinjeController(LinjeDommer linjeDommer) {
        this.linjeDommer = linjeDommer;
    }


    @GetMapping
    public List<Kusk> getActiveKusker() {
        return linjeDommer.getKusker();
    }

    @GetMapping(value = "/init", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public synchronized SseEmitter streamKusks() throws IOException {
        registerEmitter();
        emitToCurrent();
        return emitter;
    }

    public void emitToCurrent() throws IOException {
        if (emitter == null) return;
        emitter.send(SseEmitter.event().data(linjeDommer.getKusker()));
    }

    public void emitToCurrent(List<Kusk> kusker) throws IOException {
        if (emitter == null) return;
        emitter.send(SseEmitter.event().data(kusker));
    }

    private synchronized void registerEmitter() {
        // Close the previous emitter if it exists
        if (emitter != null) {
            emitter.complete();
        }
        emitter = new SseEmitter(Long.MAX_VALUE);
        emitter.onCompletion(() -> this.emitter = null);
        emitter.onTimeout(() -> this.emitter = null);
        emitter.onError(e -> this.emitter = null);
    }

    @EventListener
    @Async
    public void handleKuskerChangedEvent(OnKuskerChangedEvent event) throws IOException {
        if (event.getKusker() == null)
            emitToCurrent();
        else
            emitToCurrent(event.getKusker());
    }

}
