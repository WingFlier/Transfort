package ru.wdsoft.api;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import ru.wdsoft.utils.LogUtils;
import ru.wdsoft.utils.StreamUtils;
import ru.wdsoft.utils.Utils;

/**
 * Created by slaventii@mail.ru on 13.10.2017.
 */

public class ApiResponse {

    public static int OK = 0;
    public static int ERROR = -1;

    /**
     * For logging purposes.
     */
    private final String LOG_PREFIX = this.getClass().getName() + " -- ";

    private int mResponseCode;
    private String mResponseMessage = "";
    private String mResponseString = "";
    private byte[] mResponseContent;

    private WDResponse mWDResponse;

    public static ApiResponse errorResponse(String mes) {

        ApiResponse resp = new ApiResponse();
        resp.setResponseCode(ApiResponse.ERROR);
        resp.setResponseMessage(mes);

        return resp;
    }

    public static ApiResponse newResponse(int code, String mes) {

        ApiResponse resp = new ApiResponse();
        resp.setResponseString(code, mes);

        return resp;
    }

    public static ApiResponse newResponse(int code, byte[] content) {

        ApiResponse resp = new ApiResponse();
        resp.setResponseContent(code, content);

        return resp;
    }

    public String getResponseAsString(){

        if (getResponseContent() != null){

            try {
                return StreamUtils.decodeStream(new ByteArrayInputStream(getResponseContent()));
            } catch (OutOfMemoryError ome){
                LogUtils.debugErrorLog(LOG_PREFIX, ome);
            } catch (Exception e){
                LogUtils.debugErrorLog(LOG_PREFIX, e);
            }
        }

        return null;
    }

    public <T> T getValue(Class<T> valueType) {

        try {

            if (Utils.stringsNotEmpty(getResponseString())) {

                if (mWDResponse == null){
                    mWDResponse = new ObjectMapper().readValue(getResponseString(),
                            WDResponse.class);
                }

            } else if (getResponseContent() != null && getResponseContent().length > 0) {

                if (mWDResponse == null){
                    mWDResponse = new ObjectMapper().readValue(getResponseContent(),
                            WDResponse.class);
                }

            }

            if (mWDResponse != null){
                return mWDResponse.getValue(valueType);
            }

            return null;

        } catch (OutOfMemoryError ome) {

            LogUtils.debugErrorLog(LOG_PREFIX, ome);

            setResponseCode(ERROR);
            setResponseMessage("Не достаточно памяти для обработки ответа сервера");

        } catch (Exception e) {
            LogUtils.debugErrorLog(LOG_PREFIX, e);
        }

        return null;
    }

    public boolean isError() {
        return getResponseCode() != OK;
    }

    private byte[] getResponseContent() {
        return mResponseContent;
    }

    private void setResponseContent(int code, byte[] content) {

        mResponseContent = content;
        setResponseCode(code);

        try {

            mWDResponse = new ObjectMapper().readValue(getResponseContent(),
                    WDResponse.class);

            if (mWDResponse != null) {

                if (mWDResponse.getError() != null){
                    setResponseCode(mWDResponse.getError().getCode());
                    setResponseMessage(mWDResponse.getError().getMsg());
                }

            }

        } catch (IOException e) {
            LogUtils.debugErrorLog(LOG_PREFIX, e);
        }

    }

    private String getResponseString() {
        return mResponseString;
    }

    private void setResponseString(int code, String responseString) {

        mResponseString = responseString;
        setResponseCode(code);

        try {

            mWDResponse = new ObjectMapper().readValue(mResponseString,
                    WDResponse.class);

            if (mWDResponse != null) {

                if (mWDResponse.getError() != null){
                    setResponseCode(mWDResponse.getError().getCode());
                    setResponseMessage(mWDResponse.getError().getMsg());
                }

            }

        } catch (IOException e) {
            LogUtils.debugErrorLog(LOG_PREFIX, e);
        }

    }

    public int getResponseCode() {
        return mResponseCode;
    }

    private void setResponseCode(int code) {
        mResponseCode = code;
    }

    public String getResponseMessage() {
        return mResponseMessage;
    }

    private void setResponseMessage(String mes) {
        mResponseMessage = mes;
    }

}
