package ru.wdsoft.db.tables;

import android.database.sqlite.SQLiteStatement;

import ru.wdsoft.api.models.VehicleBrand;
import ru.wdsoft.api.models.VehicleDocumentType;
import ru.wdsoft.db.SQLUtils;
import ru.wdsoft.utils.LogUtils;

/**
 * Created by slaventii@mail.ru on 20.02.2019.
 */
public class SQLVehicleDocType extends SQLBase {

    private final static String LOG_PREFIX = "SQLVehicleDocType  -- ";

    public static final String TABLE_NAME = "VEHICLEDOCTYPE";

    public static final String FIELD_VEHICLEDOCTYPE_ID = "vehicledoctype_id";
    public static final String FIELD_VEHICLEDOCTYPE_NAME = "vehicledoctype_name";
    public static final String FIELD_VEHICLEDOCTYPE_ISDELETED = "vehicledoctype_isdeleted";

    private static final String CREATE_TABLE_SQL = "CREATE TABLE " + TABLE_NAME + " (" +
            FIELD_VEHICLEDOCTYPE_ID + " text primary key," +
            FIELD_VEHICLEDOCTYPE_NAME + " text," +
            FIELD_VEHICLEDOCTYPE_ISDELETED + " integer" +
            ");";

    public static void update(VehicleDocumentType[] vehicleDocumentTypes){

        if (vehicleDocumentTypes == null) return;

        String sql = "INSERT OR REPLACE INTO " + TABLE_NAME + " ("+
                FIELD_VEHICLEDOCTYPE_ID + "," +
                FIELD_VEHICLEDOCTYPE_NAME + "," +
                FIELD_VEHICLEDOCTYPE_ISDELETED + ")" +
                " VALUES (?,?,?)";

        try {

            getWriteDb().beginTransactionNonExclusive();

            SQLiteStatement stInsert = getWriteDb().compileStatement(sql);

            for (VehicleDocumentType vehicleDocumentType : vehicleDocumentTypes) {

                stInsert.bindString(1, vehicleDocumentType.getId());
                stInsert.bindString(2, vehicleDocumentType.getName());
                stInsert.bindLong(3, (vehicleDocumentType.isDeleted() ? 1:0));

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
