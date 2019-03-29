package ru.wdsoft.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.UUID;

import ru.wdsoft.utils.CastUtils;
import ru.wdsoft.utils.DateTimeUtils;
import ru.wdsoft.utils.LogUtils;
import ru.wdsoft.utils.Utils;

/**
 * Created by slaventii@mail.ru on 19.12.2017.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class ApiSerializable implements Serializable {

    /**
     * Для логирования
     */
    private final String LOG_PREFIX = this.getClass().getName() + " -- ";

    public String getUIName(){
        return "";
    }

    public boolean isChecked(){
        return false;
    }

    public void isChecked(boolean selected){
    }

    protected String fromJsonProp(UUID val){
        if (val == null) return "";
        return val.toString();
    }

    protected String fromJsonProp(String val){
        if (val == null) return "";
        return val;
    }

    protected Long fromJsonProp(Long val){
        if (val == null) return 0L;
        return val;
    }

    protected Integer fromJsonProp(Integer val){
        if (val == null) return 0;
        return val;
    }

    protected Boolean fromJsonProp(Boolean val){
        if (val == null) return false;
        return val;
    }

    protected Float fromJsonProp(Float val){
        if (val == null) return 0f;
        return val;
    }


    protected Long fromDateTimeJsonProp(String date){
        return CastUtils.toLong(date);
    }



    protected String toJsonProp(String val){

        if (Utils.stringsNotEmpty(val)){
            return val;
        }

        return null;
    }

    protected UUID toUUIDJsonProp(String id){

        if (!Utils.stringsNotEmpty(id)) return null;

        try{
            return UUID.fromString(id);
        }catch (Exception e){
            LogUtils.debugErrorLog(LOG_PREFIX, e);
            return null;
        }
    }

    protected String toDateTimeJsonProp(Long date){
        return CastUtils.toServerDateTime(date);
    }



//    protected Long toJsonProp(Long val){
//
//
//        return null;
//    }
//
//    protected Integer toJsonProp(Integer val){
//        return null;
//    }
//
//    protected Integer toJsonProp(Boolean val){
//        return null;
//    }

}
