package com.medibook.mainservice.data.workhours.dto;

import java.sql.Time;
import java.time.LocalTime;

public record WorkhoursDto(int day, LocalTime startTIme, LocalTime endTime) {
}
