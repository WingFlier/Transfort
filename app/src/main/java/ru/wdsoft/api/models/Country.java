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
public class Country extends ApiSerializable {

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

    @JsonProperty("NumberCode")
    private String NumberCode;

    @JsonProperty("TwoCharacterCode")
    private String TwoCharacterCode;

    @JsonProperty("ThreeCharacterCode")
    private String ThreeCharacterCode;

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
    public String getNumberCode() {
        return fromJsonProp(NumberCode);
    }

    @JsonIgnore
    public void setNumberCode(String numberCode) {
        NumberCode = toJsonProp(numberCode);
    }

    @JsonIgnore
    public String getTwoCharacterCode() {
        return fromJsonProp(TwoCharacterCode);
    }

    @JsonIgnore
    public void setTwoCharacterCode(String twoCharacterCode) {
        TwoCharacterCode = toJsonProp(twoCharacterCode);
    }

    @JsonIgnore
    public String getThreeCharacterCode() {
        return fromJsonProp(ThreeCharacterCode);
    }

    @JsonIgnore
    public void setThreeCharacterCode(String threeCharacterCode) {
        ThreeCharacterCode = toJsonProp(threeCharacterCode);
    }
}
