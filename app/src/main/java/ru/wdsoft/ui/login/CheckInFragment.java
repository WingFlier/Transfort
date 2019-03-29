package ru.wdsoft.ui.login;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.suke.widget.SwitchButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.wdsoft.R;
import ru.wdsoft.api.ApiResponse;
import ru.wdsoft.api.models.OwnerShipTypes;
import ru.wdsoft.api.models.UserId;
import ru.wdsoft.ui.base.WDFragment;
import ru.wdsoft.ui.datafield.EditDataField;
import ru.wdsoft.ui.field.FieldEmail;
import ru.wdsoft.ui.field.FieldInteger;
import ru.wdsoft.ui.field.FieldPhone;
import ru.wdsoft.ui.field.FieldText;

public class CheckInFragment extends WDFragment {

    private static final String PREF_EMAIL = "email";
    private static final String PREF_NAME = "name";
    private static final String PREF_PASS = "pass";
    private static final String PREF_PHONE = "phone";

    private UserId value;
    public static final int REGISTER = 1;
    public static final int REGISTER_GROUP = 2;
    public static final int OWN_TYPES = 3;
    public static final int SUGGESTIONS = 4;

    @BindView(R.id.fld_username)
    FieldText fldUserName;
    @BindView(R.id.fld_user_email)
    FieldEmail fldUserEmail;
    @BindView(R.id.fld_organization_phone)
    FieldPhone fldOrgPhone;
    @BindView(R.id.fld_pass)
    FieldText fldPass;
    @BindView(R.id.fld_repeat_pass)
    FieldText fldRepeatPass;
    @BindView(R.id.btn_create)
    Button btnCreate;
    @BindView(R.id.btn_clear)
    Button btnClear;
    @BindView(R.id.cb_reg_pers_data)
    CheckBox cgRegPersData;

    @BindView(R.id.swSwitchOrg)
    SwitchButton swSwitchOrg;
    @BindView(R.id.llOrganization)
    LinearLayout llOrganization;
    @BindView(R.id.sp_type_ownership)
    Spinner spTypeOwnership;
    @BindView(R.id.fld_organization_name)
    FieldText fldOrgName;
    @BindView(R.id.fld_organization_inn)
    FieldInteger fldOrgInn;
    @BindView(R.id.fld_organization_kpp)
    FieldInteger fldOrgKpp;
    @BindView(R.id.fld_organization_legal_address)
    FieldText fldOrgLegalAdress;
    @BindView(R.id.cb_organization_coin_fact)
    CheckBox cbOrgCoinFact;
    @BindView(R.id.fld_organization_fact_address)
    FieldText fldOrgFactAdress;
    @BindView(R.id.ll_org_fact_adress)
    LinearLayout llOrgFactAdress;
    @BindView(R.id.cb_organization_customer)
    CheckBox cbOrgCust;
    @BindView(R.id.cb_organization_supplier)
    CheckBox cbOrgSupp;
    @BindView(R.id.rb_organization_with_nds)
    RadioButton rbOrgWithNds;


    private CheckInPresenter presenter;

    public CheckInFragment() {
    }

    static ILogin login;

    public static CheckInFragment newInstance(ILogin ilogin) {
        login = ilogin;
        return new CheckInFragment();
    }

    public static CheckInFragment newInstance() {
        return new CheckInFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_registration, container, false);
        ButterKnife.bind(this, view);

