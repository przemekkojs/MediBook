package com.medibook.mainservice.data.doctor;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.medibook.mainservice.data.doctor.dto.DoctorDto;
import com.medibook.mainservice.tools.keycloak.KeycloakService;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/doctors")
@CrossOrigin
@RequiredArgsConstructor
public class DoctorController {
    private final KeycloakService keycloakService;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping
    public ResponseEntity<List<DoctorDto>> getDoctors() {
        return ResponseEntity.ok(keycloakService.getDoctors());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorDto> getDoctor(String id) {
        return ResponseEntity.ok(keycloakService.getDoctor(id));
    }

    @GetMapping("/redirect")
    public ResponseEntity<String> doctorRedirect(@RequestParam("code") String code) {
        String tokenUrl = "https://medibook.pl/oauth/realms/doctor/protocol/openid-connect/token";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", "development-oauth-client");
        body.add("code", code);
        body.add("redirect_uri", "/api/v1/doctors/redirect");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(tokenUrl, request, String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        String accessToken;
        try {
            JsonNode root = objectMapper.readTree(response.getBody());
            accessToken = root.path("access_token").asText();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        String redirectToFrontend = "/doctor/redirect?access_token=" + accessToken;
        HttpHeaders redirectHeaders = new HttpHeaders();
        redirectHeaders.setLocation(URI.create(redirectToFrontend));

        return new ResponseEntity<>(redirectHeaders, HttpStatus.FOUND);
    }
}
