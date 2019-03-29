package ru.wdsoft.ui.login;

import android.support.v4.app.Fragment;

public interface ILogin {
    void switchFragment(Fragment fragment, boolean addToBack, boolean clearBackStack);

    void changeToolbar(String title);

    void setToolbarVisible(boolean visible);
}