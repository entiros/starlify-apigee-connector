package com.entiros.starlify.connector.api.dto;

import lombok.Data;

@Data
public class Request {
    private String starlifyKey;
    private String apiKey;
    private String networkId;
}
