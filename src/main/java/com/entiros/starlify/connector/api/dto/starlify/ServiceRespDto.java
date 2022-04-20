package com.entiros.starlify.connector.api.dto.starlify;

import lombok.Data;

@Data
public class ServiceRespDto extends BaseDto {
    private String localId;
    private Object provider;
    private String invocations;
    private String certifiedIntegratorCompliant;
    private String attributes;
    private String engagements;
    private String observations;
    private String releaseStage;
    private String releases;
    private Object domain;
    private Object network;
    private String incomingReferences;
}
