package com.abeatrizsc.proposalapp.service;

import com.abeatrizsc.proposalapp.entity.Proposal;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class RabbitNotificationService {
    private RabbitTemplate rabbitTemplate;

    public void notificate(Proposal proposal, String exchange){
        rabbitTemplate.convertAndSend(exchange, "", proposal);
    }
}
