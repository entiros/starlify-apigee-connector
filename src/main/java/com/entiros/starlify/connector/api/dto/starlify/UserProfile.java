package com.entiros.starlify.connector.api.dto.starlify;


import lombok.Data;

import java.util.List;

@Data
public class UserProfile {
    public User user;
    public List<Asset> assetList;
}
