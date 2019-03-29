package ru.wdsoft.db.tables;

import android.database.sqlite.SQLiteStatement;

import ru.wdsoft.api.models.OwnershipType;
import ru.wdsoft.api.models.VehicleBrand;
import ru.wdsoft.db.SQLUtils;
import ru.wdsoft.utils.LogUtils;

/**
 * Created by slaventii@mail.ru on 20.02.2019.
 */
public class SQLOwnershipType extends SQLBase {

    private final static String LOG_PREFIX = "SQLOwnershipType  -- ";

    public static final String TABLE_NAME = "OWNERSHIPTYPE";

    public static final String FIELD_OWNERSHIPTYPE_CODE = "ownershiptype_code";
    public static final String FIELD_OWNERSHIPTYPE_NAME = "ownershiptype_name";
    public static final String FIELD_OWNERSHIPTYPE_ISDELETED = "ownershiptype_isdeleted";

    private static final String CREATE_TABLE_SQL = "CREATE TABLE " + TABLE_NAME + " (" +
            FIELD_OWNERSHIPTYPE_CODE + " text primary key," +
            FIELD_OWNERSHIPTYPE_NAME + " text," +
            FIELD_OWNERSHIPTYPE_ISDELETED + " integer" +
            ");";

    public static void update(OwnershipType[] ownershipTypes){

        if (ownershipTypes == null) return;

        String sql = "INSERT OR REPLACE INTO " + TABLE_NAME + " ("+
                FIELD_OWNERSHIPTYPE_CODE + "," +
                FIELD_OWNERSHIPTYPE_NAME + "," +
                FIELD_OWNERSHIPTYPE_ISDELETED + ")" +
                " VALUES (?,?,?)";

        try {

            getWriteDb().beginTransactionNonExclusive();

            SQLiteStatement stInsert = getWriteDb().compileStatement(sql);

            for (OwnershipType ownershipType : ownershipTypes) {

                stInsert.bindString(1, ownershipType.getCode());
                stInsert.bindString(2, ownershipType.getName());
                stInsert.bindLong(3, (ownershipType.isDeleted() ? 1:0));

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
