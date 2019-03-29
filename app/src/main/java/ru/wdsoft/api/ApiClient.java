package ru.wdsoft.api;

import android.content.Context;
import android.content.Intent;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.UUID;

import ru.wdsoft.R;
import ru.wdsoft.main.WDData;
import ru.wdsoft.utils.CryptoUtils;
import ru.wdsoft.utils.DateTimeUtils;
import ru.wdsoft.utils.LogUtils;
import ru.wdsoft.utils.StreamUtils;
import ru.wdsoft.utils.Utils;

/**
 * Created by slaventii@mail.ru on 10.03.2016.
 */
public class ApiClient {

    /**
     * Для логирования
     */
    private final String LOG_PREFIX = this.getClass().getName() + " -- ";

    private static Context mCtx;

    private static final int HTTP_TIMEOUT = 60000;

    private static final String HOST_WIALON = "https://hst-api.wialon.com/wialon/";

    // тестовый сервер
    private String HOST = "http://91.242.34.72:8080/api/v1.0/endpoint";

//    // рабочий сервер
//    private String HOST = "http://91.242.34.72:8080/api/transfort";

    private String API_KEY = "";
//    private String API_KEY = "379084b7-0938-44f8-ab65-1357475ba64d";
//    private String API_KEY = "d7293a90-9ebc-414e-ab2b-656bd0b649fe";

    private static ApiClient instance = null;

    public static ApiClient getInstance(Context context, boolean reset) {

        if (instance == null || reset) {
            instance = new ApiClient(context.getApplicationContext());
        }

        return instance;
    }

    public static ApiClient getInstance(Context context) {
        return getInstance(context, false);
    }

    public static ApiClient getInstance() {
        return instance;
    }

    private ApiClient(Context context) {
        mCtx = context;
    }

    private String getHost() {
        return HOST;
    }

    private WDData getData() {
        return WDData.getInstance();
    }

    public ApiResponse apiRequest(String type, String strBody) {


        if (getData() == null) return ApiResponse.errorResponse("Переменные окружения не заданы");

        Session session = getData().getSession(mCtx);

        if (session == null) return ApiResponse.errorResponse("Активная сессия не найдена");

        JSONObject params = null;
        JSONArray jaBody = null;
        JSONObject joBody = null;

        if (strBody == null) {

            params = getParamsHeader(null);

        } else {

            boolean convert_to_json_obj = false;

            try {

                jaBody = new JSONArray(strBody);

                params = getParamsHeader(jaBody.toString());

            } catch (JSONException e) {
                convert_to_json_obj = true;
                //params = getParamsHeader(null);
//                LogUtils.debugErrorLog(LOG_PREFIX, e);
            }

            if (convert_to_json_obj) {
                try {

                    joBody = new JSONObject(strBody);

                    params = getParamsHeader(joBody.toString());

                } catch (JSONException e) {
                    params = getParamsHeader(null);
//                    LogUtils.debugErrorLog(LOG_PREFIX, e);
                }
            }
        }

        try {

            if (jaBody != null) {
                params.put("body", jaBody);
            } else if (joBody != null) {
                params.put("body", joBody);
            }

            params.put("type", type);
            params.put("session", session.getSessionKey());

            return httpRequest(getHost(), "POST", params.toString());

        } catch (Exception e) {

            LogUtils.debugErrorLog(LOG_PREFIX, e);
            return ApiResponse.errorResponse(e.getMessage());

        }

    }

    public ApiResponse apiRequest(String type, Serializable body) {

        String strBody = null;

        ObjectMapper mapper = new ObjectMapper();

        try {
            strBody = mapper.writeValueAsString(body);
        } catch (JsonProcessingException e) {
            LogUtils.debugErrorLog(LOG_PREFIX, e);
        }

        return apiRequest(type, strBody);
    }

    public ApiResponse apiRequest(String type) {
        return apiRequest(type, null);
    }

    public ApiResponse Auth(String UserName, String Password) {

        if (getData() == null) return null;

        Config cfg = getData().getConfig(mCtx);

        if (cfg == null) return null;

        String hDate = cfg.getSystemTime("yyyy-MM-dd");

        JSONObject body = new JSONObject();

        try {

            String hUserName = CryptoUtils.md5(UserName);
            body.put("Login", hUserName);

            String hPassword = CryptoUtils.md5(CryptoUtils.md5(Password) + hDate + API_KEY);
            body.put("Password", hPassword);

            body.put("ShowProfile", true);

            JSONObject params = getParamsHeader(body.toString());

            params.put("body", body);

            params.put("type", "Membership.Auth");

            return httpRequest(getHost(), "POST", params.toString());

        } catch (Exception e) {

            LogUtils.debugErrorLog(LOG_PREFIX, e);
            return ApiResponse.errorResponse(mCtx.getString(R.string.auth_err));

        }
    }


