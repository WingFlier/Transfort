package ru.wdsoft.ui.field;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;

import ru.wdsoft.ui.datafield.EditDataField;

/**
 * Created by slaventii@mail.ru on 19.03.2019.
 */
public class FieldInteger extends EditDataField {

    public FieldInteger(Context context, AttributeSet attrs) {
        super(context, attrs);
        setType(EditDataField.TYPE_INT);
    }

    @Override
    public void initData(Bundle params) {
        if (params != null){
            String value = params.getString("value");
            setValue(value);
        }
    }
}
