package com.entiros.starlify.connector.api.dto.starlify;

import com.entiros.starlify.connector.api.dto.starlify.ServiceDto;
import lombok.Data;

@Data
public class RefDto {
    private String id;
    private String name;
    private ServiceDto service;
}
