package com.client;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.jboss.logging.Logger;
import org.keycloak.events.Event;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.EventListenerTransaction;
import org.keycloak.events.EventType;
import org.keycloak.events.admin.AdminEvent;
import org.keycloak.models.KeycloakSession;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.AMQP.BasicProperties.Builder;
import com.rabbitmq.client.Channel;

public class ClientLoginListener implements EventListenerProvider {

    private static final Logger log = Logger.getLogger(ClientLoginListener.class);

    private final RabbitmqConfig cfg;
    private final Channel channel;

    private final KeycloakSession session;

    private final EventListenerTransaction tx = new EventListenerTransaction(null,this::publishEvent);

    public ClientLoginListener(Channel channel, KeycloakSession session, RabbitmqConfig cfg) {
        this.cfg = cfg;
        this.channel = channel;
        this.session = session;
        session.getTransactionManager().enlistAfterCompletion(tx);
    }

    @Override
    public void close() {

    }

    @Override
    public void onEvent(Event event) {
        tx.addEvent(event.clone());
    }

    @Override
    public void onEvent(AdminEvent adminEvent, boolean includeRepresentation) {
        tx.addAdminEvent(adminEvent, includeRepresentation);
    }

    private void publishEvent(Event event) {
        if(event.getType()== EventType.REGISTER){
            ClientRegisterNotification msg = ClientRegisterNotification.create(event);
            String routingKey = RabbitmqConfig.calculateRoutingKey(event, session);
            String messageString = RabbitmqConfig.writeAsJson(msg, true);


            String jsonPayload = RabbitmqConfig.writeAsJson(event, false);
            System.out.println("Publishing to " + routingKey + ": " + jsonPayload);
            BasicProperties msgProps = ClientLoginListener.getMessageProps(ClientRegisterNotification.class.getName());
            this.publishNotification(messageString, msgProps, routingKey);
        }
    }

    private static BasicProperties getMessageProps(String className) {

        Map<String,Object> headers = new HashMap<>();
        headers.put("__TypeId__", className);

        Builder propsBuilder = new AMQP.BasicProperties.Builder()
                .appId("Keycloak")
                .headers(headers)
                .contentType("application/json")
                .contentEncoding("UTF-8");
        return propsBuilder.build();
    }

    private void publishNotification(String messageString, BasicProperties props, String routingKey) {
        try {
            channel.basicPublish(cfg.getExchange(), routingKey, props, messageString.getBytes(StandardCharsets.UTF_8));
            log.infof("keycloak-to-rabbitmq SUCCESS sending message: %s%n", routingKey);
        } catch (Exception ex) {
            log.errorf(ex, "keycloak-to-rabbitmq ERROR sending message: %s%n", routingKey);
        }
    }

}
