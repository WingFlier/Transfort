package ru.wdsoft.ui.field;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;

import ru.wdsoft.ui.datafield.EditDataField;

/**
 * Created by slaventii@mail.ru on 27.02.2019.
 */
public class FieldPhone extends EditDataField {

    public FieldPhone(Context context, AttributeSet attrs) {
        super(context, attrs);
        setType(EditDataField.TYPE_PHONE);
    }

    @Override
    public void initData(Bundle params) {
        if (params != null){
            String value = params.getString("value");
            setValue(value);
        }
    }
}
