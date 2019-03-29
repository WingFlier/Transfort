package ru.wdsoft.db.tables;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;

import ru.wdsoft.api.models.Offer;
import ru.wdsoft.api.models.Order;
import ru.wdsoft.api.models.OrderHistory;
import ru.wdsoft.api.models.VehicleBrand;
import ru.wdsoft.db.SQLUtils;
import ru.wdsoft.utils.LogUtils;
import ru.wdsoft.utils.Utils;

/**
 * Created by slaventii@mail.ru on 23.02.2019.
 */
public class SQLOrderHistory extends SQLBase {

    private final static String LOG_PREFIX = "SQLOrderHistory  -- ";

    public static final String TABLE_NAME = "ORDERHISTORY";

    public static final String FIELD_ORDERHISTORY_ORDERID = "orderhistory_orderid";
    public static final String FIELD_ORDERHISTORY_CANCELREASONID = "orderhistory_cancelreasonid";
    public static final String FIELD_ORDERHISTORY_CANCELREASONNAME = "orderhistory_cancelreasonname";
    public static final String FIELD_ORDERHISTORY_COMMENT = "orderhistory_comment";
    public static final String FIELD_ORDERHISTORY_CONSUMERTYPE = "orderhistory_consumertype";
    public static final String FIELD_ORDERHISTORY_DATE = "orderhistory_date";
    public static final String FIELD_ORDERHISTORY_ISPROTEST = "orderhistory_isprotest";
    public static final String FIELD_ORDERHISTORY_STATE = "orderhistory_state";
    public static final String FIELD_ORDERHISTORY_STATENAME = "orderhistory_statename";
    public static final String FIELD_ORDERHISTORY_USERID = "orderhistory_userid";
    public static final String FIELD_ORDERHISTORY_USERNAME = "orderhistory_username";
    public static final String FIELD_ORDERHISTORY_ISCHANGED = "orderhistory_ischanged";

    private static final String CREATE_TABLE_SQL = "CREATE TABLE " + TABLE_NAME + " (" +
            FIELD_ORDERHISTORY_ORDERID + " text," +
            FIELD_ORDERHISTORY_CANCELREASONID + " text," +
            FIELD_ORDERHISTORY_CANCELREASONNAME + " text," +
            FIELD_ORDERHISTORY_COMMENT + " text," +
            FIELD_ORDERHISTORY_CONSUMERTYPE + " text," +
            FIELD_ORDERHISTORY_DATE + " integer," +
            FIELD_ORDERHISTORY_ISPROTEST + " integer," +
            FIELD_ORDERHISTORY_STATE + " text," +
            FIELD_ORDERHISTORY_STATENAME + " text," +
            FIELD_ORDERHISTORY_USERID + " text," +
            FIELD_ORDERHISTORY_USERNAME + " text," +
            FIELD_ORDERHISTORY_ISCHANGED + " integer" +
            ");";


    public static ArrayList<OrderHistory> getOrderList(String OrderId){
        String filter = FIELD_ORDERHISTORY_ORDERID + "='" + OrderId + "'";
        return getList(filter);
    }

