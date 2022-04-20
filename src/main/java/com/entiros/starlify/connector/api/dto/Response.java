package com.entiros.starlify.connector.api.dto;

import com.entiros.starlify.connector.api.dto.starlify.Link;
import com.entiros.starlify.connector.api.dto.starlify.Page;
import lombok.Data;

import java.util.List;

@Data
public class Response<T> {
    private List<Link> links;
    private List<T> content;
    private Page page;
}
