package com.medibook.mainservice.data.client;

import com.medibook.mainservice.data.client.dto.ClientDTO;
import com.medibook.mainservice.tools.keycloak.KeycloakService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<ClientDTO> getClient(@PathVariable String id) {
        return ResponseEntity.ok(keycloakService.getClient(id));
    }

    @GetMapping("/client/token")
    public ResponseEntity<ClientDTO> getClientFromToken(JwtAuthenticationToken auth) {
        String username = auth.getToken().getClaimAsString("preferred_username");
        ClientDTO client = keycloakService.getClientByUsername(username);

        if (client == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(client);
    }
}
