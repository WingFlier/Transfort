package ru.wdsoft.ui.field;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import ru.wdsoft.R;
import ru.wdsoft.api.ApiSerializable;
import ru.wdsoft.api.models.OrderState;
import ru.wdsoft.api.models.OrderType;
import ru.wdsoft.db.tables.SQLOrderState;
import ru.wdsoft.db.tables.SQLOrderType;
import ru.wdsoft.ui.datafield.EditDataField;
import ru.wdsoft.ui.datafield.ValueAdapter;
import ru.wdsoft.utils.Utils;

/**
 * Created by slaventii@mail.ru on 26.02.2019.
 */
public class FieldOrderStateCheckList extends EditDataField {

    private String[] arrSelectedStates;

    public FieldOrderStateCheckList(Context context, AttributeSet attrs) {
        super(context, attrs);
        setType(TYPE_CHECK_LIST);
    }

    @Override
    public void initData(Bundle params) {
        if (params != null){
            arrSelectedStates = params.getStringArray("states");
        }
        setDataModel(SQLModel.OrderState());
    }

    @Override
    protected ArrayAdapter getValueAdapter() {

        if (getDataModel() == null) return null;

        ArrayList<ApiSerializable> list = SQLOrderState.getList(getDataModel().getCursor());

        if (list == null) return null;

        if (arrSelectedStates != null && arrSelectedStates.length > 0){
            for (ApiSerializable ord_state: list){
                for (String state: arrSelectedStates){
                    if ( ((OrderState)ord_state).getState().equalsIgnoreCase(state)){
                        ord_state.isChecked(true);
                        break;
                    }
                }
            }
        }


        ValueAdapter adapter = new ValueAdapter(getContext(), R.layout.item_checkbox, list);
        adapter.setLisner(new ValueAdapter.IValueListener() {
            @Override
            public void onSelected(ApiSerializable object) {
                if (mValueChangedListener != null){
                    mValueChangedListener.onChanged(FieldOrderStateCheckList.this);
                }
            }
        });

        return adapter;
    }

}
