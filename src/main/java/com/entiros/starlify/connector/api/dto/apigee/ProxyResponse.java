package com.entiros.starlify.connector.api.dto.apigee;

import lombok.Data;

import java.util.List;

@Data
public class ProxyResponse<T> {
    private List<T> proxies;
}
