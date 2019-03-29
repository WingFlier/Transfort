package ru.wdsoft.ui.base;

import android.app.ActivityManager;
import android.app.LoaderManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.Loader;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import ru.wdsoft.R;
import ru.wdsoft.api.ApiClient;
import ru.wdsoft.api.ApiResponse;
import ru.wdsoft.db.WDDbHelper;
import ru.wdsoft.main.WDData;
import ru.wdsoft.main.WDService;
import ru.wdsoft.ui.login.LoginActivity;
import ru.wdsoft.utils.DialogUtils;
import ru.wdsoft.utils.LogUtils;
import ru.wdsoft.utils.Utils;

/**
 * Created by Botezatu Veaceslav on 10.03.2016.
 */
public class WDActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks {

    private final String LOG_PREFIX = this.getClass().getName() + " -- ";

    public static final String MSG_SRV_CONNECTED = "SERVICE_CONNECTED";
    public static final String ARG_STATUS_BAR_COLOR = "STATUS_BAR_COLOR";

    private static boolean mActive = false;

    private Intent intentSrv;
    private ServiceConnection mSrvConn;
    private WDService mService;
    private Boolean mBound = false;

    private Boolean mServiceDialogIsShown = false;

    private boolean mShowLoader = true;

    private R24Event mR24Event;

    BroadcastReceiver mReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        if (getIntent().hasExtra(ARG_STATUS_BAR_COLOR)){
            int color = getIntent().getIntExtra(ARG_STATUS_BAR_COLOR,
                    getResources().getColor(R.color.colorAccent));
            setStatusBarColor(color);
        }

