package ru.wdsoft.api.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

import ru.wdsoft.api.ApiSerializable;

/**
 * Created by slaventii@mail.ru on 18.02.2019.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Vehicle extends ApiSerializable {

    @JsonProperty("Rating")
    private Float Rating;
    @JsonProperty("KmPrice")
    private Float KmPrice;
    @JsonProperty("WithVAT")
    private Boolean WithVAT;
    @JsonProperty("ExtTypes")
    private String[] ExtTypes;
    @JsonProperty("MinHours")
    private Integer MinHours;
    @JsonProperty("RegionId")
    private UUID RegionId;
    @JsonProperty("CompanyId")
    private UUID CompanyId;
    @JsonProperty("ModelName")
    private String ModelName;
    @JsonProperty("OrderType")
    private String OrderType;
    @JsonProperty("CreateDate")
    private String CreateDate;
    @JsonProperty("ModyfyDate")
    private String ModyfyDate;
    @JsonProperty("RegionName")
    private String RegionName;
    @JsonProperty("SortNumber")
    private Integer SortNumber;
    @JsonProperty("CompanyName")
    private String CompanyName;
    @JsonProperty("HourlyPrice")
    private Float HourlyPrice;
    @JsonProperty("YearOfIssue")
    private Integer YearOfIssue;
    @JsonProperty("VehicleTypeName")
    private String VehicleTypeName;
    @JsonProperty("OptionalEquipment")
    private String OptionalEquipment;


    @JsonProperty("Capacity")
    private Integer capacity;
    @JsonProperty("Comment")
    private String comment;
    @JsonProperty("ConditionId")
    private UUID conditionId;
    @JsonProperty("DepartmentId")
    private UUID departmentId;
    @JsonProperty("Id")
    private UUID id;
    @JsonProperty("IsDeleted")
    private Boolean isDeleted;
    @JsonProperty("ModelId")
    private UUID modelId;
    @JsonProperty("Name")
    private String name;
    @JsonProperty("NumberOfPassengers")
    private Integer numberOfPassengers;
    @JsonProperty("ReadyToWork")
    private Boolean readyToWork;
    @JsonProperty("RegNumber")
    private String regNumber;
    @JsonProperty("VehicleTypeId")
    private UUID VehicleTypeId;

    @JsonIgnore
    public Integer getCapacity() {
        return fromJsonProp(capacity);
    }

    @JsonIgnore
    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    @JsonIgnore
    public String getComment() {
        return fromJsonProp(comment);
    }

    @JsonIgnore
    public void setComment(String comment) {
        this.comment = toJsonProp(comment);
    }

    @JsonIgnore
    public String getConditionId() {
        return fromJsonProp(conditionId);
    }

    @JsonIgnore
    public void setConditionId(String conditionId) {
        this.conditionId = toUUIDJsonProp(conditionId);
    }

    @JsonIgnore
    public String getDepartmentId() {
        return fromJsonProp(departmentId);
    }

    @JsonIgnore
    public void setDepartmentId(String departmentId) {
        this.departmentId = toUUIDJsonProp(departmentId);
    }

    @JsonIgnore
    public String getId() {
        return fromJsonProp(id);
    }

    @JsonIgnore
    public void setId(String id) {
        this.id = toUUIDJsonProp(id);
    }

    @JsonIgnore
    public Boolean getDeleted() {
        return fromJsonProp(isDeleted);
    }

    @JsonIgnore
    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    @JsonIgnore
    public String getModelId() {
        return fromJsonProp(modelId);
    }

    @JsonIgnore
    public void setModelId(String modelId) {
        this.modelId = toUUIDJsonProp(modelId);
    }

    @JsonIgnore
    public String getName() {
        return fromJsonProp(name);
    }

    @JsonIgnore
    public void setName(String name) {
        this.name = toJsonProp(name);
    }

    @JsonIgnore
    public Integer getNumberOfPassengers() {
        return fromJsonProp(numberOfPassengers);
    }

    @JsonIgnore
    public void setNumberOfPassengers(Integer numberOfPassengers) {
        this.numberOfPassengers = numberOfPassengers;
    }

    @JsonIgnore
    public Boolean getReadyToWork() {
        return fromJsonProp(readyToWork);
    }

    @JsonIgnore
    public void setReadyToWork(Boolean readyToWork) {
        this.readyToWork = readyToWork;
    }

    @JsonIgnore
    public String getRegNumber() {
        return fromJsonProp(regNumber);
    }

    @JsonIgnore
    public void setRegNumber(String regNumber) {
        this.regNumber = toJsonProp(regNumber);
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
    public Float getRating() {
        return fromJsonProp(Rating);
    }
    @JsonIgnore
    public void setRating(Float rating) {
        Rating = rating;
    }
    @JsonIgnore
    public Float getKmPrice() {
        return fromJsonProp(KmPrice);
    }
    @JsonIgnore
    public void setKmPrice(Float kmPrice) {
        KmPrice = kmPrice;
    }
    @JsonIgnore
    public Boolean getWithVAT() {
        return fromJsonProp(WithVAT);
    }
    @JsonIgnore
    public void setWithVAT(Boolean withVAT) {
        WithVAT = withVAT;
    }
    @JsonIgnore
    public String[] getExtTypes() {
        return ExtTypes;
    }
    @JsonIgnore
    public void setExtTypes(String[] extTypes) {
        ExtTypes = extTypes;
    }
    @JsonIgnore
    public Integer getMinHours() {
        return fromJsonProp(MinHours);
    }
    @JsonIgnore
    public void setMinHours(Integer minHours) {
        MinHours = minHours;
    }
    @JsonIgnore
    public String getRegionId() {
        return fromJsonProp(RegionId);
    }
    @JsonIgnore
    public void setRegionId(String regionId) {
        RegionId = toUUIDJsonProp(regionId);
    }
    @JsonIgnore
    public String getCompanyId() {
        return fromJsonProp(CompanyId);
    }
    @JsonIgnore
    public void setCompanyId(String companyId) {
        CompanyId = toUUIDJsonProp(companyId);
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
    public String getOrderType() {
        return fromJsonProp(OrderType);
    }
    @JsonIgnore
    public void setOrderType(String orderType) {
        OrderType = toJsonProp(orderType);
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
    public Long getModyfyDate() {
        return fromDateTimeJsonProp(ModyfyDate);
    }
    @JsonIgnore
    public void setModyfyDate(Long modyfyDate) {
        ModyfyDate = toDateTimeJsonProp(modyfyDate);
    }
    @JsonIgnore
    public String getRegionName() {
        return fromJsonProp(RegionName);
    }
    @JsonIgnore
    public void setRegionName(String regionName) {
        RegionName = toJsonProp(regionName);
    }
    @JsonIgnore
    public Integer getSortNumber() {
        return fromJsonProp(SortNumber);
    }
    @JsonIgnore
    public void setSortNumber(Integer sortNumber) {
        SortNumber = sortNumber;
    }
    @JsonIgnore
    public String getCompanyName() {
        return fromJsonProp(CompanyName);
    }
    @JsonIgnore
    public void setCompanyName(String companyName) {
        CompanyName = toJsonProp(companyName);
    }
    @JsonIgnore
    public Float getHourlyPrice() {
        return fromJsonProp(HourlyPrice);
    }
    @JsonIgnore
    public void setHourlyPrice(Float hourlyPrice) {
        HourlyPrice = hourlyPrice;
    }
    @JsonIgnore
    public Integer getYearOfIssue() {
        return fromJsonProp(YearOfIssue);
    }
    @JsonIgnore
    public void setYearOfIssue(Integer yearOfIssue) {
        YearOfIssue = yearOfIssue;
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
    public String getOptionalEquipment() {
        return fromJsonProp(OptionalEquipment);
    }
    @JsonIgnore
    public void setOptionalEquipment(String optionalEquipment) {
        OptionalEquipment = toJsonProp(optionalEquipment);
    }
}
