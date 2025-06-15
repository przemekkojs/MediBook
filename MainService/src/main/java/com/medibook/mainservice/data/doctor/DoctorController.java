package com.medibook.mainservice.data.doctor;

import com.medibook.mainservice.data.client.dto.ClientDTO;
import com.medibook.mainservice.data.doctor.dto.DoctorDto;
import com.medibook.mainservice.tools.keycloak.KeycloakService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
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

    @GetMapping
    public ResponseEntity<List<DoctorDto>> getDoctors() {
        return ResponseEntity.ok(keycloakService.getDoctors());
    }

    @GetMapping("/doctor/token")
    public ResponseEntity<DoctorDto> getDoctorFromToken(JwtAuthenticationToken auth) {
        String username = auth.getToken().getClaimAsString("preferred_username");
        DoctorDto doctor = keycloakService.getDoctorByUsername(username);

        if (doctor == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(doctor);
    }
}
