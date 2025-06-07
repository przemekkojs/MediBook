package com.medibook.mainservice.data.visit.dto;

import com.medibook.mainservice.data.visit.Visit;
import org.springframework.stereotype.Component;

@Component
public class VisitMapper {

    public VisitDto toVisitDto(Visit visit) {
        return new VisitDto(
                visit.getClient().getUsername(),
                visit.getDoctor().getUsername(),
                visit.getProcedure().getId(),
                visit.getStartTime().toString(),
                visit.getDate().toString()
        );
    }
}
