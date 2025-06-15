package com.medibook.notification.rabbitmq.dto;

public record DoctorVisitInformation(String doctorNAme, String appointmentDate, String appointmentHour, String procedureName) {
}
