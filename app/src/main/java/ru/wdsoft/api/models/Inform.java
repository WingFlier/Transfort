package ru.wdsoft.api.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import ru.wdsoft.api.ApiSerializable;

/**
 * Created by slaventii@mail.ru on 21.02.2019.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Inform extends ApiSerializable {

    /**
     * Для логирования
     */
    private final String LOG_PREFIX = this.getClass().getName() + " -- ";

    public static final String TYPE_SMS = "SMS";

    @JsonProperty("Address")
    private String Address;

    @JsonProperty("Name")
    private String Name;

    @JsonProperty("Type")
    private String Type;

    @JsonIgnore
    public String getAddress() {
        return fromJsonProp(Address);
    }

    @JsonIgnore
    public void setAddress(String address) {
        Address = toJsonProp(address);
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
    public String getType() {
        return fromJsonProp(Type);
    }

    @JsonIgnore
    public void setType(String type) {
        Type = toJsonProp(type);
    }
}