    public ApiResponse passwordRecovery(String email) {
        try {
            JSONObject body = new JSONObject();
            body.put("EMail", CryptoUtils.md5(email));
            JSONObject params = getParamsHeader(body.toString());
            params.put("body", body);
            params.put("type", "Membership.PasswordRecovery");
            return httpRequest(getHost(), "POST", params.toString());
        } catch (Exception e) {
            LogUtils.debugErrorLog(LOG_PREFIX, e);
            return ApiResponse.errorResponse(mCtx.getString(R.string.recovery_err));
        }
    }

    public ApiResponse changePassword(String confirmCode, String newPassword, String email) {
        try {
            JSONObject body = new JSONObject();
            body.put("EMail", CryptoUtils.md5(email));
            body.put("Password", CryptoUtils.md5(newPassword));
            body.put("ConfirmCode", confirmCode);
            JSONObject params = getParamsHeader(body.toString());
            params.put("body", body);
            params.put("type", "Membership.PasswordReset");
            return httpRequest(getHost(), "POST", params.toString());
        } catch (Exception e) {
            LogUtils.debugErrorLog(LOG_PREFIX, e);
            return ApiResponse.errorResponse("Ошибка смены пароля");
        }
    }

    public ApiResponse registerUser(String email, String fullName, boolean isAccessGranted, String name, String password, String phoneNumber) {
        try {
            JSONObject body = new JSONObject();
            body.put("EMail", email);
            body.put("FullName", fullName);
            body.put("IsAccessGranted", isAccessGranted);
            body.put("Name", name);
            body.put("Password", CryptoUtils.md5(password));
            body.put("PhoneNumber", phoneNumber);
            JSONObject params = getParamsHeader(body.toString());
            params.put("body", body);
            params.put("type", "Membership.UserRegistration");
            return httpRequest(getHost(), "POST", params.toString());
        } catch (Exception e) {
            LogUtils.debugErrorLog(LOG_PREFIX, e);
            return ApiResponse.errorResponse("Ошибка создания пользователя");
        }
    }

    public ApiResponse registerCompany(String ownershipType, String name, String legalName, long inn, long kpp,
                                       String legalAddress, String adress, boolean isCustomer, boolean isSupplier, boolean withVat,
                                       String userId, String phone, String email) {
        try {
            JSONObject body = new JSONObject();
            body.put("Adress", adress);
            body.put("Email", email);
            body.put("INN", inn);
            body.put("KPP", kpp);
            body.put("IsCustomer", isCustomer);
            body.put("IsSupplier", isSupplier);
            body.put("LegalAddress", legalAddress);
            body.put("LegalName", legalName);
            body.put("Name", name);
            body.put("OwnershipType", ownershipType);
            body.put("Phone", phone);
            body.put("UserId", userId);
            body.put("WithVAT", withVat);
            JSONObject params = getParamsHeader(body.toString());
            params.put("body", body);
            params.put("type", "Dictionary.AddCompany");
            return httpRequest(getHost(), "POST", params.toString());
        } catch (Exception e) {
            LogUtils.debugErrorLog(LOG_PREFIX, e);
            return ApiResponse.errorResponse("Ошибка создания компании");
        }
    }

    public ApiResponse getTypes() {
        try {
            JSONObject body = new JSONObject();
            JSONObject params = getParamsHeader(body.toString());
            params.put("body", body);
            params.put("type", "Dictionary.GetOwnershipTypes");
            return httpRequest(getHost(), "POST", params.toString());
        } catch (Exception e) {
            LogUtils.debugErrorLog(LOG_PREFIX, e);
            return ApiResponse.errorResponse("Ошибка");
        }
    }

    public ApiResponse getSuggestions(String name)
    {
        try {
            JSONObject body = new JSONObject();
            body.put("query", name);
            return daDataRequest("https://suggestions.dadata.ru/suggestions/api/4_1/rs/suggest/party", "POST", body.toString());
        } catch (Exception e) {
            LogUtils.debugErrorLog(LOG_PREFIX, e);
            return ApiResponse.errorResponse("Ошибка");
        }
    }


    public ApiResponse GetConfig() {

        JSONObject params = getParamsHeader(null);

        try {

            params.put("type", "System.GetConfig");

            return httpRequest(getHost(), "POST", params.toString());

        } catch (Exception e) {

            LogUtils.debugErrorLog(LOG_PREFIX, e);
            return ApiResponse.errorResponse(mCtx.getString(R.string.auth_err));

        }
    }

