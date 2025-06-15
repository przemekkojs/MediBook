package com.medibook.mainservice.tools.rabbitmq.dto;

import lombok.Builder;

@Builder
public record ClientVisitConfirmation(String clientName, String clientEmail, String doctorName, String visitDate, String visitTime) {

}
