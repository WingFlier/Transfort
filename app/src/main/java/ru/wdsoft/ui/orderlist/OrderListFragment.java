package ru.wdsoft.ui.orderlist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.PopupMenu;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.wdsoft.R;
import ru.wdsoft.ui.base.WDFragment;
import ru.wdsoft.ui.dialog.DialogOrderSort;
import ru.wdsoft.ui.filter.OrderFilterActivity;
import ru.wdsoft.ui.order.OrderActivity;
import ru.wdsoft.utils.SysUtils;

/**
 * Created by slaventii@mail.ru on 24.02.2019.
 */
public class OrderListFragment extends WDFragment implements OrderListAdapter.IOrderListListener,
        View.OnClickListener, PopupMenu.OnMenuItemClickListener {

    /**
     * Для логирования
     */
    private final String LOG_PREFIX = this.getClass().getName() + " -- ";

    private static final int REQUEST_NEW_ORDER = 102;
    private static final int REQUEST_OPEN_ORDER = 103;
    private static final int REQUEST_FILTER = 101;

    private static final int GET_ORDERS = 0;
    private static final int SWIPE_UPDATE = 2;

    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;

    @BindView(R.id.lvOrders)
    ListView lvOrders;

    @BindView(R.id.llOrders)
    View flOrders;

    @BindView(R.id.rlProgress)
    View rlProgress;

    @BindView(R.id.fabNewOrder)
    FloatingActionButton fabNewOrder;

    private OrderListPresenter mPresenter;

    private int mOffset = 0, mLimit = 10;

    private boolean mLoaderIsRunning = false;


    public static OrderListFragment newInstance() {
        OrderListFragment fragment = new OrderListFragment();
//        Bundle args = new Bundle();
//        fragment.setArguments(args);
        return fragment;
    }

    public OrderListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        mPresenter = new OrderListPresenter();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.layout_orders, container, false);
        ButterKnife.bind(this, view);

        setHasOptionsMenu(true);

        mPresenter.attachView(this);

        initGui(view);

        refreshList();

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_FILTER){

            if (resultCode == Activity.RESULT_OK){
                rlProgress.setVisibility(View.VISIBLE);
                lvOrders.setVisibility(View.GONE);
                mOffset = 0;
                refreshList();
            }
        } else if (requestCode == REQUEST_NEW_ORDER){

            if (resultCode == Activity.RESULT_OK){
                rlProgress.setVisibility(View.VISIBLE);
                lvOrders.setVisibility(View.GONE);
                mOffset = 0;
                refreshList();
            }

        } else if (requestCode == REQUEST_OPEN_ORDER){
            if (resultCode == Activity.RESULT_OK){
                refreshList();
            }
        }
    }

    @Override
    protected void initGui(View view) {

        lvOrders.setOnScrollListener(new AbsListView.OnScrollListener() {

            private int mLastFirstVisibleItem;

            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
                if (!absListView.canScrollVertically(1)){
                    if (mPresenter != null){
                        getNextOrders();
                    }
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {

                if(mLastFirstVisibleItem < firstVisibleItem){
                    // скролл вниз
                    fabNewOrder.setVisibility(View.GONE);
                }

                if(mLastFirstVisibleItem > firstVisibleItem){
                    // скролл вверх
                    fabNewOrder.setVisibility(View.VISIBLE);
                }

                mLastFirstVisibleItem = firstVisibleItem;

            }
        });

        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (mPresenter != null) {
                    swipeRefresh();
                }
            }
        });

        fabNewOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newOrder();
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.orderlist, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {

            case R.id.actFilter:
                onFilterClick();
                break;

            case R.id.actSort:
                onSortClick();
                break;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected Object loaderBackground(int id, Bundle args) {

        switch (id) {

            case SWIPE_UPDATE:
                if (mPresenter != null){
                    mPresenter.cleanCachedOrders();

                    int limit = mOffset;
                    if (limit == 0){
                        limit = 10;
                    }

                    mPresenter.getOrdersFromServer(0, limit);
                    return mPresenter.getOrderListFromCache();
                }
                break;

            case GET_ORDERS:
                if (mPresenter != null) {

                    if (mOffset == 0) {
                        mPresenter.cleanCachedOrders();
                    }

                    mPresenter.getOrdersFromServer(mOffset, mLimit);
                    return mPresenter.getOrderListFromCache();
                }
                break;

            default:
                break;
        }

        return null;
    }

    @Override
    protected void loaderFinished(Loader<Object> loader, Object data) {

        swipeLayout.setRefreshing(false);
        rlProgress.setVisibility(View.GONE);
        lvOrders.setVisibility(View.VISIBLE);

        switch (loader.getId()) {

            case SWIPE_UPDATE:
            case GET_ORDERS:
                if (data != null) {

                    Parcelable state = lvOrders.onSaveInstanceState();
                    showList(data);
                    lvOrders.onRestoreInstanceState(state);
                } else {
                    clearList();
                }
                break;

            default:
                break;
        }

        destroyLoader(loader.getId());
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            default:
                break;
        }
    }

    private void onFilterClick() {
        Intent intent = new Intent(getActivity(), OrderFilterActivity.class);
        startActivityForResult(intent, REQUEST_FILTER);
    }

    private void onSortClick() {
        DialogOrderSort.newInstance(getContext(), new DialogOrderSort.ISortOrder() {
            @Override
            public void onSortClick() {

            }
        });
    }


    private void startRefresh(int id) {
        if (flOrders != null) {
            if (SysUtils.isNetworkAvailable(getContext())) {

//                Snackbar.make(flOrders, getString(R.string.Loading), Snackbar.LENGTH_SHORT).show();

                initLoader(id);

            } else {
                Snackbar.make(flOrders, "Отсутствует интернет соединение", Snackbar.LENGTH_SHORT).show();
            }
        }
    }

    public void getNextOrders(){
        mOffset += mLimit;
        refreshList();
    }

    private void clearList(){
        lvOrders.setAdapter(null);
    }

    private void showList(Object data){
        OrderListAdapter adapter = new OrderListAdapter(getContext(), R.layout.item_order);
        adapter.setList(data);
        adapter.setListner(this);
        lvOrders.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void refreshList() {
        startRefresh(GET_ORDERS);
    }

    public void swipeRefresh() {
        startRefresh(SWIPE_UPDATE);
    }

    private void newOrder(){
        OrderActivity.newOrder(this, REQUEST_NEW_ORDER);
    }

    private void openOrder(String id){
        OrderActivity.openOrder(this, id, REQUEST_OPEN_ORDER);
    }

    @Override
    public void OnItemClick(String id) {
        openOrder(id);
    }

    @Override
    public void OnMenuClick(String id, View view) {
        showContextMenu(id, view);
    }

    private void showContextMenu(String id, View view) {

        PopupMenu popup = new PopupMenu(getContext(), view);
        popup.setOnMenuItemClickListener(this);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.order_item, popup.getMenu());
        popup.show();
    }


    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {

        int id = menuItem.getItemId();

        switch (id) {

            case R.id.actEditOrder:
                break;

            case R.id.actRepeatOrder:
                break;

            case R.id.actCopyText:
                break;

            case R.id.actDeleteOrder:
                break;

            default:
                break;
        }

        return false;
    }
}
