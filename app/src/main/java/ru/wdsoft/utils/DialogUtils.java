package ru.wdsoft.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import ru.wdsoft.R;

/**
 * Created by slaventii@mail.ru on 13.02.2019.
 */
public class DialogUtils {

    /**
     * ДИАЛОГОВЫЕ ОКНА
     */

    public static AlertDialog showYesNoCancelDialog(Context context, String title, String msg,
                                                    DialogInterface.OnClickListener yesClick,
                                                    DialogInterface.OnClickListener noClick,
                                                    DialogInterface.OnClickListener neutClick) {

        return showAlertDialog(context, 0, title, msg,
                context.getResources().getString(R.string.Yes), yesClick,
                context.getResources().getString(R.string.No), noClick,
                context.getResources().getString(R.string.Cancel), neutClick);

    }


    public static AlertDialog showYesNoAlertDialog(Context context, String title, String msg, DialogInterface.OnClickListener yesClick, DialogInterface.OnClickListener noClick) {
        return showAlertDialog(context, 0, title, msg,
                context.getResources().getString(R.string.Yes), yesClick,
                context.getResources().getString(R.string.No), noClick, null, null);
    }

    public static AlertDialog showAlertDialog(Context context, String title, String msg, DialogInterface.OnClickListener posClick) {
        return showAlertDialog(context, 0, title, msg, context.getResources().getString(R.string.Ok), posClick, null, null, null, null);
    }

    public static AlertDialog showErrorDialog(Context context, String msg) {
        return showAlertDialog(context, 0, context.getResources().getString(R.string.Error), msg, context.getResources().getString(R.string.Ok), null, null, null, null, null);
    }

    public static AlertDialog showErrorDialog(Context context, String tag, Throwable e) {
        return showAlertDialog(context, 0, context.getResources().getString(R.string.Error), e.getLocalizedMessage(), context.getResources().getString(R.string.close), null,
                null, null, null, null);
    }


    public static AlertDialog showAlertDialog(Context context, String title, String msg) {
        return showAlertDialog(context, 0, title, msg, context.getResources().getString(R.string.Ok), null, null, null, null, null);
    }

    public static AlertDialog showAlertDialog(Context context, int iconId, String title, String msg,
                                              String posCaption,
                                              DialogInterface.OnClickListener posClick,
                                              String negCaption,
                                              DialogInterface.OnClickListener negClick,
                                              String neutCaption,
                                              DialogInterface.OnClickListener neutClick){

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);

        if (iconId > 0) {
            builder.setIcon(iconId);
        }

        builder.setTitle(title);
        builder.setMessage(msg);

        if (posCaption != null) {
            builder.setPositiveButton(posCaption, posClick);
        }

        if (negCaption != null) {
            builder.setNegativeButton(negCaption, negClick);
        }

        if (neutCaption != null) {
            builder.setNeutralButton(neutCaption, neutClick);
        }


        AlertDialog dialog = builder.create();
        dialog.show();
        return dialog;
    }

    public static View getTitleView(Activity activity, String strTitle){

        return getTitleView(activity, strTitle, null);
    }

    public static View getTitleView(Activity activity, String strTitle, Integer bg_color){

        LayoutInflater inflater = activity.getLayoutInflater();
        View viewTitle = inflater.inflate(R.layout.dialog_title, null);

        if (bg_color != null){
            viewTitle.setBackgroundColor(bg_color);
        } else {
            viewTitle.setBackgroundResource(R.drawable.side_nav_bar);
        }

        TextView title = viewTitle.findViewById(R.id.title);
        title.setText(strTitle);

        return viewTitle;
    }

}
