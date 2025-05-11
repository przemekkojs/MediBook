package com.medibook.mainservice.data.doctor;

import com.medibook.mainservice.data.doctor.dto.DoctorCreateDto;

public interface IDoctorService {
    Doctor createDoctor(DoctorCreateDto dto);
    Doctor getDoctorByUsername(String username);
    Doctor getDoctorById(String id);
}
