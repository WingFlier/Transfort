package ru.wdsoft.db.tables;

import android.database.sqlite.SQLiteStatement;

import ru.wdsoft.api.models.Country;
import ru.wdsoft.api.models.Locality;
import ru.wdsoft.db.SQLUtils;
import ru.wdsoft.utils.LogUtils;

/**
 * Created by slaventii@mail.ru on 20.02.2019.
 */
public class SQLLocality extends SQLBase {

    private final static String LOG_PREFIX = "SQLLocality  -- ";

    public static final String TABLE_NAME = "LOCALITY";

    public static final String FIELD_LOCALITY_ID = "locality_id";
    public static final String FIELD_LOCALITY_NAME = "locality_name";
    public static final String FIELD_LOCALITY_CITY = "locality_city";
    public static final String FIELD_LOCALITY_REGION = "locality_region";
    public static final String FIELD_LOCALITY_COUNTRY = "locality_country";
    public static final String FIELD_LOCALITY_CITYCODE = "locality_citycode";
    public static final String FIELD_LOCALITY_DISTRICT = "locality_district";
    public static final String FIELD_LOCALITY_TIMESHIFT = "locality_timeshift";
    public static final String FIELD_LOCALITY_MODIFYDATE = "locality_modifydate";
    public static final String FIELD_LOCALITY_REGIONCODE = "locality_regioncode";
    public static final String FIELD_LOCALITY_DISTRICTCODE = "locality_districtcode";
    public static final String FIELD_LOCALITY_LOCALITYCODE = "locality_localitycode";
    public static final String FIELD_LOCALITY_INTERNATIONALNAME = "locality_internationalname";
    public static final String FIELD_LOCALITY_ISDELETED = "locality_isdeleted";


    private static final String CREATE_TABLE_SQL = "CREATE TABLE " + TABLE_NAME + " (" +
            FIELD_LOCALITY_ID + " text primary key," +
            FIELD_LOCALITY_NAME + " text," +
            FIELD_LOCALITY_CITY + " text," +
            FIELD_LOCALITY_REGION + " text," +
            FIELD_LOCALITY_COUNTRY + " text," +
            FIELD_LOCALITY_CITYCODE + " text," +
            FIELD_LOCALITY_DISTRICT + " text," +
            FIELD_LOCALITY_TIMESHIFT + " integer," +
            FIELD_LOCALITY_MODIFYDATE + " integer," +
            FIELD_LOCALITY_REGIONCODE + " text," +
            FIELD_LOCALITY_DISTRICTCODE + " text," +
            FIELD_LOCALITY_LOCALITYCODE + " text," +
            FIELD_LOCALITY_INTERNATIONALNAME + " text," +
            FIELD_LOCALITY_ISDELETED + " integer" +
            ");";

    public static void update(Locality[] localities){

        if (localities == null) return;

        String sql = "INSERT OR REPLACE INTO " + TABLE_NAME + " ("+
                FIELD_LOCALITY_ID + "," +
                FIELD_LOCALITY_NAME + "," +
                FIELD_LOCALITY_CITY + "," +
                FIELD_LOCALITY_REGION + "," +
                FIELD_LOCALITY_COUNTRY + "," +
                FIELD_LOCALITY_CITYCODE + "," +
                FIELD_LOCALITY_DISTRICT + "," +
                FIELD_LOCALITY_TIMESHIFT + "," +
                FIELD_LOCALITY_MODIFYDATE + "," +
                FIELD_LOCALITY_REGIONCODE + "," +
                FIELD_LOCALITY_DISTRICTCODE + "," +
                FIELD_LOCALITY_LOCALITYCODE + "," +
                FIELD_LOCALITY_INTERNATIONALNAME + "," +
                FIELD_LOCALITY_ISDELETED + ")" +
                " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        try {

            getWriteDb().beginTransactionNonExclusive();

            SQLiteStatement stInsert = getWriteDb().compileStatement(sql);

            for (Locality locality : localities) {

                stInsert.bindString(1, locality.getId());
                stInsert.bindString(2, locality.getName());
                stInsert.bindString(3, locality.getCity());
                stInsert.bindString(4, locality.getRegion());
                stInsert.bindString(5, locality.getCountry());
                stInsert.bindString(6, locality.getCityCode());
                stInsert.bindString(7, locality.getDistrict());
                stInsert.bindLong(8, locality.getTimeShift());
                stInsert.bindLong(9, locality.getModifyDate());
                stInsert.bindString(10, locality.getRegionCode());
                stInsert.bindString(11, locality.getDistrictCode());
                stInsert.bindString(12, locality.getLocalityCode());
                stInsert.bindString(13, locality.getInternationalName());
                stInsert.bindLong(14, (locality.isDeleted() ? 1:0));

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
