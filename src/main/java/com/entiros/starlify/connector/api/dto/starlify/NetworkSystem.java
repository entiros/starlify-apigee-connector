package com.entiros.starlify.connector.api.dto.starlify;

import lombok.Data;

import java.util.List;

@Data
public class NetworkSystem extends BaseDto {
    private List<Reference> references;
}
