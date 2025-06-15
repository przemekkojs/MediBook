package com.medibook.mainservice.data.workhours.dto;

import java.sql.Time;
import java.time.LocalTime;

public record EditWorkhoursDto(LocalTime startTime, LocalTime endTime) {
}
