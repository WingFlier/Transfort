package ru.wdsoft.api.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

import ru.wdsoft.api.ApiSerializable;

/**
 * Created by slaventii@mail.ru on 18.02.2018.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class DocumentType extends ApiSerializable {

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

    @JsonProperty("OnlyFor")
    private String OnlyFor;

    @JsonProperty("ForEntity")
    private String ForEntity;

    @JsonProperty("OrderType")
    private String OrderType;

    @JsonProperty("IsRequired")
    private boolean IsRequired;


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
    public String getOnlyFor() {
        return fromJsonProp(OnlyFor);
    }

    @JsonIgnore
    public void setOnlyFor(String onlyFor) {
        OnlyFor = toJsonProp(onlyFor);
    }

    @JsonIgnore
    public String getForEntity() {
        return fromJsonProp(ForEntity);
    }

    @JsonIgnore
    public void setForEntity(String forEntity) {
        ForEntity = toJsonProp(forEntity);
    }

    @JsonIgnore
    public String getOrderType() {
        return fromJsonProp(OrderType);
    }

    @JsonIgnore
    public void setOrderType(String orderType) {
        OrderType = toJsonProp(orderType);
    }

    @JsonIgnore
    public boolean isRequired() {
        return fromJsonProp(IsRequired);
    }

    @JsonIgnore
    public void setRequired(boolean required) {
        IsRequired = required;
    }
}
