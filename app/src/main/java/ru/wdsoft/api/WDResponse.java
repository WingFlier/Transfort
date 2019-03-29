package ru.wdsoft.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.Serializable;

import ru.wdsoft.utils.LogUtils;
import ru.wdsoft.utils.Utils;

/**
 * Created by slaventii@mail.ru on 13.10.2017.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class WDResponse implements Serializable {

    private final String LOG_PREFIX = this.getClass().getName() + " -- ";

    @JsonProperty("body")
    private JsonNode body;

    @JsonProperty("dt")
    private String dt;

    @JsonProperty("ref")
    private String ref;

    @JsonProperty("sign")
    private String sign;

    @JsonProperty("error")
    private WDError error;

    public <T> T getValue(Class<T> valueType) {

        try {

            if (body != null && Utils.stringsNotEmpty(body.toString())) {

                return new ObjectMapper().readValue(body.toString(), valueType);
            }

        } catch (Exception e) {
            LogUtils.debugErrorLog(LOG_PREFIX, e);
        }

        return null;
    }

    @JsonIgnore
    public WDError getError() {
        return error;
    }

    @JsonIgnore
    public void setError(WDError error) {
        this.error = error;
    }

    @JsonIgnore
    public String getDt() {
        return dt;
    }

    @JsonIgnore
    public void setDt(String dt) {
        this.dt = dt;
    }

    @JsonIgnore
    public String getRef() {
        return ref;
    }

    @JsonIgnore
    public void setRef(String ref) {
        this.ref = ref;
    }

    @JsonIgnore
    public String getSign() {
        return sign;
    }

    @JsonIgnore
    public void setSign(String sign) {
        this.sign = sign;
    }
}

