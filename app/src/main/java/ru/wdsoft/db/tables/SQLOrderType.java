package ru.wdsoft.db.tables;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;

import ru.wdsoft.api.ApiSerializable;
import ru.wdsoft.api.models.OrderType;
import ru.wdsoft.api.models.VehicleBrand;
import ru.wdsoft.db.SQLUtils;
import ru.wdsoft.utils.LogUtils;

/**
 * Created by slaventii@mail.ru on 26.02.2019.
 */
public class SQLOrderType extends SQLBase {

    private final static String LOG_PREFIX = "SQLOrderType  -- ";

    public static final String TABLE_NAME = "ORDERTYPE";

    public static final String FIELD_ORDERTYPE_ID = "ordertype_id";
    public static final String FIELD_ORDERTYPE_CODE = "ordertype_code";
    public static final String FIELD_ORDERTYPE_NAME = "ordertype_name";
    public static final String FIELD_ORDERTYPE_ISDELETED = "ordertype_isdeleted";

    private static final String CREATE_TABLE_SQL = "CREATE TABLE " + TABLE_NAME + " (" +
            FIELD_ORDERTYPE_ID + " text primary key," +
            FIELD_ORDERTYPE_CODE + " text," +
            FIELD_ORDERTYPE_NAME + " text," +
            FIELD_ORDERTYPE_ISDELETED + " integer" +
            ");";


    public static ApiSerializable get(String id){

        String filter = FIELD_ORDERTYPE_ID + "='" + id + "'";

        ArrayList<ApiSerializable> orderTypes = getList(filter);

        if (orderTypes != null && orderTypes.size() > 0){
            return orderTypes.get(0);
        }

        return null;
    }

    public static ArrayList<ApiSerializable> getList(String filter){

        Cursor cursor = SQLUtils.getNomenclature(TABLE_NAME, FIELD_ORDERTYPE_CODE,
                FIELD_ORDERTYPE_NAME, "", filter, 0, "",
                "");

        return getList(cursor);

    }

    public static ArrayList<ApiSerializable> getList(Cursor cursor) {

        ArrayList<ApiSerializable> list = null;

        if (cursor != null){

            list = new ArrayList<>();

            while (cursor.moveToNext()){

                OrderType orderType = new OrderType();

                orderType.setId(cursor.getString(cursor.getColumnIndex(FIELD_ORDERTYPE_ID)));
                orderType.setCode(cursor.getString(cursor.getColumnIndex(FIELD_ORDERTYPE_CODE)));
                orderType.setName(cursor.getString(cursor.getColumnIndex(FIELD_ORDERTYPE_NAME)));
                orderType.setDeleted(cursor.getInt(cursor.getColumnIndex(FIELD_ORDERTYPE_ISDELETED))==1);

                list.add(orderType);
            }

            cursor.close();
        }

        return list;

    }

    public static void update(OrderType[] orderTypes){

        if (orderTypes == null) return;

        String sqlType = "INSERT OR REPLACE INTO " + TABLE_NAME + " ("+
                FIELD_ORDERTYPE_ID + "," +
                FIELD_ORDERTYPE_CODE + "," +
                FIELD_ORDERTYPE_NAME + "," +
                FIELD_ORDERTYPE_ISDELETED + ")" +
                " VALUES (?,?,?,?)";

        try {

            getWriteDb().beginTransactionNonExclusive();

            SQLiteStatement stType = getWriteDb().compileStatement(sqlType);

            for (OrderType orderType : orderTypes) {

                stType.bindString(1, orderType.getId());
                stType.bindString(2, orderType.getCode());
                stType.bindString(3, orderType.getName());
                stType.bindLong(4, (orderType.isDeleted() ? 1:0));

                stType.execute();
                stType.clearBindings();

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