        initGui(view);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new CheckInPresenter();
        attachView();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initClickListeners();
        login.changeToolbar("Регистрация");
        login.setToolbarVisible(true);
    }

    private void initClickListeners() {
//        edPhone.setText("+7");
        //initLoader(CheckInPresenter.GET_CONFIG);
        swSwitchOrg.setOnCheckedChangeListener((v, checked) -> {
            if (checked) llOrganization.setVisibility(View.VISIBLE);
            else llOrganization.setVisibility(View.GONE);
        });
        btnClear.setOnClickListener(v -> cancel());
        cbOrgCoinFact.setOnCheckedChangeListener((v, checked) -> {
            if (!checked)
                llOrgFactAdress.setVisibility(View.VISIBLE);
            else llOrgFactAdress.setVisibility(View.GONE);
        });
        btnCreate.setOnClickListener(v -> {
            if (validate()) {
                Bundle bundle = new Bundle();
                bundle.putString(PREF_EMAIL, fldUserEmail.getValue());
                bundle.putString(PREF_NAME, fldUserName.getValue());
                bundle.putString(PREF_PASS, fldPass.getValue());
                bundle.putString(PREF_PHONE, fldOrgPhone.getValue());
                initLoader(REGISTER, bundle);
            }
        });

        /*fldOrgName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Bundle bundle = new Bundle();
                bundle.putString("name", s.toString());
                initLoader(SUGGESTIONS, bundle);
            }
        });*/
    }

    private void attachView() {
        if (presenter != null)
            presenter.attachView(this);
    }

    private boolean validate() {
        if (fldUserName.getValue().equals("")) {
            toast(getString(R.string.enter_name));
            return false;
        }
        if (fldUserEmail.getValue().equals("")) {
            toast(getString(R.string.error_email_not_set));
            return false;
        } else if (!validateMail(fldUserEmail.getValue())) {
            toast(getString(R.string.enter_valid_email));
            return false;
        }

        if (!validatePhone(fldOrgPhone.getValue())) {
            toast(getString(R.string.enter_valid_phone_number));
            return false;
        }

        if (fldPass.getValue().equals("") ||
                fldRepeatPass.getValue().equals("")) {
            toast(getString(R.string.err_input_pass));
            return false;
        }

        if (!fldPass.getValue().trim().equals(fldRepeatPass.getValue().trim())) {
            toast(getString(R.string.passwords_dont_match));
            return false;
        }

        if (!cgRegPersData.isChecked()) {
            toast(getString(R.string.input_all_data));
            return false;
        }

        if (swSwitchOrg.isChecked()) {
            if (fldOrgName.getValue().equals("")) {
                toast("Заполните наименование организации");
                return false;
            }

            if (fldOrgInn.getValue().equals("")) {
                toast("Заполните Inn");
                return false;
            }

            if (fldOrgKpp.getValue().equals("")) {
                toast("Заполните Kpp");
                return false;
            }

            if (fldOrgLegalAdress.getValue().equals("")) {
                toast("Заполните Юридичцеский адрес");
                return false;
            }

            if (cbOrgCoinFact.isChecked()) {
                fldOrgFactAdress.setValue(fldOrgLegalAdress.getValue());
            }
        }
        return true;
    }

    private ApiResponse registerUser(Bundle bundle) {
        if (presenter != null) {
            String phone = bundle.getString(PREF_PHONE, "");
            String name = bundle.getString(PREF_NAME, "");
            String pass = bundle.getString(PREF_PASS, "");
            String email = bundle.getString(PREF_EMAIL, "");
            return presenter.registerUser(phone, name, pass, email);
        }
        return null;
    }

    private ApiResponse registerCompany(Bundle args) {
        if (presenter != null) {
            String usId = args.getString("usId");
            String ownershipType = args.getString("ownershipType");
            String orgName = args.getString("orgName");
            long orgInn = Long.valueOf(args.getString("orgInn"));
            long orgKpp = Long.valueOf(args.getString("orgKpp"));
            String orgLegalAdress = args.getString("orgLegalAdress");
            String orgFactAdress = args.getString("orgFactAdress");
            boolean chOrgCust = args.getBoolean("chOrgCust");
            boolean chOrgSupp = args.getBoolean("chOrgSupp");
            boolean rbOrgNds = args.getBoolean("rbOrgNds");
            String phone = args.getString("phone");
            String email = args.getString("email");
            phone = phone.replaceAll("\\+7 \\(", "").replaceAll("\\) ", "").replaceAll(" - ", "");
            return presenter.registerCompany(ownershipType, orgName, ownershipType + ": " + orgName,
                    orgInn, orgKpp, orgLegalAdress, orgFactAdress, chOrgCust, chOrgSupp, rbOrgNds, usId, phone, email);

        }
        return null;
    }

    public boolean validateMail(String mail) {
        if (presenter != null)
            return presenter.validateEmail(mail);
        return false;
    }

    public boolean validatePhone(String phone) {
        if (presenter != null)
            return presenter.validatePhoneNumber(phone);
        return false;
    }

    private void toast(String s) {
        Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
    }

    private void cancel() {
        ((LoginActivity) getActivity()).switchContent(LoginFragment.newInstance(), false, true);

    }

    @Override
    protected Object loaderBackground(int id, Bundle args) {
        if (args != null) {
            switch (id) {
                case REGISTER:
                    return registerUser(args);
                case REGISTER_GROUP:
                    return registerCompany(args);
                case OWN_TYPES:
                    return getTypes();
                case SUGGESTIONS:
                    return getSuggestions(args);
                default:
                    break;
            }
        }
        return null;
    }

    private Object getTypes() {
        if (presenter != null) {
            return presenter.getTypes();
        }
        return null;
    }

    private Object getSuggestions(Bundle bundle) {
        if (presenter != null) {
            return presenter.getSuggestions(bundle.getString("name"));
        }
        return null;
    }

    @Override
    protected void loaderFinished(Loader<Object> loader, Object data) {
        boolean result = true;
        switch (loader.getId()) {
            case REGISTER:
                if ((data != null) && (data instanceof ApiResponse)) {
                    ApiResponse response = ((ApiResponse) data);
                    if (!response.isError()) {
//                        TODO user registration is ok and wants to create org as well send it here else just go to login page
                        Toast.makeText(getContext(), "it's fine", Toast.LENGTH_SHORT).show();
                        if (swSwitchOrg.isChecked()) {
                            initLoader(OWN_TYPES);
                            value = response.getValue(UserId.class);
                        } else {
                            login.switchFragment(LoginFragment.newInstance(), false, true);
                        }
                    }
                }
                break;
            case REGISTER_GROUP:
                if ((data != null) && (data instanceof ApiResponse)) {
                    ApiResponse response = ((ApiResponse) data);
                    if (!response.isError()) {
                        login.switchFragment(LoginFragment.newInstance(), false, true);
                        Toast.makeText(getContext(), "it's fine group", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case OWN_TYPES:
                if ((data != null) && (data instanceof ApiResponse)) {
                    ApiResponse response = ((ApiResponse) data);
                    if (!response.isError()) {
                        String ownType = spTypeOwnership.getSelectedItem().toString();
                        OwnerShipTypes[] types = response.getValue(OwnerShipTypes[].class);
                        for (OwnerShipTypes t : types) {
                            if (t.getName().trim().equals(ownType.trim()))
                                ownType = t.getCode();
                        }
                        Bundle args = new Bundle();
                        args.putString("usId", this.value.getUserId());
                        args.putString("ownershipType", ownType);
                        args.putString("orgName", fldOrgName.getValue());
                        args.putString("orgInn", fldOrgInn.getValue());
                        args.putString("orgKpp", fldOrgKpp.getValue());
                        args.putString("orgLegalAdress", fldOrgLegalAdress.getValue());
                        args.putString("orgFactAdress", fldOrgFactAdress.getValue());
                        args.putBoolean("chOrgCust", cbOrgCust.isChecked());
                        args.putBoolean("chOrgSupp", cbOrgSupp.isChecked());
                        args.putString("phone", fldOrgPhone.getValue());
                        args.putString("email", fldUserEmail.getValue());
                        if (rbOrgWithNds.isChecked())
                            args.putBoolean("rbOrgNds", true);
                        else
                            args.putBoolean("rbOrgNds", false);
                        initLoader(REGISTER_GROUP, args);
                    }
                }
                break;
            case SUGGESTIONS:
                if ((data != null) && (data instanceof ApiResponse)) {
                    ApiResponse response = ((ApiResponse) data);
                    if (!response.isError()) {
                        Field resp = null;
                        try {
                            resp = response.getClass().getDeclaredField("mResponseString");
                            resp.setAccessible(true);
                            String json = resp.get(response).toString();
                            JSONObject jsonObject = new JSONObject(json);
                            JSONArray suggestions = (JSONArray) jsonObject.get("suggestions");
                            if (suggestions.length() < 1)
                                return;
                            JSONObject jsOBj = (JSONObject) ((JSONObject) suggestions.get(0)).get("data");
                            String kpp = jsOBj.get("kpp").toString();
                            String inn = jsOBj.get("inn").toString();
                            fldOrgKpp.setValue(kpp);
                            fldOrgInn.setValue(inn);
                        } catch (NoSuchFieldException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }
                break;
            default:
                result = false;
                break;
        }
        if (!result)
            Toast.makeText(getContext(), "Ошибка доступа", Toast.LENGTH_SHORT).show();
    }
}