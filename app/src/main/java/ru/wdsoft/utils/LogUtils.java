package ru.wdsoft.utils;

import android.util.Log;

/**
 * Created by slaventii@mail.ru on 13.02.2019.
 */
public class LogUtils {

  /**
   * ЛОГИРОВАНИЕ
   */

  public static void debugErrorLog(String tag, String mes){
    if (Utils.stringsNotEmpty(mes)){
      Log.d(tag, mes);
    }
  }

  public static void debugErrorLog(String tag, Throwable e){
    Log.w(tag, "ERROR", e);
  }

  public static void debugLog(String tag, String mes){

    if (mes.length() > 3000) {

      String strOut = mes.substring(0, 3000);
      Log.i(tag, strOut);

      String strSubs = mes.substring(3000);

      while (strSubs.length() > 3000){

        strOut = strSubs.substring(0, 3000);
        Log.i(tag, strOut);

        strSubs = strSubs.substring(3000);
      }

      Log.i(tag, strSubs);
    } else {
      Log.i(tag, mes);
    }
  }

}
