package ru.wdsoft.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by slaventii@mail.ru on 13.02.2019.
 */
public class PrefUtils {

    /**
     * Работа с префами
     */

    public static void setPref(Context ctx, String key, boolean val){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(ctx);
        sp.edit().putBoolean(key, val).apply();
    }

    public static boolean getPrefBool(Context ctx, String key, boolean def){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(ctx);
        return sp.getBoolean(key, def);
    }

}
