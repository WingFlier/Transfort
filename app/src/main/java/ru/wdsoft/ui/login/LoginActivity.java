package ru.wdsoft.ui.login;

import android.content.Loader;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import ru.wdsoft.R;
import ru.wdsoft.ui.base.WDActivity;
import ru.wdsoft.utils.Utils;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends WDActivity{
    private final String LOG_PREFIX = this.getClass().getName() + " -- ";
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // remove title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_auth);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setVisibility(View.GONE);
        login.switchFragment(LoginFragment.newInstance(login), false, true);
    }

    @Override
    protected Loader getLoader(int id) {
        return super.getLoader(id);
    }

    ILogin login = new ILogin() {
        @Override
        public void switchFragment(Fragment fragment, boolean addToBack, boolean clearBackStack) {
            switchContent(fragment, addToBack, clearBackStack);
        }

        @Override
        public void changeToolbar(String title) {
            toolbar.setVisibility(View.VISIBLE);
            setSupportActionBar(toolbar);
            showLoader(false);
            //TODO stopped here
            // make it invisible for LoginFragment and show on CheckIn and ForgotPass
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.RED));
            setStatusBarColor(Color.RED);
            setTitle(title, "");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        @Override
        public void setToolbarVisible(boolean visible) {
            toolbar.setVisibility(visible ? View.VISIBLE : View.GONE);
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switchContent(LoginFragment.newInstance());
        return super.onOptionsItemSelected(item);
    }
}

