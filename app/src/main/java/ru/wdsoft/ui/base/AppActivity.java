package ru.wdsoft.ui.base;

import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import ru.wdsoft.R;
import ru.wdsoft.utils.DialogUtils;


public class AppActivity extends WDActivity {

    /**
     * Для логирования
     */
    private final String LOG_PREFIX = this.getClass().getName() + " -- ";

    public final static String PREF_FILTER = "filter";
    public final static String ARG_FRAG = "fragment";
    public final static int STATISTIC = 0;

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    public static void newStatFragment(Context context){

        Intent intent = new Intent(context, AppActivity.class);

        intent.putExtra(ARG_FRAG, STATISTIC);

        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        int frag = getIntent().getIntExtra(ARG_FRAG, 0);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        switch (frag){

            case STATISTIC:
                break;

            default:
                break;
        }
    }

    @Override
    protected void onResume() {

        super.onResume();
        bindR24Service();

    }

    @Override
    protected void onStop() {
        unBindR24Service();
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        try {

            switch (id){

                case android.R.id.home:
                    popBackStack();
                    break;

                default:
                    break;
            }

        } catch (Exception e){
            DialogUtils.showErrorDialog(this, LOG_PREFIX, e);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected Object loaderBackground(int id, Bundle args) {

        switch (id){

            default:
                break;
        }

        return null;
    }

    @Override
    protected void loaderFinished(Loader loader, Object data) {

        switch (loader.getId()){

            default:
                break;
        }

    }


}
