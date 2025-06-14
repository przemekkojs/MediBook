package com.medibook.mainservice.data.client;

import com.medibook.mainservice.data.client.dto.ClientDTO;
import com.medibook.mainservice.tools.keycloak.KeycloakService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/clients")
@CrossOrigin
@RequiredArgsConstructor
public class ClientController {
    private final KeycloakService keycloakService;

    @GetMapping
    public ResponseEntity<List<ClientDTO>> getClients() {
        return ResponseEntity.ok(keycloakService.getClients());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> getClient(String id) {
        return ResponseEntity.ok(keycloakService.getClient(id));
    }
}
