package ru.wdsoft.api.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.UUID;

import ru.wdsoft.api.ApiSerializable;

/**
 * Created by slaventii@mail.ru on 23.02.2019.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Offer extends ApiSerializable {

    /**
     * Для логирования
     */
    private final String LOG_PREFIX = this.getClass().getName() + " -- ";

    private UUID OrderId;

    @JsonProperty("CarrierDepartmentId")
    private UUID CarrierDepartmentId;

    @JsonProperty("CarrierDepartmentName")
    private String CarrierDepartmentName;

    @JsonProperty("CarrierCompanyId")
    private UUID CarrierCompanyId;

    @JsonProperty("CarrierCompanyName")
    private String CarrierCompanyName;

    @JsonProperty("Code")
    private Integer Code;

    @JsonProperty("Email")
    private String Email;

    @JsonProperty("Phone")
    private String Phone;

    @JsonProperty("Rating")
    private Float Rating;

    @JsonProperty("WithVAT")
    private Boolean WithVAT;

    @JsonProperty("TotalAmount")
    private Float TotalAmount;

    @JsonProperty("VatRate")
    private Float VatRate;

    @JsonProperty("CreateDate")
    private String CreateDate;

    @JsonProperty("State")
    private String State;

    @JsonProperty("Price")
    private Float Price;

    @JsonProperty("VehicleId")
    private UUID VehicleId;

    @JsonProperty("VehicleHourlyPrice")
    private Float VehicleHourlyPrice;

    @JsonProperty("VehicleKmPrice")
    private Float VehicleKmPrice;

    @JsonProperty("VehicleComment")
    private String VehicleComment;

    @JsonProperty("VehicleTypeId")
    private UUID VehicleTypeId;

    @JsonProperty("VehicleTypeName")
    private String VehicleTypeName;

    @JsonProperty("ModelId")
    private UUID ModelId;

    @JsonProperty("ModelName")
    private String ModelName;

    @JsonProperty("RegNumber")
    private String RegNumber;

    @JsonProperty("Comment")
    private String Comment;

    @JsonProperty("PlannedTime")
    private Integer PlannedTime;

    @JsonProperty("UserEmail")
    private String UserEmail;

    @JsonProperty("UserPhone")
    private String UserPhone;

    @JsonProperty("UserName")
    private String UserName;

    @JsonProperty("UserId")
    private UUID UserId;

    @JsonIgnore
    public String getOrderId() {
        return fromJsonProp(OrderId);
    }
    @JsonIgnore
    public void setOrderId(String orderId) {
        OrderId = toUUIDJsonProp(orderId);
    }
    @JsonIgnore
    public String getCarrierDepartmentId() {
        return fromJsonProp(CarrierDepartmentId);
    }
    @JsonIgnore
    public void setCarrierDepartmentId(String carrierDepartmentId) {
        CarrierDepartmentId = toUUIDJsonProp(carrierDepartmentId);
    }
    @JsonIgnore
    public String getCarrierDepartmentName() {
        return fromJsonProp(CarrierDepartmentName);
    }
    @JsonIgnore
    public void setCarrierDepartmentName(String carrierDepartmentName) {
        CarrierDepartmentName = toJsonProp(carrierDepartmentName);
    }
    @JsonIgnore
    public String getCarrierCompanyId() {
        return fromJsonProp(CarrierCompanyId);
    }
    @JsonIgnore
    public void setCarrierCompanyId(String carrierCompanyId) {
        CarrierCompanyId = toUUIDJsonProp(carrierCompanyId);
    }
    @JsonIgnore
    public String getCarrierCompanyName() {
        return fromJsonProp(CarrierCompanyName);
    }
    @JsonIgnore
    public void setCarrierCompanyName(String carrierCompanyName) {
        CarrierCompanyName = toJsonProp(carrierCompanyName);
    }
    @JsonIgnore
    public Integer getCode() {
        return fromJsonProp(Code);
    }
    @JsonIgnore
    public void setCode(Integer code) {
        Code = code;
    }
    @JsonIgnore
    public String getEmail() {
        return fromJsonProp(Email);
    }
    @JsonIgnore
    public void setEmail(String email) {
        Email = toJsonProp(email);
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
    public Float getRating() {
        return fromJsonProp(Rating);
    }
    @JsonIgnore
    public void setRating(Float rating) {
        Rating = rating;
    }
    @JsonIgnore
    public Boolean withVAT() {
        return fromJsonProp(WithVAT);
    }
    @JsonIgnore
    public void withVAT(Boolean withVAT) {
        WithVAT = withVAT;
    }
    @JsonIgnore
    public Float getTotalAmount() {
        return fromJsonProp(TotalAmount);
    }
    @JsonIgnore
    public void setTotalAmount(Float totalAmount) {
        TotalAmount = totalAmount;
    }
    @JsonIgnore
    public Float getVatRate() {
        return fromJsonProp(VatRate);
    }
    @JsonIgnore
    public void setVatRate(Float vatRate) {
        VatRate = vatRate;
    }
    @JsonIgnore
    public Long getCreateDate() {
        return fromDateTimeJsonProp(CreateDate);
    }
    @JsonIgnore
    public void setCreateDate(Long createDate) {
        CreateDate = toDateTimeJsonProp(createDate);
    }
    @JsonIgnore
    public String getState() {
        return fromJsonProp(State);
    }
    @JsonIgnore
    public void setState(String state) {
        State = state;
    }
    @JsonIgnore
    public Float getPrice() {
        return fromJsonProp(Price);
    }
    @JsonIgnore
    public void setPrice(Float price) {
        Price = price;
    }
    @JsonIgnore
    public String getVehicleId() {
        return fromJsonProp(VehicleId);
    }
    @JsonIgnore
    public void setVehicleId(String vehicleId) {
        VehicleId = toUUIDJsonProp(vehicleId);
    }
    @JsonIgnore
    public Float getVehicleHourlyPrice() {
        return fromJsonProp(VehicleHourlyPrice);
    }
    @JsonIgnore
    public void setVehicleHourlyPrice(Float vehicleHourlyPrice) {
        VehicleHourlyPrice = vehicleHourlyPrice;
    }
    @JsonIgnore
    public Float getVehicleKmPrice() {
        return fromJsonProp(VehicleKmPrice);
    }
    @JsonIgnore
    public void setVehicleKmPrice(Float vehicleKmPrice) {
        VehicleKmPrice = vehicleKmPrice;
    }
    @JsonIgnore
    public String getVehicleComment() {
        return fromJsonProp(VehicleComment);
    }
    @JsonIgnore
    public void setVehicleComment(String vehicleComment) {
        VehicleComment = toJsonProp(vehicleComment);
    }
    @JsonIgnore
    public String getVehicleTypeId() {
        return fromJsonProp(VehicleTypeId);
    }
    @JsonIgnore
    public void setVehicleTypeId(String vehicleTypeId) {
        VehicleTypeId = toUUIDJsonProp(vehicleTypeId);
    }
    @JsonIgnore
    public String getVehicleTypeName() {
        return fromJsonProp(VehicleTypeName);
    }
    @JsonIgnore
    public void setVehicleTypeName(String vehicleTypeName) {
        VehicleTypeName = toJsonProp(vehicleTypeName);
    }
    @JsonIgnore
    public String getModelId() {
        return fromJsonProp(ModelId);
    }
    @JsonIgnore
    public void setModelId(String modelId) {
        ModelId = toUUIDJsonProp(modelId);
    }
    @JsonIgnore
    public String getModelName() {
        return fromJsonProp(ModelName);
    }
    @JsonIgnore
    public void setModelName(String modelName) {
        ModelName = toJsonProp(modelName);
    }
    @JsonIgnore
    public String getRegNumber() {
        return fromJsonProp(RegNumber);
    }
    @JsonIgnore
    public void setRegNumber(String regNumber) {
        RegNumber = toJsonProp(regNumber);
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
    public Integer getPlannedTime() {
        return fromJsonProp(PlannedTime);
    }
    @JsonIgnore
    public void setPlannedTime(Integer plannedTime) {
        PlannedTime = plannedTime;
    }
    @JsonIgnore
    public String getUserEmail() {
        return fromJsonProp(UserEmail);
    }
    @JsonIgnore
    public void setUserEmail(String userEmail) {
        UserEmail = toJsonProp(userEmail);
    }
    @JsonIgnore
    public String getUserPhone() {
        return fromJsonProp(UserPhone);
    }
    @JsonIgnore
    public void setUserPhone(String userPhone) {
        UserPhone = toJsonProp(userPhone);
    }
    @JsonIgnore
    public String getUserName() {
        return fromJsonProp(UserName);
    }
    @JsonIgnore
    public void setUserName(String userName) {
        UserName = toJsonProp(userName);
    }
    @JsonIgnore
    public String getUserId() {
        return fromJsonProp(UserId);
    }
    @JsonIgnore
    public void setUserId(String userId) {
        UserId = toUUIDJsonProp(userId);
    }
}
