package ru.wdsoft.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AlertDialog;

import java.io.InputStream;

import static android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS;

/**
 * Created by slaventii@mail.ru on 13.02.2019.
 */
public class SysUtils {

    private static final String LOG_PREFIX = "StreamUtils -- ";

    public static boolean hasCameraPermission(Activity activity){

//        if (ContextCompat.checkSelfPermission(activity,
//                android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                != PackageManager.PERMISSION_GRANTED
//
//                && ContextCompat.checkSelfPermission(activity,
//                Manifest.permission.CAMERA)
//                != PackageManager.PERMISSION_GRANTED ) {
//
//            ActivityCompat.requestPermissions(activity,
//                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                            Manifest.permission.CAMERA},0);
//
//            return false;
//        }

        return true;
    }

    public static void checkforLocationServices(final Context context){

        LocationManager lm = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;

        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch(Exception ex) {}

        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch(Exception ex) {}

        if(!gps_enabled && !network_enabled) {
            // notify user
            AlertDialog.Builder dialog = new AlertDialog.Builder(context);
            dialog.setMessage("Служба геолокации не включена");
            dialog.setPositiveButton("Включить геолокацию",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                            // TODO Auto-generated method stub
                            Intent myIntent = new Intent(ACTION_LOCATION_SOURCE_SETTINGS);
                            context.startActivity(myIntent);
                            //get gps
                        }
                    });
            dialog.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub

                }
            });
            dialog.show();
        }
    }

    public static void phoneCall(Context ctx, String phoneNum){

        String number = "tel: " + phoneNum;
        Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse(number));
        try {
            ctx.startActivity(callIntent);
        } catch (Exception e) {
            LogUtils.debugErrorLog(LOG_PREFIX, e);
        }
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static String getVersion(Context ctx){

        PackageInfo pInfo = null;
        try {
            pInfo = ctx.getPackageManager().getPackageInfo(ctx.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
//            Utils.debugErrorLog(LOG_PREFIX, e);
        }

        String ver = pInfo.versionName + pInfo.versionCode;
        return ver;
    }

    public static byte[] getRawString(Context ctx, int id){

        try {

            InputStream is = ctx.getResources().openRawResource(id);
            byte[] buffer = new byte[is.available()];
            is.read(buffer);

            return buffer;

        } catch (OutOfMemoryError ome){
            LogUtils.debugErrorLog(LOG_PREFIX, ome);
            return null;
        }
        catch (Exception e) {
            LogUtils.debugErrorLog(LOG_PREFIX, e);
            return null;
        }

    }

}
