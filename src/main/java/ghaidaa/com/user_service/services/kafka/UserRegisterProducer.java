package ghaidaa.com.user_service.services.kafka;


import ghaidaa.com.user_service.dtos.events.UserRegisteredEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserRegisterProducer {

    private final KafkaTemplate<String, UserRegisteredEvent> kafkaTemplate;

    public void send(UserRegisteredEvent event) {

        Message<UserRegisteredEvent> message = MessageBuilder
                .withPayload(event)
                .setHeader(KafkaHeaders.TOPIC, "email-topic")
                .build();

        log.info("Sending message: {}", message);
        kafkaTemplate.send(message);

    }
}
