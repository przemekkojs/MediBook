package com.medibook.mainservice.data.doctor;

import com.medibook.mainservice.data.doctor.dto.DoctorCreateDto;
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

    @Override
    public Doctor getDoctorByUsername(String username) {
        return doctorRepository.getDoctorByUsername(username);
    }

    @Override
    public Doctor getDoctorById(String id) {
        return doctorRepository.findById(id).orElse(null);
    }


}
