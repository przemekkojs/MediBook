package com.medibook.mainservice.data.workhours.dto;

import java.sql.Time;

public record WorkhoursDto(int day, Time startTIme, Time endTime) {
}
