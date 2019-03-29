package ru.wdsoft.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import ru.wdsoft.R;
import ru.wdsoft.db.WDDbHelper;
import ru.wdsoft.ui.base.WDActivity;
import ru.wdsoft.utils.DialogUtils;
import ru.wdsoft.utils.Utils;
import ru.wdsoft.api.Session;
import ru.wdsoft.ui.login.LoginActivity;
import ru.wdsoft.ui.main.MainActivity;
import ru.wdsoft.ui.dataload.PrimaryDataLoadActivity;


public class SplashActivity extends WDActivity {

    private final String LOG_PREFIX = this.getClass().getName() + " -- ";

    @Override
    public void onCreate(Bundle icicle) {

        super.onCreate(icicle);

        // remove title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash);

        final View splash = findViewById(R.id.splash);

        if (getDb() == null) {
            DialogUtils.showErrorDialog(this, "Ошибка создания базы данных!");
            return;
        }

        startR24Service();

        if (WDDbHelper.isCreated()) {
            startApp(splash);
        } else if (WDDbHelper.needUpdate()) {
            startDbUpdate(splash);
        } else {
            startApp(splash);
        }

    }

    private void animateSplash(final View splash, final Intent intent) {

        final Animation fade_out = AnimationUtils.loadAnimation(this, R.anim.fade_out);
        fade_out.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                splash.setVisibility(View.GONE);
                startActivity(intent);
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        Animation fade_in = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        fade_in.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                splash.startAnimation(fade_out);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

        });

        splash.startAnimation(fade_in);
    }

    private void startApp(View splash) {

        boolean fast_start = false;

        Session session = getData().getSession(this);

        if (session != null) {

            if (Utils.stringsNotEmpty(session.getSessionKey())) {
                fast_start = true;
            }
        }
        fast_start = false;
        if (fast_start) {

            Intent intent = new Intent(this, MainActivity.class);
            animateSplash(splash, intent);

        } else {

            Intent intent = new Intent(this, LoginActivity.class);
            animateSplash(splash, intent);
        }
    }

    private void startDbUpdate(View splash) {
        Intent intent = new Intent(this, PrimaryDataLoadActivity.class);
        animateSplash(splash, intent);
    }

}
