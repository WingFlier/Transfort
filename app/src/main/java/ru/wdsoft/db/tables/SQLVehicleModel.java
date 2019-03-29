package ru.wdsoft.db.tables;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;

import ru.wdsoft.api.ApiSerializable;
import ru.wdsoft.api.models.OrderType;
import ru.wdsoft.api.models.VehicleBrand;
import ru.wdsoft.api.models.VehicleModel;
import ru.wdsoft.db.SQLUtils;
import ru.wdsoft.utils.LogUtils;

/**
 * Created by slaventii@mail.ru on 20.02.2019.
 */
public class SQLVehicleModel extends SQLBase {

    private final static String LOG_PREFIX = "SQLVehicleModel  -- ";

    public static final String TABLE_NAME = "VEHICLEMODEL";

    public static final String FIELD_VMODEL_ID = "vmodel_id";
    public static final String FIELD_VMODEL_CAPACITY = "vmodel_capacity";
    public static final String FIELD_VMODEL_ISDELETED = "vmodel_isdeleted";
    public static final String FIELD_VMODEL_NAME = "vmodel_name";
    public static final String FIELD_VMODEL_NUMBEROFPASS = "vmodel_numberofpass";
    public static final String FIELD_VMODEL_VEHICLEBRANDID = "vmodel_vehiclebrandid";
    public static final String FIELD_VMODEL_VEHICLETYPEID = "vmodel_vehicletypeid";

    private static final String CREATE_TABLE_SQL = "CREATE TABLE " + TABLE_NAME + " (" +
            FIELD_VMODEL_ID  + " text primary key,"+
            FIELD_VMODEL_NAME + " text," +
            FIELD_VMODEL_CAPACITY + " integer," +
            FIELD_VMODEL_NUMBEROFPASS + " integer," +
            FIELD_VMODEL_VEHICLEBRANDID  + " text,"+
            FIELD_VMODEL_VEHICLETYPEID  + " text,"+
            FIELD_VMODEL_ISDELETED + " integer" +
            ");";


    public static ApiSerializable get(String id){

        String filter = FIELD_VMODEL_ID + "='" + id + "'";

        ArrayList<ApiSerializable> list = getList(filter);

        if (list != null && list.size() > 0){
            return list.get(0);
        }

        return null;
    }

    public static ArrayList<ApiSerializable> getList(String filter){

        Cursor cursor = SQLUtils.getNomenclature(TABLE_NAME, FIELD_VMODEL_ID,
                FIELD_VMODEL_NAME, "", filter, 0, "",
                "");

        return getList(cursor);

    }

    public static ArrayList<ApiSerializable> getList(Cursor cursor) {

        ArrayList<ApiSerializable> list = null;

        if (cursor != null){

            list = new ArrayList<>();

            while (cursor.moveToNext()){

                VehicleModel vehicleModel = new VehicleModel();

                vehicleModel.setId(cursor.getString(cursor.getColumnIndex(FIELD_VMODEL_ID)));
                vehicleModel.setName(cursor.getString(cursor.getColumnIndex(FIELD_VMODEL_NAME)));
                vehicleModel.setCapacity(cursor.getInt(cursor.getColumnIndex(FIELD_VMODEL_CAPACITY)));
                vehicleModel.setNumberOfPass(cursor.getInt(cursor.getColumnIndex(FIELD_VMODEL_NUMBEROFPASS)));
                vehicleModel.setVehicleBrandId(cursor.getString(cursor.getColumnIndex(FIELD_VMODEL_VEHICLEBRANDID)));
                vehicleModel.setVehicleTypeId(cursor.getString(cursor.getColumnIndex(FIELD_VMODEL_VEHICLETYPEID)));
                vehicleModel.setDeleted(cursor.getInt(cursor.getColumnIndex(FIELD_VMODEL_ISDELETED)) == 1);

                list.add(vehicleModel);
            }

            cursor.close();
        }

        return list;

    }

    public static void update(VehicleModel[] vehicleModels){

        if (vehicleModels == null) return;

        String sql = "INSERT OR REPLACE INTO " + TABLE_NAME + " ("+
                FIELD_VMODEL_ID + "," +
                FIELD_VMODEL_NAME + "," +
                FIELD_VMODEL_CAPACITY + "," +
                FIELD_VMODEL_NUMBEROFPASS + "," +
                FIELD_VMODEL_VEHICLEBRANDID + "," +
                FIELD_VMODEL_VEHICLETYPEID + "," +
                FIELD_VMODEL_ISDELETED + ")" +
                " VALUES (?,?,?,?,?,?,?)";

        try {

            getWriteDb().beginTransactionNonExclusive();

            SQLiteStatement stInsert = getWriteDb().compileStatement(sql);

            for (VehicleModel vehicleModel : vehicleModels) {

                stInsert.bindString(1, vehicleModel.getId());
                stInsert.bindString(2, vehicleModel.getName());
                stInsert.bindLong(3, vehicleModel.getCapacity());
                stInsert.bindLong(4, vehicleModel.getNumberOfPass());
                stInsert.bindString(5, vehicleModel.getVehicleBrandId());
                stInsert.bindString(6, vehicleModel.getVehicleTypeId());
                stInsert.bindLong(7, (vehicleModel.getDeleted() ? 1:0));

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
