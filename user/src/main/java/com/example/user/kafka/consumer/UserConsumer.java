package com.example.user.kafka.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import com.example.user.dto.event.UserCreateEvent;

@Service
public class UserConsumer {
    
    @KafkaListener(
        topics = "user-created",
        groupId = "user-service-group"
    )
    public void consume(UserCreateEvent event){
        System.out.println("consumer working" + event.getEmail());

    }

}
