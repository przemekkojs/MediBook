package com.medibook.notification.rabbitmq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.medibook.notification.mail.MailManager;
import com.medibook.notification.mail.MailMessageProvider;
import com.medibook.notification.rabbitmq.dto.ClientVisitConfirmation;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RabbitMQConsumer {
    private final MailManager mailManager;


    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(value = RabbitMQConfig.QUEUE_NAME),
                    exchange = @Exchange(name = RabbitMQConfig.EXCHANGE_NAME),
                    key = "NS.EVENT.CLIENT.VISIT")
    )
    public void clientVisitNotification(String message) throws JsonProcessingException {
        ClientVisitConfirmation confirmation = new ObjectMapper().readValue(message, ClientVisitConfirmation.class);

        String messageBody = MailMessageProvider
                .clientVisitConfirmation(confirmation.clientName(), confirmation.visitDate(),
                        confirmation.visitTime(), confirmation.doctorName());

        mailManager.sendEmail(confirmation.clientEmail(), "Visit Confirmation", messageBody);
    }

}
