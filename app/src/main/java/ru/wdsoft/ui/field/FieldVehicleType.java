package ru.wdsoft.ui.field;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import ru.wdsoft.R;
import ru.wdsoft.api.ApiSerializable;
import ru.wdsoft.api.models.VehicleType;
import ru.wdsoft.db.tables.SQLVehicleType;
import ru.wdsoft.ui.datafield.EditDataField;
import ru.wdsoft.ui.datafield.ValueAdapter;
import ru.wdsoft.utils.Utils;

/**
 * Created by slaventii@mail.ru on 26.02.2019.
 */
public class FieldVehicleType extends EditDataField {

    public FieldVehicleType(Context context, AttributeSet attrs) {
        super(context, attrs);
        setType(EditDataField.TYPE_REF);
    }

    @Override
    public void initData(Bundle params) {
        String id = "";
        if (params != null){
            id = params.getString("id");
        }
        setDataModel(SQLModel.VehicleType(id));
    }

    @Override
    protected void searchForValue(String val) {

        if (Utils.stringsNotEmpty(val)){
            String filter = SQLVehicleType.FIELD_VEHICLETYPE_NAME + " LIKE '%" + val.trim() + "%'";
            ArrayList<ApiSerializable> list = SQLVehicleType.getList(filter);
            if (list != null && list.size() > 0){

                VehicleType vehicleType = (VehicleType)list.get(0);
                setValue(vehicleType.getId(),vehicleType.getName());

            } else {
                clearValue();
            }
        }
    }

    @Override
    protected ArrayAdapter getValueAdapter() {

        if (getDataModel() == null) return null;

        ArrayList<ApiSerializable> list = SQLVehicleType.getList(getDataModel().getCursor());

        if (list == null) return null;

        ValueAdapter adapter = new ValueAdapter(getContext(), R.layout.drop_down_item, list);
        adapter.setLisner(new ValueAdapter.IValueListener() {
            @Override
            public void onSelected(ApiSerializable object) {
                if (object instanceof VehicleType){
                    VehicleType vehicleType = (VehicleType)object;
                    setValue(vehicleType.getId(),vehicleType.getName());
                }
            }
        });

        return adapter;
    }

}
