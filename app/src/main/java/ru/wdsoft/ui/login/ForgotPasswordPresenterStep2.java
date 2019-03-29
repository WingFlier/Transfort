package ru.wdsoft.ui.login;

import android.support.v4.app.Fragment;
import android.util.Patterns;

import ru.wdsoft.api.ApiClient;
import ru.wdsoft.api.ApiResponse;
import ru.wdsoft.api.BackTask;
import ru.wdsoft.api.Config;
import ru.wdsoft.main.WDData;

public class ForgotPasswordPresenterStep2 {

    private Fragment mFragment;

    public void attachView(Fragment fragment) {
        if (fragment == null) return;
        mFragment = fragment;
    }

    public void detachView() {
        mFragment = null;
    }

    public ForgotPasswordPresenterStep2() {
    }

    public boolean validate(String v) {
        return Patterns.EMAIL_ADDRESS.matcher(v).matches();
    }

    public ApiResponse config() {
        return BackTask.getConfig(mFragment.getContext());
    }

    protected WDData getData() {
        return WDData.getInstance();
    }


    public ApiResponse resetPass(String confirmCode, String newPassword, String email) {
        return BackTask.resetPass(confirmCode, newPassword, email);
    }
}
