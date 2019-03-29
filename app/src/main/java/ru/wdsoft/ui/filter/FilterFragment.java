package ru.wdsoft.ui.filter;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.wdsoft.R;
import ru.wdsoft.ui.base.WDFragment;
import ru.wdsoft.ui.datafield.EditDataField;
import ru.wdsoft.utils.CastUtils;
import ru.wdsoft.utils.DateTimeUtils;

/**
 * Created by slaventii@mail.ru on 11.03.2019.
 */
public class FilterFragment extends WDFragment implements EditDataField.OnValueChangedListener {

    private static final int FILTER_ORDERS = 0;

    @BindView(R.id.fldNumber)
    EditDataField fldNumber;

    @BindView(R.id.fldOrderType)
    EditDataField fldOrderType;

    @BindView(R.id.fldVehicleType)
    EditDataField fldVehicleType;

    @BindView(R.id.fldState)
    EditDataField fldState;

    @BindView(R.id.fldAddress)
    EditDataField fldAddress;

    @BindView(R.id.fldDateStart)
    EditDataField fldDateStart;

    @BindView(R.id.rlBottom)
    RelativeLayout rlBottom;

    @BindView(R.id.tvBottomTitle)
    TextView tvBottomTitle;

    @BindView(R.id.tvBottomShow)
    TextView tvBottomShow;

    @BindView(R.id.pbFilter)
    ProgressBar pbFilter;


    private FilterPresenter mFilterPresenter;

    public static FilterFragment newInstance() {
        FilterFragment fragment = new FilterFragment();
        return fragment;
    }

