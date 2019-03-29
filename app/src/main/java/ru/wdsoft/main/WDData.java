package ru.wdsoft.main;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.provider.Settings;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import ru.wdsoft.api.Config;
import ru.wdsoft.api.Session;
import ru.wdsoft.utils.LogUtils;
import ru.wdsoft.utils.Utils;

/**
 * Created by slaventii@mail.ru on 10.11.2017.
 */
public class WDData {

    /**
     * For logging purposes.
     */
    private final String LOG_PREFIX = this.getClass().getName() + " -- ";

    public static final String PREF_UPDATE_DB = "update_db";

    public static final String PREF_SESSION = "session";

    public static final String PREF_CFG = "cfg";

    public static final String PREF_SHOW_DATA_LOAD = "show_data_load";


    private static WDData ourInstance = new WDData();

    private Config mConfig;

    private Session mSession;

    private String mDeviceToken;

    private File mDocsDir;

    private AppMode mAppMode = AppMode.CUSTOMER;


    public static WDData getInstance() {
        return ourInstance;
    }

    private WDData() {}

    public void setMode(AppMode mode){
        mAppMode = mode;
    }

    public AppMode getMode(){
        return mAppMode;
    }

    public void setDbUpdateNeeded(Context ctx, boolean val){

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(ctx);

        sp.edit().putBoolean(PREF_UPDATE_DB, val).commit();

    }

    public boolean DbUpdateNeeded(Context ctx){

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(ctx);

        return sp.getBoolean(PREF_UPDATE_DB, false);

    }

    public void setSession(Context ctx, Session session){

        mSession = session;
        setSerializable(ctx, session, PREF_SESSION);

    }

    public Session getSession(Context ctx){

        if (mSession == null){
            mSession = getSerializable(ctx, PREF_SESSION, Session.class);
        }

        return mSession;

    }

    public void clear(){
        mConfig = null;
        mSession = null;
        mDeviceToken = null;
        mDocsDir = null;
    }

    public void setConfig(Context ctx, Config config){

        mConfig = config;
        setSerializable(ctx, config, PREF_CFG);

    }

    public Config getConfig(Context ctx){

        if (mConfig == null){
            mConfig = getSerializable(ctx, PREF_CFG, Config.class);
        }

        return mConfig;
    }

    public void setSerializable(Context ctx, Serializable obj, String pref){

        ObjectMapper mapper = new ObjectMapper();

        try {

            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(ctx);

            if (obj == null) {
                sp.edit().remove(pref).apply();
            } else {
                String strCfg = mapper.writeValueAsString(obj);
                sp.edit().putString(pref, strCfg).commit();
            }

        } catch (JsonProcessingException e) {
            LogUtils.debugErrorLog(LOG_PREFIX, e);
        }

    }

    public <T> T getSerializable(Context ctx, String pref,  Class<T> valueType){

        T obj = null;

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(ctx);

        String strCfg = sp.getString(pref, "");

        if (Utils.stringsNotEmpty(strCfg)) {

            ObjectMapper mapper = new ObjectMapper();

            try {
                obj = mapper.readValue(strCfg, valueType);
            } catch (IOException e) {
                LogUtils.debugErrorLog(LOG_PREFIX, e);
            }

        }

        return obj;
    }

    public String getUserId(){

        if (mSession != null){
            return mSession.getUserId();
        } else{
            return null;
        }
    }

    public String getUserName(){

        if (mSession != null){
            return mSession.getUserName();
        } else{
            return null;
        }
    }

    public String getUserEmail(){

        if (mSession != null){
            return mSession.getEmail();
        } else{
            return null;
        }
    }

    public String getUserPhone(){

        if (mSession != null){
            return mSession.getPhone();
        } else{
            return null;
        }
    }

    public String getDeviceId(Context ctx){

        if (!Utils.stringsNotEmpty(mDeviceToken)){
            mDeviceToken = Settings.Secure.getString(ctx.getContentResolver(),
                    Settings.Secure.ANDROID_ID);
        }

        return mDeviceToken;
    }

    public File getDocsDir(Context context){

        if (mDocsDir == null){
            mDocsDir = new File(context.getFilesDir(), "docs");
            mDocsDir.setWritable(true, false);
        }

        return mDocsDir;

    }
}
