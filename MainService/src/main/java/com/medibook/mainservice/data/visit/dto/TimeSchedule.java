package com.medibook.mainservice.data.visit.dto;

import java.util.List;

public record TimeSchedule(List<TimeScheduleEntry> timeSegments) {
}
