package com.medibook.mainservice.data.client;

import com.medibook.mainservice.data.client.dto.ClientCreateDTO;

public interface IClientService {
    void createClient(ClientCreateDTO dto);
    Client getClientByUsername(String username);
}
