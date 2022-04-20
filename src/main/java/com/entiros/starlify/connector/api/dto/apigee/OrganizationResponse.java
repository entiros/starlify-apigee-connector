package com.entiros.starlify.connector.api.dto.apigee;

import lombok.Data;

import java.util.List;

@Data
public class OrganizationResponse {
    private List<Organization> organizations;
}
