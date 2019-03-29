package ru.wdsoft.db.tables;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;

import ru.wdsoft.api.ApiSerializable;
import ru.wdsoft.api.models.Company;
import ru.wdsoft.api.models.Department;
import ru.wdsoft.api.models.VehicleModel;
import ru.wdsoft.db.SQLUtils;
import ru.wdsoft.utils.LogUtils;

/**
 * Created by slaventii@mail.ru on 20.02.2019.
 */
public class SQLDepartment extends SQLBase {

    private final static String LOG_PREFIX = "SQLDepartment  -- ";

    public static final String TABLE_NAME = "DEPARTMENT";

    public static final String FIELD_DEPARTMENT_ID = "department_id";
    public static final String FIELD_DEPARTMENT_PARENTID = "department_parentid";
    public static final String FIELD_DEPARTMENT_COMPANYID = "department_companyid";
    public static final String FIELD_DEPARTMENT_NAME = "department_name";
    public static final String FIELD_DEPARTMENT_ISDELETED = "department_isdeleted";

    public static final String FIELD_DEPARTMENT_HASCHILDS = "department_haschilds";
    public static final String FIELD_DEPARTMENT_ISUNIT = "department_isunit";
    public static final String FIELD_DEPARTMENT_MOTORCADEID = "department_motorcadeid";
    public static final String FIELD_DEPARTMENT_MOTORCADENAME = "department_motorcadename";
    public static final String FIELD_DEPARTMENT_ISSELECTED = "department_isselected";
    public static final String FIELD_DEPARTMENT_HASRIGHTS = "department_hasrights";
    public static final String FIELD_DEPARTMENT_ISCUSTOMER = "department_iscustomer";
    public static final String FIELD_DEPARTMENT_ISSUPPLIER = "department_issupplier";

    private static final String CREATE_TABLE_SQL = "CREATE TABLE " + TABLE_NAME + " (" +
            FIELD_DEPARTMENT_ID + " text primary key," +
            FIELD_DEPARTMENT_PARENTID + " text," +
            FIELD_DEPARTMENT_COMPANYID + " text," +
            FIELD_DEPARTMENT_NAME + " text," +
            FIELD_DEPARTMENT_HASCHILDS + " integer," +
            FIELD_DEPARTMENT_ISUNIT + " integer," +
            FIELD_DEPARTMENT_MOTORCADEID + " text," +
            FIELD_DEPARTMENT_MOTORCADENAME + " text," +
            FIELD_DEPARTMENT_ISSELECTED + " integer," +
            FIELD_DEPARTMENT_ISDELETED + " integer," +
            FIELD_DEPARTMENT_HASRIGHTS + " integer," +
            FIELD_DEPARTMENT_ISCUSTOMER + " integer," +
            FIELD_DEPARTMENT_ISSUPPLIER + " integer" +
            ");";


    public static void update(Department[] departments){

        if (departments == null) return;

        String sqlDepartament = "INSERT OR REPLACE INTO " + TABLE_NAME + " ("+
                FIELD_DEPARTMENT_ID + "," +
                FIELD_DEPARTMENT_PARENTID + "," +
                FIELD_DEPARTMENT_COMPANYID + "," +
                FIELD_DEPARTMENT_NAME + "," +
                FIELD_DEPARTMENT_ISDELETED + "," +
                FIELD_DEPARTMENT_HASCHILDS + "," +
                FIELD_DEPARTMENT_ISUNIT + "," +
                FIELD_DEPARTMENT_MOTORCADEID + "," +
                FIELD_DEPARTMENT_MOTORCADENAME + "," +
                FIELD_DEPARTMENT_ISSELECTED + "," +
                FIELD_DEPARTMENT_HASRIGHTS + "," +
                FIELD_DEPARTMENT_ISCUSTOMER + "," +
                FIELD_DEPARTMENT_ISSUPPLIER +
                ")" +
                " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";

        SQLiteStatement stDepartament = getWriteDb().compileStatement(sqlDepartament);

        for (Department department : departments) {
            //    private Locality[] Localities;

            stDepartament.bindString(1, department.getId());
            stDepartament.bindString(2, department.getParentId());
            stDepartament.bindString(3, department.getCompanyId());
            stDepartament.bindString(4, department.getName());
            stDepartament.bindLong(5, (department.isDeleted() ? 1:0));
            stDepartament.bindLong(6, (department.hasChilds() ? 1:0));
            stDepartament.bindLong(7, (department.isUnit() ? 1:0));
            stDepartament.bindString(8, department.getMotorCadeId());
            stDepartament.bindString(9, department.getMotorCadeName());
            stDepartament.bindLong(10, (department.isSelected() ? 1:0));
            stDepartament.bindLong(11, (department.hasRights() ? 1:0));
            stDepartament.bindLong(12, (department.isCustomer() ? 1:0));
            stDepartament.bindLong(13, (department.isSupplier() ? 1:0));

            stDepartament.execute();
            stDepartament.clearBindings();

        }

    }

    public static ApiSerializable get(String id){

        String filter = FIELD_DEPARTMENT_ID + "='" + id + "'";

        ArrayList<ApiSerializable> list = getList(filter);

        if (list != null && list.size() > 0){
            return list.get(0);
        }

        return null;
    }

    public static ArrayList<ApiSerializable> getList(String filter){

        Cursor cursor = SQLUtils.getNomenclature(TABLE_NAME, FIELD_DEPARTMENT_ID,
                FIELD_DEPARTMENT_NAME, "", filter, 0, "", "");

        return getList(cursor);

    }

    public static ArrayList<ApiSerializable> getList(Cursor cursor){

        ArrayList<ApiSerializable> list = null;

        if (cursor != null){

            list = new ArrayList<>();

            while (cursor.moveToNext()){

                Department department = new Department();
                department.setCompanyId(cursor.getString(cursor.getColumnIndex(FIELD_DEPARTMENT_COMPANYID)));
                department.setHasChilds(cursor.getLong(cursor.getColumnIndex(FIELD_DEPARTMENT_HASCHILDS))== 1);
                department.setId(cursor.getString(cursor.getColumnIndex(FIELD_DEPARTMENT_ID)));
                department.setMotorCadeId(cursor.getString(cursor.getColumnIndex(FIELD_DEPARTMENT_MOTORCADEID)));
                department.setMotorCadeName(cursor.getString(cursor.getColumnIndex(FIELD_DEPARTMENT_MOTORCADENAME)));
                department.setName(cursor.getString(cursor.getColumnIndex(FIELD_DEPARTMENT_NAME)));
                department.setParentId(cursor.getString(cursor.getColumnIndex(FIELD_DEPARTMENT_PARENTID)));
                department.setSelected(cursor.getLong(cursor.getColumnIndex(FIELD_DEPARTMENT_ISSELECTED))== 1);
                department.setUnit(cursor.getLong(cursor.getColumnIndex(FIELD_DEPARTMENT_ISUNIT))== 1);
                department.hasRights(cursor.getLong(cursor.getColumnIndex(FIELD_DEPARTMENT_HASRIGHTS))== 1);
                department.setIsCustomer(cursor.getLong(cursor.getColumnIndex(FIELD_DEPARTMENT_ISCUSTOMER))== 1);
                department.setIsSupplier(cursor.getLong(cursor.getColumnIndex(FIELD_DEPARTMENT_ISSUPPLIER))== 1);

                list.add(department);
            }

            cursor.close();
        }

        return list;

    }

    public static void createTable() {
        SQLUtils.execSQL(CREATE_TABLE_SQL);
    }

    public static void dropTable() {
        SQLUtils.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME + "; ");
    }
}
