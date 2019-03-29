package ru.wdsoft.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import ru.wdsoft.api.models.CancelationReason;
import ru.wdsoft.api.models.CapacityClass;
import ru.wdsoft.api.models.Company;
import ru.wdsoft.api.models.Country;
import ru.wdsoft.api.models.DocumentType;
import ru.wdsoft.api.models.Locality;
import ru.wdsoft.api.models.Order;
import ru.wdsoft.api.models.OrderState;
import ru.wdsoft.api.models.OrderType;
import ru.wdsoft.api.models.OwnershipType;
import ru.wdsoft.api.models.PriorityType;
import ru.wdsoft.api.models.Vehicle;
import ru.wdsoft.api.models.VehicleBrand;
import ru.wdsoft.api.models.VehicleCondition;
import ru.wdsoft.api.models.VehicleDocumentType;
import ru.wdsoft.api.models.VehicleModel;
import ru.wdsoft.api.models.VehicleType;
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
import ru.wdsoft.main.WDData;
import ru.wdsoft.utils.LogUtils;

/**
 * Created by slaventii@mail.ru on 09.11.2017.
 */
public class WDDbHelper extends SQLiteOpenHelper{

    /**
     * Для логирования
     */
    private static final String LOG_PREFIX = "WDDbHelper -- ";


    /**
     * Объявление БД
     */
    private final static int DATABASE_VERSION = 1;

    private final static String DATABASE_NAME = "tfort";

    private boolean mUpdateNeeded = false;
    private boolean mCreated = false;

    private Context mCxt;
//    private SQLiteDatabase mWriteDb;
//    private SQLiteDatabase mReadDb;

//    private SQLBase mPriorityType;

    /**
     * СТРУКТУРА. ТАБЛИЦЫ БД.
     */

    private static WDDbHelper dbInstance = null;

    private WDDbHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        mCxt = context;

        // создание базы данных (если небыла создана ранее)
//        getWriteDb();

//        SQLPriorityType.init(this);

//        mPriorityType = new SQLPriorityType(this);
    }

    public static WDDbHelper getInstance(Context context) {

        if (dbInstance == null) {
            dbInstance = new WDDbHelper(context.getApplicationContext());
            dbInstance.getWritableDatabase();
//            dbInstance.getReadableDatabase();
        }

        return dbInstance;
    }

    public static WDDbHelper getInstance(){
        return dbInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        LogUtils.debugLog(LOG_PREFIX, " --- database create ---");
        mCreated = true;

//        try {
//
//            db.beginTransaction();
//
//            createDatabaseTables();
//
//            db.setTransactionSuccessful();
//
//        }catch (Exception e){
//            LogUtils.debugErrorLog(LOG_PREFIX, e);
//
//        } finally {
//            db.endTransaction();
//        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        LogUtils.debugLog(LOG_PREFIX, " --- database update --- from ver."
                + String.valueOf(oldVersion) + " to ver." + String.valueOf(newVersion));

        mUpdateNeeded = true;

//        getData().setDbUpdateNeeded(mCxt, true);
//
//        try {
//
//            db.beginTransaction();
//
//            createDatabaseTables();
//
//            db.setTransactionSuccessful();
//
//        } finally {
//            db.endTransaction();
//        }

    }

    public static boolean needUpdate(){
        if (dbInstance == null){
            return false;
        } else {
            return dbInstance.mUpdateNeeded;
        }
    }

    public static boolean isCreated(){
        if (dbInstance == null){
            return false;
        } else {
            return dbInstance.mCreated;
        }
    }

    /**
     * СОЗДАНИЕ / ОБНОВЛЕНИЕ БАЗЫ
     */

//    private void dropDatabaseTables(){
//        SQLPriorityType.dropTable();
//        SQLCancelationReason.dropTable();
//        SQLCapacityClass.dropTable();
//        SQLVehicleType.dropTable();
//        SQLVehicleBrand.dropTable();
//        SQLVehicleDocType.dropTable();
//        SQLVehicleModel.dropTable();
//        SQLCompany.dropTable();
//        SQLVehicle.dropTable();
//        SQLVehicleCondition.dropTable();
//        SQLDocumentType.dropTable();
//        SQLCountry.dropTable();
//        SQLLocality.dropTable();
//        SQLOwnershipType.dropTable();
//        SQLOrder.dropTable();
//        SQLOrderType.dropTable();
//        SQLOrderState.dropTable();
//    }
//
//    private void createDatabaseTables(){
//        dropDatabaseTables();
//
//        SQLPriorityType.createTable();
//        SQLCancelationReason.createTable();
//        SQLCapacityClass.createTable();
//        SQLVehicleType.createTable();
//        SQLVehicleBrand.createTable();
//        SQLVehicleDocType.createTable();
//        SQLVehicleModel.createTable();
//        SQLCompany.createTable();
//        SQLVehicle.createTable();
//        SQLVehicleCondition.createTable();
//        SQLDocumentType.createTable();
//        SQLCountry.createTable();
//        SQLLocality.createTable();
//        SQLOwnershipType.createTable();
//        SQLOrder.createTable();
//        SQLOrderType.createTable();
//        SQLOrderState.createTable();
//    }

