package ru.wdsoft.ui.field;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;

import ru.wdsoft.ui.datafield.EditDataField;
import ru.wdsoft.utils.CastUtils;
import ru.wdsoft.utils.DateTimeUtils;

/**
 * Created by slaventii@mail.ru on 27.02.2019.
 */
public class FieldDateTime extends EditDataField {

    public FieldDateTime(Context context, AttributeSet attrs) {
        super(context, attrs);
        setType(EditDataField.TYPE_DATETIME);
    }

    @Override
    public void initData(Bundle params) {

        if (params != null){
            long date = params.getLong("date");
            setValue(CastUtils.toUIDateTime(date));
        }
    }
}
