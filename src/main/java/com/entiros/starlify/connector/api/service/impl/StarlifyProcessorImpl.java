package com.entiros.starlify.connector.api.service.impl;

import com.entiros.starlify.connector.api.dto.*;
import com.entiros.starlify.connector.api.dto.apigee.Organization;
import com.entiros.starlify.connector.api.dto.apigee.OrganizationResponse;
import com.entiros.starlify.connector.api.dto.apigee.Proxy;
import com.entiros.starlify.connector.api.dto.apigee.ProxyResponse;
import com.entiros.starlify.connector.api.dto.starlify.*;
import com.entiros.starlify.connector.api.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class StarlifyProcessorImpl implements StarlifyProcessor {

    private final StarlifyService starlifyService;
    private final ApigeeService apigeeService;

    private final Map<String, Map<String, NetworkSystem>> systemCache = new ConcurrentHashMap<>();
    private final Map<String, Map<String, NetworkSystem>> consumerSystemCache = new ConcurrentHashMap<>();
    private final Map<String, RequestItem> statusMap = new ConcurrentHashMap<>();

    private void processRequestIntrnl(Request request) {
        ((RequestItem)request).setStatus(RequestItem.Status.IN_PROCESS);
        OrganizationResponse organizationResponse = apigeeService.getOrganizations(request.getApiKey());

        List<NetworkSystem> systems = starlifyService.getSystems(request);
        this.populateSystems(request, systems);
        Map<String, NetworkSystem> existingSystems = systemCache.get(request.getNetworkId());
        List<Organization> data = organizationResponse.getOrganizations();
        data.forEach(s -> {
            try {
                log.info("Started service:"+s.getOrganization());
                NetworkSystem networkSystem = existingSystems != null ? existingSystems.get(s.getOrganization()) : null;
                String systemId;
                if(networkSystem == null) {
                    SystemDto systemDto = this.createSystemDto(request, s.getOrganization());
                    SystemRespDto systemRespDto = starlifyService.addSystem(request, systemDto);
                    systemId = systemRespDto.getId();
                    NetworkSystem newSystem = new NetworkSystem();
                    newSystem.setId(systemId);
                    newSystem.setName(s.getOrganization());
                    updateCache(request.getNetworkId(), newSystem);
                } else {
                    systemId = networkSystem.getId();
                }

                Response<ServiceRespDto> services = starlifyService.getServices(request, systemId);
                Set<String> serviceNames = this.getServiceNames(services);
                ProxyResponse<Proxy> proxies = apigeeService.getProxies(s.getOrganization(), request.getApiKey());

                if (proxies != null && proxies.getProxies() != null && !proxies.getProxies().isEmpty()) {
                    log.info("Endpoints size :" + proxies.getProxies().size());
                    for (Proxy p : proxies.getProxies()) {
                        try {
                            String name = p.getName();
                            if (!serviceNames.contains(name)) {
                                ServiceDto dto = new ServiceDto();
                                dto.setName(name);
                                starlifyService.addService(request, dto, systemId);
                            }
                        } catch (Throwable e) {
                            log.error("Error while processing proxy:" + p.getName(), e);
                        }
                    }
                    ((RequestItem) request).setStatus(RequestItem.Status.DONE);
                }
                ((RequestItem)request).setStatus(RequestItem.Status.DONE);
                log.info("Completed service:"+s.getOrganization());
            } catch (Throwable t) {
                log.error("Error while processing servic:"+s.getOrganization(), t);
                ((RequestItem)request).setStatus(RequestItem.Status.ERROR);
            }
        });
        // clearing cache
        consumerSystemCache.remove(request.getNetworkId());
        systemCache.remove(request.getNetworkId());
    }

    private synchronized Set<String> getServiceNames(Response<ServiceRespDto> services) {
        List<ServiceRespDto> content = services.getContent();
        Set<String> ret = new HashSet<>();
        if (content != null && !content.isEmpty()) {
            for (ServiceRespDto c : content) {
                ret.add(c.getName());
            }
        }
        return ret;
    }

    @Override
    public RequestItem processRequest(Request request) {
        RequestItem workItem = new RequestItem();
        workItem.setStatus(RequestItem.Status.NOT_STARTED);
        workItem.setStarlifyKey(request.getStarlifyKey());
        workItem.setApiKey(request.getApiKey());
        workItem.setNetworkId(request.getNetworkId());
        statusMap.put(request.getNetworkId(), workItem);
        CompletableFuture.runAsync(() -> {
            try{
                processRequestIntrnl(workItem);
            } catch (Throwable t) {
                log.error("Error while processing:", t);
                workItem.setStatus(RequestItem.Status.ERROR);
            }

        });
        return workItem;
    }


    @Override
    public RequestItem status(Request request) {
        return statusMap.get(request.getNetworkId());
    }

    private SystemDto createSystemDto(Request request, String name) {
        SystemDto s = new SystemDto();
        String id = UUID.randomUUID().toString();
        s.setId(id);
        s.setName(name);
        Network n = new Network();
        n.setId(request.getNetworkId());
        s.setNetwork(n);
        return s;
    }

    private synchronized void populateSystems(Request request, List<NetworkSystem> networkSystems) {
        if(networkSystems != null && !networkSystems.isEmpty()) {
            Map<String, NetworkSystem> existingSystems = systemCache.get(request.getNetworkId());
            if(existingSystems == null) {
                existingSystems = new ConcurrentHashMap<>();
                systemCache.put(request.getNetworkId(), existingSystems);
            }
            for (NetworkSystem ns : networkSystems) {
                existingSystems.put(ns.getName(), ns);
            }
        }
    }

    private synchronized void updateCache(String networkId, NetworkSystem networkSystem) {
        if(networkSystem != null) {
            Map<String, NetworkSystem> existingSystems = systemCache.get(networkId);
            if(existingSystems == null) {
                existingSystems = new ConcurrentHashMap<>();
                systemCache.put(networkId, existingSystems);
            }
            existingSystems.put(networkSystem.getName(), networkSystem);
        }
    }
}
