package com.medibook.mainservice.tools.rabbitmq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.medibook.mainservice.tools.rabbitmq.dto.ClientVisitConfirmation;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQService {

    @Autowired
    private RabbitTemplate rabbitTemplate;


    public void sendMessage(String message) {
        sendMessage("routing.key", message);
    }

    public void sendClientVisitNotificationMessage(ClientVisitConfirmation confirmation){
        try {
            sendMessage("NS.EVENT.CLIENT.VISIT",
                    new ObjectMapper().writeValueAsString(confirmation));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendMessage(String routingKey, String message) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, routingKey, message);
    }
}
