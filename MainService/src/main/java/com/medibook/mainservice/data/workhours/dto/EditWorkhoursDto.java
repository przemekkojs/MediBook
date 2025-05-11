package com.medibook.mainservice.data.workhours.dto;

import java.sql.Time;

public record EditWorkhoursDto(Time startTime, Time endTime) {
}
