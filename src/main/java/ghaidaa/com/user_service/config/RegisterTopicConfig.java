package ghaidaa.com.user_service.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RegisterTopicConfig {

    @Bean
    public NewTopic emailTopic() {

        return new NewTopic("email-topic", 1, (short) 1);

    }
}
