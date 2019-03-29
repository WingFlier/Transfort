package ru.wdsoft.db.tables;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;

import ru.wdsoft.api.ApiSerializable;
import ru.wdsoft.api.models.CapacityClass;
import ru.wdsoft.api.models.VehicleType;
import ru.wdsoft.db.SQLUtils;
import ru.wdsoft.utils.LogUtils;

/**
 * Created by slaventii@mail.ru on 20.02.2019.
 */
public class SQLVehicleType extends SQLBase {

    private final static String LOG_PREFIX = "SQLVehicleType  -- ";

    public static final String TABLE_NAME = "VEHICLETYPE";

    public static final String FIELD_VEHICLETYPE_ID = "vehicletype_id";
    public static final String FIELD_VEHICLETYPE_ORDERTYPE = "vehicletype_ordertype";
    public static final String FIELD_VEHICLETYPE_NAME = "vehicletype_name";
    public static final String FIELD_VEHICLETYPE_SORTNUMBER = "vehicletype_sortnumber";
    public static final String FIELD_VEHICLETYPE_ISDELETED = "vehicletype_isdeleted";

    private static final String CREATE_TABLE_SQL = "CREATE TABLE " + TABLE_NAME + " (" +
            FIELD_VEHICLETYPE_ID + " text primary key," +
            FIELD_VEHICLETYPE_ORDERTYPE + " text," +
            FIELD_VEHICLETYPE_NAME + " text," +
            FIELD_VEHICLETYPE_SORTNUMBER + " integer," +
            FIELD_VEHICLETYPE_ISDELETED + " integer" +
            ");";

    public static void update(VehicleType[] vehicleTypes){

        if (vehicleTypes == null) return;

        String sql = "INSERT OR REPLACE INTO " + TABLE_NAME + " ("+
                FIELD_VEHICLETYPE_ID + "," +
                FIELD_VEHICLETYPE_ORDERTYPE + "," +
                FIELD_VEHICLETYPE_NAME + "," +
                FIELD_VEHICLETYPE_SORTNUMBER + "," +
                FIELD_VEHICLETYPE_ISDELETED + ")" +
                " VALUES (?,?,?,?,?)";

        try {

            getWriteDb().beginTransactionNonExclusive();

            SQLiteStatement stInsert = getWriteDb().compileStatement(sql);

            for (VehicleType vehicleType : vehicleTypes) {

                stInsert.bindString(1, vehicleType.getId());
                stInsert.bindString(2, vehicleType.getOrderType());
                stInsert.bindString(3, vehicleType.getName());
                stInsert.bindLong(4, vehicleType.getSortNumber());
                stInsert.bindLong(5, (vehicleType.isDeleted() ? 1:0));
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

    public static ApiSerializable get(String id){

        String filter = FIELD_VEHICLETYPE_ID + "='" + id + "'";

        ArrayList<ApiSerializable> vehicleTypes = getList(filter);

        if (vehicleTypes != null && vehicleTypes.size() > 0){
            return vehicleTypes.get(0);
        }

        return null;
    }

    public static ArrayList<ApiSerializable> getList(String filter){

        Cursor cursor = SQLUtils.getNomenclature(TABLE_NAME, FIELD_VEHICLETYPE_ID,
                FIELD_VEHICLETYPE_NAME, "", filter, 0, FIELD_VEHICLETYPE_SORTNUMBER,
                "ASC");

        return getList(cursor);

    }

    public static ArrayList<ApiSerializable> getList(Cursor cursor){

        ArrayList<ApiSerializable> list = null;

        if (cursor != null){

            list = new ArrayList<>();

            while (cursor.moveToNext()){

                VehicleType vehicleType = new VehicleType();

                vehicleType.setSortNumber(cursor.getInt(cursor.getColumnIndex(FIELD_VEHICLETYPE_SORTNUMBER)));
                vehicleType.setDeleted(cursor.getInt(cursor.getColumnIndex(FIELD_VEHICLETYPE_ISDELETED))==1);
                vehicleType.setId(cursor.getString(cursor.getColumnIndex(FIELD_VEHICLETYPE_ID)));
                vehicleType.setName(cursor.getString(cursor.getColumnIndex(FIELD_VEHICLETYPE_NAME)));
                vehicleType.setOrderType(cursor.getString(cursor.getColumnIndex(FIELD_VEHICLETYPE_ORDERTYPE)));

                list.add(vehicleType);
            }

            cursor.close();
        }

        return list;
    }

    public static void createTable() {
        SQLUtils.execSQL(CREATE_TABLE_SQL);
    }

    public static void dropTable() {
        SQLUtils.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME + "; ");
    }
}
