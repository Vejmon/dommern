package no.vejmon.dommern.bane.bil;

import jakarta.persistence.PostPersist;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class BilEntityListener {

    @Value("#{ '${spring.ai.model.chat:}' == 'ollama' }")
    private Boolean ollamaEnabled;
    @Value("#{ T(org.springframework.util.StringUtils).hasText('${spring.mail.username:}') }")
    private Boolean emailEnabled;

    private final ApplicationEventPublisher publisher;

    public BilEntityListener(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @PostPersist
    protected void onAfterCreate(Bil bil) {
            if (emailEnabled){
                publisher.publishEvent(new BilRunEmailEvent(this, bil));
            }
            if (ollamaEnabled){
                publisher.publishEvent(new BilRunAiEvent(this, bil));
            }

    }

}
