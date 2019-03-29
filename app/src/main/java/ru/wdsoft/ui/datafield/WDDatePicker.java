package ru.wdsoft.ui.datafield;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewParent;
import android.widget.DatePicker;

/**
 * Created by slaventii@mail.ru on 03.08.2017.
 */

public class WDDatePicker extends DatePicker {

    public WDDatePicker(Context context) {
        super(context);
    }

    public WDDatePicker(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WDDatePicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        if (ev.getActionMasked() == MotionEvent.ACTION_DOWN)
        {
            ViewParent p = getParent();
            if (p != null)
                p.requestDisallowInterceptTouchEvent(true);
        }

        return false;
    }
}
