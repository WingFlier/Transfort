package ru.wdsoft.ui.base;


import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.view.View;

import java.util.ArrayList;

import ru.wdsoft.api.ApiClient;
import ru.wdsoft.api.ApiResponse;
import ru.wdsoft.db.WDDbHelper;
import ru.wdsoft.main.WDData;
import ru.wdsoft.ui.datafield.DataField;
import ru.wdsoft.utils.DialogUtils;
import ru.wdsoft.utils.LogUtils;

/**
 * Created by Botezatu Veaceslav on 11.03.2016.
 */
public class WDFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<Object>, WDActivity.R24Event,
        View.OnClickListener{

    private final String LOG_PREFIX = this.getClass().getName() + " -- ";

    private ArrayList<BroadcastReceiver> mReceivers;

    private boolean mServiceConnectedFired = false;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        if (getActivity() instanceof WDActivity){
            WDActivity activity = getR24Activity();
            activity.setListner(this);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
//        destroyReceivers();
    }

    @Override
    public void onResume() {

        super.onResume();

//        BroadcastReceiver receiver = new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                if (isAdded() && !mServiceConnectedFired){
//                    onServiceConnected();
//                }
//            }
//        };
//
//        initReceiver(receiver, WDActivity.MSG_SRV_CONNECTED);
    }

    protected void initGui(View view){

    }

    protected void setData(){

    }

    protected void setTitle(String title){
        getR24Activity().setTitle(title);

    }

    protected String getTitle(){
        return getR24Activity().getR24Title();
    }

//    protected WDService getService(){
//        return getR24Activity().getService();
//    }

    protected WDActivity getR24Activity(){
        return (WDActivity)getActivity();
    }


    protected void initLoader(int id){
        initLoader(id, new Bundle());
    }

    protected void initLoader(int id, Bundle args){
        getLoaderManager().initLoader(id, args, this).forceLoad();
    }

    protected void destroyLoader(int id){
        getLoaderManager().destroyLoader(id);
    }

    protected Object loaderBackground(final int id, final Bundle args){return null;}

    protected void loaderFinished(Loader<Object> loader, Object data){}

//    protected void onBackPressed(){}

    @Override
    public Loader<Object> onCreateLoader(final int id, final Bundle args) {

        if (!isAdded()) return null;

        return new AsyncTaskLoader<Object>(getActivity()) {
            @Override
            public Object loadInBackground() {

                if (!isAdded()) return null;

                try {
                    return loaderBackground(id, args);
                }catch (Exception e){
                    LogUtils.debugErrorLog(LOG_PREFIX, e);
                }

                return  null;
            }
        };
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {

        if (!isAdded()) return;

//        loaderFinished(loader, data);


        if (data != null && data instanceof ApiResponse) {

            ApiResponse response = (ApiResponse) data;

            if (response != null && response.isError()) {

                DialogUtils.showErrorDialog(getContext(), response.getResponseMessage());

//                int code = response.getResponseCode();
//
//                switch (code){
//
//                    case 401:
////                        getData().setUser(this, null);
////                        getData().setUserProfile(this, null);
//                        if (isAdded()){
//                            getR24Activity().finish();
//                            Intent intent = new Intent(getContext(), LoginActivity.class);
//                            startActivity(intent);
//                        }
//                        break;
//
//                    case 601:
//
//                        DialogInterface.OnClickListener posClick = new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                final String appPackageName = getContext().getPackageName(); // getPackageName() from Context or Activity object
//                                try {
//                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
//                                } catch (android.content.ActivityNotFoundException anfe) {
//                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
//                                }
//                            }
//                        };
//
//                        Utils.showAlertDialog(getContext(), getString(R.string.app_name),
//                                response.getErrorMessage(),posClick);
//
//                        break;
//
//                    default:
//                        Utils.showErrorDialog(getContext(), response.getErrorMessage());
//                        break;
//                }
            }
        }

        try {
            loaderFinished(loader, data);
        }catch (Exception e){
            LogUtils.debugErrorLog(LOG_PREFIX, e);
        }

        getLoaderManager().destroyLoader(loader.getId());
    }

    @Override
    public void onLoaderReset(Loader loader) {

    }

//    protected boolean fillField(final R24InputField fld, final String defVal) {
//
//        if (fld.getVisibility() == View.VISIBLE && !fld.hasValue()){
//
//            new Handler().post(new Runnable() {
//                @Override
//                public void run() {
//
//                    if (Utils.stringsNotEmpty(defVal)) {
//                        fld.setValue(defVal);
//                    }
//
//                    fld.showDialog();
//                }
//            });
//
//            return true;
//        }
//
//        return false;
//    }
//
//    protected boolean fillField(final R24InputField fld){
//        return fillField(fld, "");
//    }

    protected String checkField(DataField fld){

        String result = "";

        if (fld.getVisibility() == View.VISIBLE && fld.isObligatory() && !fld.hasValue()){
            result += fld.getCaption() + ", ";
        }

        return result;
    }

    protected void savePreferencesBundle(String key, Bundle preferences) {
        getR24Activity().savePreferencesBundle(key, preferences);
    }

    protected Bundle loadPreferencesBundle(String key) {
        return getR24Activity().loadPreferencesBundle(key);
    }

    protected WDData getData(){
        return getR24Activity().getData();
    }


    protected WDDbHelper getDb(){
        return getR24Activity().getDb();
    }

    protected ApiClient getApi(){
        return getR24Activity().getApi();
    }

    public boolean isTablet(){
        return getR24Activity().isTablet();
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    public void onServiceConnected() {
        mServiceConnectedFired = true;
    }


    public void initReceiver(BroadcastReceiver receiver, String filter){
        getActivity().registerReceiver(receiver, new IntentFilter(filter));

        if (mReceivers == null){
            mReceivers = new ArrayList<>();
        }

        mReceivers.add(receiver);
    }

    public void destroyReceivers(){

        try {

            if (isAdded()){
                if (mReceivers != null && mReceivers.size() > 0){

                    for (BroadcastReceiver receiver : mReceivers){
                        getActivity().unregisterReceiver(receiver);
                    }

                    mReceivers.clear();
                    mReceivers = null;

                }
            }
        } catch (Exception e){
            LogUtils.debugErrorLog(LOG_PREFIX, e);
        }
    }

    public void setPref(String key, int val){
        if (isAdded()) {
            getR24Activity().setPref(key, val);
        }
    }

    public void setPref(String key, boolean val){
        if (isAdded()) {
            getR24Activity().setPref(key, val);
        }
    }

    public void setPref(String key, float val){
        if (isAdded()) {
            getR24Activity().setPref(key, val);
        }
    }

    public void setPref(String key, String val){
        if (isAdded()) {
            getR24Activity().setPref(key, val);
        }
    }

    public int getPrefInt(String key){
        if (isAdded()) {
            return getR24Activity().getPrefInt(key);
        }
        return -1;
    }

    public int getPrefInt(String key, int def){
        if (isAdded()) {
            return getR24Activity().getPrefInt(key, def);
        }
        return def;
    }

    public boolean getPrefBool(String key, boolean def){
        if (isAdded()) {
            return getR24Activity().getPrefBool(key, def);
        }
        return def;
    }

    public String getPrefString(String key){
        if (isAdded()) {
            return getR24Activity().getPrefString(key);
        }
        return "";
    }

    public String getPrefString(String key, String def){
        if (isAdded()) {
            return getR24Activity().getPrefString(key, def);
        }
        return def;
    }

    public void setPref(String key, long val){
        if (isAdded()) {
            getR24Activity().setPref(key, val);
        }
    }

    public long getPrefLong(String key){
        if (isAdded()) {
            return getR24Activity().getPrefLong(key);
        }
        return -1;
    }

    public long getPrefLong(String key, long def){
        if (isAdded()) {
            return getR24Activity().getPrefLong(key, def);
        }
        return def;
    }

    @Override
    public void onClick(View v) {

    }

    public interface FragmentListener {
        void onBackPressed();
    }

}