    private JSONObject getParamsHeader(String body) {

        JSONObject params = new JSONObject();

        try {

            String dt = DateTimeUtils.getDateTimeAsString("yyyy-MM-dd'T'HH:mm:ss.SSSSSSZZZZZ", null);
            String from = "android";
            String mn = UUID.randomUUID().toString();


            params.put("dt", dt);
            params.put("from", from);
            params.put("mn", mn);

            String sign;

            if (Utils.stringsNotEmpty(body)) {


                String cleanBody = body.replaceAll("[^0-9A-Za-zА-Яа-я]", "");
                sign = CryptoUtils.md5(CryptoUtils.md5(cleanBody) + API_KEY + mn + dt);

            } else {
                sign = CryptoUtils.md5(API_KEY + mn + dt);
            }

            params.put("sign", sign);

        } catch (Exception e) {
            LogUtils.debugErrorLog(LOG_PREFIX, e);
        }

        return params;
    }

    public InputStream httpStreamRequest(String url) {

        if (!Utils.stringsNotEmpty(url)) return null;

        InputStream dataStream;

        try {
            URL u = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) u.openConnection();

            LogUtils.debugLog(LOG_PREFIX, "Requesting: " + url);

            conn.setConnectTimeout(HTTP_TIMEOUT);
            conn.setReadTimeout(HTTP_TIMEOUT);

            conn.connect();

            Integer httpStatus = conn.getResponseCode();
            if (httpStatus < 200 || httpStatus > 299) {
                throw new IOException("Server response code was " + httpStatus.toString() + " instead of 2XX family (OK)!");
            }

            dataStream = conn.getInputStream();
            dataStream.close();
            conn.disconnect();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return dataStream;
    }

    private ApiResponse httpRequest(String url, String httpMethod, String params) {

        URL u;
        try {
            u = new URL(url.trim());
        } catch (MalformedURLException e) {

            e.printStackTrace();

            LogUtils.debugLog(LOG_PREFIX, "MalformedURLException! msg=" + e.getMessage() + "\n" + url.trim());

            return ApiResponse.errorResponse("MalformedURLException! " + e.getMessage());
        }


        HttpURLConnection conn = null;

        InputStream stream = null;
        LogUtils.debugLog(LOG_PREFIX, "urlJson: " + url + "/" + (params == null ? "" : params));

        try {

            conn = (HttpURLConnection) u.openConnection();

            conn.addRequestProperty("Accept", "application/json");

            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);

            conn.setRequestMethod(httpMethod);

            boolean haveParams = Utils.stringsNotEmpty(params);

            if (haveParams) {
                conn.addRequestProperty("Content-type", "application/json");
                conn.addRequestProperty("Connection", "close");

                DataOutputStream out = new DataOutputStream(conn.getOutputStream());
                out.write(params.getBytes("UTF-8"));

            }

            conn.connect();
            LogUtils.debugLog(LOG_PREFIX, "HTTR RESPONSE CODE = " + Integer.toString(conn.getResponseCode()) + ": " + conn.getResponseMessage());

            /**
             * All HTTP codes of 2XX pattern mean success:
             * https://en.wikipedia.org/wiki/List_of_HTTP_status_codes#2xx_Success
             * API sometimes return success codes that differ from 200 (201 user created
             * on account sign-up, etc.)
             */
            if (conn.getResponseCode() < 200 || conn.getResponseCode() > 299) {

                int code = conn.getResponseCode();
                Intent intent = new Intent("API_ERROR");
                intent.putExtra("code", code);

                if (code == 401) {
                    mCtx.sendBroadcast(intent);
                } else if (code == 601) {
                    mCtx.sendBroadcast(intent);
                }

                return ApiResponse.errorResponse(conn.getResponseMessage());
            }

            stream = conn.getInputStream();

        } catch (SocketTimeoutException e) {

            LogUtils.debugErrorLog(LOG_PREFIX, e);
            return ApiResponse.errorResponse(e.getMessage());

        } catch (IOException e) {

            LogUtils.debugErrorLog(LOG_PREFIX, e);
            return ApiResponse.errorResponse(e.getMessage());
        }

