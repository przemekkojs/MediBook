package com.medibook.mainservice.data.visit.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record CreateVisitDto(long procedureId, String doctorId, LocalTime startTime, LocalDate date) {

}
