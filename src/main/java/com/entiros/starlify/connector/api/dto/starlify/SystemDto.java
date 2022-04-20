package com.entiros.starlify.connector.api.dto.starlify;

import lombok.Data;

@Data
public class SystemDto {
    private String id;
    private String localId;
    private String name;
    private String description;
    private Network network;
}
