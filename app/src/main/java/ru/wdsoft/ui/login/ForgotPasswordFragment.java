package ru.wdsoft.ui.login;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.wdsoft.R;
import ru.wdsoft.api.ApiResponse;
import ru.wdsoft.ui.base.WDFragment;
import ru.wdsoft.ui.field.FieldEmail;
import ru.wdsoft.ui.field.FieldText;

public class ForgotPasswordFragment extends WDFragment {
    private static final String PREF_EMAIL = "email";

    public static final int RECOVER = 0;

    @BindView(R.id.tvYouRecalAuth)
    TextView tvYouRecalAuth;
    @BindView(R.id.btn_cancel)
    Button btnCancel;
    @BindView(R.id.btn_next)
    Button btnNext;
    @BindView(R.id.fld_user_mail)
    FieldEmail fldUserEmail;
    private ForgotPasswordPresenter presenter;

    public ForgotPasswordFragment() {
    }

    static ILogin login;

    public static ForgotPasswordFragment newInstance(ILogin ilogin) {
        login = ilogin;
        return new ForgotPasswordFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_restore, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        initGui(view);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new ForgotPasswordPresenter();
        attachView();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initClickListeners();
        login.setToolbarVisible(true);
        login.changeToolbar("Восстановление пароля");

    }

    private void initClickListeners() {
        btnCancel.setOnClickListener(v -> cancel());
        tvYouRecalAuth.setOnClickListener(v -> cancel());
        btnNext.setOnClickListener(v -> {
            if (validate()) {
                Bundle bundle = new Bundle();
                bundle.putString(PREF_EMAIL, fldUserEmail.getValue());
                initLoader(RECOVER, bundle);
            } else {
                Toast.makeText(getContext(), getString(R.string.err_not_valid_email), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void attachView() {
        if (presenter != null)
            presenter.attachView(this);
    }

    private boolean validate() {
        if (presenter != null)
            return presenter.validate(fldUserEmail.getValue());
        return false;
    }

    private void cancel() {
        ((LoginActivity) getActivity()).switchContent(LoginFragment.newInstance(), false, true);
    }


    private ApiResponse recovery(String string) {
        if (presenter != null)
            return presenter.recover(string);
        return null;
    }

    public boolean setConfig(ApiResponse response) {
        if (presenter != null)
            return presenter.setConfig(response);
        return false;
    }

    @Override
    protected Object loaderBackground(int id, Bundle args) {

        if (args != null) {
            switch (id) {
                case RECOVER:
                    return recovery(args.getString(PREF_EMAIL));
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
            case RECOVER:
                if ((data != null) && (data instanceof ApiResponse)) {
                    ApiResponse response = ((ApiResponse) data);
                    if (!response.isError()) {
                        //TODO this is gonna be changed
                        switchFragment();
                    }
                }
                break;
            default:
                result = false;
                break;
        }
        if (!result) Toast.makeText(getContext(), "Ошибка доступа", Toast.LENGTH_SHORT).show();
    }

    private void switchFragment() {
        ((LoginActivity) getActivity())
                .switchContent(ForgotPasswordFragmentStep2
                                .newInstance(fldUserEmail.getValue()),
                        true, false);
    }
}