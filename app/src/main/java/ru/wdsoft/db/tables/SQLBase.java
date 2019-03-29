package ru.wdsoft.db.tables;

import android.database.sqlite.SQLiteDatabase;

import ru.wdsoft.db.WDDbHelper;

/**
 * Created by slaventii@mail.ru on 18.02.2019.
 */
public class SQLBase {

    /**
     * Для логирования
     */
    private static final String LOG_PREFIX = "SQLBase -- ";

    protected static SQLiteDatabase getWriteDb(){
        return WDDbHelper.getWriteDb();
    }

    public static SQLiteDatabase getReadDb(){
        return WDDbHelper.getReadDb();
    }

}
