package ru.wdsoft.db;

import ru.wdsoft.db.tables.SQLCancelationReason;
import ru.wdsoft.db.tables.SQLCapacityClass;
import ru.wdsoft.db.tables.SQLCompany;
import ru.wdsoft.db.tables.SQLCountry;
import ru.wdsoft.db.tables.SQLDocumentType;
import ru.wdsoft.db.tables.SQLLocality;
import ru.wdsoft.db.tables.SQLOrder;
import ru.wdsoft.db.tables.SQLOrderState;
import ru.wdsoft.db.tables.SQLOrderType;
import ru.wdsoft.db.tables.SQLOwnershipType;
import ru.wdsoft.db.tables.SQLPriorityType;
import ru.wdsoft.db.tables.SQLVehicle;
import ru.wdsoft.db.tables.SQLVehicleBrand;
import ru.wdsoft.db.tables.SQLVehicleCondition;
import ru.wdsoft.db.tables.SQLVehicleDocType;
import ru.wdsoft.db.tables.SQLVehicleModel;
import ru.wdsoft.db.tables.SQLVehicleType;
import ru.wdsoft.utils.LogUtils;

/**
 * Created by slaventii@mail.ru on 12.03.2019.
 */
public class DBManager {

    /**
     * Для логирования
     */
    private static final String LOG_PREFIX = "DBManager -- ";

    public static void doDbCreate(){

        if (WDDbHelper.getWriteDb() == null) return;

        try {

            WDDbHelper.getWriteDb().beginTransaction();

            createDatabaseTables();

            WDDbHelper.getWriteDb().setTransactionSuccessful();

        }catch (Exception e){
            LogUtils.debugErrorLog(LOG_PREFIX, e);

        } finally {
            WDDbHelper.getWriteDb().endTransaction();
        }

    }

    private static void createDatabaseTables(){

        dropDatabaseTables();

        SQLPriorityType.createTable();
        SQLCancelationReason.createTable();
        SQLCapacityClass.createTable();
        SQLVehicleType.createTable();
        SQLVehicleBrand.createTable();
        SQLVehicleDocType.createTable();
        SQLVehicleModel.createTable();
        SQLCompany.createTable();
        SQLVehicle.createTable();
        SQLVehicleCondition.createTable();
        SQLDocumentType.createTable();
        SQLCountry.createTable();
        SQLLocality.createTable();
        SQLOwnershipType.createTable();
        SQLOrder.createTable();
        SQLOrderType.createTable();
        SQLOrderState.createTable();

    }

    private static void dropDatabaseTables(){

        SQLPriorityType.dropTable();
        SQLCancelationReason.dropTable();
        SQLCapacityClass.dropTable();
        SQLVehicleType.dropTable();
        SQLVehicleBrand.dropTable();
        SQLVehicleDocType.dropTable();
        SQLVehicleModel.dropTable();
        SQLCompany.dropTable();
        SQLVehicle.dropTable();
        SQLVehicleCondition.dropTable();
        SQLDocumentType.dropTable();
        SQLCountry.dropTable();
        SQLLocality.dropTable();
        SQLOwnershipType.dropTable();
        SQLOrder.dropTable();
        SQLOrderType.dropTable();
        SQLOrderState.dropTable();
    }



}
