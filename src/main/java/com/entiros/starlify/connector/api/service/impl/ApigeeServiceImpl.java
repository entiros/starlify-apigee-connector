package com.entiros.starlify.connector.api.service.impl;

import com.entiros.starlify.connector.api.dto.apigee.OrganizationResponse;
import com.entiros.starlify.connector.api.dto.apigee.Proxy;
import com.entiros.starlify.connector.api.dto.apigee.ProxyResponse;
import com.entiros.starlify.connector.api.service.ApigeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ApigeeServiceImpl implements ApigeeService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${apigee.server.url}")
    private String apiServer;

    @Override
    public ProxyResponse<Proxy> getProxies(String organization, String accessToken) {
        HttpHeaders headers = getHttpHeaders(accessToken);
        ResponseEntity<ProxyResponse<Proxy>> response = restTemplate.exchange(apiServer + "/organizations/"+organization+"/apis?includeMetaData=true",
                HttpMethod.GET,
                new HttpEntity<>(null, headers),
                new ParameterizedTypeReference<ProxyResponse<Proxy>>() {
                });

        return response.getBody();
    }

    @Override
    public OrganizationResponse getOrganizations(String accessToken) {
        HttpHeaders headers = getHttpHeaders(accessToken);
        ResponseEntity<OrganizationResponse> response = restTemplate.exchange(apiServer + "/organizations",
                HttpMethod.GET,
                new HttpEntity<>(null, headers),
                new ParameterizedTypeReference<OrganizationResponse>() {
                });

        return response.getBody();
    }

    private HttpHeaders getHttpHeaders(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("Authorization", "Bearer " + accessToken);
        return headers;
    }
}
