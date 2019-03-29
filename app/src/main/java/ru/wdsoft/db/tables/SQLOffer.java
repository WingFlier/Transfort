package ru.wdsoft.db.tables;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;

import ru.wdsoft.api.models.Offer;
import ru.wdsoft.api.models.Person;
import ru.wdsoft.db.SQLUtils;
import ru.wdsoft.utils.LogUtils;
import ru.wdsoft.utils.Utils;

/**
 * Created by slaventii@mail.ru on 23.02.2019.
 */
public class SQLOffer extends SQLBase {

    private final static String LOG_PREFIX = "SQLOffer  -- ";

    public static final String TABLE_NAME = "OFFER";

    public static final String FIELD_OFFER_ORDERID = "offer_orderid";
    public static final String FIELD_OFFER_CARRIERDEPARTMENTID = "offer_carrierdepartmentid";
    public static final String FIELD_OFFER_CARRIERDEPARTMENTNAME = "offer_carrierdepartmentname";
    public static final String FIELD_OFFER_CARRIERCOMPANYID = "offer_carriercompanyid";
    public static final String FIELD_OFFER_CARRIERCOMPANYNAME = "offer_carriercompanyname";
    public static final String FIELD_OFFER_CODE = "offer_code";
    public static final String FIELD_OFFER_EMAIL = "offer_email";
    public static final String FIELD_OFFER_PHONE = "offer_phone";
    public static final String FIELD_OFFER_RATING = "offer_rating";
    public static final String FIELD_OFFER_WITHVAT = "offer_withvat";
    public static final String FIELD_OFFER_TOTALAMOUNT = "offer_totalamount";
    public static final String FIELD_OFFER_VATRATE = "offer_vatrate";
    public static final String FIELD_OFFER_CREATEDATE = "offer_createdate";
    public static final String FIELD_OFFER_STATE = "offer_state";
    public static final String FIELD_OFFER_PRICE = "offer_price";
    public static final String FIELD_OFFER_VEHICLEID = "offer_vehicleid";
    public static final String FIELD_OFFER_VEHICLEHOURLYPRICE = "offer_vehiclehourlyprice";
    public static final String FIELD_OFFER_VEHICLEKMPRICE = "offer_vehiclekmprice";
    public static final String FIELD_OFFER_VEHICLECOMMENT = "offer_vehiclecomment";
    public static final String FIELD_OFFER_VEHICLETYPEID = "offer_vehicletypeid";
    public static final String FIELD_OFFER_VEHICLETYPENAME = "offer_vehicletypename";
    public static final String FIELD_OFFER_MODELID = "offer_modelid";
    public static final String FIELD_OFFER_MODELNAME = "offer_modelname";
    public static final String FIELD_OFFER_REGNUMBER = "offer_regnumber";
    public static final String FIELD_OFFER_COMMENT = "offer_comment";
    public static final String FIELD_OFFER_PLANNEDTIME = "offer_plannedtime";
    public static final String FIELD_OFFER_USEREMAIL = "offer_useremail";
    public static final String FIELD_OFFER_USERPHONE = "offer_userphone";
    public static final String FIELD_OFFER_USERNAME = "offer_username";
    public static final String FIELD_OFFER_USERID = "offer_userid";

    private static final String CREATE_TABLE_SQL = "CREATE TABLE " + TABLE_NAME + " (" +
            FIELD_OFFER_ORDERID + " text," +
            FIELD_OFFER_CARRIERDEPARTMENTID + " text," +
            FIELD_OFFER_CARRIERDEPARTMENTNAME + " text," +
            FIELD_OFFER_CARRIERCOMPANYID + " text," +
            FIELD_OFFER_CARRIERCOMPANYNAME + " text," +
            FIELD_OFFER_CODE + " integer," +
            FIELD_OFFER_EMAIL + " text," +
            FIELD_OFFER_PHONE + " text," +
            FIELD_OFFER_RATING + " real," +
            FIELD_OFFER_WITHVAT + " integer," +
            FIELD_OFFER_TOTALAMOUNT + " real," +
            FIELD_OFFER_VATRATE + " real," +
            FIELD_OFFER_CREATEDATE + " integer," +
            FIELD_OFFER_STATE + " text," +
            FIELD_OFFER_PRICE + " real," +
            FIELD_OFFER_VEHICLEID + " text," +
            FIELD_OFFER_VEHICLEHOURLYPRICE + " real," +
            FIELD_OFFER_VEHICLEKMPRICE + " real," +
            FIELD_OFFER_VEHICLECOMMENT + " text," +
            FIELD_OFFER_VEHICLETYPEID + " text," +
            FIELD_OFFER_VEHICLETYPENAME + " text," +
            FIELD_OFFER_MODELID + " text," +
            FIELD_OFFER_MODELNAME + " text," +
            FIELD_OFFER_REGNUMBER + " text," +
            FIELD_OFFER_COMMENT + " text," +
            FIELD_OFFER_PLANNEDTIME + " integer," +
            FIELD_OFFER_USEREMAIL + " text," +
            FIELD_OFFER_USERPHONE + " text," +
            FIELD_OFFER_USERNAME + " text," +
            FIELD_OFFER_USERID + " text" +
            ");";

