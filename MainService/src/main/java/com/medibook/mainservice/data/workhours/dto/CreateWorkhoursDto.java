package com.medibook.mainservice.data.workhours.dto;

import java.sql.Time;
import java.time.LocalTime;

public record CreateWorkhoursDto(int day, LocalTime startTime, LocalTime endTime) {
}
