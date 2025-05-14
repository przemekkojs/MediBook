package com.medibook.mainservice.data.workhours.dto;

import com.medibook.mainservice.data.workhours.Workhours;
import org.springframework.stereotype.Component;

@Component
public class WorkhourMapper {

    public WorkhoursDto toDto(Workhours workhours) {
        return new WorkhoursDto(workhours.getDay(), workhours.getStartTime(), workhours.getEndTime());
    }
}
