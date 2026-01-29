package no.vejmon.dommern.lyttere;

import lombok.extern.slf4j.Slf4j;
import no.vejmon.dommern.bane.BaneType;
import no.vejmon.dommern.judge.NyLydEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import java.io.InputStream;
import java.util.List;

@Slf4j
@Component
public class LydLytter {

    private final List<BaneType> baneList;

    public LydLytter(List<BaneType> baneList) {
        this.baneList = baneList;
    }

    @EventListener
    @Async
    public void handleLyd(NyLydEvent lydEvent){
        Lyd lyd = lydEvent.getLyd();

        if (lyd.getLydType() == LydType.RACE_COUNTDOWN){
            playCountDown(lyd);
            return;
        }

        String lydpath = buildResourcePath(lyd);
        playSound(lydpath);

    }

    private String buildResourcePath(Lyd lyd) {
        List<LydType> baneLydTyper =
                List.of(LydType.RECORD, LydType.DEFAULT);

        if (baneLydTyper.contains(lyd.getLydType())) {
            return "/public/lyd/" + lyd.hentLydNavn(baneList);
        }

        return "/public/lyd/" + lyd.getLydType().getName();
    }

    private void playSound(String lydPath) {
        try (InputStream is = getClass().getResourceAsStream(lydPath)) {
            if (is == null) {
                log.error("Sound file not found: {}", lydPath);
                return;
            }

            Clip clip = AudioSystem.getClip();
            clip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    clip.close();
                }
            });

            clip.open(AudioSystem.getAudioInputStream(is));
            clip.start();

        } catch (Exception e) {
            log.error("Failed to play sound {}", lydPath, e);
        }
    }
    private void playCountDown(Lyd lyd)  {
        for (int i = 0; i < 5; i++){
            String lydPath = lyd.getLydType().getName().formatted(i);
            playSound(lydPath);
            try {
                wait(1500);
            } catch (InterruptedException e) {
                log.error("Error while waiting while playing countdown", e);
            }
        }
    }
}
