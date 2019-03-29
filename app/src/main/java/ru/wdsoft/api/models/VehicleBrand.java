package ru.wdsoft.api.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

import ru.wdsoft.api.ApiSerializable;

/**
 * Created by slaventii@mail.ru on 18.02.2019.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class VehicleBrand extends ApiSerializable {

    /**
     * Для логирования
     */
    private final String LOG_PREFIX = this.getClass().getName() + " -- ";

    @JsonProperty("Id")
    private UUID Id;

    @JsonProperty("Name")
    private String Name;

    @JsonProperty("IsDeleted")
    private boolean IsDeleted;

    @JsonIgnore
    public String getId() {
        return fromJsonProp(Id);
    }

    @JsonIgnore
    public void setId(String id) {
        this.Id = toUUIDJsonProp(id);
    }

    @JsonIgnore
    public String getName() {
        return fromJsonProp(Name);
    }

    @JsonIgnore
    public void setName(String name) {
        Name = toJsonProp(name);
    }

    @JsonIgnore
    public boolean isDeleted() {
        return fromJsonProp(IsDeleted);
    }

    @JsonIgnore
    public void setDeleted(boolean deleted) {
        IsDeleted = deleted;
    }

}
