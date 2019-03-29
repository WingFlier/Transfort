package ru.wdsoft.api.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

import ru.wdsoft.api.ApiSerializable;

/**
 * Created by slaventii@mail.ru on 19.02.2019.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class OwnershipType extends ApiSerializable {

    /**
     * Для логирования
     */
    private final String LOG_PREFIX = this.getClass().getName() + " -- ";

    @JsonProperty("Code")
    private String Code;

    @JsonProperty("Name")
    private String Name;

    @JsonProperty("IsDeleted")
    private boolean IsDeleted;

    @JsonIgnore
    public String getCode() {
        return fromJsonProp(Code);
    }

    @JsonIgnore
    public void setCode(String code) {
        Code = toJsonProp(code);
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
