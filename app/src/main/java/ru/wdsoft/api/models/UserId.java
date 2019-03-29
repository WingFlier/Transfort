package ru.wdsoft.api.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import ru.wdsoft.api.ApiSerializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserId extends ApiSerializable {
    @JsonProperty("UserId")
    private String userId;

    @JsonIgnore
    public String getUserId() {
        return userId;
    }

    @JsonIgnore
    public void setUserId(String userId) {
        this.userId = userId;
    }
}
