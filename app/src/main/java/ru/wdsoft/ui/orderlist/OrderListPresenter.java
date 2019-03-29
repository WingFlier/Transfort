package ru.wdsoft.ui.orderlist;

import android.support.v4.app.Fragment;

import java.util.ArrayList;

import ru.wdsoft.api.BackTask;
import ru.wdsoft.api.models.Filter;
import ru.wdsoft.api.models.Order;
import ru.wdsoft.main.AppMode;
import ru.wdsoft.main.WDData;
import ru.wdsoft.ui.filter.FilterModel;

/**
 * Created by slaventii@mail.ru on 24.02.2019.
 */
public class OrderListPresenter {

    /**
     * Для логирования
     */
    private final String LOG_PREFIX = this.getClass().getName() + " -- ";

    private Fragment mFragment;

    public void attachView(Fragment fragment) {

        if (fragment == null) return;

        mFragment = fragment;
    }

    public void detachView() {
        mFragment = null;
    }

    public ArrayList<Order> getOrderListFromCache() {
        return BackTask.getOrderList();
    }

    public void getOrdersFromServer(int offset, int limit){
        updateList(offset, limit);
    }

    public void cleanCachedOrders(){
        BackTask.deleteOrders();
    }

    private void updateList(int offset, int limit){

        if (mFragment == null) return;

        Filter filter = getFilter();

        if (filter == null){
            filter = new Filter();
        }

        filter.setLimit(limit);
        filter.setOffset(offset);
        filter.setShowHistory(true);
        filter.setShowOffers(true);
        filter.setSortOrder("DESC");
        filter.setRightsOfUserId(getData().getUserId());
//        filter.setWithCount(true);

        if (getData().getMode() == AppMode.CUSTOMER){
            filter.asCustomer(true);
        }

//        BackTask.updateOrders(mFragment.getContext(), filter);
        BackTask.apiRequest(mFragment.getContext(), BackTask.GET_ORDERS, filter);
    }

//    public void getNextOrders(){
//        mOffset += mLimit;
//        refreshList();
//    }


    private Filter getFilter(){
        return FilterModel.getFilter();
    }

    private WDData getData(){
        return WDData.getInstance();
    }



}