    public static ArrayList<OrderHistory> getList(String filter){

        ArrayList<OrderHistory> list = null;

        Cursor cursor = getCursor(filter);

        if (cursor != null){

            list = new ArrayList<>();

            while (cursor.moveToNext()){

                OrderHistory orderHistory = new OrderHistory();

                //  FIELD_ORDERHISTORY_ORDERID
                orderHistory.setOrderId(cursor.getString(cursor.getColumnIndex(FIELD_ORDERHISTORY_ORDERID)));
                //  FIELD_ORDERHISTORY_CANCELREASONID
                orderHistory.setCancelReasonId(cursor.getString(cursor.getColumnIndex(FIELD_ORDERHISTORY_ORDERID)));
                //  FIELD_ORDERHISTORY_CANCELREASONNAME
                orderHistory.setCancelReasonName(cursor.getString(cursor.getColumnIndex(FIELD_ORDERHISTORY_ORDERID)));
                //  FIELD_ORDERHISTORY_COMMENT
                orderHistory.setComment(cursor.getString(cursor.getColumnIndex(FIELD_ORDERHISTORY_ORDERID)));
                //  FIELD_ORDERHISTORY_CONSUMERTYPE
                orderHistory.setConsumerType(cursor.getString(cursor.getColumnIndex(FIELD_ORDERHISTORY_ORDERID)));
                //  FIELD_ORDERHISTORY_DATE
                orderHistory.setDate(cursor.getLong(cursor.getColumnIndex(FIELD_ORDERHISTORY_ORDERID)));
                //  FIELD_ORDERHISTORY_ISPROTEST
                orderHistory.setProtest(cursor.getInt(cursor.getColumnIndex(FIELD_ORDERHISTORY_ISCHANGED))==1);
                //  FIELD_ORDERHISTORY_STATE
                orderHistory.setState(cursor.getString(cursor.getColumnIndex(FIELD_ORDERHISTORY_ORDERID)));
                //  FIELD_ORDERHISTORY_STATENAME
                orderHistory.setStateName(cursor.getString(cursor.getColumnIndex(FIELD_ORDERHISTORY_ORDERID)));
                //  FIELD_ORDERHISTORY_USERID
                orderHistory.setUserId(cursor.getString(cursor.getColumnIndex(FIELD_ORDERHISTORY_ORDERID)));
                // FIELD_ORDERHISTORY_USERNAME
                orderHistory.setUserName(cursor.getString(cursor.getColumnIndex(FIELD_ORDERHISTORY_ORDERID)));
                //  FIELD_ORDERHISTORY_ISCHANGED
                orderHistory.setChanged(cursor.getInt(cursor.getColumnIndex(FIELD_ORDERHISTORY_ISCHANGED))==1);

                list.add(orderHistory);
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

        sql += " ORDER BY " + FIELD_ORDERHISTORY_DATE + " DESC ";

        return SQLUtils.selectData(sql);
    }

    public static void update(OrderHistory[] orderHistories){

        if (orderHistories == null) return;

        String sql = "INSERT OR REPLACE INTO " + TABLE_NAME + " ("+
                FIELD_ORDERHISTORY_ORDERID + " ," +
                FIELD_ORDERHISTORY_CANCELREASONID + " ," +
                FIELD_ORDERHISTORY_CANCELREASONNAME + " ," +
                FIELD_ORDERHISTORY_COMMENT + " ," +
                FIELD_ORDERHISTORY_CONSUMERTYPE + " ," +
                FIELD_ORDERHISTORY_DATE + " ," +
                FIELD_ORDERHISTORY_ISPROTEST + " ," +
                FIELD_ORDERHISTORY_STATE + " ," +
                FIELD_ORDERHISTORY_STATENAME + " ," +
                FIELD_ORDERHISTORY_USERID + " ," +
                FIELD_ORDERHISTORY_USERNAME + " ," +
                FIELD_ORDERHISTORY_ISCHANGED + ")" +
                " VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";

        try {

            getWriteDb().beginTransactionNonExclusive();

            SQLiteStatement stInsert = getWriteDb().compileStatement(sql);

            for (OrderHistory orderHistory : orderHistories) {

//                FIELD_ORDERHISTORY_ORDERID
                stInsert.bindString(1, orderHistory.getOrderId());
//                FIELD_ORDERHISTORY_CANCELREASONID
                stInsert.bindString(2, orderHistory.getCancelReasonId());
//                FIELD_ORDERHISTORY_CANCELREASONNAME
                stInsert.bindString(3, orderHistory.getCancelReasonName());
//                FIELD_ORDERHISTORY_COMMENT
                stInsert.bindString(4, orderHistory.getComment());
//                FIELD_ORDERHISTORY_CONSUMERTYPE
                stInsert.bindString(5, orderHistory.getConsumerType());
//                FIELD_ORDERHISTORY_DATE
                stInsert.bindLong(6, orderHistory.getDate());
//                FIELD_ORDERHISTORY_ISPROTEST
                stInsert.bindLong(7, (orderHistory.isProtest() ? 1:0));
//                FIELD_ORDERHISTORY_STATE
                stInsert.bindString(8, orderHistory.getState());
//                FIELD_ORDERHISTORY_STATENAME
                stInsert.bindString(9, orderHistory.getStateName());
//                FIELD_ORDERHISTORY_USERID
                stInsert.bindString(10, orderHistory.getUserId());
//                FIELD_ORDERHISTORY_USERNAME
                stInsert.bindString(11, orderHistory.getUserName());
//                FIELD_ORDERHISTORY_ISCHANGED
                stInsert.bindLong(12, (orderHistory.isChanged() ? 1:0));

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
