package ru.wdsoft.api.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

import ru.wdsoft.api.ApiSerializable;

/**
 * Created by slaventii@mail.ru on 19.02.2019.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Locality extends ApiSerializable {

    /**
     * Для логирования
     */
    private final String LOG_PREFIX = this.getClass().getName() + " -- ";

    @JsonProperty("Id")
    private UUID Id;

    @JsonProperty("Name")
    private String Name;

    @JsonProperty("IsDeleted")
    private boolean IsDeleted;

    @JsonProperty("City")
    private String City;

    @JsonProperty("Region")
    private String Region;

    @JsonProperty("Country")
    private String Country;

    @JsonProperty("CityCode")
    private String CityCode;

    @JsonProperty("District")
    private String District;

    @JsonProperty("TimeShift")
    private Integer TimeShift;

    @JsonProperty("ModifyDate")
    private String ModifyDate;

    @JsonProperty("RegionCode")
    private String RegionCode;

    @JsonProperty("DistrictCode")
    private String DistrictCode;

    @JsonProperty("LocalityCode")
    private String LocalityCode;

    @JsonProperty("InternationalName")
    private String InternationalName;

    @JsonIgnore
    public String getId() {
        return fromJsonProp(Id);
    }

    @JsonIgnore
    public void setId(String id) {
        this.Id = toUUIDJsonProp(id);
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
    public String getCity() {
        return fromJsonProp(City);
    }

    @JsonIgnore
    public void setCity(String city) {
        City = toJsonProp(city);
    }

    @JsonIgnore
    public String getRegion() {
        return fromJsonProp(Region);
    }

    @JsonIgnore
    public void setRegion(String region) {
        Region = toJsonProp(region);
    }

    @JsonIgnore
    public String getCountry() {
        return fromJsonProp(Country);
    }

    @JsonIgnore
    public void setCountry(String country) {
        Country = toJsonProp(country);
    }

    @JsonIgnore
    public String getCityCode() {
        return fromJsonProp(CityCode);
    }

    @JsonIgnore
    public void setCityCode(String cityCode) {
        CityCode = toJsonProp(cityCode);
    }

    @JsonIgnore
    public String getDistrict() {
        return fromJsonProp(District);
    }

    @JsonIgnore
    public void setDistrict(String district) {
        District = toJsonProp(district);
    }

    @JsonIgnore
    public Integer getTimeShift() {
        return fromJsonProp(TimeShift);
    }

    @JsonIgnore
    public void setTimeShift(Integer timeShift) {
        TimeShift = timeShift;
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
        RegionCode = toJsonProp(regionCode);
    }

    @JsonIgnore
    public String getDistrictCode() {
        return fromJsonProp(DistrictCode);
    }

    @JsonIgnore
    public void setDistrictCode(String districtCode) {
        DistrictCode = toJsonProp(districtCode);
    }

    @JsonIgnore
    public String getLocalityCode() {
        return fromJsonProp(LocalityCode);
    }

    @JsonIgnore
    public void setLocalityCode(String localityCode) {
        LocalityCode = toJsonProp(localityCode);
    }

    @JsonIgnore
    public String getInternationalName() {
        return fromJsonProp(InternationalName);
    }

    @JsonIgnore
    public void setInternationalName(String internationalName) {
        InternationalName = toJsonProp(internationalName);
    }
}