    public FilterFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.layout_order_filter, container, false);

        mFilterPresenter = new FilterPresenter(getContext());

        ButterKnife.bind(this, view);

        setHasOptionsMenu(true);

        initGui(view);

        return view;
    }

    @Override
    protected void initGui(View view) {

        String ordType=null, vehType=null, address=null, number=null;
        String[] states=null;
        Long date=null;

        if (mFilterPresenter != null){
            number = mFilterPresenter.getOrderNumber();
            ordType = mFilterPresenter.getOrderType();
            vehType = mFilterPresenter.getVehicleType();
            states = mFilterPresenter.getStates();
            address = mFilterPresenter.getAddress();
            date = mFilterPresenter.getDateStart();
        }

        initOrderNumber(number);
        initOrderType(ordType);
        initVehicleType(vehType);
        initState(states);
        initAddress(address);
        initDateStart(date);

        tvBottomShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                applyFilter();
            }
        });

        updateInfo(0);
        filterOrders();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (mFilterPresenter != null){
            mFilterPresenter.detach();
            mFilterPresenter = null;
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.filter, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id){

            case R.id.action_clear:
                onClearClick();
                break;

            case android.R.id.home:
                getActivity().setResult(Activity.RESULT_CANCELED);
                getActivity().finish();
                break;

            default:
                break;
        }


        return super.onOptionsItemSelected(item);
    }

    private void onClearClick() {

        fldNumber.setOnValueChangedListener(null);
        fldOrderType.setOnValueChangedListener(null);
        fldVehicleType.setOnValueChangedListener(null);
        fldState.setOnValueChangedListener(null);
        fldAddress.setOnValueChangedListener(null);
        fldDateStart.setOnValueChangedListener(null);

        initOrderNumber("");
        initOrderType("");
        initVehicleType("");
        initState(null);
        initAddress("");
        initDateStart(DateTimeUtils.getDateNow());

        filterOrders();
    }

    private void initOrderNumber(String number){
        Bundle params = new Bundle();
        params.putString("value", number);

        fldNumber.initData(params);
        fldNumber.setOnValueChangedListener(this);
        fldNumber.setGroupSelected(fldNumber.hasValue());
    }

    private void initOrderType(String id){
        Bundle params = new Bundle();
        params.putString("id", id);

        fldOrderType.initData(params);
        fldOrderType.setOnValueChangedListener(this);
        fldOrderType.setGroupSelected(fldOrderType.hasValue());
    }

    private void initVehicleType(String id){
        Bundle params = new Bundle();
        params.putString("id", id);

        fldVehicleType.initData(params);
        fldVehicleType.setOnValueChangedListener(this);
        fldVehicleType.setGroupSelected(fldVehicleType.hasValue());
    }

    private void initState(String[] states){
        Bundle params = new Bundle();
        params.putStringArray("states", states);

        fldState.initData(params);
        fldState.setOnValueChangedListener(this);
        fldState.setGroupSelected(fldState.hasValue());
    }

    private void initAddress(String address){
        Bundle params = new Bundle();
        params.putString("address", address);

        fldAddress.initData(params);
        fldAddress.setOnValueChangedListener(this);
        fldAddress.setGroupSelected(fldAddress.hasValue());
    }

    private void initDateStart(Long date){
        Bundle params = new Bundle();
        params.putLong("date", date);

        fldDateStart.initData(params);
        fldDateStart.setOnValueChangedListener(this);
        fldDateStart.setGroupSelected(fldDateStart.hasValue());
    }


    @Override
    protected Object loaderBackground(int id, Bundle args) {

        switch (id) {

            case FILTER_ORDERS:
                if (mFilterPresenter != null){
                    return mFilterPresenter.filterOrders();
                }
                break;

            default:
                break;
        }

        return null;
    }

    @Override
    protected void loaderFinished(Loader<Object> loader, Object data) {

        switch (loader.getId()) {

            case FILTER_ORDERS:
                pbFilter.setVisibility(View.GONE);
                if (data != null && data instanceof Integer) {
                    updateInfo((Integer)data);
                }
                break;

            default:
                break;
        }

        destroyLoader(loader.getId());
    }

    private void updateInfo(Integer count){
        String txt = String.format(getString(R.string.findNOrders), count);
        tvBottomTitle.setText(txt);
    }

    private void applyFilter() {
        if (mFilterPresenter != null){
            mFilterPresenter.applyFilter();
            getActivity().setResult(Activity.RESULT_OK);
            getActivity().finish();
        }
    }

    private void filterOrders(){
        pbFilter.setVisibility(View.VISIBLE);
        initLoader(FILTER_ORDERS);
    }

    @Override
    public void onBeforeChange(EditDataField view) {}

    @Override
    public void onChanged(EditDataField view) {

        if (mFilterPresenter == null) return;

        int id = view.getId();

        switch (id){

            case R.id.fldNumber:
                mFilterPresenter.setOrderNumber(view.getValue());
                break;

            case R.id.fldOrderType:
                mFilterPresenter.setOrderType(view.getGuid());
                break;

            case R.id.fldVehicleType:
                mFilterPresenter.setVehicleType(view.getGuid());
                break;

            case R.id.fldState:
                setStates(view.getAdapterValues());
                break;

            case R.id.fldAddress:
                mFilterPresenter.setAddress(view.getValue());
                break;

            case R.id.fldDateStart:
                mFilterPresenter.setDateStart(CastUtils.toLong(view.getValue()));
                break;

            default:
                break;
        }

        view.setGroupSelected(view.hasValue());
        filterOrders();
    }

//    private void setNumber(){
//        if (mFilterPresenter != null){
//
//        }
//    }
//
//    private void setOrderType(){
//        if (mFilterPresenter != null){
//
//        }
//    }
//
//    private void setVehicleType(){
//        if (mFilterPresenter != null){
//
//        }
//    }
//
//    private void setAddress(){
//        if (mFilterPresenter != null){
//
//        }
//    }
//
//    private void setAddress(){
//        if (mFilterPresenter != null){
//
//        }
//    }

    private void setStates(ArrayList<Object> orderStates){

        if (mFilterPresenter != null){
            mFilterPresenter.setStates(orderStates);
        }
    }

    @Override
    public void onClose(EditDataField view) {}

    @Override
    public void onCancel(EditDataField view) {}
}
