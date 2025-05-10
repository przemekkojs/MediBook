package com.medibook.mainservice.rabbitmq;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQConsumer {

    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(value = "keycloak-notification-client"),
                    exchange = @Exchange(
                            name = "keycloak-notification",
                            autoDelete = "true",
                            ignoreDeclarationExceptions = "true"),
                    key = "KK.EVENT.client.SUCCESS.account-console.REGISTER")
    )
    public void listenClient(String message) {
        System.out.println("Received message: " + message);
    }

    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(value = "keycloak-notification-client"),
                    exchange = @Exchange(
                            name = "keycloak-notification",
                            autoDelete = "true",
                            ignoreDeclarationExceptions = "true"),
                    key = "KK.EVENT.doctor.SUCCESS.account-console.REGISTER")
    )
    public void listenDoctor(String message) {
        System.out.println("Received message: " + message);
    }
}
