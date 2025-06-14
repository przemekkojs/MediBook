package com.medibook.mainservice.data.visit;

import com.medibook.mainservice.data.visit.dto.CreateVisitDto;

import java.util.List;

public interface IVisitService {
    Visit getVisit(long id);

    List<Visit> getAllVisitsFromClient(String username);

    List<Visit> getAllVisitsFromDoctor(String username);

    Visit createVisit(CreateVisitDto visit,String username);

    void deleteVisit(long id);
}
