package ru.wdsoft.utils;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by slaventii@mail.ru on 13.02.2019.
 */
public class ViewUtils {

    private static final String LOG_PREFIX = "ViewUtils -- ";

    public static void showSnackBar(View container, String text){

        if (container != null) {
            Snackbar.make(container, text, Snackbar.LENGTH_SHORT).show();
        }

    }

    public static void showSnackBar(View container, String text, int duration){

        if (container != null) {
            Snackbar.make(container, text, duration).show();
        }

    }

    public static void hideSoftKeyboard(Context context, View view) {

        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);

        try {
            if (imm.isAcceptingText()) { // verify if the soft keyboard is open
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        } catch (NullPointerException e) {
            LogUtils.debugErrorLog(LOG_PREFIX, e);
        }
    }

    public static void hideSoftKeyboard(Activity context, View view) {

        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);

        try {
            if (imm.isAcceptingText()) { // verify if the soft keyboard is open
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        } catch (NullPointerException e) {
            LogUtils.debugErrorLog(LOG_PREFIX, e);
        }
    }

    public static void showSoftKeyboard(Activity context, View view) {

        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        try {
            imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
        } catch (NullPointerException e) {
            LogUtils.debugErrorLog(LOG_PREFIX, e);
        }
    }

    public static void hideSoftKeyboard(Activity context, View view, boolean hide) {

        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);

        try {

            if (hide) {

                if (imm.isAcceptingText()) { // verify if the soft keyboard is open
                    imm.hideSoftInputFromWindow(context.getCurrentFocus().getWindowToken(), 0);
                }
            } else {
                imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
            }
        } catch (NullPointerException e) {
            LogUtils.debugErrorLog(LOG_PREFIX, e);
        }
    }



}
