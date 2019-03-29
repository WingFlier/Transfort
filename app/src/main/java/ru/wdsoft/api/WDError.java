package ru.wdsoft.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by slaventii@mail.ru on 06.11.2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class WDError implements Serializable {

    /**
     * For logging purposes.
     */
    private final String LOG_PREFIX = this.getClass().getName() + " -- ";

    @JsonProperty("code")
    private Integer code;

    @JsonProperty("msg")
    private String msg;

    @JsonIgnore
    public Integer getCode() {
        return code;
    }

    @JsonIgnore
    public void setCode(Integer code) {
        this.code = code;
    }

    @JsonIgnore
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
