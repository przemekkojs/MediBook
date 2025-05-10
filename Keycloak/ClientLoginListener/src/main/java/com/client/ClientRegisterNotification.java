package com.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.xml.bind.annotation.XmlRootElement;
import org.keycloak.events.Event;

import java.io.Serializable;


@JsonIgnoreProperties(ignoreUnknown = true)
@XmlRootElement
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
public class ClientRegisterNotification extends Event implements Serializable {

    public static ClientRegisterNotification create(Event event) {
        ClientRegisterNotification msg = new ClientRegisterNotification();
        msg.setClientId(event.getClientId());
        msg.setDetails(event.getDetails());
        msg.setError(event.getError());
        msg.setIpAddress(event.getIpAddress());
        msg.setRealmId(event.getRealmId());
        msg.setSessionId(event.getSessionId());
        msg.setTime(event.getTime());
        msg.setType(event.getType());
        msg.setUserId(event.getUserId());

        return msg;
    }
}
