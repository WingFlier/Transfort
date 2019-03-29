package ru.wdsoft.ui.base;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.view.View;

import ru.wdsoft.db.WDDbHelper;
import ru.wdsoft.main.WDData;
import ru.wdsoft.utils.DialogUtils;
import ru.wdsoft.utils.LogUtils;
import ru.wdsoft.api.ApiResponse;
import ru.wdsoft.ui.datafield.DataField;

import static ru.wdsoft.db.WDDbHelper.getInstance;

/**
 * Created by slaventii@mail.ru on 17.01.2017.
 */
public class WDDialogFragment extends DialogFragment  implements
        LoaderManager.LoaderCallbacks<Object> {

    private final String LOG_PREFIX = this.getClass().getName() + " -- ";

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return createDialog();
    }

    private Dialog createDialog(){

        View view = getDialogView();

        if (view != null){

            initGui(view);

            initData();

            return getDialog(view);

        }

        return null;

    }

    protected void initGui(View view){

    }

    protected void initData(){

    }

    protected Dialog getDialog(View view){
        return null;
    }

    protected View getDialogView(){
        return null;
    }

    protected WDActivity getWDActivity(){
        return (WDActivity)getActivity();
    }

    protected WDData getData(){
        return WDData.getInstance();
    }



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

        if (data != null && data instanceof ApiResponse) {

            ApiResponse response = (ApiResponse) data;

            if (response != null && response.isError()) {

                DialogUtils.showErrorDialog(getContext(), response.getResponseMessage());

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




}
