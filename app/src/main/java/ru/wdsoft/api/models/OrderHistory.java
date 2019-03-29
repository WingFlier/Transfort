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
public class OrderHistory extends ApiSerializable {

    /**
     * Для логирования
     */
    private final String LOG_PREFIX = this.getClass().getName() + " -- ";


    private UUID OrderId;

    @JsonProperty("CancelReasonId")
    private UUID CancelReasonId;

    @JsonProperty("CancelReasonName")
    private String CancelReasonName;

    @JsonProperty("Comment")
    private String Comment;

    @JsonProperty("ConsumerType")
    private String ConsumerType;

    @JsonProperty("Date")
    private String Date;

    @JsonProperty("IsProtest")
    private Boolean IsProtest;

    @JsonProperty("State")
    private String State;

    @JsonProperty("StateName")
    private String StateName;

    @JsonProperty("UserId")
    private UUID UserId;

    @JsonProperty("UserName")
    private String UserName;

    private Boolean IsChanged;

    @JsonIgnore
    public String getUserName() {
        return fromJsonProp(UserName);
    }

    @JsonIgnore
    public void setUserName(String userName) {
        UserName = toJsonProp(userName);
    }

    @JsonIgnore
    public String getStateName() {
        return fromJsonProp(StateName);
    }

    @JsonIgnore
    public void setStateName(String stateName) {
        StateName = toJsonProp(stateName);
    }

    @JsonIgnore
    public String getOrderId() {
        return fromJsonProp(OrderId);
    }

    @JsonIgnore
    public void setOrderId(String orderId) {
        this.OrderId = toUUIDJsonProp(orderId);
    }

    @JsonIgnore
    public String getCancelReasonId() {
        return fromJsonProp(CancelReasonId);
    }

    @JsonIgnore
    public void setCancelReasonId(String cancelReasonId) {
        CancelReasonId = toUUIDJsonProp(cancelReasonId);
    }

    @JsonIgnore
    public String getCancelReasonName() {
        return fromJsonProp(CancelReasonName);
    }

    @JsonIgnore
    public void setCancelReasonName(String cancelReasonName) {
        CancelReasonName = toJsonProp(cancelReasonName);
    }

    @JsonIgnore
    public String getComment() {
        return fromJsonProp(Comment);
    }

    @JsonIgnore
    public void setComment(String comment) {
        Comment = toJsonProp(comment);
    }

    @JsonIgnore
    public String getConsumerType() {
        return fromJsonProp(ConsumerType);
    }

    @JsonIgnore
    public void setConsumerType(String consumerType) {
        ConsumerType = toJsonProp(consumerType);
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
    public boolean isProtest() {
        return fromJsonProp(IsProtest);
    }

    @JsonIgnore
    public void setProtest(boolean protest) {
        IsProtest = protest;
    }

    @JsonIgnore
    public String getState() {
        return fromJsonProp(State);
    }

    @JsonIgnore
    public void setState(String state) {
        State = toJsonProp(state);
    }

    @JsonIgnore
    public String getUserId() {
        return fromJsonProp(UserId);
    }

    @JsonIgnore
    public void setUserId(String userId) {
        this.UserId = toUUIDJsonProp(userId);
    }

    @JsonIgnore
    public Boolean isChanged() {
        return fromJsonProp(IsChanged);
    }

    @JsonIgnore
    public void setChanged(Boolean changed) {
        IsChanged = changed;
    }
}
