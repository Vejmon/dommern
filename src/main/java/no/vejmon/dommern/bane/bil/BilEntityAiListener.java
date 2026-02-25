package no.vejmon.dommern.bane.bil;

import jakarta.persistence.PostPersist;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@ConditionalOnExpression("'${spring.ai.model.chat:}' == 'ollama'")
public class BilEntityAiListener {


    private final ChatClient chatClient;

    public BilEntityAiListener(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @PostPersist
    protected void onAfterCreate(Bil bil) {
            log.info("Ny bil '{}' er registrert kjører Ai agenter ", bil.getName());
            String response = chatClient.prompt()
                    .system("You are a helpful assistant for the Dommern slot-car racing game. Your task is to provide a fun and engaging message whenever a new slot-car is registered. The message should include the name of the car, the name of the driver, and any interesting details about the car or driver that you can infer from the information provided. Keep the tone light-hearted and entertaining.")
                    .user("A new slot-car has been registered with the following details: " + bil)
                    .call()
                    .content();
            log.info("AI response for new car '{}': {}", bil.getName(), response);

    }

}
