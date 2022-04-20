package com.entiros.starlify.connector.api.dto.starlify;

import lombok.Data;

@Data
public class Reference extends BaseDto {
    private NetworkSystem consumingNetworkSystem;
    private Service service;
}
