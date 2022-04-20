package com.entiros.starlify.connector.api.dto.starlify;

import lombok.Data;

@Data
public class RefResp extends BaseDto {
    private NetworkSystem consumingSystem;
    private Service service;
    private String attributes;
    private String releaseStage;
    private String releases;
    private Object domain;
    private Object network;
}
