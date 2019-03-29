package ru.wdsoft.ui.login;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.Loader;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.wdsoft.R;
import ru.wdsoft.api.ApiResponse;
import ru.wdsoft.ui.base.WDFragment;
import ru.wdsoft.ui.field.FieldInteger;
import ru.wdsoft.ui.field.FieldPassword;
import ru.wdsoft.ui.field.FieldText;

public class ForgotPasswordFragmentStep2 extends WDFragment {
    private static final String PREF_EMAIL = "email";
    private static final String PREF_CONFIRM_CODE = "confCode";
    private static final String PREF_NEW_PASS = "newpass";


    public static final int CHANGE_PASS = 0;
    private static String email;


    @BindView(R.id.fld_input_code)
    FieldText fldInputCode;
    @BindView(R.id.fld_new_password)
    FieldPassword fldNewPassword;
    @BindView(R.id.fld_new_password_repeat)
    FieldPassword fldNewPasswordRepeat;
    @BindView(R.id.btn_save)
    Button btnSave;
    @BindView(R.id.btn_cancel)
    Button btnCancel;
    @BindView(R.id.tvYouRecalAuth)
    TextView tvYouRecalAuth;

    private ForgotPasswordPresenterStep2 presenter;

    public ForgotPasswordFragmentStep2() {
    }

    public static ForgotPasswordFragmentStep2 newInstance(String mail) {
        email = mail;
        return new ForgotPasswordFragmentStep2();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_restore_step2, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        initGui(view);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new ForgotPasswordPresenterStep2();
        attachView();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        edMail.setText("mi7r7n7iy@mail.ru");
        initClickListeners();
    }

    private void initClickListeners() {
        btnCancel.setOnClickListener(v -> cancel());
        tvYouRecalAuth.setOnClickListener(v -> cancel());
        btnSave.setOnClickListener(v -> {
            if (validate()) {
                Bundle bundle = new Bundle();
                bundle.putString(PREF_CONFIRM_CODE, fldInputCode.getValue());
                bundle.putString(PREF_NEW_PASS, fldNewPassword.getValue());
                bundle.putString(PREF_EMAIL, email);
                initLoader(CHANGE_PASS, bundle);
            }
        });

    }

    private boolean validate() {
        if (fldInputCode.getValue().equals("")) {
            toast(getString(R.string.input_confirm_code));
            return false;
        }

        if (fldNewPassword.getValue().equals("") ||
                fldNewPasswordRepeat.getValue().equals("")) {
            toast(getString(R.string.err_input_pass));
            return false;
        }
        if (!fldNewPassword.getValue().trim().equals(fldNewPasswordRepeat.getValue().trim())) {
            toast(getString(R.string.passwords_dont_match));
            return false;
        }
        if (!fldNewPassword.getValue().matches("^[a-zA-Z0-9]+")) {
            toast(getString(R.string.only_latin_and_nums));
            return false;
        }
        return true;
    }

    private void toast(String s) {
        Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
    }


    private void attachView() {
        if (presenter != null)
            presenter.attachView(this);
    }

    private void cancel() {
        ((LoginActivity) getActivity()).switchContent(LoginFragment.newInstance(), false, true);
    }


    private ApiResponse resetPass(String confirmCode, String newPassword, String email) {
        if (presenter != null) {
            return presenter.resetPass(confirmCode, newPassword, email);
        }
        return null;
    }

    @Override
    protected Object loaderBackground(int id, Bundle args) {
        if (args != null) {
            switch (id) {
                case CHANGE_PASS:
                    String confirmCode = args.getString(PREF_CONFIRM_CODE);
                    String newPassword = args.getString(PREF_NEW_PASS);
                    String email = args.getString(PREF_EMAIL);
                    return resetPass(confirmCode, newPassword, email);
                default:
                    break;
            }
        }
        return null;
    }

    @Override
    protected void loaderFinished(Loader<Object> loader, Object data) {
        boolean result = true;
        switch (loader.getId()) {
            case CHANGE_PASS:
                if ((data != null) && (data instanceof ApiResponse)) {
                    ApiResponse response = ((ApiResponse) data);
                    if (!response.isError()) {
                        toast(getString(R.string.password_suc_changed));
                        ((LoginActivity) getActivity()).switchContent(LoginFragment.newInstance(),
                                false, true);
                    }
                }
                break;
            default:
                result = false;
                break;
        }
        if (!result) toast("Ошибка доступа");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (presenter != null)
            presenter.detachView();
    }
}