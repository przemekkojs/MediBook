package com.medibook.mainservice.doctor;

import com.medibook.mainservice.doctor.dto.DoctorCreateDto;

public interface IDoctorService {
    void createDoctor(DoctorCreateDto dto);
}
