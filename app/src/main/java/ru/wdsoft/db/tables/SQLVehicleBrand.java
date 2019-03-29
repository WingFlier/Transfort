package ru.wdsoft.db.tables;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;

import ru.wdsoft.api.models.VehicleBrand;
import ru.wdsoft.api.models.VehicleType;
import ru.wdsoft.db.SQLUtils;
import ru.wdsoft.utils.LogUtils;

/**
 * Created by slaventii@mail.ru on 20.02.2019.
 */
public class SQLVehicleBrand extends SQLBase {

    private final static String LOG_PREFIX = "SQLVehicleBrand  -- ";

    public static final String TABLE_NAME = "VEHICLEBRAND";

    public static final String FIELD_VEHICLEBRAND_ID = "vehiclebrand_id";
    public static final String FIELD_VEHICLEBRAND_NAME = "vehiclebrand_name";
    public static final String FIELD_VEHICLEBRAND_ISDELETED = "vehiclebrand_isdeleted";

    private static final String CREATE_TABLE_SQL = "CREATE TABLE " + TABLE_NAME + " (" +
            FIELD_VEHICLEBRAND_ID + " text primary key," +
            FIELD_VEHICLEBRAND_NAME + " text," +
            FIELD_VEHICLEBRAND_ISDELETED + " integer" +
            ");";

    public static void update(VehicleBrand[] vehicleBrands){

        if (vehicleBrands == null) return;

        String sql = "INSERT OR REPLACE INTO " + TABLE_NAME + " ("+
                FIELD_VEHICLEBRAND_ID + "," +
                FIELD_VEHICLEBRAND_NAME + "," +
                FIELD_VEHICLEBRAND_ISDELETED + ")" +
                " VALUES (?,?,?)";

        try {

            getWriteDb().beginTransactionNonExclusive();

            SQLiteStatement stInsert = getWriteDb().compileStatement(sql);

            for (VehicleBrand vehicleBrand : vehicleBrands) {

                stInsert.bindString(1, vehicleBrand.getId());
                stInsert.bindString(2, vehicleBrand.getName());
                stInsert.bindLong(3, (vehicleBrand.isDeleted() ? 1:0));

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
