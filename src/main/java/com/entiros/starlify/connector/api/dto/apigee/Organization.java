package com.entiros.starlify.connector.api.dto.apigee;

import lombok.Data;

import java.util.List;

@Data
public class Organization {
    private String organization;
    private List<String> projectIds;
    private String projectId;
}
