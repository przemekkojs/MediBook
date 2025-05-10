package com.medibook.mainservice.client;

import com.medibook.mainservice.client.dto.ClientCreateDTO;

public interface IClientService {
    void createClient(ClientCreateDTO dto);
}
