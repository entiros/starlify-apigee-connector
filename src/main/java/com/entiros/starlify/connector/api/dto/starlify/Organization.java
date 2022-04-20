package com.entiros.starlify.connector.api.dto.starlify;
import java.util.Date;
import java.util.List;
public class Organization{
    public String name;
    public String id;
    public Date createdAt;
    public Date updatedAt;
    public String ownerId;
    public String clientId;
    public String idprovider_id;
    public boolean isFederated;
    public List<Object> parentOrganizationIds;
    public List<Object> subOrganizationIds;
    public List<Object> tenantOrganizationIds;
    public String mfaRequired;
    public boolean isAutomaticAdminPromotionExempt;
    public String orgType;
    public String domain;
    public boolean isMaster;
    public Object subscription;
    public Object properties;
    public Object entitlements;
}
