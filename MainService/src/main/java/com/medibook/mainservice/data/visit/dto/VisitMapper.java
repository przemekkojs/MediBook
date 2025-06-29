package com.medibook.mainservice.data.visit.dto;

import com.medibook.mainservice.data.visit.Visit;
import org.springframework.stereotype.Component;

@Component
public class VisitMapper {
    public VisitDto toVisitDto(Visit visit) {
        return new VisitDto(
            visit.getId(),
            visit.getClient().getId(),
            visit.getDoctor().getId(),
            visit.getState().toString(),
            visit.getProcedure().getId(),
            visit.getStartTime().toString(),
            visit.getDate().toString()
        );
    }
}
