package ru.wdsoft.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.Calendar;

import ru.wdsoft.utils.LogUtils;
import ru.wdsoft.utils.Utils;

/**
 * Created by slaventii@mail.ru on 20.02.2019.
 */
public class SQLUtils {

    /**
     * Для логирования
     */
    private static final String LOG_PREFIX = "SQLUtils -- ";

    public static void execSQL(String sql){
        WDDbHelper.getWriteDb().execSQL(sql);
    }

    public static Cursor selectData(String sql, String sqlFilter){

        if (Utils.stringsNotEmpty(sqlFilter)){
            sql += " WHERE " + sqlFilter;
        }

        return selectData(sql);

    }

    public static Cursor selectData(String sql){

        float mAverageSqlTime = 0f;

        try {

            LogUtils.debugLog(LOG_PREFIX, sql);

            long start = Calendar.getInstance().getTimeInMillis();

            SQLiteDatabase db = WDDbHelper.getReadDb();

            Cursor c = db.rawQuery(sql, null);

            long finish = Calendar.getInstance().getTimeInMillis();
            long delta = finish - start;

            if (delta > 300) {
                LogUtils.debugLog(LOG_PREFIX, "sql time = " + String.valueOf(delta) + " ms");

                if (mAverageSqlTime == 0f){
                    mAverageSqlTime = (float) delta;
                } else {
                    mAverageSqlTime = (mAverageSqlTime + (float) delta)/2;
                }
                LogUtils.debugLog(LOG_PREFIX, "Average sql time = " + String.valueOf(mAverageSqlTime) + " ms");
            }

            return c;

        }catch (Exception e){
            LogUtils.debugErrorLog(LOG_PREFIX, e);
        }

        return null;
    }

    private static void logCursor(String tag, Cursor c) {

        if (c == null) {
            LogUtils.debugLog(tag, " is null");
        }

        while (c.moveToNext()) {

            String log = "";

            for (int i = 0; i < c.getColumnCount(); i++) {

                String key = c.getColumnName(i);
                log += key;

                switch (c.getType(i)) {

                    case Cursor.FIELD_TYPE_STRING:
                        log += "=" + c.getString(i) + "; ";
                        break;

                    case Cursor.FIELD_TYPE_INTEGER:
                        log += "=" + String.valueOf(c.getInt(i)) + "; ";
                        break;

                    case Cursor.FIELD_TYPE_FLOAT:
                        log += "=" + String.valueOf(c.getFloat(i)) + "; ";
                        break;

                    default:
                        continue;
                }

            }

            LogUtils.debugLog(LOG_PREFIX, log);
        }
    }

    public static String getNomenclatureSql(String table_name, String field_id, String field_name){

        String sql = "SELECT " + field_id + " AS _id, " +
                "(" + field_name + ") AS name, * " +
                " FROM " + table_name;

        return sql;
    }

    public static String getNomenclatureSql(String table_name, String field_id, String field_name,
                                     String name, String filter, int limit, String ord_field,
                                     String ord_direction) {

        String sqlFilter = "";

        String sql = "SELECT " + field_id + " AS _id, " +
                "(" + field_name + ") AS name, * " +
                " FROM " + table_name;

        if (Utils.stringsNotEmpty(name)){
            sqlFilter += field_name + " LIKE '%" + name + "%' ";
        }

        if (Utils.stringsNotEmpty(filter)){

            if (Utils.stringsNotEmpty(sqlFilter)){
                sqlFilter += " AND ";
            }

            sqlFilter += filter;
        }

        if (Utils.stringsNotEmpty(sqlFilter)){
            sql += " WHERE " + sqlFilter;
        }

        if (limit > 0) {
            sql += " LIMIT " + String.valueOf(limit);
        }

        if (Utils.stringsNotEmpty(ord_field)){

            if (!Utils.stringsNotEmpty(ord_direction)){
                ord_direction = "ASC";
            }

            sql += " ORDER BY " + ord_field + " " + ord_direction + " ";
        }



        return sql;

    }

    public static Cursor getNomenclature(String table_name, String field_id, String field_name,
                                  String name, String filter, int limit, String ord_field,
                                  String ord_direction) {

        String sql = getNomenclatureSql(table_name, field_id, field_name, name, filter,
                limit, ord_field, ord_direction);

        Cursor c = selectData(sql);

//        logCursor(TABLE_CARS, c);

        return c;
    }


}
