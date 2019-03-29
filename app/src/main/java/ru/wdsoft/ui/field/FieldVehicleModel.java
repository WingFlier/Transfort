package ru.wdsoft.ui.field;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import ru.wdsoft.R;
import ru.wdsoft.api.ApiSerializable;
import ru.wdsoft.api.models.OrderType;
import ru.wdsoft.api.models.VehicleModel;
import ru.wdsoft.db.tables.SQLOrderType;
import ru.wdsoft.db.tables.SQLVehicleModel;
import ru.wdsoft.ui.datafield.EditDataField;
import ru.wdsoft.ui.datafield.ValueAdapter;
import ru.wdsoft.utils.Utils;

/**
 * Created by slaventii@mail.ru on 27.02.2019.
 */
public class FieldVehicleModel extends EditDataField {

    public FieldVehicleModel(Context context, AttributeSet attrs) {
        super(context, attrs);
        setType(EditDataField.TYPE_REF);
    }

    @Override
    public void initData(Bundle params) {
        String id = "";
        if (params != null){
            id = params.getString("id");
        }
        setDataModel(SQLModel.VehicleModel(id));
    }

    @Override
    protected void searchForValue(String val) {

        if (Utils.stringsNotEmpty(val)){
            String filter = SQLVehicleModel.FIELD_VMODEL_NAME + " LIKE '%" + val.trim() + "%'";
            ArrayList<ApiSerializable> list = SQLVehicleModel.getList(filter);
            if (list != null && list.size() > 0){

                VehicleModel vehicleModel = (VehicleModel)list.get(0);
                setValue(vehicleModel.getId(), vehicleModel.getName());

            } else {
                clearValue();
            }
        }
    }

    @Override
    protected ArrayAdapter getValueAdapter() {

        if (getDataModel() == null) return null;

        ArrayList<ApiSerializable> list = SQLVehicleModel.getList(getDataModel().getCursor());

        if (list == null) return null;

        ValueAdapter adapter = new ValueAdapter(getContext(), R.layout.drop_down_item, list);
        adapter.setLisner(new ValueAdapter.IValueListener() {
            @Override
            public void onSelected(ApiSerializable object) {
                if (object instanceof VehicleModel){
                    VehicleModel vehicleModel = (VehicleModel)object;
                    setValue(vehicleModel.getId(),vehicleModel.getName());
                }
            }
        });

        return adapter;
    }
}
