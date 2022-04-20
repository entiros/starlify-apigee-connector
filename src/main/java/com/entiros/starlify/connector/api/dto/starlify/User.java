package com.entiros.starlify.connector.api.dto.starlify;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class User{
    public String id;
    public Date createdAt;
    public Date updatedAt;
    public String organizationId;
    public String firstName;
    public String lastName;
    public String email;
    public String phoneNumber;
    public String username;
    public String idprovider_id;
    public boolean enabled;
    public boolean deleted;
    public Date lastLogin;
    public boolean mfaVerificationExcluded;
    public String mfaVerifiersConfigured;
    public boolean isFederated;
    public String type;
    public OrganizationPreferences organizationPreferences;
    public Organization organization;
    public Object properties;
    public List<Object> memberOfOrganizations;
    public List<Object> contributorOfOrganizations;
}
