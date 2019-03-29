package ru.wdsoft.ui.field;

import ru.wdsoft.api.ApiSerializable;
import ru.wdsoft.api.models.Department;
import ru.wdsoft.api.models.Vehicle;
import ru.wdsoft.api.models.VehicleType;
import ru.wdsoft.db.SQLUtils;
import ru.wdsoft.db.tables.SQLCompany;
import ru.wdsoft.db.tables.SQLDepartment;
import ru.wdsoft.db.tables.SQLOrderState;
import ru.wdsoft.db.tables.SQLOrderType;
import ru.wdsoft.db.tables.SQLVehicle;
import ru.wdsoft.db.tables.SQLVehicleModel;
import ru.wdsoft.db.tables.SQLVehicleType;
import ru.wdsoft.ui.datafield.IUIFieldDataModel;
import ru.wdsoft.ui.datafield.SQLFieldDataModel;
import ru.wdsoft.utils.Utils;

/**
 * Created by slaventii@mail.ru on 15.06.2018.
 */
public class SQLModel extends SQLFieldDataModel {

    public SQLModel(String sql, String filter, String sqlSelectedItem) {
        super(sql, filter, sqlSelectedItem);
    }


    public static IUIFieldDataModel OrderState() {

        String sql = SQLUtils.getNomenclatureSql(SQLOrderState.TABLE_NAME,
                SQLOrderState.FIELD_ORDERSTATE_STATE, SQLOrderState.FIELD_ORDERSTATE_NAME);

        String sqlFilter = SQLOrderState.FIELD_ORDERSTATE_ISDELETED + " = 0";

        DataFilter dataFilter = new DataFilter() {
            @Override
            public String updateDataFilter(String[] params) {
                return null;
            }

            @Override
            public String getNoDataFilter() {
                return null;
            }

            @Override
            public String updateItemFilter(String[] params) {
                return null;
            }
        };

        IUIFieldDataModel fieldDataModel = new SQLModel(sql, sqlFilter, null);

        fieldDataModel.setDataFilter(dataFilter);

        return fieldDataModel;
    }

    public static IUIFieldDataModel OrderType(String id) {

        String sql = SQLUtils.getNomenclatureSql(SQLOrderType.TABLE_NAME,
                SQLOrderType.FIELD_ORDERTYPE_CODE, SQLOrderType.FIELD_ORDERTYPE_NAME);

        String sqlFilter = SQLOrderType.FIELD_ORDERTYPE_ISDELETED + " = 0";

        String sqlSelectedItem = null;

        if (Utils.stringsNotEmpty(id)) {

            sqlSelectedItem = "_id = '" + id + "'";

        }

        DataFilter dataFilter = new DataFilter() {
            @Override
            public String updateDataFilter(String[] params) {
                return null;
            }

            @Override
            public String getNoDataFilter() {
                return null;
            }

            @Override
            public String updateItemFilter(String[] params) {

                if (params != null){
                    String VehicleId = params[0];
                    VehicleType vehicleType = (VehicleType) SQLVehicleType.get(VehicleId);
                    if (vehicleType != null){
                        return "_id = '" + vehicleType.getOrderType()+ "'";
                    }
                }

                return null;
            }
        };

        IUIFieldDataModel fieldDataModel = new SQLModel(sql, sqlFilter, sqlSelectedItem);

        fieldDataModel.setDataFilter(dataFilter);

        return fieldDataModel;
    }


    public static IUIFieldDataModel VehicleType(String id){

        String sql = SQLUtils.getNomenclatureSql(SQLVehicleType.TABLE_NAME,
                SQLVehicleType.FIELD_VEHICLETYPE_ID,
                SQLVehicleType.FIELD_VEHICLETYPE_NAME);

        String sqlFilter = SQLVehicleType.FIELD_VEHICLETYPE_ISDELETED + " = 0";

        String sqlSelectedItem = null;

        if (Utils.stringsNotEmpty(id)){

            sqlSelectedItem = "_id = '" + id + "'";

        }

        DataFilter dataFilter = new DataFilter() {
            @Override
            public String updateDataFilter(String[] params) {

                String filter = SQLVehicleType.FIELD_VEHICLETYPE_ISDELETED + "=0";

                if (params != null){

                    filter = SQLVehicleType.FIELD_VEHICLETYPE_ORDERTYPE + "='" + params[0] + "' AND " +
                            SQLVehicleType.FIELD_VEHICLETYPE_ISDELETED + "=0" ;
                }

                return filter;

            }

            @Override
            public String getNoDataFilter() {
                return null;
            }

            @Override
            public String updateItemFilter(String[] params) {

                if (params != null){
                    String id = params[0];
                    Vehicle vehicle = (Vehicle)SQLVehicle.get(id);
                    if (vehicle != null){
                        String sqlSelectedItem = "_id = '" + vehicle.getVehicleTypeId() + "'";
                        return sqlSelectedItem;
                    }
                }

                return null;
            }
        };

        IUIFieldDataModel fieldDataModel = new SQLModel(sql, sqlFilter, sqlSelectedItem);

        fieldDataModel.setDataFilter(dataFilter);

        return fieldDataModel;
    }

