package ru.wdsoft.api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

import ru.wdsoft.api.ApiSerializable;

/**
 * Created by slaventii@mail.ru on 18.02.2019.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class VehicleModel extends ApiSerializable {

    @JsonProperty("Capacity")
    private Integer capacity;
    @JsonProperty("Id")
    private UUID id;
    @JsonProperty("IsDeleted")
    private Boolean isDeleted;
    @JsonProperty("Name")
    private String name;
    @JsonProperty("NumberOfPass")
    private Integer numberOfPass;
    @JsonProperty("VehicleBrandId")
    private UUID vehicleBrandId;
    @JsonProperty("VehicleTypeId")
    private UUID vehicleTypeId;


    @Override
    public String getUIName() {
        return getName();
    }

    public Integer getCapacity() {
        return fromJsonProp(capacity);
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getId() {
        return fromJsonProp(id);
    }

    public void setId(String id) {
        this.id = toUUIDJsonProp(id);
    }

    public Boolean getDeleted() {
        return fromJsonProp(isDeleted);
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public String getName() {
        return fromJsonProp(name);
    }

    public void setName(String name) {
        this.name = toJsonProp(name);
    }

    public Integer getNumberOfPass() {
        return fromJsonProp(numberOfPass);
    }

    public void setNumberOfPass(Integer numberOfPass) {
        this.numberOfPass = numberOfPass;
    }

    public String getVehicleBrandId() {
        return fromJsonProp(vehicleBrandId);
    }

    public void setVehicleBrandId(String vehicleBrandId) {
        this.vehicleBrandId = toUUIDJsonProp(vehicleBrandId);
    }

    public String getVehicleTypeId() {
        return fromJsonProp(vehicleTypeId);
    }

    public void setVehicleTypeId(String vehicleTypeId) {
        this.vehicleTypeId = toUUIDJsonProp(vehicleTypeId);
    }
}
