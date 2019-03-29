package ru.wdsoft.ui.field;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;

import ru.wdsoft.ui.datafield.EditDataField;
import ru.wdsoft.utils.CastUtils;

/**
 * Created by slaventii@mail.ru on 27.02.2019.
 */
public class FieldText extends EditDataField {

    public FieldText(Context context, AttributeSet attrs) {
        super(context, attrs);
        setType(EditDataField.TYPE_NONE);
    }

    @Override
    public void initData(Bundle params) {
        if (params != null){
            String value = params.getString("value");
            setValue(value);
        }
    }
}
