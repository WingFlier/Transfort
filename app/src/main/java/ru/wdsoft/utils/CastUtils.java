package ru.wdsoft.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.UUID;

/**
 * Created by slaventii@mail.ru on 13.02.2019.
 */
public class CastUtils {

    public static final String UI_ONLY_DATE_PATTERN = "d MMMM ";
    public static final String UI_DATE_TIME_PATTERN_SHORT = "dd-MM-yyyy";
//    public static final String UI_DATE_TIME_PATTERN_LONG = "dd-MM-yyyy HH:mm";
    public static final String UI_DATE_TIME_PATTERN_LONG = "dd MMMM yyyy, EE HH:mm";

    public static final String SERVER_DATE_TIME_PATTERN_SHORT = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String SERVER_DATE_TIME_PATTERN_LONG = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSZZZZZ";


    public static <T> T getSerializable(String val, Class<T> valueType) {

        try {

            if (val != null && Utils.stringsNotEmpty(val.toString())) {

                return new ObjectMapper().readValue(val, valueType);
            }

        } catch (Exception e) {
//            Utils.debugErrorLog(LOG_PREFIX, e);
        }

        return null;
    }

    public static String toString(Serializable obj){

        String str = null;

        ObjectMapper mapper = new ObjectMapper();

        try {
            str = mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
//            Utils.debugErrorLog(LOG_PREFIX, e);
        }

        return str;
    }

    public static Integer toInteger(Integer val){

        if (val == null) return 0;

        return val;

    }

    public static String toString(String val){

        if (val == null) return "";

        return val;

    }

    public static String fromString(String val){

        if (val != null) return val;

        return null;
    }

    public static Long fromLong(Long val){

        if (val != null) return val;

        return null;
    }

    public static Integer fromInt(Integer val){

        if (val != null) return val;

        return null;
    }

    public static Long toLong(Long val){

        if (val == null) return 0L;

        return val;

    }

    public static String fromUUID(UUID id){

        if (id == null) {
            return "";
        }

        return id.toString();
    }

    public static UUID toUUID(String id){

        try{
            return UUID.fromString(id);
        }catch (Exception e){
//            Utils.debugErrorLog(LOG_PREFIX, e);
            return null;
        }

    }

    public static Long toLong(String src){

        if (src == null) {
            return 0L;
        }

        Date date = toDateTime(src);

        if (date != null){
            return date.getTime();
        } else {
            return 0L;
        }

    }

    public static Date toDateTime(String str) {

        if (!Utils.stringsNotEmpty(str)) return null;

        Date date;

//        String formatSrc;

        date = toDateTime(str, SERVER_DATE_TIME_PATTERN_LONG);
        if (date != null) return date;

        date = toDateTime(str, UI_DATE_TIME_PATTERN_LONG);
        if (date != null) return date;

        date = toDateTime(str, SERVER_DATE_TIME_PATTERN_SHORT);
        if (date != null) return date;

//        date = toDateTime(str, UI_DATE_TIME_PATTERN_SHORT);
//        if (date != null) return date;
//
//        if (str.length() > 25) {
//            formatSrc = SERVER_DATE_TIME_PATTERN_LONG;
//        } else if (str.length() > 21) {
//            formatSrc = UI_DATE_TIME_PATTERN_LONG;
//        } else if (str.length() > 20) {
//            formatSrc = SERVER_DATE_TIME_PATTERN_SHORT;
//        } else {
//            formatSrc = UI_DATE_TIME_PATTERN_SHORT;
//        }

        return date;

    }

    public static Date toDateTime(String str, String formatSrc) {

        if (str == null) return null;

        SimpleDateFormat sourceFormat = new SimpleDateFormat(formatSrc);

        try {

            Date parsed = sourceFormat.parse(str);
            return parsed;


        } catch (ParseException | NullPointerException e) {
//            Utils.debugErrorLog(LOG_PREFIX, e);
        }

        return null;
    }
    public static String toUIOnlyDate(Long dtime){
        return toDateTime(dtime, UI_ONLY_DATE_PATTERN, null);
    }

    public static String toUIDate(Long dtime){
        return toDateTime(dtime, UI_DATE_TIME_PATTERN_SHORT, null);
    }

    public static String toUIDateTime(Long dtime){
        return toDateTime(dtime, UI_DATE_TIME_PATTERN_LONG, null);
    }

    public static String toServerDateTime(Long dtime){
        return toDateTime(dtime, SERVER_DATE_TIME_PATTERN_SHORT, null);
    }

    public static String toServerDateTime(Long dtime, boolean short_pattern){

        String pattern = SERVER_DATE_TIME_PATTERN_LONG;
        if (short_pattern){
            pattern = SERVER_DATE_TIME_PATTERN_SHORT;
        }
        return toDateTime(dtime, pattern, null);
    }

    public static String toDateTime(Long dtime, String fromatDest, String timeZone) {

        if (dtime == null) return null;

        if (dtime == 0) return null;

        Date date = new Date(dtime);

        try {

            SimpleDateFormat destFormat = new SimpleDateFormat(fromatDest);

            if (Utils.stringsNotEmpty(timeZone)){
                destFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
            }

            return destFormat.format(date);

        } catch (NullPointerException e) {
//            Utils.debugErrorLog(LOG_PREFIX, e);
        }

        return null;
    }

}
