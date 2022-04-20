package com.entiros.starlify.connector.api.service;

import com.entiros.starlify.connector.api.dto.*;
import com.entiros.starlify.connector.api.dto.starlify.*;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public interface StarlifyService {
    public List<NetworkSystem> getSystems(Request request);
    public SystemRespDto addSystem(Request request, SystemDto systemDto);
    public String addService(Request request, ServiceDto serviceDto, String systemId);
    public Response<ServiceRespDto> getServices(Request request, String systemId);
}