    public static ArrayList<Offer> getOrderList(String OrderId){

        String filter = FIELD_OFFER_ORDERID + "='" + "'";
        return getList(filter);

    }

    public static ArrayList<Offer> getList(String filter){

        ArrayList<Offer> list = null;

        Cursor cursor = getCursor(filter);

        if (cursor != null){

            list = new ArrayList<>();

            while (cursor.moveToNext()){

                Offer offer = new Offer();

                //FIELD_OFFER_ORDERID + " ," +
                offer.setOrderId(cursor.getString(cursor.getColumnIndex(FIELD_OFFER_ORDERID)));
                //FIELD_OFFER_CARRIERDEPARTMENTID + " ," +
                offer.setCarrierDepartmentId(cursor.getString(cursor.getColumnIndex(FIELD_OFFER_CARRIERDEPARTMENTID)));
                //FIELD_OFFER_CARRIERDEPARTMENTNAME + " ," +
                offer.setCarrierDepartmentName(cursor.getString(cursor.getColumnIndex(FIELD_OFFER_CARRIERDEPARTMENTNAME)));
                //FIELD_OFFER_CARRIERCOMPANYID + " ," +
                offer.setCarrierCompanyId(cursor.getString(cursor.getColumnIndex(FIELD_OFFER_CARRIERCOMPANYID)));
                //FIELD_OFFER_CARRIERCOMPANYNAME + " ," +
                offer.setCarrierCompanyName(cursor.getString(cursor.getColumnIndex(FIELD_OFFER_CARRIERCOMPANYNAME)));
                //FIELD_OFFER_CODE + " ," +
                offer.setCode(cursor.getInt(cursor.getColumnIndex(FIELD_OFFER_CODE)));
                //FIELD_OFFER_EMAIL + " ," +
                offer.setEmail(cursor.getString(cursor.getColumnIndex(FIELD_OFFER_EMAIL)));
                //FIELD_OFFER_PHONE + " ," +
                offer.setPhone(cursor.getString(cursor.getColumnIndex(FIELD_OFFER_PHONE)));
                //FIELD_OFFER_RATING + " ," +
                offer.setRating(cursor.getFloat(cursor.getColumnIndex(FIELD_OFFER_RATING)));
                //FIELD_OFFER_WITHVAT + " ," +
                offer.withVAT(cursor.getInt(cursor.getColumnIndex(FIELD_OFFER_WITHVAT))==1);
                //FIELD_OFFER_TOTALAMOUNT + " ," +
                offer.setTotalAmount(cursor.getFloat(cursor.getColumnIndex(FIELD_OFFER_TOTALAMOUNT)));
                //FIELD_OFFER_VATRATE + " ," +
                offer.setVatRate(cursor.getFloat(cursor.getColumnIndex(FIELD_OFFER_VATRATE)));
                //FIELD_OFFER_CREATEDATE + " ," +
                offer.setCreateDate(cursor.getLong(cursor.getColumnIndex(FIELD_OFFER_CREATEDATE)));
                //FIELD_OFFER_STATE + " ," +
                offer.setState(cursor.getString(cursor.getColumnIndex(FIELD_OFFER_STATE)));
                //FIELD_OFFER_PRICE + " ," +
                offer.setPrice(cursor.getFloat(cursor.getColumnIndex(FIELD_OFFER_PRICE)));
                //FIELD_OFFER_VEHICLEID + " ," +
                offer.setVehicleId(cursor.getString(cursor.getColumnIndex(FIELD_OFFER_VEHICLEID)));
                //FIELD_OFFER_VEHICLEHOURLYPRICE + " ," +
                offer.setVehicleHourlyPrice(cursor.getFloat(cursor.getColumnIndex(FIELD_OFFER_VEHICLEHOURLYPRICE)));
                //FIELD_OFFER_VEHICLEKMPRICE + " ," +
                offer.setVehicleKmPrice(cursor.getFloat(cursor.getColumnIndex(FIELD_OFFER_VEHICLEKMPRICE)));
                //FIELD_OFFER_VEHICLECOMMENT + " ," +
                offer.setVehicleComment(cursor.getString(cursor.getColumnIndex(FIELD_OFFER_VEHICLECOMMENT)));
                //FIELD_OFFER_VEHICLETYPEID + " ," +
                offer.setVehicleTypeId(cursor.getString(cursor.getColumnIndex(FIELD_OFFER_VEHICLETYPEID)));
                //FIELD_OFFER_VEHICLETYPENAME + " ," +
                offer.setVehicleTypeName(cursor.getString(cursor.getColumnIndex(FIELD_OFFER_VEHICLETYPENAME)));
                //FIELD_OFFER_MODELID + " ," +
                offer.setModelId(cursor.getString(cursor.getColumnIndex(FIELD_OFFER_MODELID)));
                //FIELD_OFFER_MODELNAME + " ," +
                offer.setModelName(cursor.getString(cursor.getColumnIndex(FIELD_OFFER_MODELNAME)));
                //FIELD_OFFER_REGNUMBER + " ," +
                offer.setRegNumber(cursor.getString(cursor.getColumnIndex(FIELD_OFFER_REGNUMBER)));
                //FIELD_OFFER_COMMENT + " ," +
                offer.setComment(cursor.getString(cursor.getColumnIndex(FIELD_OFFER_COMMENT)));
                //FIELD_OFFER_PLANNEDTIME + " ," +
                offer.setPlannedTime(cursor.getInt(cursor.getColumnIndex(FIELD_OFFER_PLANNEDTIME)));
                //FIELD_OFFER_USEREMAIL + " ," +
                offer.setUserEmail(cursor.getString(cursor.getColumnIndex(FIELD_OFFER_USEREMAIL)));
                //FIELD_OFFER_USERPHONE + " ," +
                offer.setUserPhone(cursor.getString(cursor.getColumnIndex(FIELD_OFFER_USERPHONE)));
                //FIELD_OFFER_USERNAME + " ," +
                offer.setUserName(cursor.getString(cursor.getColumnIndex(FIELD_OFFER_USERNAME)));
                //FIELD_OFFER_USERID + ")" +
                offer.setUserId(cursor.getString(cursor.getColumnIndex(FIELD_OFFER_USERID)));

                list.add(offer);
            }

            cursor.close();
        }

        return list;
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

        sql += " ORDER BY " + FIELD_OFFER_CREATEDATE + " DESC ";

        return SQLUtils.selectData(sql);
    }

