package ru.wdsoft.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by slaventii@mail.ru on 06.12.2017.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Profile extends ApiSerializable {

    /**
     * Для логирования
     */
    private final String LOG_PREFIX = this.getClass().getName() + " -- ";

    @JsonProperty("Email")
    private String Email;

    @JsonProperty("Phone")
    private String Phone;

    @JsonProperty("UserName")
    private String UserName;


    @JsonIgnore
    public String getEmail() {
        return fromJsonProp(Email);
    }

    @JsonIgnore
    public void setEmail(String email) {
        Email = email;
    }

    @JsonIgnore
    public String getPhone() {
        return fromJsonProp(Phone);
    }

    @JsonIgnore
    public void setPhone(String phone) {
        Phone = phone;
    }

    @JsonIgnore
    public String getUserName() {
        return fromJsonProp(UserName);
    }

    @JsonIgnore
    public void setUserName(String userName) {
        UserName = userName;
    }

}
