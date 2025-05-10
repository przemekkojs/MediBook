package com.medibook.mainservice.doctor;

import com.medibook.mainservice.doctor.dto.DoctorCreateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements IDoctorService {

    private final DoctorRepository doctorRepository;

    @Override
    public void createDoctor(DoctorCreateDto dto) {
        Doctor doctor = Doctor.builder()
                .id(dto.id())
                .username(dto.username())
                .build();
    }



}
