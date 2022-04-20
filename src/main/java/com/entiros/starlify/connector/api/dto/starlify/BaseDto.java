package com.entiros.starlify.connector.api.dto.starlify;

import lombok.Data;

import java.util.List;
import java.util.Objects;

@Data
public class BaseDto {
    private String type;
    private String id;
    private String created;
    private String updated;
    private String name;
    private Object links;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseDto baseDto = (BaseDto) o;
        return Objects.equals(getName(), baseDto.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }
}
