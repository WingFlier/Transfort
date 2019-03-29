package ru.wdsoft.db.tables;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import ru.wdsoft.api.models.CancelationReason;
import ru.wdsoft.api.models.CapacityClass;
import ru.wdsoft.db.SQLUtils;
import ru.wdsoft.utils.LogUtils;

/**
 * Created by slaventii@mail.ru on 20.02.2019.
 */
public class SQLCapacityClass extends SQLBase {

    private final static String LOG_PREFIX = "SQLCapacityClass  -- ";

    public static final String TABLE_NAME = "CAPACITYCLASS";

    public static final String FIELD_CAPACITYCLASS_ID = "capacityclass_id";
    public static final String FIELD_CAPACITYCLASS_NAME = "capacityclass_name";
    public static final String FIELD_CAPACITYCLASS_FROM = "capacityclass_from";
    public static final String FIELD_CAPACITYCLASS_TO = "capacityclass_to";
    public static final String FIELD_CAPACITYCLASS_ISDELETED = "capacityclass_isdeleted";

    private static final String CREATE_TABLE_SQL = "CREATE TABLE " + TABLE_NAME + " (" +
            FIELD_CAPACITYCLASS_ID + " text primary key," +
            FIELD_CAPACITYCLASS_NAME + " text," +
            FIELD_CAPACITYCLASS_FROM + " integer," +
            FIELD_CAPACITYCLASS_TO + " integer," +
            FIELD_CAPACITYCLASS_ISDELETED + " integer" +
            ");";

    public static void update(CapacityClass[] capacityClasses){

        if (capacityClasses == null) return;

        String sql = "INSERT OR REPLACE INTO " + TABLE_NAME + " ("+
                FIELD_CAPACITYCLASS_ID + "," +
                FIELD_CAPACITYCLASS_NAME + "," +
                FIELD_CAPACITYCLASS_FROM + "," +
                FIELD_CAPACITYCLASS_TO + "," +
                FIELD_CAPACITYCLASS_ISDELETED + ")" +
                " VALUES (?,?,?,?,?)";

        try {

            getWriteDb().beginTransactionNonExclusive();

            SQLiteStatement stType = getWriteDb().compileStatement(sql);

            for (CapacityClass capacityClass : capacityClasses) {

                stType.bindString(1, capacityClass.getId());
                stType.bindString(2, capacityClass.getName());
                stType.bindLong(3, capacityClass.getFrom());
                stType.bindLong(4, capacityClass.getTo());
                stType.bindLong(5, (capacityClass.isDeleted() ? 1:0));

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

    public static CapacityClass getCapacityClass(String id){

        String filter = FIELD_CAPACITYCLASS_ID + "='" + id + "'";

        String sql = "SELECT * FROM " + TABLE_NAME;

        Cursor c = SQLUtils.selectData(sql, filter);

        if (c != null){
            if (c.moveToFirst()){
                CapacityClass capacityClass = new CapacityClass();
                capacityClass.setId(c.getString(c.getColumnIndex(FIELD_CAPACITYCLASS_ID)));
                capacityClass.setName(c.getString(c.getColumnIndex(FIELD_CAPACITYCLASS_NAME)));
                capacityClass.setFrom(c.getInt(c.getColumnIndex(FIELD_CAPACITYCLASS_FROM)));
                capacityClass.setTo(c.getInt(c.getColumnIndex(FIELD_CAPACITYCLASS_TO)));
                return capacityClass;
            }
        }

        return null;
    }


    public static void createTable() {
        SQLUtils.execSQL(CREATE_TABLE_SQL);
    }

    public static void dropTable() {
        SQLUtils.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME + "; ");
    }
}
