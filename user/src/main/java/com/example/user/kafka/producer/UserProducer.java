package com.example.user.kafka.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.example.user.dto.event.UserCreateEvent;

@Service
public class UserProducer {
    private final KafkaTemplate<String, UserCreateEvent> template;

    @Autowired
    public UserProducer(KafkaTemplate<String, UserCreateEvent> kafkaTemplate){
        this.template = kafkaTemplate;
    }

    public void publishUserCreated(UserCreateEvent event){
        template.send("user-created", event);

        
    }

}
