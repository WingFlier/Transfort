package ru.wdsoft.api.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

import ru.wdsoft.api.ApiSerializable;

/**
 * Created by slaventii@mail.ru on 12.03.2019.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class FilteredOrders extends ApiSerializable {

    /**
     * Для логирования
     */
    private final String LOG_PREFIX = this.getClass().getName() + " -- ";

    @JsonProperty("count")
    private Integer count;

    @JsonIgnore
    public Integer getCount() {
        return fromJsonProp(count);
    }

    @JsonIgnore
    public void setCount(Integer count) {
        this.count = count;
    }
}
