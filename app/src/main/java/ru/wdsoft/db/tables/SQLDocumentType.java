package ru.wdsoft.db.tables;

import android.database.sqlite.SQLiteStatement;

import ru.wdsoft.api.models.DocumentType;
import ru.wdsoft.api.models.VehicleBrand;
import ru.wdsoft.db.SQLUtils;
import ru.wdsoft.utils.LogUtils;

/**
 * Created by slaventii@mail.ru on 20.02.2019.
 */
public class SQLDocumentType extends SQLBase {

    private final static String LOG_PREFIX = "SQLDocumentType  -- ";

    public static final String TABLE_NAME = "DOCUMENTTYPE";

    public static final String FIELD_DOCUMENTTYPE_ID = "documenttype_id";
    public static final String FIELD_DOCUMENTTYPE_NAME = "documenttype_name";
    public static final String FIELD_DOCUMENTTYPE_ONLYFOR = "documenttype_onlyfor";
    public static final String FIELD_DOCUMENTTYPE_FORENTITY = "documenttype_forentity";
    public static final String FIELD_DOCUMENTTYPE_ORDERTYPE = "documenttype_ordertype";
    public static final String FIELD_DOCUMENTTYPE_ISREQUIRED = "documenttype_isrequired";
    public static final String FIELD_DOCUMENTTYPE_ISDELETED = "documenttype_isdeleted";

    private static final String CREATE_TABLE_SQL = "CREATE TABLE " + TABLE_NAME + " (" +
            FIELD_DOCUMENTTYPE_ID + " text primary key," +
            FIELD_DOCUMENTTYPE_NAME + " text," +
            FIELD_DOCUMENTTYPE_ONLYFOR + " text," +
            FIELD_DOCUMENTTYPE_FORENTITY + " text," +
            FIELD_DOCUMENTTYPE_ORDERTYPE + " text," +
            FIELD_DOCUMENTTYPE_ISREQUIRED + " integer," +
            FIELD_DOCUMENTTYPE_ISDELETED + " integer" +
            ");";

    public static void update(DocumentType[] documentTypes){

        if (documentTypes == null) return;

        String sql = "INSERT OR REPLACE INTO " + TABLE_NAME + " ("+
                FIELD_DOCUMENTTYPE_ID + "," +
                FIELD_DOCUMENTTYPE_NAME + "," +
                FIELD_DOCUMENTTYPE_ONLYFOR + "," +
                FIELD_DOCUMENTTYPE_FORENTITY + "," +
                FIELD_DOCUMENTTYPE_ORDERTYPE + "," +
                FIELD_DOCUMENTTYPE_ISREQUIRED + "," +
                FIELD_DOCUMENTTYPE_ISDELETED + ")" +
                " VALUES (?,?,?,?,?,?,?)";

        try {

            getWriteDb().beginTransactionNonExclusive();

            SQLiteStatement stInsert = getWriteDb().compileStatement(sql);

            for (DocumentType documentType : documentTypes) {

                stInsert.bindString(1, documentType.getId());
                stInsert.bindString(2, documentType.getName());
                stInsert.bindString(3, documentType.getOnlyFor());
                stInsert.bindString(4, documentType.getForEntity());
                stInsert.bindString(5, documentType.getOrderType());
                stInsert.bindLong(6, (documentType.isRequired() ? 1:0));
                stInsert.bindLong(7, (documentType.isDeleted() ? 1:0));

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
