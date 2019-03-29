package ru.wdsoft.ui.order;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.wdsoft.R;
import ru.wdsoft.main.AppMode;
import ru.wdsoft.ui.base.WDFragment;
import ru.wdsoft.ui.datafield.EditDataField;
import ru.wdsoft.utils.CastUtils;
import ru.wdsoft.utils.Utils;


public class OrderFragment extends WDFragment implements
        EditDataField.OnValueChangedListener {

    /**
     * Для логирования
     */
    private final String LOG_PREFIX = this.getClass().getName() + " -- ";

    public static final String PHONE_MASK = "+#(###)-###-##-##";
    private boolean mHasChanges = false;
    private boolean mCopyOrder = false;

    private String mOrderId;
    public final static int SAVE_ORDER = 0;
    public final static int GET_ORDER = 1;

    public final static String NEW_ORDER_ADDED = "new_order";

    public static final String ARG_ORDER = "order";
    public static final String ARG_COPY_ORDER = "copy";
    public static final String ARG_HAS_CHANGES = "has_changes";

    @BindView(R.id.llOrder)
    LinearLayout llOrder;

    @BindView(R.id.fldOrderType)
    EditDataField fldOrderType;

    @BindView(R.id.fldVehicleType)
    EditDataField fldVehicleType;

    @BindView(R.id.fldBodyType)
    EditDataField fldBodyType;

    @BindView(R.id.fldPassangerCount)
    EditDataField fldPassangerCount;

    @BindView(R.id.fldVehicleModel)
    EditDataField fldVehicleModel;

    @BindView(R.id.fldComment)
    EditDataField fldComment;

    @BindView(R.id.fldDateStart)
    EditDataField fldDateStart;

    @BindView(R.id.fldDateEnd)
    EditDataField fldDateEnd;

    @BindView(R.id.fldAddressStart)
    EditDataField fldAddressStart;

    @BindView(R.id.fldAddressEnd)
    EditDataField fldAddressEnd;

    @BindView(R.id.fldCustomer)
    EditDataField fldCustomer;

    @BindView(R.id.fldContactPerson)
    EditDataField fldContactPerson;

    @BindView(R.id.fldPhone)
    EditDataField fldPhone;

    @BindView(R.id.fldEmail)
    EditDataField fldEmail;

    @BindView(R.id.tvOrderNumber)
    TextView tvOrderNumber;

    @BindView(R.id.tvOrderCreateDate)
    TextView tvOrderCreateDate;

    @BindView(R.id.tvUseProfile)
    TextView tvUseProfile;

    @BindView(R.id.butPreview)
    Button butPreview;

    @BindView(R.id.butCreate)
    Button butCreate;

    @BindView(R.id.cbTheSameAddress)
    CheckBox cbTheSameAddress;

    private OrderPresenter mOrderPresenter;

    public static OrderFragment newInstance(String order_id, boolean copy) {

        OrderFragment fragment = new OrderFragment();

        Bundle args = new Bundle();
        args.putString(OrderFragment.ARG_ORDER, order_id);
        args.putBoolean(OrderFragment.ARG_COPY_ORDER, copy);
        fragment.setArguments(args);

        return fragment;
    }

    public OrderFragment() {
        // Required empty public constructor
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);

        outState.putBoolean(ARG_HAS_CHANGES, mHasChanges);
        outState.putBoolean(ARG_COPY_ORDER, mCopyOrder);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        if (savedInstanceState != null){
            mHasChanges = savedInstanceState.getBoolean(ARG_HAS_CHANGES);
            mCopyOrder = savedInstanceState.getBoolean(ARG_COPY_ORDER);
        } else {

            if (getArguments() != null) {
                mOrderId = getArguments().getString(ARG_ORDER);
                mCopyOrder = getArguments().getBoolean(ARG_COPY_ORDER, false);
            }
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.layout_order, container, false);

        ButterKnife.bind(this, view);

        setHasOptionsMenu(true);

        initGui(view);

        return view;

    }

    @Override
    protected void initGui(View view) {

        initOrderData();

        butCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initLoader(SAVE_ORDER);
            }
        });
    }

    private void initFields(){

        if (mOrderPresenter == null) return;

        tvOrderNumber.setText("№ " + mOrderPresenter.getOrderNumber());
        tvOrderCreateDate.setText("от " + CastUtils.toUIOnlyDate(mOrderPresenter.getCreateDate()));

        initOrderType(mOrderPresenter.getOrderType());
        initVehicleType(mOrderPresenter.getVehicleType());
        initBodyType(mOrderPresenter.getBodyType());
        initCustomer(mOrderPresenter.getCustomer());
        initVehicleModel(mOrderPresenter.getModel());
        initPasangersCount(mOrderPresenter.getNumberOfPassengers());
        initAddressEnd("");
        initAddressStart(mOrderPresenter.getRentAddress());
        initComment(mOrderPresenter.getComment());
        initContactPerson(mOrderPresenter.getContactPersonName());
        initPhone(mOrderPresenter.getContactPhone());
        initEmail(mOrderPresenter.getContactEmail());

        initDateStart(mOrderPresenter.getRentStartDate());
        initDateEnd(mOrderPresenter.getRentEndDate());
    }

    private void initOrderData(){

        if (Utils.stringsNotEmpty(mOrderId)){
            initLoader(GET_ORDER);
        } else {
            mOrderPresenter = new OrderPresenter();
            initFields();
        }

    }

    private Boolean saveOrder(){
        if (mOrderPresenter == null) return false;
        return mOrderPresenter.saveOrder();
    }

    private void close(){
        if (mOrderPresenter == null) return;
        Intent intent = new Intent();

        if (mOrderPresenter.isNew()){
            intent.putExtra(NEW_ORDER_ADDED, mOrderPresenter.getOrderId());
        }

        getActivity().setResult(Activity.RESULT_OK, intent);

        getActivity().finish();
    }

    private void onDataFieldChanged(){

        if (mOrderPresenter == null) return;

        mOrderPresenter.setOrderType(fldOrderType.getGuid());
        mOrderPresenter.setVehicleType(fldVehicleType.getGuid());
        mOrderPresenter.setBodyType(fldBodyType.getGuid());
        mOrderPresenter.setModel(fldVehicleModel.getGuid());
        mOrderPresenter.setComment(fldComment.getValue());
        mOrderPresenter.setNumberOfPassengers(Integer.valueOf(fldPassangerCount.getValue()));
        mOrderPresenter.setRentStartDate(CastUtils.toLong(fldDateStart.getValue()));
        mOrderPresenter.setRentEndDate(CastUtils.toLong(fldDateEnd.getValue()));
//        mOrderPresenter.setRentTime(fldOrderType.getGuid());
        mOrderPresenter.setRentAddress(fldAddressStart.getValue());
        mOrderPresenter.setCustomer(fldCustomer.getGuid());
        mOrderPresenter.setContactPersonName(fldContactPerson.getValue());
        mOrderPresenter.setContactPersonPhone(fldPhone.getValue());
        mOrderPresenter.setContactPersonEmail(fldEmail.getValue());
        mOrderPresenter.setChanged();

    }

    private void initPasangersCount(int count){
        Bundle params = new Bundle();
        params.putString("value", String.valueOf(count));

        fldPassangerCount.initData(params);
        fldPassangerCount.setOnValueChangedListener(this);
    }

    private void initBodyType(String code){
        Bundle params = new Bundle();
        params.putString("code", code);

        fldBodyType.initData(params);
        fldBodyType.setOnValueChangedListener(this);
    }

    private void initOrderType(String id){
        Bundle params = new Bundle();
        params.putString("id", id);

        fldOrderType.initData(params);
        fldOrderType.setOnValueChangedListener(this);
    }

    private void initVehicleType(String id){
        Bundle params = new Bundle();
        params.putString("id", id);

        fldVehicleType.initData(params);
        fldVehicleType.setOnValueChangedListener(this);
    }

    private void initCustomer(String id){
        Bundle params = new Bundle();
        params.putString("id", id);

        fldCustomer.initData(params);
        fldCustomer.setOnValueChangedListener(this);
    }

    private void initVehicleModel(String id){
        Bundle params = new Bundle();
        params.putString("id", id);

        fldVehicleModel.initData(params);
        fldVehicleModel.setOnValueChangedListener(this);
    }

    private void initAddressEnd(String value){
        Bundle params = new Bundle();
        params.putString("value", value);

        fldAddressEnd.initData(params);
        fldAddressEnd.setOnValueChangedListener(this);
    }

    private void initAddressStart(String value){
        Bundle params = new Bundle();
        params.putString("value", value);

        fldAddressStart.initData(params);
        fldAddressStart.setOnValueChangedListener(this);
    }

    private void initComment(String id){
        Bundle params = new Bundle();
        params.putString("value", id);

        fldComment.initData(params);
        fldComment.setOnValueChangedListener(this);
    }

    private void initContactPerson(String value){
        Bundle params = new Bundle();
        params.putString("value", value);

        fldContactPerson.initData(params);
        fldContactPerson.setOnValueChangedListener(this);
    }

    private void initPhone(String value){
        Bundle params = new Bundle();
        params.putString("value", value);

        fldPhone.initData(params);
        fldPhone.setOnValueChangedListener(this);
    }

    private void initEmail(String value){
        Bundle params = new Bundle();
        params.putString("value", value);

        fldEmail.initData(params);
        fldEmail.setOnValueChangedListener(this);
    }

    private void initDateStart(Long date){
        Bundle params = new Bundle();
        params.putLong("date", date);

        fldDateStart.initData(params);
        fldDateStart.setOnValueChangedListener(this);
    }

    private void initDateEnd(Long date){
        Bundle params = new Bundle();
        params.putLong("date", date);

        fldDateEnd.initData(params);
        fldDateEnd.setOnValueChangedListener(this);
    }

    @Override
    protected Object loaderBackground(int id, Bundle args) {

        switch (id){

            case SAVE_ORDER:
                return saveOrder();
            case GET_ORDER:
                mOrderPresenter = new OrderPresenter(mOrderId, mCopyOrder);
                break;

            default:
                break;

        }

        return null;
    }

    @Override
    protected void loaderFinished(Loader<Object> loader, final Object data) {

        switch (loader.getId()){

            case GET_ORDER:
                initFields();
                break;

            case SAVE_ORDER:
                if (data != null){
                    if (data instanceof Boolean){
                        if ((Boolean)data){
                            close();
                            break;
                        }
                    }
                }
                Snackbar.make(llOrder, "Не удалось отправить заявку на сервер",
                        Snackbar.LENGTH_SHORT).show();
                break;

            default:
                break;
        }

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.order, menu);

        if (getData().getMode() == AppMode.CUSTOMER){

            if (Utils.stringsNotEmpty(mOrderId)){
                menu.getItem(0).setVisible(true);
            } else {
                menu.getItem(1).setVisible(true);
            }

        }
    }

    @Override
    public void onResume() {

        super.onResume();

//        mHasChanges = getPresenter().isNew();
//
//        getPresenter().attachView(this);
//
//        // Запрешаем редактировать существующие на сервере заявки, елси нет интернет соединения.
//
//
//        // Если заявка доступна для редактирования, проверим если заявка существует на сервере
//        if (!getPresenter().isReadOnly() && !getPresenter().isNew()) {
//
//            // Если это заявка существует на сервере, проверить интернет соединение
//            if (!getPresenter().isChanged()) {
//
//                // Проверим интернет соединение
//                if (!Utils.isNetworkAvailable(getContext())){
//
//                    // Соединение отсутствует, оповестим пользователя и запретим редактирование
//                    Utils.showAlertDialog(getContext(), "Внимание!",
//                            "Отсутствует интернет соединение. Редактирование существующей заявки невозможно!" );
//
//                    setReadOnly(true);
//                }
//            }
//        }
//
//        setFieldsVisibility();
//
//        fillObligatoryFields();
//
//        setFieldsValueChangedListener();
//
//
//
    }

    @Override
    public void onPause() {
        super.onPause();
//        getPresenter().detachView();
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

        switch (id){

//            case R.id.save:
//                showSaveOrderDialog();
//                break;

            default:
                break;
        }

    }


    @Override
    public void onBeforeChange(EditDataField view) {

        switch (view.getId()){
            case R.id.fldOrderType:
                break;

            case R.id.fldVehicleType:
                onBeforeVehicleTypeChange();
                break;

            default:
                break;
        }
    }

    @Override
    public void onChanged(EditDataField view) {

        switch (view.getId()){

            case R.id.fldOrderType:
                onOrderTypeChanged();
                break;

            case R.id.fldVehicleType:
                break;

                default:
                    break;

        }

        onDataFieldChanged();

    }

    @Override
    public void onClose(EditDataField view) {

    }

    @Override
    public void onCancel(EditDataField view) {

    }

    private void onOrderTypeChanged() {
        fldVehicleType.clearValue();
    }

    private void onBeforeVehicleTypeChange(){

        String[] filter = new String[1];

        if (fldOrderType.hasValue()){
            filter[0] = fldOrderType.getGuid();
        }

//        if (fldCustomer.hasValue()){
//            filter[1] = fldCustomer.getValueGuid();
//        }
// ||  Utils.stringsNotEmpty(filter[1])
        if (Utils.stringsNotEmpty(filter[0])){
            fldVehicleType.updateDataFilter(filter);
        }

    }


}

