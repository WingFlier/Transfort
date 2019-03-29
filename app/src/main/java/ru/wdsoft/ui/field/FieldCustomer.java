package ru.wdsoft.ui.field;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import ru.wdsoft.R;
import ru.wdsoft.api.ApiSerializable;
import ru.wdsoft.api.models.Company;
import ru.wdsoft.db.tables.SQLCompany;
import ru.wdsoft.ui.datafield.EditDataField;
import ru.wdsoft.ui.datafield.ValueAdapter;
import ru.wdsoft.utils.Utils;

/**
 * Created by slaventii@mail.ru on 27.02.2019.
 */
public class FieldCustomer extends EditDataField {

    public FieldCustomer(Context context, AttributeSet attrs) {
        super(context, attrs);
        setType(EditDataField.TYPE_REF);
    }

    @Override
    public void initData(Bundle params) {

        if (params != null){
            String id = params.getString("id");
            setDataModel(SQLModel.Customer(id));
        }

    }

    @Override
    protected void searchForValue(String val) {

        if (Utils.stringsNotEmpty(val)){
            String filter = SQLCompany.FIELD_COMPANY_NAME + " LIKE '%" + val.trim() + "%'";
            ArrayList<ApiSerializable> list = SQLCompany.getList(filter);
            if (list != null && list.size() > 0){

                Company company = (Company)list.get(0);
                setValue(company.getId(), company.getName());

            } else {
                clearValue();
            }
        }
    }

    @Override
    protected ArrayAdapter getValueAdapter() {

        if (getDataModel() == null) return null;

        ArrayList<ApiSerializable> list = SQLCompany.getList(getDataModel().getCursor());

        if (list == null) return null;

        ValueAdapter adapter = new ValueAdapter(getContext(), R.layout.drop_down_item, list);
        adapter.setLisner(new ValueAdapter.IValueListener() {
            @Override
            public void onSelected(ApiSerializable object) {
                if (object instanceof Company){
                    Company company = (Company) object;
                    setValue(company.getId(), company.getName());
                }
            }
        });

        return adapter;
    }

}
