package ru.wdsoft.ui.dataload;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import ru.wdsoft.R;
import ru.wdsoft.api.BackTask;
import ru.wdsoft.ui.base.WDActivity;
import ru.wdsoft.ui.login.LoginActivity;
import ru.wdsoft.ui.main.MainActivity;


public class PrimaryDataLoadActivity extends WDActivity {

    private static final int UPDATE_NOMENCLATURE = 0;

//    private TextView mDescr;


    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        // remove title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_primary_load);

//        mDescr = (TextView)findViewById(R.id.descr);

        showLoader(false);

    }

    @Override
    protected void onResume() {
        super.onResume();
        initLoader(UPDATE_NOMENCLATURE);
    }

    @Override
    protected Object loaderBackground(int id, Bundle args) {

        switch (id) {

            case UPDATE_NOMENCLATURE:
                updateNomenclatures();
                break;

            default:
                break;
        }

        return null;

    }

    @Override
    public void onBackPressed() {
        destroyLoader(UPDATE_NOMENCLATURE);
        showLogin();
    }

    @Override
    protected void loaderFinished(android.content.Loader loader, Object data) {

        switch (loader.getId()) {

            case UPDATE_NOMENCLATURE:
                getData().setDbUpdateNeeded(this, false);
                showOrders();
                break;

            default:
                break;
        }
    }

    public void updateNomenclatures() {
        BackTask.updateDb();
        BackTask.updateNomenclatures(this);
    }


    private void showLogin(){
        Intent intentLogin = new Intent(this, LoginActivity.class);
        startActivity(intentLogin);
        finish();
    }

    private void showOrders(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(MainActivity.ARG_STATUS_BAR_COLOR, R.color.colorDarkBlue);
        startActivity(intent);
        finish();
    }

}
