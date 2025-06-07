package com.medibook.mainservice.data.client;

import com.medibook.mainservice.data.client.dto.ClientCreateDTO;
import com.medibook.mainservice.data.client.dto.ClientCreateDetailsDto;
import com.medibook.mainservice.data.client.dto.ClientDTO;
import com.medibook.mainservice.tools.keycloak.KeycloakService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements IClientService {
    private final ClientRepository clientRepository;
    private final KeycloakService keycloakService;

    public Client createClient(ClientCreateDTO dto){

        Client client = Client.builder()
                .id(dto.userId())
                .username(dto.details().username())
                .build();

        return clientRepository.save(client);
    }

    @Override
    public Client getClientByUsername(String username) {
        Client client =  clientRepository.findByUsername(username);

        if(client == null){
            ClientDTO dto = keycloakService.getClientByUsername(username);

            client = dto == null ? null : createClient(new ClientCreateDTO(
                    dto.id(),
                    new ClientCreateDetailsDto(dto.username())
            ));
        }

        return client;
    }
}
