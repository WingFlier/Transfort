package ru.wdsoft.ui.filter;

import android.os.Bundle;

import ru.wdsoft.api.models.Filter;
import ru.wdsoft.api.models.OrderState;
import ru.wdsoft.utils.DateTimeUtils;
import ru.wdsoft.utils.Utils;

/**
 * Created by slaventii@mail.ru on 04.03.2019
 */

public class FilterModel {

    private static FilterModel filterInstance = new FilterModel();

    private Filter mFilter;

    public static FilterModel newInstance(){

        filterInstance = new FilterModel();
        return filterInstance;

    }

    public static FilterModel getInstance(){

        if (filterInstance == null) return newInstance();

        return filterInstance;
    }

    public static Filter getFilter(){

        if (filterInstance == null){
            return null;
        }

        return filterInstance.mFilter;

    }

    public FilterModel() {
        mFilter = new Filter();
        setDefaultFilter();
    }

    private void setDefaultFilter(){
        mFilter.setDateFrom(DateTimeUtils.getDateNow());
    }

    public void clearFilter(){

        if (mFilter != null) {

            mFilter = new Filter();

//            mFilter.remove(FILTER_DATE_START);
//            mFilter.remove(FILTER_STATE);
//
//            mFilter.remove(FILTER_ORDER_NUMBER);
//            mFilter.remove(FILTER_ORDER_TYPE);
//            mFilter.remove(FILTER_VEHICLE_TYPE);
//            mFilter.remove(FILTER_ADDRESS);

            setDefaultFilter();
        }
    }
}