        if (isTablet()) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        LoadingView.setActivity(this);

    }

    protected void setStatusBarColor(int color){

        // finally change the color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            Window window = this.getWindow();

            // clear FLAG_TRANSLUCENT_STATUS flag:
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

            window.setStatusBarColor(color);
        }
    }

    protected void onServiceConnected(){

//        Toast.makeText(this, "BROADCAST SENT SERVICE_CONNECTED", Toast.LENGTH_SHORT).show();

//        if (mR24Event != null){
//            mR24Event.onServiceConnected();
//        }

        Intent intentNCF = new Intent(MSG_SRV_CONNECTED);
        sendBroadcast(intentNCF);

    }

    @Override
    protected void onStart() {
        super.onStart();
        serviceConnect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mActive = false;
        destroyReceiver();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mActive = true;
        initReceiver();
    }

    public void bindR24Service(){

        intentSrv = new Intent(this, WDService.class);
        bindService(intentSrv, mSrvConn, 0);
    }

    public void unBindR24Service(){

        if (!mBound) return;

        unbindService(mSrvConn);
        mBound = false;

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        if (mR24Event != null){
            mR24Event.onBackPressed();
        } else {
            super.onBackPressed();
        }
    }

    protected void popBackStack(){

        FragmentManager fm = getSupportFragmentManager();

        if (fm.getBackStackEntryCount() > 1) {
            fm.popBackStack();
        } else {
            finish();
        }
    }

    private void serviceConnect(){

        mSrvConn = new ServiceConnection() {

            public void onServiceConnected(ComponentName name, IBinder binder) {

                LogUtils.debugLog(LOG_PREFIX, "WDActivity onServiceConnected");
                mService = ((WDService.R24Binder) binder).getService();
                mBound = true;

                WDActivity.this.onServiceConnected();

            }

            public void onServiceDisconnected(ComponentName name) {
                LogUtils.debugLog(LOG_PREFIX, "WDActivity onServiceDisconnected");
                mBound = false;
            }
        };

    }


    public WDService getService(){
        return mService;
    }

    public ApiClient getApi(){
        return ApiClient.getInstance(this);
    }

    public ApiClient getApi(boolean resetApi){
        return ApiClient.getInstance(this, resetApi);
    }


    public WDDbHelper getDb(){
        return WDDbHelper.getInstance(this);
    }

    public WDData getData(){
        return WDData.getInstance();
    }

    @Override
    public Loader onCreateLoader(final int id, final Bundle args) {

        if (!isActive()) {
            return null;
        }

        if (mShowLoader){
            LoadingView.startProgress(getResources().getString(R.string.Loading));
        }

        return new android.content.AsyncTaskLoader(this) {

            @Override
            public Object loadInBackground() {

                try{
                    return loaderBackground(id, args);
                } catch (final Exception e){
                    LogUtils.debugErrorLog(LOG_PREFIX, e);
                    return ApiResponse.errorResponse(e.getMessage());
                }
            }

        };
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {

        if (!isActive()) {
            return;
        }

        if (mShowLoader){
            LoadingView.stopProgress();
        }

        if (data != null && data instanceof ApiResponse) {

            ApiResponse response = (ApiResponse) data;

            if (response != null && response.isError()) {
                DialogUtils.showErrorDialog(this, response.getResponseMessage());
            }
        }

        loaderFinished(loader, data);

        getLoaderManager().destroyLoader(loader.getId());
    }

    @Override
    public void onLoaderReset(Loader loader) {}

    protected Object loaderBackground(final int id, final Bundle args){return null;}

    protected void loaderFinished(Loader loader, Object data){}

    protected void initLoader(int id){
        initLoader(id, new Bundle());
    }

    protected void initLoader(int id, Bundle args){

        if (getLoader(id) != null) {
            destroyLoader(id);
        }

        getLoaderManager().initLoader(id, args, this).forceLoad();
    }

    protected void destroyLoader(int id){
        getLoaderManager().destroyLoader(id);
    }

    protected Loader getLoader(int id){
        return getLoaderManager().getLoader(id);
    }

    protected boolean isActive(){
        return mActive;
    }

    public boolean isTablet(){
        return getResources().getBoolean(R.bool.isTablet);
    }

    public void switchContent(Fragment fragment) {
        switchContent(fragment, true, false);
    }

    public void switchContent(Fragment fragment, boolean addToBack, boolean clearBackStack) {

        FragmentTransaction t = getSupportFragmentManager().beginTransaction();

        if (clearBackStack) {
            getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }

        if (addToBack) {
            t.addToBackStack(fragment.getClass().getName());
        }


        t.replace(R.id.container, fragment).commit();

    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    protected void startR24Service(){

//        if (!isMyServiceRunning(WDService.class)) {
            Intent intent = new Intent(this, WDService.class);
            stopService(intent);
            startService(intent);
//        }
    }

    protected void stopR24Service(){
        Intent intent = new Intent(this, WDService.class);
        stopService(intent);
    }

    protected void enableHomeButton(boolean enabled){
        getSupportActionBar().setHomeButtonEnabled(enabled);
        getSupportActionBar().setDisplayHomeAsUpEnabled(enabled);
        //getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_keyboard_arrow_down_black_24dp);
    }

    protected void setTitle(String title, String subtitle){
        getSupportActionBar().setTitle(title);

        if (Utils.stringsNotEmpty(subtitle)) {
            getSupportActionBar().setSubtitle(subtitle);
        }

    }

    protected void setTitle(String title){
        setTitle(getString(R.string.app_name), title);
    }

    public String getR24Title(){
        return getSupportActionBar().getTitle().toString();
    }


    public void setListner(R24Event listner){

        mR24Event = listner;

////        Toast.makeText(this, "WDActivity setListner We are here", Toast.LENGTH_SHORT).show();
        if (mService != null && mBound){
////            Toast.makeText(this, "WDActivity setListner onServiceConnected", Toast.LENGTH_SHORT).show();
            mR24Event.onServiceConnected();
        }

    }

    public void clearAllPrefs(){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        sp.edit().clear().commit();
    }

    public void setPref(String key, int val){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        sp.edit().putInt(key, val).apply();
    }

    public void setPref(String key, boolean val){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        sp.edit().putBoolean(key, val).apply();
    }

    public void setPref(String key, float val){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        sp.edit().putFloat(key, val).apply();
    }

    public void setPref(String key, String val){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        sp.edit().putString(key, val).apply();
    }

    public void setPref(String key, long val){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        sp.edit().putLong(key, val).apply();
    }

    public long getPrefLong(String key){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        return sp.getLong(key, -1);
    }

    public long getPrefLong(String key, long def){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        return sp.getLong(key, def);
    }

    public int getPrefInt(String key){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        return sp.getInt(key, -1);
    }

    public int getPrefInt(String key, int def){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        return sp.getInt(key, def);
    }

    public boolean getPrefBool(String key, boolean def){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        return sp.getBoolean(key, def);
    }

    public String getPrefString(String key){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        return sp.getString(key, "");
    }

    public String getPrefString(String key, String def){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        return sp.getString(key, def);
    }


    public float getPrefFloat(String key){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        return sp.getFloat(key, 0f);
    }

    public Map<String, ?> getPrefAll(){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        return sp.getAll();
    }

    public void showLoader(boolean val){
        mShowLoader = val;
    }

    public void savePreferencesBundle(String key, Bundle preferences) {

        SharedPreferences sp = getSharedPreferences(key, Context.MODE_PRIVATE);
        sp.edit().clear().apply();

        Set<String> keySet = preferences.keySet();
        Iterator<String> it = keySet.iterator();
        String prefKeyPrefix = key + "$";

        while (it.hasNext()){
            String bundleKey = it.next();
            Object o = preferences.get(bundleKey);

            if (o == null){
                //editor.remove(prefKeyPrefix + bundleKey);
            } else if (o instanceof Integer){
                sp.edit().putInt(prefKeyPrefix + bundleKey, (Integer) o).apply();
            } else if (o instanceof Long){
                sp.edit().putLong(prefKeyPrefix + bundleKey, (Long) o).apply();
            } else if (o instanceof Boolean){
                sp.edit().putBoolean(prefKeyPrefix + bundleKey, (Boolean) o).apply();
            } else if (o instanceof CharSequence){
                sp.edit().putString(prefKeyPrefix + bundleKey, (o).toString()).apply();;
            } else if (o instanceof Bundle){
                savePreferencesBundle(prefKeyPrefix + bundleKey, ((Bundle) o));
            }
        }
    }

    public Bundle loadPreferencesBundle(String key) {

        Bundle bundle = new Bundle();
        SharedPreferences sp = getSharedPreferences(key, Context.MODE_PRIVATE);
        Map<String, ?> all = sp.getAll();
        Iterator<String> it = all.keySet().iterator();
        String prefKeyPrefix = key + "$";

        while (it.hasNext()) {

            String prefKey = it.next();

            if (prefKey.startsWith(prefKeyPrefix)) {
                String bundleKey = prefKey.replace(prefKeyPrefix, "");


                Object o = all.get(prefKey);
                if (o == null) {
                    // Ignore null keys
                } else if (o instanceof Integer) {
                    bundle.putInt(bundleKey, (Integer) o);
                } else if (o instanceof Long) {
                    bundle.putLong(bundleKey, (Long) o);
                } else if (o instanceof Boolean) {
                    bundle.putBoolean(bundleKey, (Boolean) o);
                } else if (o instanceof CharSequence) {
                    bundle.putString(bundleKey, o.toString());
                }
            }
        }
        return bundle;

    }

    private void initReceiver(){

        mReceiver = new R24Receiver();

        registerReceiver(mReceiver, new IntentFilter("API_ERROR"));
    }

    private void destroyReceiver(){

        try {
            if (mReceiver != null){
                unregisterReceiver(mReceiver);
            }
        } catch (Exception e){
            LogUtils.debugErrorLog(LOG_PREFIX, e);
        }
    }

    class R24Receiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent != null) {

                int code = intent.getIntExtra("code", -1);

                switch (code){

                    case 401:
                        if (isActive() && !mServiceDialogIsShown){

                            DialogInterface.OnClickListener posClick = new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    finish();
                                    setPref(WDData.PREF_SHOW_DATA_LOAD, true);
                                    setPref("PASSW", "");

                                    startActivity(new Intent(WDActivity.this, LoginActivity.class));

                                    mServiceDialogIsShown = false;

                                }
                            };

                            DialogUtils.showAlertDialog(WDActivity.this, getString(R.string.app_name),
                                    "Данные устарели. Необходимо заново авторизоваться",posClick);

                            mServiceDialogIsShown = true;
                        }
                        break;

                    case 601:
                        if (isActive() && !mServiceDialogIsShown){
                            DialogInterface.OnClickListener posClick = new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    mServiceDialogIsShown = false;
                                    final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                                    try {
                                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                                    } catch (android.content.ActivityNotFoundException anfe) {
                                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                                    }
                                }
                            };

                            DialogUtils.showAlertDialog(WDActivity.this, getString(R.string.app_name),
                                    "Версия приложения устарела! Необходимо обновить.",posClick);

                            mServiceDialogIsShown = true;

                        }
                        break;

                    default:
                        break;
                }
            }
        }
    }

    public interface R24Event {
        void onBackPressed();
        void onServiceConnected();
    }


}
