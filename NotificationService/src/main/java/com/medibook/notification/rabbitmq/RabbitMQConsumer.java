package com.medibook.notification.rabbitmq;

import com.medibook.notification.mail.MailManager;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RabbitMQConsumer {
    private final MailManager mailManager;

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void listen(String message) {
        mailManager.sendEmail("Placeholder","Test",message);
        System.out.println("Received message: " + message);
    }

}
