package ru.wdsoft.db.tables;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;

import ru.wdsoft.api.ApiSerializable;
import ru.wdsoft.api.models.PriorityType;
import ru.wdsoft.db.SQLUtils;
import ru.wdsoft.db.tables.SQLBase;
import ru.wdsoft.utils.LogUtils;

/**
 * Created by slaventii@mail.ru on 18.02.2019.
 */
public class SQLPriorityType extends SQLBase {

    private final static String LOG_PREFIX = "SQLPriorityType  -- ";

    public static final String TABLE_NAME = "PRIORITY";

    public static final String PRIORITY_LOW = "L";
    public static final String PRIORITY_NORMAL = "M";
    public static final String PRIORITY_HIGH = "H";

    public static final String FIELD_PRIORITY_ID = "priority_id";
    public static final String FIELD_PRIORITY_N = "priority_n";
    public static final String FIELD_PRIORITY_NAME = "priority_name";
    public static final String FIELD_PRIORITY_PRIORITY = "priority_priority";
    public static final String FIELD_PRIORITY_DESCR = "priority_descr";
    public static final String FIELD_PRIORITY_ISDELETED = "priority_isdeleted";

    private static String CREATE_TABLE_SQL = "CREATE TABLE " + TABLE_NAME + " (" +
            FIELD_PRIORITY_ID + " text primary key," +
            FIELD_PRIORITY_N + " integer," +
            FIELD_PRIORITY_NAME + " text," +
            FIELD_PRIORITY_PRIORITY + " text," +
            FIELD_PRIORITY_DESCR + " text," +
            FIELD_PRIORITY_ISDELETED + " integer" +
            ");";

    public static PriorityType getPriority(String id){

        String filter = FIELD_PRIORITY_ID + "='" + id + "'";

        ArrayList<PriorityType> priorityTypes = getPriorityList(filter);

        if (priorityTypes != null && priorityTypes.size() > 0){
            return priorityTypes.get(0);
        }

        return null;
    }

    public static ArrayList<PriorityType> getPriorityList(String filter){

        ArrayList<PriorityType> list = null;

        Cursor cursorPriorities = SQLUtils.getNomenclature(TABLE_NAME, FIELD_PRIORITY_ID,
                FIELD_PRIORITY_NAME, "", filter, 0, FIELD_PRIORITY_N, "ASC");

        if ((cursorPriorities != null) && (cursorPriorities.getCount() > 0)){

            list = new ArrayList<>();

            while (cursorPriorities.moveToNext()){

                PriorityType priorityType = new PriorityType();

                priorityType.setPriorityId(cursorPriorities.getString(cursorPriorities.getColumnIndex(FIELD_PRIORITY_ID)));
                priorityType.setPriority(cursorPriorities.getString(cursorPriorities.getColumnIndex(FIELD_PRIORITY_PRIORITY)));
                priorityType.setDescription(cursorPriorities.getString(cursorPriorities.getColumnIndex(FIELD_PRIORITY_DESCR)));
                priorityType.setName(cursorPriorities.getString(cursorPriorities.getColumnIndex(FIELD_PRIORITY_NAME)));
                priorityType.setN(cursorPriorities.getInt(cursorPriorities.getColumnIndex(FIELD_PRIORITY_N)));
                list.add(priorityType);
            }

            cursorPriorities.close();
        }

        return list;
    }

    public static void update(PriorityType[] priorityTypes){

        if (priorityTypes == null){
            return;
        }
        String sqlType = "INSERT OR REPLACE INTO " + TABLE_NAME + " ("+
                FIELD_PRIORITY_ID + "," +
                FIELD_PRIORITY_N + "," +
                FIELD_PRIORITY_NAME + "," +
                FIELD_PRIORITY_PRIORITY + "," +
                FIELD_PRIORITY_DESCR + "," +
                FIELD_PRIORITY_ISDELETED + ")" +
                " VALUES (?,?,?,?,?,?)";

        try {

            getWriteDb().beginTransactionNonExclusive();

            SQLiteStatement stType = getWriteDb().compileStatement(sqlType);

            for (PriorityType priorityType : priorityTypes) {

                stType.bindString(1, priorityType.getPriorityId());
                stType.bindLong(2, priorityType.getN());
                stType.bindString(3, priorityType.getName());
                stType.bindString(4, priorityType.getPriority());
                stType.bindString(5, priorityType.getDescription());
                stType.bindLong(6, (priorityType.isDeleted() ? 1:0));

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
