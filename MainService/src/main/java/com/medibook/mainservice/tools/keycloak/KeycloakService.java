package com.medibook.mainservice.tools.keycloak;

import com.medibook.mainservice.data.client.dto.ClientDTO;
import com.medibook.mainservice.data.doctor.dto.DoctorDto;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class KeycloakService {
    @Value("${spring.security.oauth2.keycloak.admin-api}")
    private String apiPrefix;
    @Value("${spring.security.oauth2.keycloak.client-realm}")
    private String clientRealm;
    @Value("${spring.security.oauth2.keycloak.doctor-realm}")
    private String doctorRealm;
    @Value("${spring.security.oauth2.keycloak.admin-username}")
    private String adminUsername;
    @Value("${spring.security.oauth2.keycloak.admin-password}")
    private String adminPassword;
    @Value("${spring.security.oauth2.keycloak.rest-uri-prefix}")
    private String clientApiPrefix;

    private Keycloak getKeycloak(String realm){
        return KeycloakBuilder.builder()
                .serverUrl(clientApiPrefix)
                .realm(realm)
                .authorization(getAdminToken())
                .build();
    };


    private String getAdminToken(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("client_id", "admin-cli"); // or your client
        body.add("username", adminUsername);
        body.add("password", adminPassword);
        body.add("grant_type", "password");

        System.out.println(body.toString());

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

        String path = apiPrefix+"/protocol/openid-connect/token";
        System.out.println(path);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Map> response = restTemplate.postForEntity(
                path,
                request,
                Map.class
        );

        return (String) response.getBody().get("access_token");
    }


    public ClientDTO getClient(String id){
        Keycloak keycloak = getKeycloak(clientRealm);

        UserResource userResource = keycloak.realm(clientRealm).users().get(id);
        UserRepresentation user = userResource.toRepresentation();

        return new ClientDTO(
                user.getId(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                ""
        );
    }

    public DoctorDto getDoctor(String id){
        Keycloak keycloak = getKeycloak(doctorRealm);

        UserResource userResource = keycloak.realm(doctorRealm).users().get(id);
        UserRepresentation user = userResource.toRepresentation();

        return new DoctorDto(
                user.getId(),
                null,
                user.getFirstName(),
                user.getLastName()
        );
    }

    public DoctorDto getDoctorByUsername(String username){
        Keycloak keycloak = getKeycloak(doctorRealm);

        List<UserRepresentation> userResource = keycloak.realm(doctorRealm).users().search(username);

        if(userResource.isEmpty()){
            return null;
        }

        UserRepresentation user = userResource.get(0);

        return new DoctorDto(
                user.getId(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName()
        );
    }

    public ClientDTO getClientByUsername(String username){
        Keycloak keycloak = getKeycloak(clientRealm);

        List<UserRepresentation> userResource = keycloak.realm(clientRealm).users().search(username);

        if(userResource.isEmpty()){
            return null;
        }

        UserRepresentation user = userResource.get(0);

        return new ClientDTO(
                user.getId(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                ""
        );
    }

    public List<DoctorDto> getDoctors(){
        Keycloak keycloak = getKeycloak(doctorRealm);

        List<UserRepresentation> userResources =keycloak.realm(doctorRealm).users().list();

        return userResources.stream().map(userResource -> new DoctorDto(
                userResource.getId(),
                null,
                userResource.getFirstName(),
                userResource.getLastName())).toList();
    }

    public List<ClientDTO> getClients(){
        Keycloak keycloak = getKeycloak(clientRealm);

        List<UserRepresentation> userResources =keycloak.realm(clientRealm).users().list();

        return userResources.stream().map(userResource -> new ClientDTO(
                userResource.getId(),
                null,
                userResource.getFirstName(),
                userResource.getLastName(),
                userResource.getEmail(),
                ""
        )).toList();
    }

}
