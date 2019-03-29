package ru.wdsoft.ui.filter;

import android.content.Context;
import android.os.Bundle;

import java.util.ArrayList;

import ru.wdsoft.api.BackTask;
import ru.wdsoft.api.models.Filter;
import ru.wdsoft.api.models.OrderState;
import ru.wdsoft.main.AppMode;
import ru.wdsoft.main.WDData;
import ru.wdsoft.ui.datafield.EditDataField;
import ru.wdsoft.utils.DateTimeUtils;

/**
 * Created by slaventii@mail.ru on 04.03.2019
 */

public class FilterPresenter  {

    private String mOrderNumber, mOrderType, mVehicleType, mAddress;
    private String[] mStates;
    private Long mDateStart;

    private Context mCtx;

    public FilterPresenter(Context ctx) {
        attach(ctx);
        init();
    }

    public void attach(Context ctx){
        mCtx = ctx;
    }

    public void detach(){
        mCtx = null;
    }

    private void init(){

        Filter filter = FilterModel.getFilter();

        mOrderNumber = filter.getNumber();
        mOrderType = filter.getOrderType();
        mVehicleType = filter.getVehicleTypeId();
        mStates = filter.getState();
        mDateStart = filter.getDateFrom();
    }

    public String getOrderNumber() {
        return mOrderNumber;
    }

    public void setOrderNumber(String mOrderNumber) {
        this.mOrderNumber = mOrderNumber;
    }

    public String getOrderType() {
        return mOrderType;
    }

    public void setOrderType(String mOrderType) {
        this.mOrderType = mOrderType;
    }

    public String getVehicleType() {
        return mVehicleType;
    }

    public void setVehicleType(String mVehicleType) {
        this.mVehicleType = mVehicleType;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String mAddress) {
        this.mAddress = mAddress;
    }

    public String[] getStates() {
        return mStates;
    }

    public void setStates(ArrayList<Object> orderStates) {

        String[] mStates = null;

        if (orderStates != null) {

            ArrayList<String> listState = new ArrayList<>();

            for (Object object: orderStates){
                if ( ((OrderState)object).isChecked() ){
                    listState.add(((OrderState)object).getState());
                }
            }

            if (listState.size() > 0){
                mStates = listState.toArray(new String[0]);
            }

        }

        this.mStates = mStates;
    }

    public Long getDateStart() {
        return mDateStart;
    }

    public void setDateStart(Long mDateStart) {
        this.mDateStart = mDateStart;
    }

    private FilterModel getFilter(){
        return FilterModel.getInstance();
    }

    public int filterOrders(){

        if (mCtx ==null) return 0;

        Filter filter = new Filter();

        filter.setLimit(0);
        filter.setDateFrom(mDateStart);
        filter.setOrderType(mOrderType);
        filter.setVehicleTypeId(mVehicleType);
        filter.setState(mStates);
        filter.setNumber(mOrderNumber);
        filter.setWithCount(true);

        filter.setRightsOfUserId(getData().getUserId());
        filter.setWithCount(true);

        if (getData().getMode() == AppMode.CUSTOMER){
            filter.asCustomer(true);
        }

        return BackTask.filterOrders(mCtx, filter);

    }

    public void clearFilter(){

        mDateStart = DateTimeUtils.getDateNow();
        mOrderType = null;
        mVehicleType = null;
        mStates = null;
        mOrderNumber = null;
        mAddress = null;

//        if (getFilter() != null){
//            getFilter().clearFilter();
//        }
    }

    public void applyFilter(){

        Filter filter = FilterModel.getFilter();
        filter.setDateFrom(mDateStart);
        filter.setOrderType(mOrderType);
        filter.setVehicleTypeId(mVehicleType);
        filter.setState(mStates);
        filter.setNumber(mOrderNumber);

    }

    private WDData getData(){
        return WDData.getInstance();
    }
}