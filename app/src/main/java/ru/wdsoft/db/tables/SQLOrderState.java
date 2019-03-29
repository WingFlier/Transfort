package ru.wdsoft.db.tables;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;

import ru.wdsoft.api.ApiSerializable;
import ru.wdsoft.api.models.OrderState;
import ru.wdsoft.api.models.VehicleBrand;
import ru.wdsoft.db.SQLUtils;
import ru.wdsoft.utils.LogUtils;

/**
 * Created by slaventii@mail.ru on 11.03.2019.
 */
public class SQLOrderState extends SQLBase {

    private final static String LOG_PREFIX = "SQLOrderState  -- ";

    public static final String TABLE_NAME = "ORDERSTATE";

    public static final String FIELD_ORDERSTATE_ID = "orderstate_id";
    public static final String FIELD_ORDERSTATE_NAME = "orderstate_name";
    public static final String FIELD_ORDERSTATE_STATE = "orderstate_state";
    public static final String FIELD_ORDERSTATE_ISDELETED = "orderstate_isdeleted";

    private static final String CREATE_TABLE_SQL = "CREATE TABLE " + TABLE_NAME + " (" +
            FIELD_ORDERSTATE_ID + " text primary key," +
            FIELD_ORDERSTATE_NAME + " text," +
            FIELD_ORDERSTATE_STATE + " text," +
            FIELD_ORDERSTATE_ISDELETED + " integer" +
            ");";


    public static ApiSerializable get(String code){

        String filter = FIELD_ORDERSTATE_STATE + "='" + code + "'";

        ArrayList<ApiSerializable> list = getList(filter);

        if (list != null && list.size() > 0){
            return list.get(0);
        }

        return null;
    }

    public static ArrayList<ApiSerializable> getList(String filter){

        Cursor cursor = SQLUtils.getNomenclature(TABLE_NAME, FIELD_ORDERSTATE_STATE,
                FIELD_ORDERSTATE_NAME, "", filter, 0, "",
                "");

        return getList(cursor);

    }

    public static ArrayList<ApiSerializable> getList(Cursor cursor) {

        ArrayList<ApiSerializable> list = null;

        if (cursor != null){

            list = new ArrayList<>();

            while (cursor.moveToNext()){

                OrderState orderType = new OrderState();

                orderType.setId(cursor.getString(cursor.getColumnIndex(FIELD_ORDERSTATE_ID)));
                orderType.setState(cursor.getString(cursor.getColumnIndex(FIELD_ORDERSTATE_STATE)));
                orderType.setName(cursor.getString(cursor.getColumnIndex(FIELD_ORDERSTATE_NAME)));
                orderType.setDeleted(cursor.getInt(cursor.getColumnIndex(FIELD_ORDERSTATE_ISDELETED))==1);

                list.add(orderType);
            }

            cursor.close();
        }

        return list;

    }

    public static void update(OrderState[] orderStates){

        if (orderStates == null) return;

        String sql = "INSERT OR REPLACE INTO " + TABLE_NAME + " ("+
                FIELD_ORDERSTATE_ID + "," +
                FIELD_ORDERSTATE_NAME + "," +
                FIELD_ORDERSTATE_STATE + "," +
                FIELD_ORDERSTATE_ISDELETED + ")" +
                " VALUES (?,?,?,?)";

        try {

            getWriteDb().beginTransactionNonExclusive();

            SQLiteStatement stInsert = getWriteDb().compileStatement(sql);

            for (OrderState orderState : orderStates) {

                stInsert.bindString(1, orderState.getId());
                stInsert.bindString(2, orderState.getName());
                stInsert.bindString(3, orderState.getState());
                stInsert.bindLong(4, (orderState.isDeleted() ? 1:0));

                stInsert.execute();
                stInsert.clearBindings();

            }

            getWriteDb().setTransactionSuccessful();

        } catch (Exception e) {
            LogUtils.debugErrorLog(LOG_PREFIX, e);
        } finally {
            getWriteDb().endTransaction();
        }
    }

    public static void createTable() {
        SQLUtils.execSQL(CREATE_TABLE_SQL);
    }

    public static void dropTable() {
        SQLUtils.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME + "; ");
    }
}