        try {

            //byte[] content = Utils.streamToByteArray(stream);
            String content = StreamUtils.decodeStream(stream);

            if (content != null) {

                LogUtils.debugLog(LOG_PREFIX, "Incoming stream: " + content);

//                try {
//                    String strResponse = Utils.decodeStream(new ByteArrayInputStream(content));
//                    Utils.debugLog(LOG_PREFIX, "Incoming stream: " + strResponse);
//                } catch (OutOfMemoryError ome){
//                    Utils.debugErrorLog(LOG_PREFIX, ome);
//                    return ApiResponse.errorResponse(ome.getMessage());
//                } catch (Exception e){
//                    Utils.debugErrorLog(LOG_PREFIX, e);
//                    return ApiResponse.errorResponse(e.getMessage());
//                }

                try {
                    return ApiResponse.newResponse(ApiResponse.OK, content);
                } catch (Exception e) {
                    LogUtils.debugErrorLog(LOG_PREFIX, e);
                    return ApiResponse.errorResponse(e.toString());
                }
            }

            return ApiResponse.newResponse(ApiResponse.OK, "");

        } catch (Exception e) {
            LogUtils.debugLog(LOG_PREFIX, e.getLocalizedMessage());
            return ApiResponse.errorResponse(e.getMessage());
        }
    }

    private ApiResponse daDataRequest(String url, String httpMethod, String params) {

        URL u;
        try {
            u = new URL(url.trim());
        } catch (MalformedURLException e) {

            e.printStackTrace();

            LogUtils.debugLog(LOG_PREFIX, "MalformedURLException! msg=" + e.getMessage() + "\n" + url.trim());

            return ApiResponse.errorResponse("MalformedURLException! " + e.getMessage());
        }


        HttpURLConnection conn = null;

        InputStream stream = null;
        LogUtils.debugLog(LOG_PREFIX, "urlJson: " + url + "/" + (params == null ? "" : params));

        try {

            conn = (HttpURLConnection) u.openConnection();

            conn.addRequestProperty("Accept", "application/json");
            conn.addRequestProperty("Authorization", "Token af399c90c802189b0297bce99952da444f4fbc5d");

            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);

            conn.setRequestMethod(httpMethod);

            boolean haveParams = Utils.stringsNotEmpty(params);

            if (haveParams) {
                conn.addRequestProperty("Content-type", "application/json");
                conn.addRequestProperty("Connection", "close");

                DataOutputStream out = new DataOutputStream(conn.getOutputStream());
                out.write(params.getBytes("UTF-8"));

            }

            conn.connect();
            LogUtils.debugLog(LOG_PREFIX, "HTTR RESPONSE CODE = " + Integer.toString(conn.getResponseCode()) + ": " + conn.getResponseMessage());

            /**
             * All HTTP codes of 2XX pattern mean success:
             * https://en.wikipedia.org/wiki/List_of_HTTP_status_codes#2xx_Success
             * API sometimes return success codes that differ from 200 (201 user created
             * on account sign-up, etc.)
             */
            if (conn.getResponseCode() < 200 || conn.getResponseCode() > 299) {

                int code = conn.getResponseCode();
                Intent intent = new Intent("API_ERROR");
                intent.putExtra("code", code);

                if (code == 401) {
                    mCtx.sendBroadcast(intent);
                } else if (code == 601) {
                    mCtx.sendBroadcast(intent);
                }

                return ApiResponse.errorResponse(conn.getResponseMessage());
            }

            stream = conn.getInputStream();

        } catch (SocketTimeoutException e) {

            LogUtils.debugErrorLog(LOG_PREFIX, e);
            return ApiResponse.errorResponse(e.getMessage());

        } catch (IOException e) {

            LogUtils.debugErrorLog(LOG_PREFIX, e);
            return ApiResponse.errorResponse(e.getMessage());
        }

        try {

            //byte[] content = Utils.streamToByteArray(stream);
            String content = StreamUtils.decodeStream(stream);

            if (content != null) {

                LogUtils.debugLog(LOG_PREFIX, "Incoming stream: " + content);

//                try {
//                    String strResponse = Utils.decodeStream(new ByteArrayInputStream(content));
//                    Utils.debugLog(LOG_PREFIX, "Incoming stream: " + strResponse);
//                } catch (OutOfMemoryError ome){
//                    Utils.debugErrorLog(LOG_PREFIX, ome);
//                    return ApiResponse.errorResponse(ome.getMessage());
//                } catch (Exception e){
//                    Utils.debugErrorLog(LOG_PREFIX, e);
//                    return ApiResponse.errorResponse(e.getMessage());
//                }

                try {
                    return ApiResponse.newResponse(ApiResponse.OK, content);
                } catch (Exception e) {
                    LogUtils.debugErrorLog(LOG_PREFIX, e);
                    return ApiResponse.errorResponse(e.toString());
                }
            }

            return ApiResponse.newResponse(ApiResponse.OK, "");

        } catch (Exception e) {
            LogUtils.debugLog(LOG_PREFIX, e.getLocalizedMessage());
            return ApiResponse.errorResponse(e.getMessage());
        }
    }

}
