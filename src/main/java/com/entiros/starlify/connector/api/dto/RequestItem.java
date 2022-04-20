package com.entiros.starlify.connector.api.dto;

import lombok.Data;

@Data
public class RequestItem extends Request {
    private Status status;
    private String errorMessage;

    public enum Status {
        NOT_STARTED,
        IN_PROCESS,
        DONE,
        ERROR
    }
}
