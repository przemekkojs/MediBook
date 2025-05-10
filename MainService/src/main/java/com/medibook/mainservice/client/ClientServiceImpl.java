package com.medibook.mainservice.client;

import com.medibook.mainservice.client.dto.ClientCreateDTO;
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
}
