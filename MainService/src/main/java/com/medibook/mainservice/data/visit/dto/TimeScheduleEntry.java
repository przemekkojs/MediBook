package com.medibook.mainservice.data.visit.dto;

import java.sql.Time;
import java.time.LocalTime;

public record TimeScheduleEntry(LocalTime start, LocalTime end) {
}
