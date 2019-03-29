package ru.wdsoft.db.tables;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;

import ru.wdsoft.api.ApiSerializable;
import ru.wdsoft.api.models.Vehicle;
import ru.wdsoft.api.models.VehicleBrand;
import ru.wdsoft.api.models.VehicleType;
import ru.wdsoft.db.SQLUtils;
import ru.wdsoft.utils.LogUtils;

/**
 * Created by slaventii@mail.ru on 20.02.2019.
 */
public class SQLVehicle extends SQLBase {

    private final static String LOG_PREFIX = "SQLVehicle  -- ";

    public static final String TABLE_NAME = "VEHICLE";

    //    private String[] ExtTypes;
    public static final String FIELD_VEHICLE_CAPACITY = "vehicle_capacity";
    public static final String FIELD_VEHICLE_COMMENT = "vehicle_comment";
    public static final String FIELD_VEHICLE_CONDITIONID = "vehicle_conditionid";
    public static final String FIELD_VEHICLE_DEPARTMENTID = "vehicle_departmentid";
    public static final String FIELD_VEHICLE_ID = "vehicle_id";
    public static final String FIELD_VEHICLE_ISDELETED = "vehicle_isdeleted";
    public static final String FIELD_VEHICLE_MODELID = "vehicle_modelid";
    public static final String FIELD_VEHICLE_NAME = "vehicle_name";
    public static final String FIELD_VEHICLE_NUMBEROFPASSENGERS = "vehicle_numberofpassengers";
    public static final String FIELD_VEHICLE_READYTOWORK = "vehicle_readytowork";
    public static final String FIELD_VEHICLE_REGNUMBER = "vehicle_regnumber";
    public static final String FIELD_VEHICLE_VEHICLETYPEID = "vehicle_vehicletypeid";

    public static final String FIELD_VEHICLE_RATING = "vehicle_rating";
    public static final String FIELD_VEHICLE_KMPRICE = "vehicle_kmprice";
    public static final String FIELD_VEHICLE_WITHVAT = "vehicle_withvat";
    public static final String FIELD_VEHICLE_MINHOURS = "vehicle_minhours";
    public static final String FIELD_VEHICLE_REGIONID = "vehicle_regionid";
    public static final String FIELD_VEHICLE_COMPANYID = "vehicle_companyid";
    public static final String FIELD_VEHICLE_MODELNAME = "vehicle_modelname";
    public static final String FIELD_VEHICLE_ORDERTYPE = "vehicle_ordertype";
    public static final String FIELD_VEHICLE_CREATEDATE = "vehicle_createdate";
    public static final String FIELD_VEHICLE_MODYFYDATE = "vehicle_modyfydate";
    public static final String FIELD_VEHICLE_REGIONNAME = "vehicle_regionname";
    public static final String FIELD_VEHICLE_SORTNUMBER = "vehicle_sortnumber";
    public static final String FIELD_VEHICLE_COMPANYNAME = "vehicle_companyname";
    public static final String FIELD_VEHICLE_HOURLYPRICE = "vehicle_hourlyprice";
    public static final String FIELD_VEHICLE_YEAROFISSUE = "vehicle_yearofissue";
    public static final String FIELD_VEHICLE_VEHICLETYPENAME = "vehicle_vehicletypename";
    public static final String FIELD_VEHICLE_OPTIONALEQUIPMENT = "vehicle_optionalequipment";

    private static final String CREATE_TABLE_SQL = "CREATE TABLE " + TABLE_NAME + " (" +

            FIELD_VEHICLE_ID  + " text primary key,"+
            FIELD_VEHICLE_NAME  + " text,"+
            FIELD_VEHICLE_REGNUMBER  + " text,"+
            FIELD_VEHICLE_CAPACITY + " integer," +
            FIELD_VEHICLE_NUMBEROFPASSENGERS  + " integer,"+
            FIELD_VEHICLE_COMMENT + " text," +
            FIELD_VEHICLE_MODELID  + " text,"+
            FIELD_VEHICLE_CONDITIONID + " text," +
            FIELD_VEHICLE_VEHICLETYPEID + " text," +
            FIELD_VEHICLE_DEPARTMENTID + " text," +
            FIELD_VEHICLE_RATING + " real," +
            FIELD_VEHICLE_KMPRICE + " real," +
            FIELD_VEHICLE_WITHVAT + " integer," +
            FIELD_VEHICLE_MINHOURS + " integer," +
            FIELD_VEHICLE_REGIONID + " text," +
            FIELD_VEHICLE_COMPANYID + " text," +
            FIELD_VEHICLE_MODELNAME + " text," +
            FIELD_VEHICLE_ORDERTYPE + " text," +
            FIELD_VEHICLE_CREATEDATE + " integer," +
            FIELD_VEHICLE_MODYFYDATE + " integer," +
            FIELD_VEHICLE_REGIONNAME + " text," +
            FIELD_VEHICLE_SORTNUMBER + " integer," +
            FIELD_VEHICLE_COMPANYNAME + " text," +
            FIELD_VEHICLE_HOURLYPRICE + " real," +
            FIELD_VEHICLE_YEAROFISSUE + " integer," +
            FIELD_VEHICLE_VEHICLETYPENAME + " text," +
            FIELD_VEHICLE_OPTIONALEQUIPMENT + " text," +
            FIELD_VEHICLE_READYTOWORK  + " integer,"+
            FIELD_VEHICLE_ISDELETED  + " integer"+
            ");";

