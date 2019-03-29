package ru.wdsoft.api.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import ru.wdsoft.api.ApiSerializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OwnerShipTypes extends ApiSerializable {
    @JsonProperty("Code")
    private String code;
    @JsonProperty("Name")
    private String name;

    @JsonIgnore
    public String getCode() {
        return code;
    }

    @JsonIgnore
    public void setCode(String code) {
        this.code = code;
    }

    @JsonIgnore
    public String getName() {
        return name;
    }

    @JsonIgnore
    public void setName(String name) {
        this.name = name;
    }
}
