package com.entiros.starlify.connector.api.dto.starlify;

import lombok.Data;

@Data
public class FlowRespDto extends BaseDto {
    private String description;
    private String documentationUrl;
    private String sourceSystems;
    private String targetSystems;
    private String invocations;
    private String attributes;
    private Object domain;
    private Object network;
}
