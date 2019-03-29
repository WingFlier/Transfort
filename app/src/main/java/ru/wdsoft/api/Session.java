package ru.wdsoft.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by slaventii@mail.ru on 07.11.2017.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Session implements Serializable {

    /**
     * For logging purposes.
     */
    private final String LOG_PREFIX = this.getClass().getName() + " -- ";

    @JsonProperty("SessionKey")
    private String SessionKey;

    @JsonProperty("UserId")
    private String UserId;

    @JsonProperty("MailIsConfirmed")
    private Boolean MailIsConfirmed;

    @JsonProperty("IsCustomer")
    private Boolean IsCustomer;

    @JsonProperty("IsSupplier")
    private Boolean IsSupplier;

    @JsonProperty("Profile")
    private Profile mProfile;

    @JsonIgnore
    public Boolean mailIsConfirmed() {
        return MailIsConfirmed;
    }

    @JsonIgnore
    public void mailIsConfirmed(Boolean mailIsConfirmed) {
        MailIsConfirmed = mailIsConfirmed;
    }

    @JsonIgnore
    public Boolean IsCustomer() {
        return IsCustomer;
    }

    @JsonIgnore
    public void setIsCustomer(Boolean customer) {
        IsCustomer = customer;
    }

    @JsonIgnore
    public Boolean getSupplier() {
        return IsSupplier;
    }

    @JsonIgnore
    public void setSupplier(Boolean supplier) {
        IsSupplier = supplier;
    }

    @JsonIgnore
    public String getSessionKey() {
        return SessionKey;
    }

    @JsonIgnore
    public void setSessionKey(String sessionKey) {
        SessionKey = sessionKey;
    }

    @JsonIgnore
    public String getUserId() {
        return UserId;
    }

    @JsonIgnore
    public void setUserId(String userId) {
        UserId = userId;
    }

    @JsonIgnore
    public Profile getProfile() {
        return mProfile;
    }

    @JsonIgnore
    public void setProfile(Profile profile) {
        this.mProfile = profile;
    }

    @JsonIgnore
    public String getUserName(){

        if (getProfile() != null){
            return getProfile().getUserName();
        }

        return "";
    }

    @JsonIgnore
    public String getEmail(){

        if (getProfile() != null){
            return getProfile().getEmail();
        }

        return "";
    }

    @JsonIgnore
    public String getPhone(){

        if (getProfile() != null){
            return getProfile().getPhone();
        }

        return "";
    }


}
