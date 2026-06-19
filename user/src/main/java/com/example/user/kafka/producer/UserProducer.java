package com.example.user.kafka.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserProducer {
    private final KafkaTemplate<String, String> template;

    public UserProducer(KafkaTemplate<String, String> template){
        this.template = template;
    }

    public void publishUserCreated(String email){
        template.send("user-created", email);
    }
}
