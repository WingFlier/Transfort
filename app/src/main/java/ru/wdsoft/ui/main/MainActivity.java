package ru.wdsoft.ui.main;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.wdsoft.R;
import ru.wdsoft.main.AppMode;
import ru.wdsoft.ui.base.WDActivity;
import ru.wdsoft.ui.orderlist.OrderListFragment;
import ru.wdsoft.utils.LogUtils;

public class MainActivity extends WDActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private final String LOG_PREFIX = this.getClass().getName() + " -- ";

    private NavigationView mNavigationView;
    DrawerLayout drawer;
    private Toolbar toolbar;

    @BindView(R.id.rlHeader)
    RelativeLayout rlHeader;

    @BindView(R.id.ivHeaderLogo)
    ImageView ivHeaderLogo;

    @BindView(R.id.tvHeaderTitle)
    TextView tvHeaderTitle;

    @BindView(R.id.tvHeaderStatus)
    TextView tvHeaderStatus;

    @BindView(R.id.btnHeaderSwitch)
    Button btnHeaderSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorAccent));

        setSupportActionBar(toolbar);


        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        mNavigationView = findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);

        View headerView = mNavigationView.getHeaderView(0);
        if (headerView != null){
            ButterKnife.bind(this,headerView);
        }

//        rlHeader = drawer.findViewById(R.id.rlHeader);




        setAppMode(getData().getMode());
//
//        PackageInfo pInfo = null;
//        try {
//            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
//        } catch (PackageManager.NameNotFoundException e) {
//            LogUtils.debugErrorLog(LOG_PREFIX, e);
//        }

        showOrdersList();

        btnHeaderSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchAppMode();
            }
        });


//        TextView version = findViewById(R.id.version);
//
//        String ver = version.getText().toString();
//        ver += " " + pInfo.versionName + "." + pInfo.versionCode;
//
//        version.setText(ver);
    }

    private void switchAppMode(){
        if (getData().getMode() == AppMode.SUPPLIER){
            setAppMode(AppMode.CUSTOMER);
        } else {
            setAppMode(AppMode.SUPPLIER);
        }
    }

    private void setAppMode(AppMode mode){

        int color = getResources().getColor(R.color.colorCustomer);
        String txtMode = "Заказчик";

        if (mode == AppMode.SUPPLIER) {
            color = getResources().getColor(R.color.colorSuplier);
            txtMode = "Исполнитель";
        }

        setStatusBarColor(color);
        toolbar.setBackgroundColor(color);
        rlHeader.setBackgroundColor(color);
        tvHeaderStatus.setText(txtMode);

        tvHeaderTitle.setText(getData().getUserName());

        getData().setMode(mode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        switch (id){

            default:
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void showOrdersList(){
        setTitle("Мои заказы","");
        switchContent(OrderListFragment.newInstance(), false, true);

    }

    @Override
    public void onBackPressed() {
        finish();
    }
}