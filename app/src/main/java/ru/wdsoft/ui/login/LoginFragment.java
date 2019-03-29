package ru.wdsoft.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.wdsoft.R;
import ru.wdsoft.api.ApiResponse;
import ru.wdsoft.ui.base.WDFragment;
import ru.wdsoft.ui.dataload.PrimaryDataLoadActivity;
import ru.wdsoft.utils.Utils;

import static ru.wdsoft.ui.login.LoginPresenter.PREF_LOGIN;
import static ru.wdsoft.ui.login.LoginPresenter.PREF_PASSW;

/**
 * A login screen that offers login via email/password.
 */
public class LoginFragment extends WDFragment {
    LoginPresenter presenter;
    public static final int GET_CONFIG = 0;
    public static final int AUTH = 1;

    // UI references.
    @BindView(R.id.et_login)
    EditText etLogin;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.btn_enter)
    Button butLogin;
    @BindView(R.id.tv_forgot_password)
    TextView tvForgot;
    @BindView(R.id.tv_check_in)
    TextView tvCheckIn;


    static ILogin ilogin;

    public static LoginFragment newInstance(ILogin login) {
        ilogin = login;
        return new LoginFragment();
    }

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }



    public LoginFragment() {
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initLoader(GET_CONFIG);
        ilogin.setToolbarVisible(false);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private ApiResponse auth(String login, String passw) {
        if (presenter != null) {
            return presenter.auth(login, passw);
        }
        return null;
    }

    private ApiResponse config() {
        if (presenter != null) {
            return presenter.config();
        }
        return null;
    }

    public boolean setConfig(ApiResponse response) {
        if (presenter != null)
            return presenter.setConfig(response);
        return false;
    }

    public boolean setSession(ApiResponse response) {
        if (presenter != null)
            return presenter.setSession(response);
        return false;
    }

    @Override
    protected Object loaderBackground(int id, Bundle args) {
        if (args != null) {
            switch (id) {
                case GET_CONFIG:
                    return config();
                case AUTH:
                    String login = args.getString(PREF_LOGIN, "");
                    String passw = args.getString(PREF_PASSW, "");
                    return auth(login, passw);
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
            case GET_CONFIG:
                if ((data != null) && (data instanceof ApiResponse)) {
                    ApiResponse response = ((ApiResponse) data);
                    setConfig(response);
                } else {
                    enableControls(true);
                }
                break;
            case AUTH:
                if ((data != null) && (data instanceof ApiResponse)) {
                    ApiResponse response = ((ApiResponse) data);
                    if (!response.isError()) {
                        if (setSession(response)) {
                            startPrimaryLoadActivity();
                        }
                    }
                }
                enableControls(true);
                break;
            default:
                result = false;
                break;
        }
        if (!result) Toast.makeText(getContext(), "Ошибка доступа", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new LoginPresenter();
        attachView();
    }

    private void attachView() {
        if (presenter != null)
            presenter.attachView(this);
    }

    private void enableControls(boolean enable) {
        butLogin.setEnabled(enable);
        etLogin.setEnabled(enable);
        etPassword.setEnabled(enable);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        ButterKnife.bind(this, view);

        setHasOptionsMenu(true);

        initGui(view);

        return view;
    }

    @Override
    protected void initGui(View view) {
        // Set up the login form.
        etLogin.setText(getPrefString(PREF_LOGIN));
        etPassword.setText(getPrefString(PREF_PASSW));
        etPassword.setOnEditorActionListener((textView, id, keyEvent) -> {
            if (id == R.id.login || id == EditorInfo.IME_NULL) {
                attemptLogin();
                return true;
            }
            return false;
        });
        butLogin.setOnClickListener(view1 -> attemptLogin());
        tvForgot.setOnClickListener(v ->
                ((LoginActivity) getActivity())
                        .switchContent(ForgotPasswordFragment.newInstance(ilogin),
                                false, true));
        tvCheckIn.setOnClickListener(v -> ((LoginActivity) getActivity())
                .switchContent(CheckInFragment.newInstance(ilogin),
                        false, true));

        etLogin.setText("mi7r7n7iy@mail.ru");
        etPassword.setText("123");
    }


    public void attemptLogin() {
        enableControls(false);

        // Reset errors.
        etLogin.setError(null);
        etPassword.setError(null);

        // Store values at the time of the login attempt.
        String login = etLogin.getText().toString();
        String password = etPassword.getText().toString();

        if (!(Utils.stringsNotEmpty(login) && Utils.stringsNotEmpty(password))) {
            return;
        }
        setPref(PREF_LOGIN, login);
        setPref(PREF_PASSW, password);
        Bundle args = new Bundle();
        args.putString(PREF_LOGIN, login);
        args.putString(PREF_PASSW, password);
        initLoader(AUTH, args);
    }

    public void startPrimaryLoadActivity() {
        Intent intent = new Intent(getContext(), PrimaryDataLoadActivity.class);
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }
}