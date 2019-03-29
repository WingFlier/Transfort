package ru.wdsoft.db.tables;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;

import ru.wdsoft.api.ApiSerializable;
import ru.wdsoft.api.models.Company;
import ru.wdsoft.api.models.Department;
import ru.wdsoft.db.SQLUtils;
import ru.wdsoft.utils.LogUtils;

/**
 * Created by slaventii@mail.ru on 20.02.2019.
 */
public class SQLCompany extends SQLBase {

    private final static String LOG_PREFIX = "SQLCompany  -- ";

    public static final String TABLE_NAME = "COMPANY";

    public static final String FIELD_COMPANY_ID = "company_id";
    public static final String FIELD_COMPANY_NAME = "company_name";
    public static final String FIELD_COMPANY_LEGALADDRESS = "company_legaladdress";
    public static final String FIELD_COMPANY_LEGALNAME = "company_legalname";
    public static final String FIELD_COMPANY_ADDRESS = "company_address";
    public static final String FIELD_COMPANY_EMAIL = "company_email";
    public static final String FIELD_COMPANY_INN = "company_inn";
    public static final String FIELD_COMPANY_KPP = "company_kpp";
    public static final String FIELD_COMPANY_OWNERSHIPTYPE = "company_ownershiptype";
    public static final String FIELD_COMPANY_PHONE = "company_phone";
    public static final String FIELD_COMPANY_WITHVAT = "company_withvat";
    public static final String FIELD_COMPANY_ISAPPROVED = "company_isapproved";
    public static final String FIELD_COMPANY_ISCUSTOMER = "company_iscustomer";
    public static final String FIELD_COMPANY_ISDELETED = "company_isdeleted";
    public static final String FIELD_COMPANY_ISSUPPLIER = "company_issupplier";
    public static final String FIELD_COMPANY_ISSELECTED = "company_isselected";
    public static final String FIELD_COMPANY_CODE = "company_code";
    public static final String FIELD_COMPANY_RATING = "company_rating";
    public static final String FIELD_COMPANY_HASRIGHTS = "company_hasrights";
    public static final String FIELD_COMPANY_MODIFYDATE = "company_modifydate";
    public static final String FIELD_COMPANY_REGIONCODE = "company_regioncode";


    private static final String CREATE_TABLE_SQL = "CREATE TABLE " + TABLE_NAME + " (" +
            FIELD_COMPANY_ID + " text primary key," +
            FIELD_COMPANY_NAME + " text," +
            FIELD_COMPANY_LEGALADDRESS + " text," +
            FIELD_COMPANY_LEGALNAME + " text," +
            FIELD_COMPANY_ADDRESS  + " text,"+
            FIELD_COMPANY_EMAIL  + " text,"+
            FIELD_COMPANY_INN  + " integer,"+
            FIELD_COMPANY_KPP  + " integer,"+
            FIELD_COMPANY_OWNERSHIPTYPE  + " text,"+
            FIELD_COMPANY_PHONE  + " text,"+
            FIELD_COMPANY_WITHVAT  + " integer,"+
            FIELD_COMPANY_CODE  + " integer,"+
            FIELD_COMPANY_RATING  + " real,"+
            FIELD_COMPANY_HASRIGHTS  + " integer,"+
            FIELD_COMPANY_MODIFYDATE  + " integer,"+
            FIELD_COMPANY_REGIONCODE  + " text,"+
            FIELD_COMPANY_ISAPPROVED  + " integer,"+
            FIELD_COMPANY_ISCUSTOMER  + " integer, "+
            FIELD_COMPANY_ISSUPPLIER  + " integer, "+
            FIELD_COMPANY_ISDELETED  + " integer, "+
            FIELD_COMPANY_ISSELECTED  + " integer"+
            ");";

    public static ApiSerializable get(String id){

        String filter = FIELD_COMPANY_ID + "='" + id + "'";

        ArrayList<ApiSerializable> list = getList(filter);

        if (list != null && list.size() > 0){
            return list.get(0);
        }

        return null;
    }

    public static ArrayList<ApiSerializable> getList(String filter){

        Cursor cursor = SQLUtils.getNomenclature(TABLE_NAME, FIELD_COMPANY_ID,
                FIELD_COMPANY_NAME, "", filter, 0, "", "");

        return getList(cursor);

    }