    public static void update(Vehicle[] vehicles){

        if (vehicles == null) return;

        String sql = "INSERT OR REPLACE INTO " + TABLE_NAME + " ("+
                FIELD_VEHICLE_ID + "," +
                FIELD_VEHICLE_NAME + "," +
                FIELD_VEHICLE_REGNUMBER + "," +
                FIELD_VEHICLE_CAPACITY + "," +
                FIELD_VEHICLE_NUMBEROFPASSENGERS + "," +
                FIELD_VEHICLE_COMMENT + "," +
                FIELD_VEHICLE_MODELID + "," +
                FIELD_VEHICLE_CONDITIONID + "," +
                FIELD_VEHICLE_DEPARTMENTID + "," +
                FIELD_VEHICLE_VEHICLETYPEID + "," +
                FIELD_VEHICLE_RATING + "," +
                FIELD_VEHICLE_KMPRICE + "," +
                FIELD_VEHICLE_WITHVAT + "," +
                FIELD_VEHICLE_MINHOURS + "," +
                FIELD_VEHICLE_REGIONID + "," +
                FIELD_VEHICLE_COMPANYID + "," +
                FIELD_VEHICLE_MODELNAME + "," +
                FIELD_VEHICLE_ORDERTYPE + "," +
                FIELD_VEHICLE_CREATEDATE + "," +
                FIELD_VEHICLE_MODYFYDATE + "," +
                FIELD_VEHICLE_REGIONNAME + "," +
                FIELD_VEHICLE_SORTNUMBER + "," +
                FIELD_VEHICLE_COMPANYNAME + "," +
                FIELD_VEHICLE_HOURLYPRICE + "," +
                FIELD_VEHICLE_YEAROFISSUE + "," +
                FIELD_VEHICLE_VEHICLETYPENAME + "," +
                FIELD_VEHICLE_OPTIONALEQUIPMENT + "," +
                FIELD_VEHICLE_READYTOWORK + "," +
                FIELD_VEHICLE_ISDELETED + ")" +
                " VALUES (?,?,?,?,?,?,?,?,?,?,?,?, ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        try {

            getWriteDb().beginTransactionNonExclusive();

            SQLiteStatement stInsert = getWriteDb().compileStatement(sql);

            for (Vehicle vehicle : vehicles) {

                stInsert.bindString(1, vehicle.getId());
                stInsert.bindString(2, vehicle.getName());
                stInsert.bindString(3, vehicle.getRegNumber());
                stInsert.bindLong(4, vehicle.getCapacity());
                stInsert.bindLong(5, vehicle.getNumberOfPassengers());
                stInsert.bindString(6, vehicle.getComment());
                stInsert.bindString(7, vehicle.getModelId());
                stInsert.bindString(8, vehicle.getConditionId());
                stInsert.bindString(9, vehicle.getDepartmentId());
                stInsert.bindString(10, vehicle.getVehicleTypeId());
                stInsert.bindDouble(11, vehicle.getRating());
                stInsert.bindDouble(12, vehicle.getKmPrice());
                stInsert.bindLong(13, (vehicle.getWithVAT() ? 1:0));
                stInsert.bindLong(14, vehicle.getMinHours());
                stInsert.bindString(15, vehicle.getRegionId());
                stInsert.bindString(16, vehicle.getCompanyId());
                stInsert.bindString(17, vehicle.getModelName());
                stInsert.bindString(18, vehicle.getOrderType());
                stInsert.bindLong(19, vehicle.getCreateDate());
                stInsert.bindLong(20, vehicle.getModyfyDate());
                stInsert.bindString(21, vehicle.getRegionName());
                stInsert.bindLong(22, vehicle.getSortNumber());
                stInsert.bindString(23, vehicle.getCompanyName());
                stInsert.bindDouble(24, vehicle.getHourlyPrice());
                stInsert.bindLong(25, vehicle.getYearOfIssue());
                stInsert.bindString(26, vehicle.getVehicleTypeName());
                stInsert.bindString(27, vehicle.getOptionalEquipment());

                stInsert.bindLong(28, (vehicle.getReadyToWork() ? 1:0));
                stInsert.bindLong(29, (vehicle.getDeleted() ? 1:0));

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

        String filter = FIELD_VEHICLE_ID + "='" + id + "'";

        ArrayList<ApiSerializable> list = getList(filter);

        if (list != null && list.size() > 0){
            return list.get(0);
        }

        return null;
    }

    public static ArrayList<ApiSerializable> getList(String filter){

        Cursor cursor = SQLUtils.getNomenclature(TABLE_NAME, FIELD_VEHICLE_ID,
                FIELD_VEHICLE_NAME, "", filter, 0, FIELD_VEHICLE_SORTNUMBER,
                "ASC");

        return getList(cursor);

    }

    public static ArrayList<ApiSerializable> getList(Cursor cursor){

        ArrayList<ApiSerializable> list = null;

        if (cursor != null){

            list = new ArrayList<>();

            while (cursor.moveToNext()){

                Vehicle vehicle = new Vehicle();

                vehicle.setId(cursor.getString(cursor.getColumnIndex(FIELD_VEHICLE_ID)));
                vehicle.setName(cursor.getString(cursor.getColumnIndex(FIELD_VEHICLE_NAME)));
                vehicle.setRegNumber(cursor.getString(cursor.getColumnIndex(FIELD_VEHICLE_REGNUMBER)));
                vehicle.setCapacity(cursor.getInt(cursor.getColumnIndex(FIELD_VEHICLE_CAPACITY)));
                vehicle.setNumberOfPassengers(cursor.getInt(cursor.getColumnIndex(FIELD_VEHICLE_NUMBEROFPASSENGERS)));
                vehicle.setComment(cursor.getString(cursor.getColumnIndex(FIELD_VEHICLE_COMMENT)));
                vehicle.setModelId(cursor.getString(cursor.getColumnIndex(FIELD_VEHICLE_MODELID)));
                vehicle.setConditionId(cursor.getString(cursor.getColumnIndex(FIELD_VEHICLE_CONDITIONID)));
                vehicle.setDepartmentId(cursor.getString(cursor.getColumnIndex(FIELD_VEHICLE_DEPARTMENTID)));
                vehicle.setVehicleTypeId(cursor.getString(cursor.getColumnIndex(FIELD_VEHICLE_VEHICLETYPEID)));
                vehicle.setRating(cursor.getFloat(cursor.getColumnIndex(FIELD_VEHICLE_RATING)));
                vehicle.setKmPrice(cursor.getFloat(cursor.getColumnIndex(FIELD_VEHICLE_KMPRICE)));
                vehicle.setWithVAT(cursor.getInt(cursor.getColumnIndex(FIELD_VEHICLE_WITHVAT))==1);
                vehicle.setMinHours(cursor.getInt(cursor.getColumnIndex(FIELD_VEHICLE_MINHOURS)));
                vehicle.setRegionId(cursor.getString(cursor.getColumnIndex(FIELD_VEHICLE_REGIONID)));
                vehicle.setCompanyId(cursor.getString(cursor.getColumnIndex(FIELD_VEHICLE_CONDITIONID)));
                vehicle.setModelName(cursor.getString(cursor.getColumnIndex(FIELD_VEHICLE_MODELNAME)));
                vehicle.setOrderType(cursor.getString(cursor.getColumnIndex(FIELD_VEHICLE_ORDERTYPE)));
                vehicle.setCreateDate(cursor.getLong(cursor.getColumnIndex(FIELD_VEHICLE_COMPANYNAME)));
                vehicle.setModyfyDate(cursor.getLong(cursor.getColumnIndex(FIELD_VEHICLE_MODELNAME)));
                vehicle.setRegionName(cursor.getString(cursor.getColumnIndex(FIELD_VEHICLE_REGIONNAME)));
                vehicle.setSortNumber(cursor.getInt(cursor.getColumnIndex(FIELD_VEHICLE_SORTNUMBER)));
                vehicle.setCompanyName(cursor.getString(cursor.getColumnIndex(FIELD_VEHICLE_COMPANYNAME)));
                vehicle.setHourlyPrice(cursor.getFloat(cursor.getColumnIndex(FIELD_VEHICLE_HOURLYPRICE)));
                vehicle.setYearOfIssue(cursor.getInt(cursor.getColumnIndex(FIELD_VEHICLE_YEAROFISSUE)));
                vehicle.setVehicleTypeName(cursor.getString(cursor.getColumnIndex(FIELD_VEHICLE_VEHICLETYPENAME)));
                vehicle.setOptionalEquipment(cursor.getString(cursor.getColumnIndex(FIELD_VEHICLE_OPTIONALEQUIPMENT)));
                vehicle.setReadyToWork(cursor.getInt(cursor.getColumnIndex(FIELD_VEHICLE_READYTOWORK))==1);
                vehicle.setDeleted(cursor.getInt(cursor.getColumnIndex(FIELD_VEHICLE_ISDELETED))==1);

                list.add(vehicle);
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
