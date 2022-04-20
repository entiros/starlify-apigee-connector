package com.entiros.starlify.connector.api.service.impl;

import com.entiros.starlify.connector.api.dto.*;
import com.entiros.starlify.connector.api.dto.starlify.RefDto;
import com.entiros.starlify.connector.api.dto.starlify.*;
import com.entiros.starlify.connector.api.service.ApigeeService;
import com.entiros.starlify.connector.api.service.StarlifyService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StarlifyServiceImpl implements StarlifyService {
    private final RestTemplate restTemplate;

    private final ApigeeService apigeeService;

    @Value("${starlify.url}")
    private String starlifyServer;

    @Override
    public List<NetworkSystem> getSystems(Request request) {
        HttpHeaders headers = getHttpHeaders(request);
        return restTemplate.exchange(starlifyServer + "/hypermedia/networks/{networkId}/systems?paged=false",
                HttpMethod.GET,
                new HttpEntity<>(null, headers),
                new ParameterizedTypeReference<List<NetworkSystem>>() {
                }, request.getNetworkId()).getBody();
    }

    @Override
    public SystemRespDto addSystem(Request request, SystemDto systemDto) {
        HttpHeaders headers = getHttpHeaders(request);
        return restTemplate.exchange(starlifyServer + "/hypermedia/networks/{networkId}/systems",
                HttpMethod.POST,
                new HttpEntity<>(systemDto, headers),
                new ParameterizedTypeReference<SystemRespDto>() {
                }, request.getNetworkId()).getBody();
    }

    @Override
    public String addService(Request request, ServiceDto serviceDto, String systemId) {
        HttpHeaders headers = getHttpHeaders(request);
        return restTemplate.exchange(starlifyServer + "/hypermedia/systems/{systemId}/services",
                HttpMethod.POST,
                new HttpEntity<>(serviceDto, headers),
                new ParameterizedTypeReference<String>() {
                }, systemId).getBody();
    }

    @Override
    public Response<ServiceRespDto> getServices(Request request, String systemId) {
        HttpHeaders headers = getHttpHeaders(request);
        return restTemplate.exchange(starlifyServer + "/hypermedia/systems/{systemId}/services",
                HttpMethod.GET,
                new HttpEntity<>(null, headers),
                new ParameterizedTypeReference<Response<ServiceRespDto>>() {
                }, systemId).getBody();
    }

    private HttpHeaders getHttpHeaders(Request request) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("X-API-KEY", request.getStarlifyKey());
        return headers;
    }

    private void addRef(Request request, NetworkSystem consumer, ServiceRespDto serviceRespDto, String flowId) {
        HttpHeaders headers = getHttpHeaders(request);
        RefDto dt = new RefDto();
        dt.setName("ref:"+consumer.getName()+" -> "+ serviceRespDto.getName());
        ServiceDto sdto = new ServiceDto();
        sdto.setId(serviceRespDto.getId());
        dt.setService(sdto);
        RefResp refResp = restTemplate.exchange(starlifyServer + "/hypermedia/systems/{systemId}/references",
                    HttpMethod.POST,
                    new HttpEntity<>(dt, headers),
                    new ParameterizedTypeReference<RefResp>() {
                    }, consumer.getId()).getBody();
        assert refResp != null;
        this.addFlowInvocation(request, refResp, flowId);
    }

    private void addFlowInvocation(Request request, RefResp refResp, String flowId) {
        HttpHeaders headers = getHttpHeaders(request);
        InvocationDto dt = new InvocationDto();
        RefDto refDto = new RefDto();
        refDto.setId(refResp.getId());
        dt.setReference(refDto);
        restTemplate.exchange(starlifyServer + "/hypermedia/flows/{flowId}/invocations",
                HttpMethod.POST,
                new HttpEntity<>(dt, headers),
                new ParameterizedTypeReference<String>() {
                }, flowId).getBody();
    }
}
