package ru.wdsoft.ui.base;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by slaventii@mail.ru on 19.03.2019.
 */
public class WDDialog extends Dialog {

    public WDDialog(@NonNull Context context) {
        super(context);
    }

    public WDDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected WDDialog(@NonNull Context context, boolean cancelable,
                       @Nullable DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }


}
