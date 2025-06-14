package com.medibook.mainservice.tools.rabbitmq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.medibook.mainservice.data.client.IClientService;
import com.medibook.mainservice.data.client.dto.ClientCreateDTO;
import com.medibook.mainservice.data.doctor.IDoctorService;
import com.medibook.mainservice.data.doctor.dto.DoctorCreateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RabbitMQConsumer {

    private final IClientService clientService;
    private final IDoctorService doctorService;

    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(value = "keycloak-notification-client"),
                    exchange = @Exchange(
                            name = "keycloak-notification",
                            autoDelete = "true",
                            ignoreDeclarationExceptions = "true"),
                    key = "KK.EVENT.SUCCESS.REGISTER.client")
    )
    public void listenClient(String message) throws JsonProcessingException {
        System.out.println(message);

        ClientCreateDTO client = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .readValue(message, ClientCreateDTO.class);
        clientService.createClient(client);
    }

    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(value = "keycloak-notification-client"),
                    exchange = @Exchange(
                            name = "keycloak-notification",
                            autoDelete = "true",
                            ignoreDeclarationExceptions = "true"),
                    key = "KK.EVENT.SUCCESS.REGISTER.client")
    )
    public void listenDoctor(String message) throws JsonProcessingException {
        System.out.println(message);

        DoctorCreateDto doctor = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .readValue(message, DoctorCreateDto.class);
        doctorService.createDoctor(doctor);
    }
}
