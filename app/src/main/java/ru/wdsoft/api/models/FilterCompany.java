package ru.wdsoft.api.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

import ru.wdsoft.api.ApiSerializable;

/**
 * Created by slaventii@mail.ru on 01.03.2019.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class FilterCompany extends ApiSerializable {

    @JsonProperty("UserId")
    private UUID UserId;

    @JsonIgnore
    public String getUserId() {
        return fromJsonProp(UserId);
    }

    @JsonIgnore
    public void setUserId(String userId) {
        UserId = toUUIDJsonProp(userId);
    }
}
