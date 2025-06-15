package com.medibook.mainservice.data.visit;

import com.medibook.mainservice.data.visit.dto.CreateVisitDto;
import com.medibook.mainservice.data.visit.dto.TimeSchedule;

import java.time.LocalDate;
import java.util.List;

public interface IVisitService {
    Visit getVisit(long id);

    List<Visit> getAllVisitsFromClient(String username);

    List<Visit> getAllVisitsFromDoctor(String username);

    Visit createVisit(CreateVisitDto visit,String username);
    public TimeSchedule getTimeSchedule(String id, LocalDate date);

    void deleteVisit(long id);
}
