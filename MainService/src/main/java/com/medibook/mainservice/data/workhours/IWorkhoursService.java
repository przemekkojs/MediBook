package com.medibook.mainservice.data.workhours;

import com.medibook.mainservice.data.workhours.dto.CreateWorkhoursDto;
import com.medibook.mainservice.data.workhours.dto.EditWorkhoursDto;

import java.util.List;

public interface IWorkhoursService {
    void createWorkHours(CreateWorkhoursDto dto, String doctorId);
    void updateWorkHours(EditWorkhoursDto dto, long id);
    void deleteWorkHours(long id);
    Workhours getWorkhoursById(long id);
    List<Workhours> getWorkHoursForDoctor(String doctorId);
}
