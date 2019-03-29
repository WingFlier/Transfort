package ru.wdsoft.ui.login;

import android.support.v4.app.Fragment;
import android.util.Patterns;

import ru.wdsoft.api.ApiResponse;
import ru.wdsoft.api.BackTask;
import ru.wdsoft.main.WDData;
import ru.wdsoft.ui.base.WDFragment;

public class CheckInPresenter {
    private Fragment mFragment;

    public void attachView(Fragment fragment) {
        if (fragment == null) return;
        mFragment = fragment;
    }

    public void detachView() {
        mFragment = null;
    }

    public CheckInPresenter() {
    }

    public boolean validateEmail(String v) {
        return Patterns.EMAIL_ADDRESS.matcher(v).matches();
    }

    public boolean validatePhoneNumber(String phoneNumber) {
        return phoneNumber.length() == 11;
    }

    protected WDData getData() {
        return WDData.getInstance();
    }

    public ApiResponse registerUser(String phone, String name, String pass, String email) {
        return BackTask.registerUser(phone, name, pass, email);
    }

    public ApiResponse registerCompany(String ownershipType, String name, String legalName,
                                       long inn, long kpp, String legalAddress,
                                       String address, boolean isCustomer,
                                       boolean isSupplier, boolean withVat,
                                       String userId, String phone, String email) {
        return BackTask.registerCompany(ownershipType, name, legalName, inn, kpp, legalAddress,
                address, isCustomer, isSupplier, withVat, userId, phone, email);
    }

    public ApiResponse getTypes() {
        return BackTask.getTypes();
    }

    public ApiResponse getSuggestions(String name) {
        return BackTask.getSuggestions(name);
    }
}