    public static IUIFieldDataModel VehicleModel(String id){

        String sql = SQLUtils.getNomenclatureSql(SQLVehicleModel.TABLE_NAME,
                SQLVehicleModel.FIELD_VMODEL_ID,
                SQLVehicleModel.FIELD_VMODEL_NAME);

        String sqlFilter = SQLVehicleModel.FIELD_VMODEL_ISDELETED + " = 0";

        String sqlSelectedItem = null;

        if (Utils.stringsNotEmpty(id)){

            sqlSelectedItem = "_id = '" + id + "'";

        }

        return new SQLModel(sql, sqlFilter, sqlSelectedItem);
    }

    public static IUIFieldDataModel Customer(String id){

        String sql = SQLUtils.getNomenclatureSql(SQLCompany.TABLE_NAME, SQLCompany.FIELD_COMPANY_ID,
                SQLCompany.FIELD_COMPANY_NAME);

        String sqlFilter = SQLCompany.FIELD_COMPANY_ISCUSTOMER + "= 1 AND "
                + SQLCompany.FIELD_COMPANY_ISDELETED + " =0";

        String sqlSelectedItem = null;

        if (Utils.stringsNotEmpty(id)){
            sqlSelectedItem = "_id = '" + id + "'";
        }

        DataFilter dataFilter = new DataFilter() {
            @Override
            public String updateDataFilter(String[] params) {
                return null;
            }

            @Override
            public String getNoDataFilter() {
                return null;
            }

            @Override
            public String updateItemFilter(String[] params) {
                if (params != null){
                    String id = params[0];
                    Department department = (Department)SQLDepartment.get(id);
                    if (department != null){
                        String sqlSelectedItem = "_id = '" + department.getCompanyId()+ "'";
                        return sqlSelectedItem;
                    }
                }
                return null;
            }
        };

        IUIFieldDataModel fieldDataModel = new SQLModel(sql, sqlFilter, sqlSelectedItem);

        fieldDataModel.setDataFilter(dataFilter);

        return fieldDataModel;
    }

