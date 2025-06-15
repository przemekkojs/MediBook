package com.medibook.notification.rabbitmq.dto;

public record ClientVisitConfirmation(String clientName,String clientEmail, String doctorName, String visitDate, String visitTime) {

}
