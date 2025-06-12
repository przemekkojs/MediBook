package com.medibook.mainservice.data.doctor;

import com.medibook.mainservice.data.client.dto.ClientDTO;
import com.medibook.mainservice.data.doctor.dto.DoctorDto;
import com.medibook.mainservice.tools.keycloak.KeycloakService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/doctors")
@CrossOrigin
@RequiredArgsConstructor
public class DoctorController {
    private final KeycloakService keycloakService;

    public DoctorController(KeycloakService keycloakService) {
        this.keycloakService = keycloakService;
    }

    @GetMapping
    public ResponseEntity<List<DoctorDto>> getDoctors() {
        return ResponseEntity.ok(keycloakService.getDoctors());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorDto> getDoctor(String id) {
        return ResponseEntity.ok(keycloakService.getDoctor(id));
    }
}
