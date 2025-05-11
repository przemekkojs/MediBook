package com.medibook.mainservice.data.doctor;

import com.medibook.mainservice.data.doctor.dto.DoctorCreateDto;

public interface IDoctorService {
    void createDoctor(DoctorCreateDto dto);
    Doctor getDoctorByUsername(String username);
    Doctor getDoctorById(String id);
}
