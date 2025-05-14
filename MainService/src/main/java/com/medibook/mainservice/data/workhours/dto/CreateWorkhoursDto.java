package com.medibook.mainservice.data.workhours.dto;

import java.sql.Time;

public record CreateWorkhoursDto(int day, Time startTime, Time endTime) {
}
