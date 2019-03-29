package ru.wdsoft.api.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import ru.wdsoft.api.ApiSerializable;

/**
 * Created by slaventii@mail.ru on 21.02.2019.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class RentParameters extends ApiSerializable {

    /**
     * Для логирования
     */
    private final String LOG_PREFIX = this.getClass().getName() + " -- ";


    @JsonProperty("Address")
    private String Address;

    @JsonProperty("Date")
    private String Date;

    @JsonProperty("EndDate")
    private String EndDate;

    @JsonProperty("RentTime")
    private Integer RentTime;

    @JsonProperty("Comment")
    private String Comment;

    @JsonIgnore
    public String getAddress() {
        return fromJsonProp(Address);
    }

    @JsonIgnore
    public void setAddress(String address) {
        Address = toJsonProp(address);
    }

    @JsonIgnore
    public Long getDate() {
        return fromDateTimeJsonProp(Date);
    }

    @JsonIgnore
    public void setDate(Long date) {
        Date = toDateTimeJsonProp(date);
    }

    @JsonIgnore
    public Long getEndDate() {
        return fromDateTimeJsonProp(EndDate);
    }

    @JsonIgnore
    public void setEndDate(String endDate) {
        EndDate = endDate;
    }

    @JsonIgnore
    public Integer getRentTime() {
        return fromJsonProp(RentTime);
    }

    @JsonIgnore
    public void setRentTime(Integer rentTime) {
        RentTime = rentTime;
    }

    @JsonIgnore
    public String getComment() {
        return fromJsonProp(Comment);
    }

    @JsonIgnore
    public void setComment(String comment) {
        Comment = toJsonProp(comment);
    }
}
