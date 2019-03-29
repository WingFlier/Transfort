package ru.wdsoft.api.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

import ru.wdsoft.api.ApiSerializable;

/**
 * Created by slaventii@mail.ru on 21.02.2019.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Person extends ApiSerializable {

    /**
     * Для логирования
     */
    private final String LOG_PREFIX = this.getClass().getName() + " -- ";

    private UUID OrderId;

    @JsonProperty("Name")
    private String Name;

    @JsonProperty("EMail")
    private String EMail;

    @JsonProperty("Phone")
    private String Phone;

    @JsonProperty("FromExtSystem")
    private Boolean FromExtSystem;

    @JsonIgnore
    public String getOrderId() {
        return fromJsonProp(OrderId);
    }

    @JsonIgnore
    public void setOrderId(String orderId) {
        OrderId = toUUIDJsonProp(orderId);
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
    public String getEMail() {
        return fromJsonProp(EMail);
    }

    @JsonIgnore
    public void setEMail(String EMail) {
        this.EMail = toJsonProp(EMail);
    }

    @JsonIgnore
    public String getPhone() {
        return fromJsonProp(Phone);
    }

    @JsonIgnore
    public void setPhone(String phone) {
        Phone = toJsonProp(phone);
    }

    @JsonIgnore
    public Boolean fromExtSystem() {
        return fromJsonProp(FromExtSystem);
    }

    @JsonIgnore
    public void fromExtSystem(Boolean fromExtSystem) {
        FromExtSystem = fromExtSystem;
    }
}
