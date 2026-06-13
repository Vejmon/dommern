package no.vejmon.dommern.lyttere;

import lombok.extern.slf4j.Slf4j;
import no.vejmon.dommern.bane.BaneType;
import no.vejmon.dommern.bane.runde.MinimalRunde;
import no.vejmon.dommern.bane.runde.Runde;
import no.vejmon.dommern.judge.NyRundeEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;


@Profile("local")
@Component
@Slf4j
public class KeyboardLytter extends KeyAdapter implements Lytter {

    private final ApplicationEventPublisher publisher;
    private final List<BaneType> baneList;
    private JFrame frame;

    public KeyboardLytter(ApplicationEventPublisher publisher,
                          List<BaneType> baneList) {
        this.publisher = publisher;
        this.baneList = baneList;
        init();
    }

    public void init(){
        frame = new JFrame("Keyboard Listener");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setVisible(true);

        frame.addKeyListener(this);

        frame.setFocusable(true);
        frame.requestFocus();
    }

    @Override
    public void keyPressed(KeyEvent e){
        BaneType baneType = Runde.hentBaneType(e.getKeyCode(), baneList);
        if (baneType == BaneType.UTENFOR_BANEN) return;
        MinimalRunde runde = new MinimalRunde(baneType);
        publisher.publishEvent(new NyRundeEvent(this, runde));
        log.debug("Ny runde: {}", runde);

    }
}
