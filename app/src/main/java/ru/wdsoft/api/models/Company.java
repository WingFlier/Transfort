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
public class Company extends ApiSerializable {

    /**
     * Для логирования
     */
    private final String LOG_PREFIX = this.getClass().getName() + " -- ";

    //"Files": null,
    @JsonProperty("Code")
    private Integer Code;
    @JsonProperty("Rating")
    private Float Rating;
    @JsonProperty("HasRights")
    private Boolean HasRights;
    @JsonProperty("ModifyDate")
    private String ModifyDate;
    @JsonProperty("RegionCode")
    private String RegionCode;

    @JsonProperty("Address")
    private String address;
    @JsonProperty("EMail")
    private String eMail;
    @JsonProperty("INN")
    private Long iNN;
    @JsonProperty("Id")
    private UUID id;
    @JsonProperty("IsApproved")
    private Boolean isApproved;
    @JsonProperty("IsCustomer")
    private Boolean isCustomer;
    @JsonProperty("IsDeleted")
    private Boolean isDeleted;
    @JsonProperty("IsSupplier")
    private Boolean isSupplier;
    @JsonProperty("KPP")
    private Long kPP;
    @JsonProperty("LegalAddress")
    private String legalAddress;
    @JsonProperty("LegalName")
    private String legalName;
    @JsonProperty("Name")
    private String name;
    @JsonProperty("OwnershipType")
    private String ownershipType;
    @JsonProperty("Phone")
    private String phone;
    @JsonProperty("WithVAT")
    private Boolean withVAT;
    @JsonProperty("Selected")
    private Boolean Selected;
    @JsonProperty("Departments")
    private Department[] Departments;

    @Override
    public String getUIName() {
        return getName();
    }

    @JsonIgnore
    public Department[] getDepartments() {
        return Departments;
    }
    @JsonIgnore
    public void setDepartments(Department[] departments) {
        Departments = departments;
    }
    @JsonIgnore
    public String getAddress() {
        return fromJsonProp(address);
    }
    @JsonIgnore
    public void setAddress(String address) {
        this.address = toJsonProp(address);
    }
    @JsonIgnore
    public String getEmail() {
        return fromJsonProp(eMail);
    }
    @JsonIgnore
    public void setEmail(String eMail) {
        this.eMail = toJsonProp(eMail);
    }
    @JsonIgnore
    public Long getiNN() {
        return fromJsonProp(iNN);
    }
    @JsonIgnore
    public void setiNN(Long iNN) {
        this.iNN = iNN;
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
    public Boolean getApproved() {
        return fromJsonProp(isApproved);
    }
    @JsonIgnore
    public void setApproved(Boolean approved) {
        isApproved = approved;
    }
    @JsonIgnore
    public Boolean isCustomer() {
        return fromJsonProp(isCustomer);
    }
    @JsonIgnore
    public void isCustomer(Boolean customer) {
        isCustomer = customer;
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
    public Boolean isSupplier() {
        return fromJsonProp(isSupplier);
    }
    @JsonIgnore
    public void isSupplier(Boolean supplier) {
        isSupplier = supplier;
    }
    @JsonIgnore
    public Long getkPP() {
        return fromJsonProp(kPP);
    }
    @JsonIgnore
    public void setkPP(Long kPP) {
        this.kPP = kPP;
    }
    @JsonIgnore
    public String getLegalAddress() {
        return fromJsonProp(legalAddress);
    }
    @JsonIgnore
    public void setLegalAddress(String legalAddress) {
        this.legalAddress = legalAddress;
    }
    @JsonIgnore
    public String getLegalName() {
        return fromJsonProp(legalName);
    }
    @JsonIgnore
    public void setLegalName(String legalName) {
        this.legalName = legalName;
    }
    @JsonIgnore
    public String getName() {
        return fromJsonProp(name);
    }
    @JsonIgnore
    public void setName(String name) {
        this.name = name;
    }
    @JsonIgnore
    public String getOwnershipType() {
        return fromJsonProp(ownershipType);
    }
    @JsonIgnore
    public void setOwnershipType(String ownershipType) {
        this.ownershipType = ownershipType;
    }
    @JsonIgnore
    public String getPhone() {
        return fromJsonProp(phone);
    }
    @JsonIgnore
    public void setPhone(String phone) {
        this.phone = phone;
    }
    @JsonIgnore
    public Boolean getWithVAT() {
        return fromJsonProp(withVAT);
    }
    @JsonIgnore
    public void setWithVAT(Boolean withVAT) {
        this.withVAT = withVAT;
    }
    @JsonIgnore
    public Boolean isSelected() {
        return fromJsonProp(Selected);
    }
    @JsonIgnore
    public void setSelected(Boolean selected) {
        Selected = selected;
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
    public Float getRating() {
        return fromJsonProp(Rating);
    }
    @JsonIgnore
    public void setRating(Float rating) {
        Rating = rating;
    }
    @JsonIgnore
    public Boolean hasRights() {
        return fromJsonProp(HasRights);
    }
    @JsonIgnore
    public void hasRights(Boolean hasRights) {
        HasRights = hasRights;
    }
    @JsonIgnore
    public Long getModifyDate() {
        return fromDateTimeJsonProp(ModifyDate);
    }
    @JsonIgnore
    public void setModifyDate(Long modifyDate) {
        ModifyDate = toDateTimeJsonProp(modifyDate);
    }
    @JsonIgnore
    public String getRegionCode() {
        return fromJsonProp(RegionCode);
    }
    @JsonIgnore
    public void setRegionCode(String regionCode) {
        RegionCode = fromJsonProp(regionCode);
    }
}
