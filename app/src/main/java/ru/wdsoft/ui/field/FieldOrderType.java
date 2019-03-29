package ru.wdsoft.ui.field;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import ru.wdsoft.R;
import ru.wdsoft.api.ApiSerializable;
import ru.wdsoft.api.models.OrderType;
import ru.wdsoft.db.tables.SQLOrderType;
import ru.wdsoft.ui.datafield.EditDataField;
import ru.wdsoft.ui.datafield.ValueAdapter;
import ru.wdsoft.utils.Utils;

/**
 * Created by slaventii@mail.ru on 26.02.2019.
 */
public class FieldOrderType extends EditDataField {

    public FieldOrderType(Context context, AttributeSet attrs) {
        super(context, attrs);
        setType(TYPE_REF);
    }

    @Override
    public void initData(Bundle params) {
        String id = "";
        if (params != null){
            id = params.getString("id");
        }
        setDataModel(SQLModel.OrderType(id));
    }


    @Override
    protected void searchForValue(String val) {

        if (Utils.stringsNotEmpty(val)){
            String filter = SQLOrderType.FIELD_ORDERTYPE_NAME + " LIKE '%" + val.trim() + "%'";
            ArrayList<ApiSerializable> list = SQLOrderType.getList(filter);
            if (list != null && list.size() > 0){

                OrderType orderType = (OrderType)list.get(0);
                setValue(orderType.getCode(), orderType.getName());

            } else {
                clearValue();
            }
        }
    }

    @Override
    protected ArrayAdapter getValueAdapter() {

        if (getDataModel() == null) return null;

        ArrayList<ApiSerializable> list = SQLOrderType.getList(getDataModel().getCursor());

        if (list == null) return null;

        ValueAdapter adapter = new ValueAdapter(getContext(), R.layout.drop_down_item, list);
        adapter.setLisner(new ValueAdapter.IValueListener() {
            @Override
            public void onSelected(ApiSerializable object) {
                if (object instanceof OrderType){
                    OrderType orderType = (OrderType)object;
                    setValue(orderType.getCode(),orderType.getName());
                }
            }
        });

        return adapter;
    }

}
