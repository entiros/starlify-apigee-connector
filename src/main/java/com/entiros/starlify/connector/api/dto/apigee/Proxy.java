package com.entiros.starlify.connector.api.dto.apigee;

import lombok.Data;

@Data
public class Proxy{
    private MetaData metaData;
    private String name;
    private String apiProxyType;
}
