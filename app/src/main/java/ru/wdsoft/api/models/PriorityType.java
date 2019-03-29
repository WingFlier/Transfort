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
public class PriorityType extends ApiSerializable {

    /**
     * Для логирования
     */
    private final String LOG_PREFIX = this.getClass().getName() + " -- ";

    @JsonProperty("N")
    private Integer N;

    @JsonProperty("PriorityId")
    private UUID PriorityId;

    @JsonProperty("Name")
    private String Name;

    @JsonProperty("Priority")
    private String Priority;

    @JsonProperty("Description")
    private String Description;

    @JsonProperty("IsDeleted")
    private boolean IsDeleted;

    @JsonIgnore
    public Integer getN() {
        return fromJsonProp(N);
    }

    @JsonIgnore
    public void setN(Integer n) {
        N = n;
    }

    @JsonIgnore
    public String getPriorityId() {
        return fromJsonProp(PriorityId);
    }

    @JsonIgnore
    public void setPriorityId(String id) {
        this.PriorityId = toUUIDJsonProp(id);
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
    public String getPriority() {
        return fromJsonProp(Priority);
    }

    @JsonIgnore
    public void setPriority(String priority) {
        Priority = toJsonProp(priority);
    }

    @JsonIgnore
    public String getDescription() {
        return fromJsonProp(Description);
    }

    @JsonIgnore
    public void setDescription(String description) {
        Description = toJsonProp(description);
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
