package com.entiros.starlify.connector.api.service;

import com.entiros.starlify.connector.api.dto.Request;
import com.entiros.starlify.connector.api.dto.RequestItem;

import java.util.concurrent.ExecutionException;

public interface StarlifyProcessor {
    public RequestItem processRequest(Request request);
    public RequestItem status(Request request);
}
