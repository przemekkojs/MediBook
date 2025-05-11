package com.medibook.mainservice.data.client;

import com.medibook.mainservice.data.client.dto.ClientCreateDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements IClientService {
    private final ClientRepository clientRepository;

    public void createClient(ClientCreateDTO dto){

        Client client = Client.builder()
                .id(dto.userId())
                .username(dto.details().username())
                .build();

        clientRepository.save(client);
    }

    @Override
    public Client getClientByUsername(String username) {
        return clientRepository.findByUsername(username);
    }
}
