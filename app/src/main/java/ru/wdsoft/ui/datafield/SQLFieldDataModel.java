package ru.wdsoft.ui.datafield;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;

import ru.wdsoft.db.SQLUtils;
import ru.wdsoft.db.WDDbHelper;
import ru.wdsoft.utils.Utils;

/**
 * Created by slaventii@mail.ru on 04.06.2018.
 */

public class SQLFieldDataModel implements IUIFieldDataModel {

    private String mSql;
    private String mSqlFilter;
    private String mSqlNoDataFilter;
    private String mSqlSelectedItem;
    private DataFilter mDataFilter;

    public SQLFieldDataModel(String sql, String filter, String sqlSelectedItem){
        mSql = sql;
        mSqlFilter = filter;
        mSqlSelectedItem = sqlSelectedItem;
    }

    @Override
    public void setDataFilter(DataFilter dataFilter){
        mDataFilter = dataFilter;
    }

    @Override
    public Cursor getCursor(String filter) {

        if (!Utils.stringsNotEmpty(filter)){
            return getCursor();
        }

        String new_filter = mSqlFilter;

        if (Utils.stringsNotEmpty(new_filter)){
            new_filter += " AND ";
        }

        new_filter += "(" + filter + ")";

        return SQLUtils.selectData(mSql, new_filter);
    }

    @Override
    public Cursor getCursor() {

        Cursor cursor = SQLUtils.selectData(mSql, mSqlFilter);

        if (cursor == null || cursor.getCount() == 0){
            if (mDataFilter != null){
                String filter = mDataFilter.getNoDataFilter();
                if (Utils.stringsNotEmpty(filter)){
                    mSqlFilter = filter;
                }
            }

            cursor = SQLUtils.selectData(mSql, mSqlNoDataFilter);
        }

        return cursor;
    }

    @Override
    public Bundle getDefaultSelected() {

        Bundle def = null;

        if (Utils.stringsNotEmpty(mSql, mSqlSelectedItem)){

            Cursor c = SQLUtils.selectData(mSql, mSqlSelectedItem);

            if (c != null && c.getCount() > 0){

                if (c.moveToFirst()){
                    def = new Bundle();

                    def.putString("id", c.getString(0));
                    def.putString("val", c.getString(1));
                }

                c.close();
            }

        }

        return def;
    }

    @Override
    public void updateDataFilter(String[] params) {
        if (mDataFilter != null){
            String filter = mDataFilter.updateDataFilter(params);
            if (Utils.stringsNotEmpty(filter)){
                mSqlFilter = filter;
            }
        }
    }

    @Override
    public void udpateItemFilter(String[] params) {
        if (mDataFilter != null){
            String filter = mDataFilter.updateItemFilter(params);
            if (Utils.stringsNotEmpty(filter)){
                mSqlSelectedItem = filter;
            }
        }
    }
}
