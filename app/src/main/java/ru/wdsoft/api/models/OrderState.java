package ru.wdsoft.api.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

import ru.wdsoft.api.ApiSerializable;

/**
 * Created by slaventii@mail.ru on 11.03.2019.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderState extends ApiSerializable {
    /**
     * Для логирования
     */
    private final String LOG_PREFIX = this.getClass().getName() + " -- ";

    @JsonProperty("Id")
    private UUID Id;

    @JsonProperty("Name")
    private String Name;

    @JsonProperty("State")
    private String State;

    @JsonProperty("IsDeleted")
    private boolean IsDeleted;

    private boolean isSelected;

    @Override
    public String getUIName() {
        return getName();
    }

    @Override
    public boolean isChecked() {
        return isSelected;
    }

    @Override
    public void isChecked(boolean selected) {
        isSelected = selected;
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
    public String getName() {
        return fromJsonProp(Name);
    }

    @JsonIgnore
    public void setName(String name) {
        Name = toJsonProp(name);
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
    public boolean isDeleted() {
        return fromJsonProp(IsDeleted);
    }

    @JsonIgnore
    public void setDeleted(boolean deleted) {
        IsDeleted = deleted;
    }

}
