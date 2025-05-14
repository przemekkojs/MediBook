package com.medibook.mainservice.data.doctor;

import com.medibook.mainservice.data.doctor.dto.DoctorCreateDto;
import com.medibook.mainservice.data.doctor.dto.DoctorDto;
import com.medibook.mainservice.tools.keycloak.KeycloakService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements IDoctorService {

    private final DoctorRepository doctorRepository;
    private final KeycloakService keycloak;

    @Override
    public Doctor createDoctor(DoctorCreateDto dto) {
        Doctor doctor = Doctor.builder()
                .id(dto.id())
                .username(dto.username())
                .build();

        return doctorRepository.save(doctor);
    }

    @Override
    public Doctor getDoctorByUsername(String username) {
        Doctor doctor =doctorRepository.getDoctorByUsername(username);

        if(doctor == null){
            DoctorDto dto = keycloak.getDoctorByUsername(username);

            doctor = dto == null ? null : createDoctor(
                    new DoctorCreateDto(dto.id(), dto.username())
            );
        }
        return doctor;
    }

    @Override
    public Doctor getDoctorById(String id) {
        return doctorRepository.findById(id).orElse(null);
    }


}
