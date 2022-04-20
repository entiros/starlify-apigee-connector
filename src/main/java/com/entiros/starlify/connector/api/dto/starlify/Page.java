package com.entiros.starlify.connector.api.dto.starlify;

import lombok.Data;

@Data
public class Page {
    private int size;
    private long totalElements;
    private int totalPages;
    private int number;
}
