package ru.wdsoft.ui.datafield;

import java.lang.reflect.Field;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.NumberPicker;
import android.widget.TimePicker;

public class TimePickerDialog150Hours extends TimePickerDialog {

    private final static int TIME_PICKER_INTERVAL = 1;
    private TimePicker timePicker;
    private final OnTimeSetListener callback;
    private int mMaxVal = 149;

    private int mHour;

    public TimePickerDialog150Hours(Context context, OnTimeSetListener callBack,
                                    int hourOfDay, int minute, boolean is24HourView, int maxVal) {

        super(context, TimePickerDialog.THEME_HOLO_LIGHT, callBack, hourOfDay, minute / TIME_PICKER_INTERVAL,
                is24HourView);

        mHour = hourOfDay;
        mMaxVal = maxVal;

        this.callback = callBack;

    }

    public TimePickerDialog150Hours(Context context, OnTimeSetListener callBack,
                                    int hourOfDay, int minute, boolean is24HourView) {

        super(context, TimePickerDialog.THEME_HOLO_LIGHT, callBack, hourOfDay, minute / TIME_PICKER_INTERVAL,
                is24HourView);

        mHour = hourOfDay;

        this.callback = callBack;
    }


    @Override
    public void onClick(DialogInterface dialog, int which) {

        if (which == -1 && callback != null && timePicker != null) {
            timePicker.clearFocus();
            callback.onTimeSet(timePicker, timePicker.getCurrentHour(),
                    timePicker.getCurrentMinute() * TIME_PICKER_INTERVAL);
        }
    }

    @Override
    protected void onStop() {
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();


        try {
            Class<?> classForid = Class.forName("com.android.internal.R$id");
            Field timePickerField = classForid.getField("timePicker");
            this.timePicker = (TimePicker) findViewById(timePickerField
                    .getInt(null));
            Field field = classForid.getField("hour");
            NumberPicker mMinuteSpinner = (NumberPicker) timePicker
                    .findViewById(field.getInt(null));
            mMinuteSpinner.setMinValue(0);
            mMinuteSpinner.setMaxValue(mMaxVal);

            mMinuteSpinner.setValue(mHour);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}