package ru.wdsoft.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import ru.wdsoft.utils.LogUtils;
import ru.wdsoft.utils.Utils;

/**
 * Created by slaventii@mail.ru on 27.10.2017.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Config implements Serializable {

    /**
     * For logging purposes.
     */
    private final String LOG_PREFIX = this.getClass().getName() + " -- ";


    // System time format is yyyy-MM-dd'T'HH:mm:ss.SSSSSSZZZZZ
    @JsonProperty("SystemTime")
    private String SystemTime;

    @JsonIgnore
    public String getSystemTime() {
        return SystemTime;
    }

    @JsonIgnore
    public String getSystemTime(String formatDate) {

        if (!Utils.stringsNotEmpty(SystemTime)) return null;

        SimpleDateFormat sourceFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSZZZZZ");

        try {

            Date parsed = sourceFormat.parse(SystemTime);

            SimpleDateFormat destFormat = new SimpleDateFormat(formatDate);

            return destFormat.format(parsed);

        } catch (ParseException | NullPointerException e) {
            LogUtils.debugErrorLog(LOG_PREFIX, e);
        }

        return SystemTime;
    }

    @JsonIgnore
    public void setSystemTime(String systemTime) {
        SystemTime = systemTime;
    }
}