    public static ArrayList<ApiSerializable> getList(Cursor cursor){

        ArrayList<ApiSerializable> list = null;

        if (cursor != null){

            list = new ArrayList<>();

            while (cursor.moveToNext()){

                Company company = new Company();

                company.setId(cursor.getString(cursor.getColumnIndex(FIELD_COMPANY_ID)));
                company.setName(cursor.getString(cursor.getColumnIndex(FIELD_COMPANY_NAME)));
                company.setLegalAddress(cursor.getString(cursor.getColumnIndex(FIELD_COMPANY_LEGALADDRESS)));
                company.setLegalName(cursor.getString(cursor.getColumnIndex(FIELD_COMPANY_LEGALNAME)));
                company.setAddress(cursor.getString(cursor.getColumnIndex(FIELD_COMPANY_ADDRESS)));
                company.setEmail(cursor.getString(cursor.getColumnIndex(FIELD_COMPANY_NAME)));
                company.setiNN(cursor.getLong(cursor.getColumnIndex(FIELD_COMPANY_INN)));
                company.setkPP(cursor.getLong(cursor.getColumnIndex(FIELD_COMPANY_KPP)));
                company.setOwnershipType(cursor.getString(cursor.getColumnIndex(FIELD_COMPANY_OWNERSHIPTYPE)));
                company.setPhone(cursor.getString(cursor.getColumnIndex(FIELD_COMPANY_PHONE)));
                company.setWithVAT(cursor.getInt(cursor.getColumnIndex(FIELD_COMPANY_WITHVAT))==1);
                company.setApproved(cursor.getInt(cursor.getColumnIndex(FIELD_COMPANY_ISAPPROVED))==1);
                company.isCustomer(cursor.getInt(cursor.getColumnIndex(FIELD_COMPANY_ISCUSTOMER))==1);
                company.isSupplier(cursor.getInt(cursor.getColumnIndex(FIELD_COMPANY_ISSUPPLIER))==1);
                company.setDeleted(cursor.getInt(cursor.getColumnIndex(FIELD_COMPANY_ISDELETED))==1);
                company.setSelected(cursor.getInt(cursor.getColumnIndex(FIELD_COMPANY_ISSELECTED))==1);
                company.setCode(cursor.getInt(cursor.getColumnIndex(FIELD_COMPANY_CODE)));
                company.setRating(cursor.getFloat(cursor.getColumnIndex(FIELD_COMPANY_RATING)));
                company.hasRights(cursor.getInt(cursor.getColumnIndex(FIELD_COMPANY_HASRIGHTS))==1);
                company.setModifyDate(cursor.getLong(cursor.getColumnIndex(FIELD_COMPANY_MODIFYDATE)));
                company.setRegionCode(cursor.getString(cursor.getColumnIndex(FIELD_COMPANY_REGIONCODE)));

                list.add(company);
            }

            cursor.close();
        }

        return list;

    }

    public static void update(Company[] companies){

        if (companies == null) return;

        String sqlCompany = "INSERT OR REPLACE INTO " + TABLE_NAME + " ("+
                FIELD_COMPANY_ID + "," +
                FIELD_COMPANY_NAME + "," +
                FIELD_COMPANY_LEGALADDRESS + "," +
                FIELD_COMPANY_LEGALNAME + "," +
                FIELD_COMPANY_ADDRESS  + ","+
                FIELD_COMPANY_EMAIL  + ","+
                FIELD_COMPANY_INN  + ","+
                FIELD_COMPANY_KPP  + ","+
                FIELD_COMPANY_OWNERSHIPTYPE  + ","+
                FIELD_COMPANY_PHONE  + ","+
                FIELD_COMPANY_WITHVAT  + ","+
                FIELD_COMPANY_ISAPPROVED  + ","+
                FIELD_COMPANY_ISCUSTOMER  + ","+
                FIELD_COMPANY_ISSUPPLIER  + ","+
                FIELD_COMPANY_ISDELETED  + ","+
                FIELD_COMPANY_ISSELECTED  + ","+
                FIELD_COMPANY_CODE  + ","+
                FIELD_COMPANY_RATING  + ","+
                FIELD_COMPANY_HASRIGHTS  + ","+
                FIELD_COMPANY_MODIFYDATE  + ","+
                FIELD_COMPANY_REGIONCODE  +
                ")"+
                " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

       try {

            getWriteDb().beginTransactionNonExclusive();

            SQLiteStatement stCompany = getWriteDb().compileStatement(sqlCompany);

            for (Company company : companies) {
                stCompany.bindString(1, company.getId());
                stCompany.bindString(2, company.getName());
                stCompany.bindString(3, company.getLegalAddress());
                stCompany.bindString(4, company.getLegalName());
                stCompany.bindString(5, company.getAddress());
                stCompany.bindString(6, company.getEmail());
                stCompany.bindLong(7, company.getiNN());
                stCompany.bindLong(8, company.getkPP());
                stCompany.bindString(9, company.getOwnershipType());
                stCompany.bindString(10, company.getPhone());
                stCompany.bindLong(11, (company.getWithVAT() ? 1:0));
                stCompany.bindLong(12, (company.getApproved() ? 1:0));
                stCompany.bindLong(13, (company.isCustomer() ? 1:0));
                stCompany.bindLong(14, (company.isSupplier() ? 1:0));
                stCompany.bindLong(15, (company.getDeleted() ? 1:0));
                stCompany.bindLong(16, (company.isSelected() ? 1:0));

                stCompany.bindLong(17, company.getCode());
                stCompany.bindDouble(18, company.getRating());
                stCompany.bindLong(19, (company.hasRights() ? 1:0));
                stCompany.bindLong(20, company.getModifyDate());
                stCompany.bindString(21, company.getRegionCode());

                stCompany.execute();
                stCompany.clearBindings();

                for (Department department : company.getDepartments()) {

                    department.setCompanyId(company.getId());
                    department.setIsCustomer(company.isCustomer());
                    department.setIsSupplier(company.isSupplier());

                }

                SQLDepartment.update(company.getDepartments());
            }

            getWriteDb().setTransactionSuccessful();

        } catch (Exception e) {
            LogUtils.debugErrorLog(LOG_PREFIX, e);
        } finally {
            getWriteDb().endTransaction();
        }    }

    public static void createTable() {
        SQLDepartment.createTable();
        SQLUtils.execSQL(CREATE_TABLE_SQL);
    }

    public static void dropTable() {
        SQLDepartment.dropTable();
        SQLUtils.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME + "; ");
    }
}
