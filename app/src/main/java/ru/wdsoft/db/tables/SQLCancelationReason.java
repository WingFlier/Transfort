package ru.wdsoft.db.tables;

import android.database.sqlite.SQLiteStatement;

import ru.wdsoft.api.models.CancelationReason;
import ru.wdsoft.db.SQLUtils;
import ru.wdsoft.utils.LogUtils;

/**
 * Created by slaventii@mail.ru on 20.02.2019.
 */
public class SQLCancelationReason extends SQLBase {

    private final static String LOG_PREFIX = "SQLCancelationReason  -- ";

    public static final String TABLE_NAME = "CANCELATIONREASONS";

    public static final String FIELD_CANCELATIONREASONS_ID = "cancelationreasons_id";
    public static final String FIELD_CANCELATIONREASONS_NAME = "cancelationreasons_name";
    public static final String FIELD_CANCELATIONREASONS_ISDELETED = "cancelationreasons_isdeleted";
    public static final String FIELD_CANCELATIONREASONS_ISCUSTOMERREASON = "cancelationreasons_iscustomerreason ";
    public static final String FIELD_CANCELATIONREASONS_ISVALIDFORCALC = "cancelationreasons_isvalidforcalc";

    private static final String CREATE_TABLE_SQL = "CREATE TABLE " + TABLE_NAME + " (" +
            FIELD_CANCELATIONREASONS_ID + " text primary key," +
            FIELD_CANCELATIONREASONS_NAME + " text," +
            FIELD_CANCELATIONREASONS_ISCUSTOMERREASON + " integer," +
            FIELD_CANCELATIONREASONS_ISVALIDFORCALC + " integer," +
            FIELD_CANCELATIONREASONS_ISDELETED + " integer" +
            ");";

    public static void update(CancelationReason[] cancelationReasonses){

        if (cancelationReasonses == null) return;

        String sql = "INSERT OR REPLACE INTO " + TABLE_NAME + " ("+
                FIELD_CANCELATIONREASONS_ID + "," +
                FIELD_CANCELATIONREASONS_NAME + "," +
                FIELD_CANCELATIONREASONS_ISCUSTOMERREASON + "," +
                FIELD_CANCELATIONREASONS_ISVALIDFORCALC + "," +
                FIELD_CANCELATIONREASONS_ISDELETED + ")" +
                " VALUES (?,?,?,?,?)";

        try {

            getWriteDb().beginTransactionNonExclusive();

            SQLiteStatement stType = getWriteDb().compileStatement(sql);

            for (CancelationReason cancelationReason : cancelationReasonses) {

                stType.bindString(1, cancelationReason.getId());
                stType.bindString(2, cancelationReason.getName());
                stType.bindLong(3, (cancelationReason.isCustomerReason() ? 1:0));
                stType.bindLong(4, (cancelationReason.isValidForCalc() ? 1:0));
                stType.bindLong(5, (cancelationReason.isDeleted() ? 1:0));
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
