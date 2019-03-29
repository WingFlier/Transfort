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
public class Department extends ApiSerializable {

    /**
     * Для логирования
     */
    private final String LOG_PREFIX = this.getClass().getName() + " -- ";

    @JsonProperty("Localities")
    private Locality[] Localities;

    @JsonProperty("Id")
    private UUID Id;

    @JsonProperty("ParentId")
    private UUID ParentId;

    private UUID CompanyId;

    @JsonProperty("Name")
    private String Name;

    @JsonProperty("HasChilds")
    private boolean HasChilds;

    @JsonProperty("IsUnit")
    private boolean IsUnit;

    @JsonProperty("MotorCadeId")
    private UUID MotorCadeId;

    @JsonProperty("MotorCadeName")
    private String MotorCadeName;

    @JsonProperty("Selected")
    private boolean Selected;

    @JsonProperty("HasRights")
    private boolean HasRights;

    @JsonProperty("IsDeleted")
    private boolean IsDeleted;

    private Boolean isCustomer;

    private Boolean isSupplier;

    @Override
    public String getUIName() {
        return getName();
    }

    @JsonIgnore
    public String getId() {
        return fromJsonProp(Id);
    }
    @JsonIgnore
    public void setId(String id) {
        this.Id = toUUIDJsonProp(id);
    }

    @JsonIgnore
    public String getParentId() {
        return fromJsonProp(ParentId);
    }

    @JsonIgnore
    public void setParentId(String parentId) {
        this.ParentId = toUUIDJsonProp(parentId);
    }

    @JsonIgnore
    public String getCompanyId() {
        return fromJsonProp(CompanyId);
    }

    @JsonIgnore
    public void setCompanyId(String companyId) {
        this.CompanyId = toUUIDJsonProp(companyId);
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
    public boolean isDeleted() {
        return fromJsonProp(IsDeleted);
    }
    @JsonIgnore
    public void setDeleted(boolean deleted) {
        IsDeleted = deleted;
    }
    @JsonIgnore
    public boolean hasChilds() {
        return fromJsonProp(HasChilds);
    }
    @JsonIgnore
    public void setHasChilds(boolean hasChilds) {
        HasChilds = hasChilds;
    }
    @JsonIgnore
    public boolean isUnit() {
        return fromJsonProp(IsUnit);
    }
    @JsonIgnore
    public void setUnit(boolean unit) {
        IsUnit = unit;
    }
    @JsonIgnore
    public boolean isSelected() {
        return fromJsonProp(Selected);
    }
    @JsonIgnore
    public void setSelected(boolean selected) {
        Selected = selected;
    }
    @JsonIgnore
    public String getMotorCadeId() {
        return fromJsonProp(MotorCadeId);
    }
    @JsonIgnore
    public void setMotorCadeId(String motorCadeId) {
        MotorCadeId = toUUIDJsonProp(motorCadeId);
    }
    @JsonIgnore
    public String getMotorCadeName() {
        return fromJsonProp(MotorCadeName);
    }
    @JsonIgnore
    public void setMotorCadeName(String motorCadeName) {
        MotorCadeName = toJsonProp(motorCadeName);
    }
    @JsonIgnore
    public Boolean isCustomer() {
        return isCustomer;
    }
    @JsonIgnore
    public void setIsCustomer(Boolean is_customer) {
        isCustomer = is_customer;
    }
    @JsonIgnore
    public Boolean isSupplier() {
        return isSupplier;
    }
    @JsonIgnore
    public void setIsSupplier(Boolean is_supplier) {
        isSupplier = is_supplier;
    }
    @JsonIgnore
    public Locality[] getLocalities() {
        return Localities;
    }
    @JsonIgnore
    public void setLocalities(Locality[] localities) {
        Localities = localities;
    }
    @JsonIgnore
    public boolean hasRights() {
        return HasRights;
    }
    @JsonIgnore
    public void hasRights(boolean hasRights) {
        HasRights = hasRights;
    }
}
