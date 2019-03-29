package ru.wdsoft.ui.field;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import ru.wdsoft.R;
import ru.wdsoft.api.ApiSerializable;
import ru.wdsoft.api.models.BodyType;
import ru.wdsoft.api.models.Department;
import ru.wdsoft.db.tables.SQLDepartment;
import ru.wdsoft.ui.datafield.EditDataField;
import ru.wdsoft.ui.datafield.ValueAdapter;
import ru.wdsoft.utils.Utils;

/**
 * Created by slaventii@mail.ru on 19.03.2019.
 */
public class FieldBodyType extends EditDataField {

    private ArrayList<ApiSerializable> listBodyType;

    public FieldBodyType(Context context, AttributeSet attrs) {
        super(context, attrs);
        setType(EditDataField.TYPE_REF);
        listBodyType = getBodyTypes();
        setValueAdapter();
    }

    private ArrayList<ApiSerializable> getBodyTypes(){

        ArrayList<ApiSerializable> bodyTypes = new ArrayList<>();
        bodyTypes.add(createBodyType("O", "Открытый"));
        bodyTypes.add(createBodyType("C", "Закрытый"));

        return bodyTypes;

    }

    private BodyType createBodyType(String code, String name){

        BodyType bodyType = new BodyType();
        bodyType.setCode(code);
        bodyType.setName(name);
        return bodyType;

    }

    @Override
    public void initData(Bundle params) {

        if (params != null){
            String code = params.getString("code");

            if (Utils.stringsNotEmpty(code)){
                for (ApiSerializable bodyType: listBodyType){
                    BodyType btype = (BodyType)bodyType;
                    if (btype.getCode().equalsIgnoreCase(code)){
                        setValue(btype.getName());
                        setGuid(btype.getCode());
                        break;
                    }
                }
            }
        }
    }

//    @Override
//    protected void searchForValue(String val) {
//
//        if (Utils.stringsNotEmpty(val)){
//            String filter = SQLDepartment.FIELD_DEPARTMENT_NAME + " LIKE '%" + val.trim() + "%'";
//            ArrayList<ApiSerializable> list = SQLDepartment.getList(filter);
//            if (list != null && list.size() > 0){
//                Department department = (Department)list.get(0);
//                setValue(department.getId(), department.getName());
//
//            } else {
//                clearValue();
//            }
//        }
//    }

    @Override
    protected ArrayAdapter getValueAdapter() {

        if (listBodyType == null) return null;

        ValueAdapter adapter = new ValueAdapter(getContext(), R.layout.drop_down_item, listBodyType);
        adapter.setLisner(new ValueAdapter.IValueListener() {
            @Override
            public void onSelected(ApiSerializable object) {
                if (object instanceof BodyType){
                    BodyType bodyType = (BodyType) object;
                    setValue(bodyType.getCode(), bodyType.getName());
                }
            }
        });

        return adapter;
    }

}
