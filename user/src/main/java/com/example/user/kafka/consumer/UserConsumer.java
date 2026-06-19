package com.example.user.kafka.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class UserConsumer {
    
    @KafkaListener(
        topics = "user-created",
        groupId = "user-service-group"
    )
    public void consume(String email){
        System.out.println("email received " + email);
    }
}
