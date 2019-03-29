package ru.wdsoft.ui.filter;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.wdsoft.R;
import ru.wdsoft.main.AppMode;
import ru.wdsoft.ui.base.WDActivity;
import ru.wdsoft.ui.order.OrderFragment;
import ru.wdsoft.utils.DialogUtils;
import ru.wdsoft.utils.Utils;

public class OrderFilterActivity extends WDActivity {

//    private ExpandableListAdapter listAdapter;
//    private ExpandableListView expListView;
//    private List<String> listDataHeader;

//    @BindView(R.id.tvBottomShow)
//    TextView tvBottomShow;
//    private FilterPresenter mPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);

//        mPresenter = new FilterPresenter();

        ButterKnife.bind(this);

//        expListView = findViewById(R.id.elvMain);
//        prepareListData();
//        listAdapter = new ExpandableListAdapter(this, listDataHeader, mPresenter);
//        expListView.setAdapter(listAdapter);
//
//        tvBottomShow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onBackPressed();
//            }
//        });
//
        init();
    }

    private void init(){

        int color = getResources().getColor(R.color.colorCustomer);

        if (getData().getMode() == AppMode.SUPPLIER) {
            color = getResources().getColor(R.color.colorSuplier);
        } else {
        }

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(color));
        setStatusBarColor(color);

        setTitle("Фильтр заказов", "");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        switchContent(FilterFragment.newInstance(), false, true);

    }

//    private void prepareListData() {
//        listDataHeader = new ArrayList<String>();
//        listDataHeader.add(getString(R.string.number));
//        listDataHeader.add(getString(R.string.status));
//        listDataHeader.add(getString(R.string.order_type));
//
//        listDataHeader.add(getString(R.string.auto_class));
//        listDataHeader.add(getString(R.string.address));
//        listDataHeader.add(getString(R.string.date));
//
//    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.filter, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        int id = item.getItemId();
//
//        try {
//
//            switch (id){
//
//                case android.R.id.home:
//                    onBackPressed();
//                    break;
//
//                default:
//                    break;
//            }
//
//        } catch (Exception e){
////            DialogUtils.showErrorDialog(this, LOG_PREFIX, e);
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    public void onBackPressed() {
        finish();
    }

}