    public static void update(Offer[] offers){

        if (offers == null) return;

        String sql = "INSERT OR REPLACE INTO " + TABLE_NAME + " ("+
                FIELD_OFFER_ORDERID + " ," +
                FIELD_OFFER_CARRIERDEPARTMENTID + " ," +
                FIELD_OFFER_CARRIERDEPARTMENTNAME + " ," +
                FIELD_OFFER_CARRIERCOMPANYID + " ," +
                FIELD_OFFER_CARRIERCOMPANYNAME + " ," +
                FIELD_OFFER_CODE + " ," +
                FIELD_OFFER_EMAIL + " ," +
                FIELD_OFFER_PHONE + " ," +
                FIELD_OFFER_RATING + " ," +
                FIELD_OFFER_WITHVAT + " ," +
                FIELD_OFFER_TOTALAMOUNT + " ," +
                FIELD_OFFER_VATRATE + " ," +
                FIELD_OFFER_CREATEDATE + " ," +
                FIELD_OFFER_STATE + " ," +
                FIELD_OFFER_PRICE + " ," +
                FIELD_OFFER_VEHICLEID + " ," +
                FIELD_OFFER_VEHICLEHOURLYPRICE + " ," +
                FIELD_OFFER_VEHICLEKMPRICE + " ," +
                FIELD_OFFER_VEHICLECOMMENT + " ," +
                FIELD_OFFER_VEHICLETYPEID + " ," +
                FIELD_OFFER_VEHICLETYPENAME + " ," +
                FIELD_OFFER_MODELID + " ," +
                FIELD_OFFER_MODELNAME + " ," +
                FIELD_OFFER_REGNUMBER + " ," +
                FIELD_OFFER_COMMENT + " ," +
                FIELD_OFFER_PLANNEDTIME + " ," +
                FIELD_OFFER_USEREMAIL + " ," +
                FIELD_OFFER_USERPHONE + " ," +
                FIELD_OFFER_USERNAME + " ," +
                FIELD_OFFER_USERID + ")" +
                " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        try {

            getWriteDb().beginTransactionNonExclusive();

            SQLiteStatement stInsert = getWriteDb().compileStatement(sql);

            for (Offer offer : offers) {

                //FIELD_OFFER_ORDERID + " ," +
                stInsert.bindString(1, offer.getOrderId());
                //FIELD_OFFER_CARRIERDEPARTMENTID + " ," +
                stInsert.bindString(2, offer.getCarrierDepartmentId());
                //FIELD_OFFER_CARRIERDEPARTMENTNAME + " ," +
                stInsert.bindString(3, offer.getCarrierDepartmentName());
                //FIELD_OFFER_CARRIERCOMPANYID + " ," +
                stInsert.bindString(4, offer.getCarrierCompanyId());
                //FIELD_OFFER_CARRIERCOMPANYNAME + " ," +
                stInsert.bindString(5, offer.getCarrierCompanyName());
                //FIELD_OFFER_CODE + " ," +
                stInsert.bindLong(6, offer.getCode());
                //FIELD_OFFER_EMAIL + " ," +
                stInsert.bindString(7, offer.getEmail());
                //FIELD_OFFER_PHONE + " ," +
                stInsert.bindString(8, offer.getPhone());
                //FIELD_OFFER_RATING + " ," +
                stInsert.bindDouble(9, offer.getRating());
                //FIELD_OFFER_WITHVAT + " ," +
                stInsert.bindLong(10, (offer.withVAT() ? 1:0));
                //FIELD_OFFER_TOTALAMOUNT + " ," +
                stInsert.bindDouble(11, offer.getTotalAmount());
                //FIELD_OFFER_VATRATE + " ," +
                stInsert.bindDouble(12, offer.getVatRate());
                //FIELD_OFFER_CREATEDATE + " ," +
                stInsert.bindLong(13, offer.getCreateDate());
                //FIELD_OFFER_STATE + " ," +
                stInsert.bindString(14, offer.getState());
                //FIELD_OFFER_PRICE + " ," +
                stInsert.bindDouble(15, offer.getPrice());
                //FIELD_OFFER_VEHICLEID + " ," +
                stInsert.bindString(16, offer.getVehicleId());
                //FIELD_OFFER_VEHICLEHOURLYPRICE + " ," +
                stInsert.bindDouble(17, offer.getVehicleHourlyPrice());
                //FIELD_OFFER_VEHICLEKMPRICE + " ," +
                stInsert.bindDouble(18, offer.getVehicleKmPrice());
                //FIELD_OFFER_VEHICLECOMMENT + " ," +
                stInsert.bindString(19, offer.getVehicleComment());
                //FIELD_OFFER_VEHICLETYPEID + " ," +
                stInsert.bindString(20, offer.getVehicleTypeId());
                //FIELD_OFFER_VEHICLETYPENAME + " ," +
                stInsert.bindString(21, offer.getVehicleTypeName());
                //FIELD_OFFER_MODELID + " ," +
                stInsert.bindString(22, offer.getModelId());
                //FIELD_OFFER_MODELNAME + " ," +
                stInsert.bindString(23, offer.getModelName());
                //FIELD_OFFER_REGNUMBER + " ," +
                stInsert.bindString(24, offer.getRegNumber());
                //FIELD_OFFER_COMMENT + " ," +
                stInsert.bindString(25, offer.getComment());
                //FIELD_OFFER_PLANNEDTIME + " ," +
                stInsert.bindLong(26, offer.getPlannedTime());
                //FIELD_OFFER_USEREMAIL + " ," +
                stInsert.bindString(27, offer.getUserEmail());
                //FIELD_OFFER_USERPHONE + " ," +
                stInsert.bindString(28, offer.getUserPhone());
                //FIELD_OFFER_USERNAME + " ," +
                stInsert.bindString(29, offer.getUserName());
                //FIELD_OFFER_USERID + ")" +
                stInsert.bindString(30, offer.getUserId());

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
