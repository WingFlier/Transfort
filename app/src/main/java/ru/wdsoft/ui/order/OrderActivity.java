package ru.wdsoft.ui.order;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.MenuItem;

import ru.wdsoft.R;
import ru.wdsoft.main.AppMode;
import ru.wdsoft.ui.base.WDActivity;
import ru.wdsoft.utils.DialogUtils;
import ru.wdsoft.utils.Utils;

public class OrderActivity extends WDActivity {

    /**
     * Для логирования
     */
    private final String LOG_PREFIX = this.getClass().getName() + " -- ";

    public static final int MODE_ORDER = 0;
    public static final int MODE_MASTER = 1;
    private int mMode = MODE_ORDER;


    public final static String ARG_MODE = "mode";
    public final static String ARG_STATUS_ID = "status_id";
    public final static String ARG_ORDER_ID = "order_id";
    public final static String ARG_COPY = "copy";
    public final static String ARG_READ_ONLY = "read_only";
    public static final String ARG_CONFIRM_STATUS = "confirm_status";
    public final static String ARG_ORDER = "order";
    public static final String ARG_REQUEST_CODE = "ARG_REQUEST_CODE";

    private String mOrderId;
    private int mStatusId;
    private boolean mCopyOrder = false;
    private boolean mReadOnly = false;
    private int mConfirmStatus;

    public static void newOrder(Fragment fragment, int requestCode){
        openOrder(fragment, null, requestCode);
    }

    public static void openOrder(Fragment fragment, String OrderId, int requestCode){
        Intent intent = new Intent(fragment.getContext(), OrderActivity.class);
        intent.putExtra(OrderActivity.ARG_REQUEST_CODE, requestCode);
        intent.putExtra(OrderActivity.ARG_ORDER_ID, OrderId);
        fragment.startActivityForResult(intent, requestCode);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);
        outState.putString(ARG_ORDER_ID, mOrderId);
        outState.putInt(ARG_STATUS_ID, mStatusId);
        outState.putBoolean(ARG_COPY, mCopyOrder);
        outState.putBoolean(ARG_READ_ONLY, mReadOnly);
        outState.putInt(ARG_CONFIRM_STATUS, mConfirmStatus);
        outState.putInt(ARG_MODE, mMode);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            mOrderId = savedInstanceState.getString(ARG_ORDER_ID);
            mStatusId = savedInstanceState.getInt(ARG_STATUS_ID);
            mCopyOrder = savedInstanceState.getBoolean(ARG_COPY);
            mReadOnly = savedInstanceState.getBoolean(ARG_READ_ONLY);
            mConfirmStatus = savedInstanceState.getInt(ARG_CONFIRM_STATUS);
            mMode = savedInstanceState.getInt(ARG_MODE);
        } else {
            mOrderId = getIntent().getStringExtra(ARG_ORDER_ID);
            mCopyOrder = getIntent().getBooleanExtra(ARG_COPY, false);
            mReadOnly = getIntent().getBooleanExtra(ARG_READ_ONLY, false);
            mMode = getIntent().getIntExtra(ARG_MODE, MODE_ORDER);
        }

        setContentView(R.layout.activity_order);

        init();

    }

    private void init() {

        int color = getResources().getColor(R.color.colorCustomer);

        if (getData().getMode() == AppMode.SUPPLIER) {
            color = getResources().getColor(R.color.colorSuplier);
        } else {
        }

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(color));
        setStatusBarColor(color);

        String title = "Создание заказа";
        if (Utils.stringsNotEmpty(mOrderId)) {
            title = "Редактирование";
        }

        setTitle(title, "");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        switchContent(OrderFragment.newInstance(mOrderId, false), false, true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        try {

            switch (id) {

                case android.R.id.home:
                    showSaveOrderDialog();
                    break;

                default:
                    break;
            }

        } catch (Exception e) {
            DialogUtils.showErrorDialog(this, LOG_PREFIX, e);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        showSaveOrderDialog();

    }

    private void showSaveOrderDialog() {
        finish();
    }


}
