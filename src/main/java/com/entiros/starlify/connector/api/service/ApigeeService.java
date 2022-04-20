package com.entiros.starlify.connector.api.service;

import com.entiros.starlify.connector.api.dto.apigee.OrganizationResponse;
import com.entiros.starlify.connector.api.dto.apigee.Proxy;
import com.entiros.starlify.connector.api.dto.apigee.ProxyResponse;

public interface ApigeeService {
    public ProxyResponse<Proxy> getProxies(String organization, String accessToken);
    public OrganizationResponse getOrganizations(String accessToken);
}
