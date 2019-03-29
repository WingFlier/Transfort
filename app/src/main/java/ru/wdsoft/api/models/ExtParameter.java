package ru.wdsoft.api.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import ru.wdsoft.api.ApiSerializable;

/**
 * Created by slaventii@mail.ru on 21.02.2019.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class ExtParameter extends ApiSerializable {

    /**
     * Для логирования
     */
    private final String LOG_PREFIX = this.getClass().getName() + " -- ";

    public static final String PARAM_ShippingName = "ShippingName";
    public static final String PARAM_Slingers = "Slingers";
    public static final String PARAM_AreaOfWork = "AreaOfWork";
    public static final String PARAM_NatureOfWork = "NatureOfWork";
    public static final String PARAM_WorkNearPowerLines = "WorkNearPowerLines";
    public static final String PARAM_Route = "Route";
    public static final String PARAM_IsLongDuration = "IsLongDuration";
    public static final String PARAM_Responsible = "Responsible";
    public static final String PARAM_Service = "Service";

    @JsonProperty("key")
    private String key;

    @JsonProperty("value")
    private Object value;

    @JsonIgnore
    public String getKey() {
        return fromJsonProp(key);
    }

    @JsonIgnore
    public void setKey(String key) {
        this.key = toJsonProp(key);
    }

    @JsonIgnore
    public String toString(){

        if (value instanceof String){
            return fromJsonProp((String) value);
        }

        return null;
    }

    @JsonIgnore
    public Integer toInteger(){

        if (value instanceof Integer){
            return fromJsonProp((Integer) value);
        }

        return null;
    }


    @JsonIgnore
    public Boolean toBoolean(){

        if (value instanceof Boolean){
            return fromJsonProp((Boolean) value);
        }

        return null;
    }

    @JsonIgnore
    public void setValue(String val){
        value = fromJsonProp(val);
    }

    @JsonIgnore
    public void setValue(Integer val){
        value = val;
    }

    @JsonIgnore
    public void setValue(Boolean val){
        value = val;
    }


}
