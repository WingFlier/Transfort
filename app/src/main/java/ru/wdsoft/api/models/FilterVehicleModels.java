package ru.wdsoft.api.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import ru.wdsoft.api.ApiSerializable;

/**
 * Created by slaventii@mail.ru on 01.03.2019.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class FilterVehicleModels extends ApiSerializable {

    @JsonProperty("TypeIdIsNotEmpty")
    private Boolean TypeIdIsNotEmpty;

    @JsonIgnore
    public Boolean typeIdIsNotEmpty() {
        return fromJsonProp(TypeIdIsNotEmpty);
    }

    @JsonIgnore
    public void typeIdIsNotEmpty(Boolean typeIdIsNotEmpty) {
        TypeIdIsNotEmpty = typeIdIsNotEmpty;
    }
}
