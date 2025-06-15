package com.medibook.mainservice.data.visit.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

public record CreateVisitDto(long procedureId, String doctorId, LocalTime startTime, @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

}