//    public void CleanDB(){
//
//        try {
//
//            getWriteDb().beginTransaction();
//
//            createDatabaseTables();
//
//            getWriteDb().setTransactionSuccessful();
//
//        }catch (Exception e){
//            LogUtils.debugErrorLog(LOG_PREFIX, e);
//        } finally {
//            getWriteDb().endTransaction();
//        }
//
//    }

    public static SQLiteDatabase getWriteDb(){

        return dbInstance.getWritableDatabase();

//        if (dbInstance.mWriteDb == null) {
//            dbInstance.mWriteDb = dbInstance.getWritableDatabase();
//        }
//        return dbInstance.mWriteDb;
    }

    public static SQLiteDatabase getReadDb(){

        return dbInstance.getReadableDatabase();

//        if (dbInstance.mReadDb == null) {
//            dbInstance.mReadDb = dbInstance.getReadableDatabase();
//        }
//        return dbInstance.mReadDb;
    }


    /**
     * ОБЩИЕ ФУНКЦИИ
     */

    public void updateStatistic(){

//        String sql = "ANALYZE " + TABLE_COMPANY + "; ";
//        getWriteDb().execSQL(sql);
//
//        sql = "ANALYZE " + TABLE_VEHICLE + "; ";
//        getWriteDb().execSQL(sql);
//
//        sql = "ANALYZE " + TABLE_VEHICLEMODEL + "; ";
//        getWriteDb().execSQL(sql);
//
//        sql = "ANALYZE " + TABLE_WORKTYPE + "; ";
//        getWriteDb().execSQL(sql);

    }

    private void execSQL(String sql){

        try {

            LogUtils.debugLog(LOG_PREFIX, sql);

            getWriteDb().beginTransaction();

            getWriteDb().execSQL(sql);

            getWriteDb().setTransactionSuccessful();

        } catch (Exception e) {

            getWriteDb().endTransaction();

            LogUtils.debugErrorLog(LOG_PREFIX, e);

        } finally {
            getWriteDb().endTransaction();
        }

    }

    public void update(PriorityType[] priorityTypes){
        SQLPriorityType.update(priorityTypes);
    }

    public void update(CancelationReason[] cancelationReasons){
        SQLCancelationReason.update(cancelationReasons);
    }

    public void update(CapacityClass[] capacityClasses){
        SQLCapacityClass.update(capacityClasses);
    }

    public void update(VehicleType[] vehicleTypes){
        SQLVehicleType.update(vehicleTypes);
    }

    public void update(VehicleBrand[] vehicleBrands){
        SQLVehicleBrand.update(vehicleBrands);
    }

    public void update(VehicleModel[] vehicleModels){
        SQLVehicleModel.update(vehicleModels);
    }

    public void update(VehicleDocumentType[] vehicleDocumentTypes){
        SQLVehicleDocType.update(vehicleDocumentTypes);
    }

    public void update(Company[] companies){
        SQLCompany.update(companies);
    }

    public void update(Vehicle[] vehicles){
        SQLVehicle.update(vehicles);
    }

    public void update(VehicleCondition[] vehicleConditions){
        SQLVehicleCondition.update(vehicleConditions);
    }

    public void update(DocumentType[] documentTypes){
        SQLDocumentType.update(documentTypes);
    }

    public void update(Country[] countries){
        SQLCountry.update(countries);
    }

    public void update(Locality[] localities){
        SQLLocality.update(localities);
    }

    public void update(OwnershipType[] ownershipTypes){
        SQLOwnershipType.update(ownershipTypes);
    }

    public void update(OrderType[] orderTypes){
        SQLOrderType.update(orderTypes);
    }

    public void update(OrderState[] orderStates){
        SQLOrderState.update(orderStates);
    }

    public void update(Order[] orders){
        SQLOrder.update(orders);
    }

    private WDData getData(){
        return WDData.getInstance();
    }

}