    public static IUIFieldDataModel Department(String id_customer, String id){

        String sql = SQLUtils.getNomenclatureSql(SQLDepartment.TABLE_NAME,
                SQLDepartment.FIELD_DEPARTMENT_ID,
                SQLDepartment.FIELD_DEPARTMENT_NAME);

        String sqlFilter = null;

        if (Utils.stringsNotEmpty(id_customer)){
            sqlFilter = SQLDepartment.FIELD_DEPARTMENT_COMPANYID + "='" + id_customer + "' AND " +
                    SQLDepartment.FIELD_DEPARTMENT_ISDELETED + "=0";
        }

        String sqlSelectedItem = null;

        if (Utils.stringsNotEmpty(id)){

            sqlSelectedItem = "_id = '" + id + "'";

        }

        DataFilter dataFilter = new DataFilter() {
            @Override
            public String updateDataFilter(String[] params) {
                if (params == null){
                    return SQLDepartment.FIELD_DEPARTMENT_ISCUSTOMER + "=1 AND "
                            + SQLDepartment.FIELD_DEPARTMENT_ISDELETED + "=0";
                } else {
                    return SQLDepartment.FIELD_DEPARTMENT_ISCUSTOMER + "=1 AND "
                            + SQLDepartment.FIELD_DEPARTMENT_COMPANYID + "='" + params[0] + "'";
                }
            }

            @Override
            public String getNoDataFilter() {
                return null;
            }

            @Override
            public String updateItemFilter(String[] params) {
                return null;
            }
        };

        IUIFieldDataModel fieldDataModel = new SQLModel(sql, sqlFilter, sqlSelectedItem);

        fieldDataModel.setDataFilter(dataFilter);

        return fieldDataModel;
    }


//    public static IUIFieldDataModel Priority(Context ctx, String id){
//
//        String sql = getDb(ctx).getNomenclatureSql(TABLE_PRIORITY,
//                FIELD_PRIORITY_ID, FIELD_PRIORITY_NAME);
//
//        String sqlFilter = FIELD_PRIORITY_ISDELETED + " = 0";
//
//        String sqlSelectedItem = null;
//
//        if (Utils.stringsNotEmpty(id)){
//
//            sqlSelectedItem = "_id = '" + id + "'";
//
//        }
//
//        return new SQLModel(ctx, sql, sqlFilter, sqlSelectedItem);
//    }
//


//    public static IUIFieldDataModel OrderState(Context ctx, String id) {
//
//        String sql = getDb(ctx).getNomenclatureSql(TABLE_ORDER_STATE, FIELD_ORDSTATE_CODE,
//                FIELD_ORDSTATE_NAME);
//
//        String sqlFilter = FIELD_ORDSTATE_ISDELETED + " = 0";
//
//        String sqlSelectedItem = null;
//
//        if (Utils.stringsNotEmpty(id)) {
//            sqlSelectedItem = "_id = '" + id + "'";
//        }
//
//        return new SQLModel(ctx, sql, sqlFilter, sqlSelectedItem);
//    }
//
//    public static IUIFieldDataModel OrgDep(Context ctx, String id){
//
//        String sql = getDb(ctx).getNomenclatureSql(TABLE_DEPARTMENT, FIELD_DEPARTMENT_ID,
//                FIELD_DEPARTMENT_NAME);
//
//        String sqlFilter = FIELD_DEPARTMENT_ISSUPPLIER + "=1 AND " +
//                FIELD_DEPARTMENT_ISDELETED + "=0";
//
//        String sqlSelectedItem;
//
//        if (Utils.stringsNotEmpty(id)){
//
//            sqlSelectedItem = "_id = '" + id + "'";
//
//        } else {
//            sqlSelectedItem = FIELD_DEPARTMENT_ISSUPPLIER + "=1 AND " + FIELD_DEPARTMENT_ISDELETED
//                    + " = 0 AND " + FIELD_DEPARTMENT_ISSELECTED + " = 1";
//        }
//
//        IUIFieldDataModel.DataFilter dataFilter = new IUIFieldDataModel.DataFilter() {
//            @Override
//            public String updateDataFilter(String[] params) {
//
//                String filter = FIELD_DEPARTMENT_ISSUPPLIER + "=1 AND "
//                        + FIELD_DEPARTMENT_ISDELETED + " = 0";
//
//                if (params != null){
//                    filter += " AND " + FIELD_DEPARTMENT_COMPANYID + "='" + params[0] + "'";
//                }
//
//                return filter;
//            }
//
//            @Override
//            public String getNoDataFilter() {
//                return null;
//            }
//
//            @Override
//            public String updateItemFilter(String[] params) {
//                return null;
//            }
//        };
//
//        IUIFieldDataModel fieldDataModel = new SQLModel(ctx, sql, sqlFilter, sqlSelectedItem);
//
//        fieldDataModel.setDataFilter(dataFilter);
//
//        return fieldDataModel;
//    }
//
//    public static IUIFieldDataModel Vehicle(final Context ctx, String id){
//
//        String sql = getDb(ctx).getNomenclatureSql(TABLE_VEHICLE, FIELD_VEHICLE_ID,
//                FIELD_VEHICLE_NAME);
//
//        String sqlFilter = FIELD_VEHICLE_ISDELETED + " = 0 AND " +
//                FIELD_VEHICLE_VEHICLETYPEID + "<>''";
//
//        String sqlSelectedItem = null;
//
//        if (Utils.stringsNotEmpty(id)){
//
//            sqlSelectedItem = "_id = '" + id + "'";
//
//        }
//
//        IUIFieldDataModel.DataFilter dataFilter = new IUIFieldDataModel.DataFilter() {
//            @Override
//            public String updateDataFilter(String[] params) {
//
//                String filter = FIELD_VEHICLE_ISDELETED + "=0";
//
//                if (params != null){
//
//                    if (params.length > 0) {
//                        String id_vtype = params[0];
//                        if (Utils.stringsNotEmpty(id_vtype)) {
//                            filter += " AND " + FIELD_VEHICLE_VEHICLETYPEID + "='" + id_vtype + "'";
//                        }
//                    }
//
//                    if (params.length > 1){
//
//                        String id_capacity = params[1];
//
//                        if (Utils.stringsNotEmpty(id_capacity)){
//                            CapacityClass capacityClass = getDb(ctx).getCapacityClass(id_capacity);
//                            if (capacityClass != null) {
//
//                                filter += " AND " + FIELD_VEHICLE_CAPACITY + ">=" +
//                                        String.valueOf(capacityClass.getFrom()) + " AND " +
//                                        FIELD_VEHICLE_CAPACITY + "<=" +
//                                        String.valueOf(capacityClass.getTo());
//                            }
//                        }
//                    }
//                }
//
//                return filter;
//
//            }
//
//            @Override
//            public String getNoDataFilter() {
//                return null;
//            }
//
//            @Override
//            public String updateItemFilter(String[] params) {
//                return null;
//            }
//        };
//
//        IUIFieldDataModel fieldDataModel = new SQLModel(ctx, sql, sqlFilter, sqlSelectedItem);
//
//        fieldDataModel.setDataFilter(dataFilter);
//
//        return fieldDataModel;
//    }
//
//
//    public static IUIFieldDataModel CapacityClass(final Context ctx, String id){
//
//        String sql = getDb(ctx).getNomenclatureSql(TABLE_CAPACITYCLASS, FIELD_CAPACITYCLASS_ID,
//                FIELD_CAPACITYCLASS_NAME);
//
//        String sqlFilter = FIELD_CAPACITYCLASS_ISDELETED + " = 0";
//
//        String sqlSelectedItem = null;
//
//        if (Utils.stringsNotEmpty(id)){
//            sqlSelectedItem = "_id = '" + id + "'";
//        }
//
//        IUIFieldDataModel.DataFilter dataFilter = new IUIFieldDataModel.DataFilter() {
//            @Override
//            public String updateDataFilter(String[] params) {
//                return null;
//            }
//
//            @Override
//            public String getNoDataFilter() {
//                return null;
//            }
//
//            @Override
//            public String updateItemFilter(String[] params) {
//                if (params != null){
//
//                    String id_vehicle = params[0];
//
//                    Vehicle vehicle = getDb(ctx).getVehicle(id_vehicle);
//
//                    if (vehicle != null) {
//                        String capacity = String.valueOf(vehicle.getCapacity());
//
//                        String sqlSelectedItem = capacity + ">=" + FIELD_CAPACITYCLASS_FROM +
//                                " AND " + capacity + "<=" + FIELD_CAPACITYCLASS_TO;
//                        return sqlSelectedItem;
//                    }
//                }
//                return null;
//            }
//        };
//
//        IUIFieldDataModel fieldDataModel = new SQLModel(ctx, sql, sqlFilter, sqlSelectedItem);
//
//        fieldDataModel.setDataFilter(dataFilter);
//
//        return fieldDataModel;
//    }
//

//
//    public static IUIFieldDataModel Organization(final Context ctx, String id){
//
//        String sql = getDb(ctx).getNomenclatureSql(TABLE_COMPANY, FIELD_COMPANY_ID,
//                FIELD_COMPANY_NAME);
//
//        String sqlFilter = FIELD_COMPANY_ISSUPPLIER + "= 1 AND " + FIELD_COMPANY_ISDELETED + " =0";
//
//        String sqlSelectedItem;
//
//        if (Utils.stringsNotEmpty(id)){
//
//            sqlSelectedItem = "_id = '" + id + "'";
//
//        } else {
//            sqlSelectedItem = FIELD_COMPANY_ISSUPPLIER + "= 1 AND " +
//                    FIELD_COMPANY_ISDELETED + " = 0 AND " +
//                    FIELD_COMPANY_ISSELECTED + " = 1";
//
//        }
//
//        return new SQLModel(ctx, sql, sqlFilter, sqlSelectedItem);
//    }
}
