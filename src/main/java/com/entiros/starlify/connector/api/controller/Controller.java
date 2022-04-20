package com.entiros.starlify.connector.api.controller;

import com.entiros.starlify.connector.api.dto.*;
import com.entiros.starlify.connector.api.service.StarlifyProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@Slf4j
@RestController
@RequestMapping
@RequiredArgsConstructor
public class Controller {

    private final StarlifyProcessor starlifyProcessor;

    @PostMapping("/status")
    public RequestItem getStatus(@RequestBody Request request) {
        return starlifyProcessor.status(request);
    }

    @PostMapping("/submitRequest")
    public RequestItem processRequest(@RequestBody Request request) throws ExecutionException, InterruptedException {
        log.info("key recieved {}", request.getApiKey());
        return starlifyProcessor.processRequest(request);
    }

}

