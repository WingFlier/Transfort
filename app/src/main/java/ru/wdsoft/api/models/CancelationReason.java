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
public class CancelationReason extends ApiSerializable {

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

    @JsonProperty("IsCustomerReason")
    private boolean IsCustomerReason;

    @JsonProperty("IsValidForCalc")
    private boolean IsValidForCalc;

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
    @JsonIgnore
    public boolean isCustomerReason() {
        return fromJsonProp(IsCustomerReason);
    }
    @JsonIgnore
    public void setCustomerReason(boolean customerReason) {
        IsCustomerReason = customerReason;
    }
    @JsonIgnore
    public boolean isValidForCalc() {
        return fromJsonProp(IsValidForCalc);
    }
    @JsonIgnore
    public void setValidForCalc(boolean validForCalc) {
        IsValidForCalc = validForCalc;
    }
}
