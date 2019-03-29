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
public class FilterVehicle extends ApiSerializable {

    @JsonProperty("RightsOfUserId")
    private UUID RightsOfUserId;

    @JsonIgnore
    public String getRightsOfUserId() {
        return fromJsonProp(RightsOfUserId);
    }

    @JsonIgnore
    public void setRightsOfUserId(String userId) {
        RightsOfUserId = toUUIDJsonProp(userId);
    }
}
