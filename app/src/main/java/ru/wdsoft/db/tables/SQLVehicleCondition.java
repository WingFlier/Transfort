package ru.wdsoft.db.tables;

import android.database.sqlite.SQLiteStatement;

import ru.wdsoft.api.models.VehicleBrand;
import ru.wdsoft.api.models.VehicleCondition;
import ru.wdsoft.db.SQLUtils;
import ru.wdsoft.utils.LogUtils;

/**
 * Created by slaventii@mail.ru on 20.02.2019.
 */
public class SQLVehicleCondition extends SQLBase {

    private final static String LOG_PREFIX = "SQLVehicleCondition  -- ";

    public static final String TABLE_NAME = "VEHICLECONDITION";

    public static final String FIELD_VEHICLECONDITION_ID = "vehiclecondition_id";
    public static final String FIELD_VEHICLECONDITION_NAME = "vehiclecondition_name";
    public static final String FIELD_VEHICLECONDITION_ISREADYTOWORK = "vehiclecondition_isreadytowork";
    public static final String FIELD_VEHICLECONDITION_ISDELETED = "vehiclecondition_isdeleted";

    private static final String CREATE_TABLE_SQL = "CREATE TABLE " + TABLE_NAME + " (" +
            FIELD_VEHICLECONDITION_ID + " text primary key," +
            FIELD_VEHICLECONDITION_NAME + " text," +
            FIELD_VEHICLECONDITION_ISREADYTOWORK + " integer," +
            FIELD_VEHICLECONDITION_ISDELETED + " integer" +
            ");";

    public static void update(VehicleCondition[] vehicleConditions){

        if (vehicleConditions == null) return;

        String sql = "INSERT OR REPLACE INTO " + TABLE_NAME + " ("+
                FIELD_VEHICLECONDITION_ID + "," +
                FIELD_VEHICLECONDITION_NAME + "," +
                FIELD_VEHICLECONDITION_ISREADYTOWORK + "," +
                FIELD_VEHICLECONDITION_ISDELETED + ")" +
                " VALUES (?,?,?,?)";

        try {

            getWriteDb().beginTransactionNonExclusive();

            SQLiteStatement stInsert = getWriteDb().compileStatement(sql);

            for (VehicleCondition vehicleCondition : vehicleConditions) {

                stInsert.bindString(1, vehicleCondition.getId());
                stInsert.bindString(2, vehicleCondition.getName());
                stInsert.bindLong(3, (vehicleCondition.readyToWork() ? 1:0));
                stInsert.bindLong(4, (vehicleCondition.isDeleted() ? 1:0));

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
