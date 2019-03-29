package ru.wdsoft.ui.login;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.util.Patterns;

import ru.wdsoft.api.ApiClient;
import ru.wdsoft.api.ApiResponse;
import ru.wdsoft.api.BackTask;
import ru.wdsoft.api.Config;
import ru.wdsoft.main.WDData;

public class ForgotPasswordPresenter {


    private Fragment mFragment;

    public void attachView(Fragment fragment) {

        if (fragment == null) return;

        mFragment = fragment;
    }

    public void detachView() {
        mFragment = null;
    }


    public ForgotPasswordPresenter() {

    }

    public boolean validate(String v) {
        return Patterns.EMAIL_ADDRESS.matcher(v).matches();
    }

    public ApiResponse recover(String string) {
        return BackTask.passwordRecovery(string);
    }

    public boolean setConfig(ApiResponse response) {
        Config config = response.getValue(Config.class);
        if (config != null) {
            getData().setConfig(mFragment.getContext(), config);
            return true;
        }
        return false;
    }

    protected WDData getData() {
        return WDData.getInstance();
    }


}
