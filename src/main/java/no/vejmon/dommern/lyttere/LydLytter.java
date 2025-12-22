package no.vejmon.dommern.lyttere;

import javazoom.jl.player.Player;
import lombok.extern.slf4j.Slf4j;
import no.vejmon.dommern.bane.BaneType;
import no.vejmon.dommern.judge.NyLydEvent;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class LydLytter {

    private final Map<BaneType, Integer> baneMapLyd;

    public LydLytter(@Qualifier("baneMapLyd") Map<BaneType, Integer> baneMapLyd) {
        this.baneMapLyd = baneMapLyd;
    }

    @EventListener
    @Async
    public void handleLyd(NyLydEvent lydEvent){
        StringBuilder sb = new StringBuilder("/static/lyd/");
        if (lydEvent.getLyd().getLydType() == LydType.RACE_COUNTDOWN){
            return; // todo: handle RACE_COUNTDOWN lyd i loop med race start
        }
        List<LydType> baneLydTyper = List.of(LydType.RECORD, LydType.DEFAULT);
        Lyd lyd = lydEvent.getLyd();
        if (baneLydTyper.contains(lyd.getLydType()))
            sb.append(lyd.hentLydNavn(baneMapLyd));
        else sb.append(lyd.getLydType().getName());

        try (InputStream is = getClass().getResourceAsStream(sb.toString())) {
            if (is == null) {
                log.error("Sound file not found: {}", sb);
                return;
            }
            Player player = new Player(is);
            player.play();

        } catch (Exception e) {
            log.error("Failed to play sound {}", sb, e);
        }

    }
}
