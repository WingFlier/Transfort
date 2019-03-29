package ru.wdsoft.ui.login;

import android.support.v4.app.Fragment;

import ru.wdsoft.api.ApiResponse;
import ru.wdsoft.api.BackTask;
import ru.wdsoft.api.Config;
import ru.wdsoft.api.Session;
import ru.wdsoft.main.WDData;

public class LoginPresenter {


    public static final String PREF_LOGIN = "login";
    public static final String PREF_PASSW = "passw";

    private Fragment mFragment;

    public void attachView(Fragment fragment) {

        if (fragment == null) return;

        mFragment = fragment;
    }

    public void detachView() {
        mFragment = null;
    }


    public LoginPresenter() {

    }


    public ApiResponse config() {
        return BackTask.getConfig(mFragment.getContext());
    }

    public ApiResponse auth(String login, String passw) {
        return BackTask.auth(login, passw);
    }

    public boolean setConfig(ApiResponse response) {
        Config config = response.getValue(Config.class);
        if (config != null) {
            getData().setConfig(mFragment.getContext(), config);
            return true;
        }
        return false;
    }

    public boolean setSession(ApiResponse response) {
        Session session = response.getValue(Session.class);
        if (session != null) {
            getData().setSession(mFragment.getContext(), session);
            return true;
        }
        return false;
    }

    protected WDData getData() {
        return WDData.getInstance();
    }
}