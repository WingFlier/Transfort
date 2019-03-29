package ru.wdsoft.db.tables;

import android.database.sqlite.SQLiteStatement;

import ru.wdsoft.api.models.Country;
import ru.wdsoft.api.models.DocumentType;
import ru.wdsoft.db.SQLUtils;
import ru.wdsoft.utils.LogUtils;

/**
 * Created by slaventii@mail.ru on 20.02.2019.
 */
public class SQLCountry extends SQLBase {

    private final static String LOG_PREFIX = "SQLCountry  -- ";

    public static final String TABLE_NAME = "COUNTRY";


    public static final String FIELD_COUNTRY_ID = "country_id";
    public static final String FIELD_COUNTRY_NAME = "country_name";
    public static final String FIELD_COUNTRY_NUMBERCODE = "country_numbercode";
    public static final String FIELD_COUNTRY_TWOCHARACTERCODE = "country_twocharactercode";
    public static final String FIELD_COUNTRY_THREECHARACTERCODE = "country_threecharactercode";
    public static final String FIELD_COUNTRY_ISDELETED = "country_isdeleted";

    private static final String CREATE_TABLE_SQL = "CREATE TABLE " + TABLE_NAME + " (" +
            FIELD_COUNTRY_ID + " text primary key," +
            FIELD_COUNTRY_NAME + " text," +
            FIELD_COUNTRY_NUMBERCODE + " text," +
            FIELD_COUNTRY_TWOCHARACTERCODE + " text," +
            FIELD_COUNTRY_THREECHARACTERCODE + " text," +
            FIELD_COUNTRY_ISDELETED + " integer" +
            ");";

    public static void update(Country[] countries){

        if (countries == null) return;

        String sql = "INSERT OR REPLACE INTO " + TABLE_NAME + " ("+
                FIELD_COUNTRY_ID + "," +
                FIELD_COUNTRY_NAME + "," +
                FIELD_COUNTRY_NUMBERCODE + "," +
                FIELD_COUNTRY_TWOCHARACTERCODE + "," +
                FIELD_COUNTRY_THREECHARACTERCODE + "," +
                FIELD_COUNTRY_ISDELETED + ")" +
                " VALUES (?,?,?,?,?,?)";

        try {

            getWriteDb().beginTransactionNonExclusive();

            SQLiteStatement stInsert = getWriteDb().compileStatement(sql);

            for (Country country : countries) {

                stInsert.bindString(1, country.getId());
                stInsert.bindString(2, country.getName());
                stInsert.bindString(3, country.getNumberCode());
                stInsert.bindString(4, country.getTwoCharacterCode());
                stInsert.bindString(5, country.getThreeCharacterCode());
                stInsert.bindLong(6, (country.isDeleted() ? 1:0));

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
