package ru.wdsoft.db.tables;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;

import ru.wdsoft.api.models.OrderHistory;
import ru.wdsoft.api.models.Person;
import ru.wdsoft.api.models.VehicleBrand;
import ru.wdsoft.db.SQLUtils;
import ru.wdsoft.utils.LogUtils;
import ru.wdsoft.utils.Utils;

/**
 * Created by slaventii@mail.ru on 23.02.2019.
 */
public class SQLOrderCustomerPerson extends SQLBase {

    private final static String LOG_PREFIX = "SQLOrderCustomerPerson  -- ";

    public static final String TABLE_NAME = "ORDERCUSTOMERPERSON";

    public static final String FIELD_ORDCUSTOMERPERSON_ORDERID = "ordercustomerperson_orderid";
    public static final String FIELD_ORDERCUSTOMERPERSON_NAME = "ordercustomerperson_name";
    public static final String FIELD_ORDERCUSTOMERPERSON_EMAIL = "ordercustomerperson_email";
    public static final String FIELD_ORDERCUSTOMERPERSON_PHONE = "ordercustomerperson_phone";
    public static final String FIELD_ORDERCUSTOMERPERSON_FROMEXTSYSTEM = "ordercustomerperson_fromextsystem";

    private static final String CREATE_TABLE_SQL = "CREATE TABLE " + TABLE_NAME + " (" +
            FIELD_ORDCUSTOMERPERSON_ORDERID + " text," +
            FIELD_ORDERCUSTOMERPERSON_NAME + " text," +
            FIELD_ORDERCUSTOMERPERSON_EMAIL + " text," +
            FIELD_ORDERCUSTOMERPERSON_PHONE + " text," +
            FIELD_ORDERCUSTOMERPERSON_FROMEXTSYSTEM + " integer" +
            ");";

    public static Person getOrderPerson(String OrderId){
        String filter = FIELD_ORDCUSTOMERPERSON_ORDERID + "='" + OrderId + "'";
        return get(filter);
    }

    public static Person get(String filter){

        Person person = null;

        Cursor cursor = getCursor(filter);

        if (cursor != null){

            if (cursor.moveToFirst()){

                person = new Person();

//              FIELD_ORDCUSTOMERPERSON_ORDERID
                person.setOrderId(cursor.getString(cursor.getColumnIndex(FIELD_ORDCUSTOMERPERSON_ORDERID)));
//                FIELD_ORDERCUSTOMERPERSON_NAME
                person.setName(cursor.getString(cursor.getColumnIndex(FIELD_ORDERCUSTOMERPERSON_NAME)));
//                FIELD_ORDERCUSTOMERPERSON_EMAIL
                person.setEMail(cursor.getString(cursor.getColumnIndex(FIELD_ORDERCUSTOMERPERSON_EMAIL)));
//                FIELD_ORDERCUSTOMERPERSON_PHONE
                person.setPhone(cursor.getString(cursor.getColumnIndex(FIELD_ORDERCUSTOMERPERSON_PHONE)));
//                FIELD_ORDERCUSTOMERPERSON_FROMEXTSYSTEM
                person.fromExtSystem(cursor.getInt(cursor.getColumnIndex(FIELD_ORDERCUSTOMERPERSON_FROMEXTSYSTEM))==1);

            }

            cursor.close();
        }

        return person;
    }

    public static Cursor getCursor(String filter) {

        String sqlFilter = "";

        String sql = "SELECT * FROM " + TABLE_NAME;

        if (Utils.stringsNotEmpty(filter)){

            if (Utils.stringsNotEmpty(sqlFilter)){
                sqlFilter += " AND ";
            }

            sqlFilter += filter;
        }

        if (Utils.stringsNotEmpty(sqlFilter)){
            sql += " WHERE " + sqlFilter;
        }

        return SQLUtils.selectData(sql);
    }

    public static void update(Person person){

        if (person == null) return;

        String sql = "INSERT OR REPLACE INTO " + TABLE_NAME + " ("+
                FIELD_ORDCUSTOMERPERSON_ORDERID + "," +
                FIELD_ORDERCUSTOMERPERSON_NAME + "," +
                FIELD_ORDERCUSTOMERPERSON_EMAIL + "," +
                FIELD_ORDERCUSTOMERPERSON_PHONE + "," +
                FIELD_ORDERCUSTOMERPERSON_FROMEXTSYSTEM + ")" +
                " VALUES (?,?,?,?,?)";

        try {

            getWriteDb().beginTransactionNonExclusive();

            SQLiteStatement stInsert = getWriteDb().compileStatement(sql);

            stInsert.bindString(1, person.getOrderId());
            stInsert.bindString(2, person.getName());
            stInsert.bindString(3, person.getEMail());
            stInsert.bindString(4, person.getPhone());
            stInsert.bindLong(5, (person.fromExtSystem() ? 1:0));

            stInsert.execute();
            stInsert.clearBindings();

            getWriteDb().setTransactionSuccessful();

        } catch (Exception e) {
            LogUtils.debugErrorLog(LOG_PREFIX, e);
        } finally {
            getWriteDb().endTransaction();
        }
    }

    public static void deleteAll(){

        try {
            getWriteDb().delete(TABLE_NAME, null, null);
        } catch (Exception e) {
            LogUtils.debugErrorLog(LOG_PREFIX, e);
        }
    }

    public static void createTable() {
        SQLUtils.execSQL(CREATE_TABLE_SQL);
    }

    public static void dropTable() {
        SQLUtils.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME + "; ");
    }
}
