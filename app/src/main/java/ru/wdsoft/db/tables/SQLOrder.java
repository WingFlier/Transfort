package ru.wdsoft.db.tables;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;

import ru.wdsoft.api.models.Offer;
import ru.wdsoft.api.models.Order;
import ru.wdsoft.api.models.OrderHistory;
import ru.wdsoft.api.models.Person;
import ru.wdsoft.db.SQLUtils;
import ru.wdsoft.utils.LogUtils;
import ru.wdsoft.utils.Utils;

/**
 * Created by slaventii@mail.ru on 21.02.2019.
 */
public class SQLOrder extends SQLBase {

    private final static String LOG_PREFIX = "SQLOrder  -- ";

    public static final String TABLE_NAME = "ORDERS";

//    private UUID Id;
//    private String Date;
//    private String DateD;
//    private String Group;
//    private Float Price;
//    private String State;
//    private String Number;
//    private Float Rating;
//    private UUID UserId;
//    private String Comment;
//    private Integer Mileage;
//    private UUID ModelId;
//    private Float VatRate;
//    private Boolean WithVAT;
//    private String BodyType;
//    private String Priority;
//    private Integer RentTime;
//    private String RentType;
//    private String UserName;
//    private String ExtNumber;
//    private Boolean IsDeleted;
//    private Boolean IsProtest;
//    private String ModelName;
//    private String OrderType;
//    private String RegNumber;
//    private String RentTimeD;
//    private String StateName;
//    private UUID VehicleId;
//    private String CreateDate;
//    private UUID LocalityId;
//    private String ModifyDate;
//    private Integer OfferCount;
//    private UUID PriorityId;
//    private Float CargoWeight;
//    private Integer CarrierCode;
//    private String RentAddress;
//    private String RentEndDate;
//    private Float TotalAmount;
//    private String VehicleName;
//    private String ConsumerType;
//    private String ContactPhone;
//    private String LocalityName;
//    private String PriorityName;
//    private String RentEndDateD;
//    private String ContactPerson;
//    private Integer EquipmentTime;
//    private String OrderTypeName;
//    private Float PlannedAmount;
//    private String RentStartDate;
//    private UUID VehicleTypeId;
//    private Integer CarrierMileage;
//    private Person CustomerPerson;
//    private Float CustomerRating;
//    private String RentStartDateD;
//    private Float VehicleKmPrice;
//    private UUID CapacityClassId;
//    private Integer CarrierRentTime;
//    private Integer CustomerMileage;
//    private String VehicleTypeName;
//    private UUID CarrierCompanyId;
//    private UUID CarrierManagerId;
//    private Integer CustomerRentTime;
//    private UUID CustomerCompanyId;
//    private String CarrierCompanyName;
//    private String CarrierManagerName;
//    private Float CarrierTotalAmount;
//    private Integer NumberOfPassengers;
//    private Float VehicleHourlyPrice;
//    private String CarrierCompanyEmail;
//    private String CarrierCompanyPhone;
//    private UUID CarrierDepartmentId;
//    private String CarrierManagerEmail;
//    private String CarrierManagerPhone;
//    private Integer CustomerCompanyCode;
//    private String CustomerCompanyName;
//    private Integer CarrierEquipmentTime;
//    private UUID CustomerDepartmentId;
//    private String CarrierDepartmentName;
//    private Integer CustomerEquipmentTime;
//    private String CustomerDepartmentName;

    public static final String FIELD_ORDER_ID = "order_id";
    public static final String FIELD_ORDER_DATE = "order_date";
    public static final String FIELD_ORDER_GROUP = "order_group";
    public static final String FIELD_ORDER_PRICE = "order_price";
    public static final String FIELD_ORDER_STATE = "order_state";
    public static final String FIELD_ORDER_NUMBER = "order_number";
    public static final String FIELD_ORDER_RATING = "order_rating";
    public static final String FIELD_ORDER_USERID = "order_userid";
    public static final String FIELD_ORDER_COMMENT = "order_comment";
    public static final String FIELD_ORDER_MILEAGE = "order_mileage";
    public static final String FIELD_ORDER_MODELID = "order_modelid";
    public static final String FIELD_ORDER_VATRATE = "order_vatrate";
    public static final String FIELD_ORDER_WITHVAT = "order_withvat";
    public static final String FIELD_ORDER_BODYTYPE = "order_bodytype";
    public static final String FIELD_ORDER_PRIORITY = "order_priority";
    public static final String FIELD_ORDER_RENTTIME = "order_renttime";
    public static final String FIELD_ORDER_RENTTYPE = "order_renttype";
    public static final String FIELD_ORDER_USERNAME = "order_username";
    public static final String FIELD_ORDER_EXTNUMBER = "order_extnumber";
    public static final String FIELD_ORDER_ISDELETED = "order_isdeleted";
    public static final String FIELD_ORDER_ISPROTEST = "order_isprotest";
    public static final String FIELD_ORDER_MODELNAME = "order_modelname";
    public static final String FIELD_ORDER_ORDERTYPE = "order_ordertype";
    public static final String FIELD_ORDER_REGNUMBER = "order_regnumber";
    public static final String FIELD_ORDER_STATENAME = "order_statename";
    public static final String FIELD_ORDER_VEHICLEID = "order_vehicleid";
    public static final String FIELD_ORDER_CREATEDATE = "order_createdate";
    public static final String FIELD_ORDER_LOCALITYID = "order_localityid";
    public static final String FIELD_ORDER_MODIFYDATE = "order_modifydate";
    public static final String FIELD_ORDER_OFFERCOUNT = "order_offercount";
    public static final String FIELD_ORDER_PRIORITYID = "order_priorityid";
    public static final String FIELD_ORDER_CARGOWEIGHT = "order_cargoweight";
    public static final String FIELD_ORDER_CARRIERCODE = "order_carriercode";
    public static final String FIELD_ORDER_RENTADDRESS = "order_rentaddress";
    public static final String FIELD_ORDER_RENTENDDATE = "order_rentenddate";
    public static final String FIELD_ORDER_TOTALAMOUNT = "order_totalamount";
    public static final String FIELD_ORDER_VEHICLENAME = "order_vehiclename";
    public static final String FIELD_ORDER_CONSUMERTYPE = "order_consumertype";
    public static final String FIELD_ORDER_CONTACTPHONE = "order_contactphone";
    public static final String FIELD_ORDER_LOCALITYNAME = "order_localityname";
    public static final String FIELD_ORDER_PRIORITYNAME = "order_priorityname";
    public static final String FIELD_ORDER_CONTACTPERSON = "order_contactperson";
    public static final String FIELD_ORDER_EQUIPMENTTIME = "order_equipmenttime";
    public static final String FIELD_ORDER_ORDERTYPENAME = "order_ordertypename";
    public static final String FIELD_ORDER_PLANNEDAMOUNT = "order_plannedamount";
    public static final String FIELD_ORDER_RENTSTARTDATE = "order_rentstartdate";
    public static final String FIELD_ORDER_VEHICLETYPEID = "order_vehicletypeid";
    public static final String FIELD_ORDER_CARRIERMILEAGE = "order_carriermileage";
//    public static final String FIELD_ORDER_CUSTOMERPERSON = "order_customerperson";
    public static final String FIELD_ORDER_CUSTOMERRATING = "order_customerrating";
    public static final String FIELD_ORDER_VEHICLEKMPRICE = "order_vehiclekmprice";
    public static final String FIELD_ORDER_CAPACITYCLASSID = "order_capacityclassid";
    public static final String FIELD_ORDER_CARRIERRENTTIME = "order_carrierrenttime";
    public static final String FIELD_ORDER_CUSTOMERMILEAGE = "order_customermileage";
    public static final String FIELD_ORDER_VEHICLETYPENAME = "order_vehicletypename";
    public static final String FIELD_ORDER_CARRIERCOMPANYID = "order_carriercompanyid";
    public static final String FIELD_ORDER_CARRIERMANAGERID = "order_carriermanagerid";
    public static final String FIELD_ORDER_CUSTOMERRENTTIME = "order_customerrenttime";
    public static final String FIELD_ORDER_CUSTOMERCOMPANYID = "order_customercompanyid";
    public static final String FIELD_ORDER_CARRIERCOMPANYNAME = "order_carriercompanyname";
    public static final String FIELD_ORDER_CARRIERMANAGERNAME = "order_carriermanagername";
    public static final String FIELD_ORDER_CARRIERTOTALAMOUNT = "order_carriertotalamount";
    public static final String FIELD_ORDER_NUMBEROFPASSENGERS = "order_numberofpassengers";
    public static final String FIELD_ORDER_VEHICLEHOURLYPRICE = "order_vehiclehourlyprice";
    public static final String FIELD_ORDER_CARRIERCOMPANYEMAIL = "order_carriercompanyemail";
    public static final String FIELD_ORDER_CARRIERCOMPANYPHONE = "order_carriercompanyphone";
    public static final String FIELD_ORDER_CARRIERDEPARTMENTID = "order_carrierdepartmentid";
    public static final String FIELD_ORDER_CARRIERMANAGEREMAIL = "order_carriermanageremail";
    public static final String FIELD_ORDER_CARRIERMANAGERPHONE = "order_carriermanagerphone";
    public static final String FIELD_ORDER_CUSTOMERCOMPANYCODE = "order_customercompanycode";
    public static final String FIELD_ORDER_CUSTOMERCOMPANYNAME = "order_customercompanyname";
    public static final String FIELD_ORDER_CARRIEREQUIPMENTTIME = "order_carrierequipmenttime";
    public static final String FIELD_ORDER_CUSTOMERDEPARTMENTID = "order_customerdepartmentid";
    public static final String FIELD_ORDER_CARRIERDEPARTMENTNAME = "order_carrierdepartmentname";
    public static final String FIELD_ORDER_CUSTOMEREQUIPMENTTIME = "order_customerequipmenttime";
    public static final String FIELD_ORDER_CUSTOMERDEPARTMENTNAME = "order_customerdepartmentname";
    public static final String FIELD_ORDER_INFORM = "order_inform";

    public static final String FIELD_ORDER_ISCHANGED = "order_ischanged";
    public static final String FIELD_ORDER_ISNEW = "order_isnew";

    private static final String CREATE_TABLE_SQL = "CREATE TABLE " + TABLE_NAME + " (" +
            FIELD_ORDER_ID + " text primary key," +
            FIELD_ORDER_DATE + " integer," +
            FIELD_ORDER_GROUP + " text," +
            FIELD_ORDER_PRICE + " real," +
            FIELD_ORDER_STATE + " text," +
            FIELD_ORDER_NUMBER + " text," +
            FIELD_ORDER_RATING + " real," +
            FIELD_ORDER_USERID + " text," +
            FIELD_ORDER_COMMENT + " text," +
            FIELD_ORDER_MILEAGE + " integer," +
            FIELD_ORDER_MODELID + " text," +
            FIELD_ORDER_VATRATE + " real," +
            FIELD_ORDER_WITHVAT + " integer," +
            FIELD_ORDER_BODYTYPE + " text," +
            FIELD_ORDER_PRIORITY + " text," +
            FIELD_ORDER_RENTTIME + " integer," +
            FIELD_ORDER_RENTTYPE + " text," +
            FIELD_ORDER_USERNAME + " text," +
            FIELD_ORDER_EXTNUMBER + " text," +
            FIELD_ORDER_ISDELETED + " integer," +
            FIELD_ORDER_ISPROTEST + " integer," +
            FIELD_ORDER_MODELNAME + " text," +
            FIELD_ORDER_ORDERTYPE + " text," +
            FIELD_ORDER_REGNUMBER + " text," +
            FIELD_ORDER_STATENAME + " text," +
            FIELD_ORDER_VEHICLEID + " text," +
            FIELD_ORDER_CREATEDATE + " integer," +
            FIELD_ORDER_LOCALITYID + " text," +
            FIELD_ORDER_MODIFYDATE + " integer," +
            FIELD_ORDER_OFFERCOUNT + " integer," +
            FIELD_ORDER_PRIORITYID + " text," +
            FIELD_ORDER_CARGOWEIGHT + " real," +
            FIELD_ORDER_CARRIERCODE + " integer," +
            FIELD_ORDER_RENTADDRESS + " text," +
            FIELD_ORDER_RENTENDDATE + " integer," +
            FIELD_ORDER_TOTALAMOUNT + " real," +
            FIELD_ORDER_VEHICLENAME + " text," +
            FIELD_ORDER_CONSUMERTYPE + " text," +
            FIELD_ORDER_CONTACTPHONE + " text," +
            FIELD_ORDER_LOCALITYNAME + " text," +
            FIELD_ORDER_PRIORITYNAME + " text," +
            FIELD_ORDER_CONTACTPERSON + " text," +
            FIELD_ORDER_EQUIPMENTTIME + " integer," +
            FIELD_ORDER_ORDERTYPENAME + " text," +
            FIELD_ORDER_PLANNEDAMOUNT + " real," +
            FIELD_ORDER_RENTSTARTDATE + " integer," +
            FIELD_ORDER_VEHICLETYPEID + " text," +
            FIELD_ORDER_CARRIERMILEAGE + " integer," +
            FIELD_ORDER_CUSTOMERRATING + " real," +
            FIELD_ORDER_VEHICLEKMPRICE + " real," +
            FIELD_ORDER_CAPACITYCLASSID + " text," +
            FIELD_ORDER_CARRIERRENTTIME + " integer," +
            FIELD_ORDER_CUSTOMERMILEAGE + " integer," +
            FIELD_ORDER_VEHICLETYPENAME + " text," +
            FIELD_ORDER_CARRIERCOMPANYID + " text," +
            FIELD_ORDER_CARRIERMANAGERID + " text," +
            FIELD_ORDER_CUSTOMERRENTTIME + " integer," +
            FIELD_ORDER_CUSTOMERCOMPANYID + " text," +
            FIELD_ORDER_CARRIERCOMPANYNAME + " text," +
            FIELD_ORDER_CARRIERMANAGERNAME + " text," +
            FIELD_ORDER_CARRIERTOTALAMOUNT + " real," +
            FIELD_ORDER_NUMBEROFPASSENGERS + " integer," +
            FIELD_ORDER_VEHICLEHOURLYPRICE + " real," +
            FIELD_ORDER_CARRIERCOMPANYEMAIL + " text," +
            FIELD_ORDER_CARRIERCOMPANYPHONE + " text," +
            FIELD_ORDER_CARRIERDEPARTMENTID + " text," +
            FIELD_ORDER_CARRIERMANAGEREMAIL + " text," +
            FIELD_ORDER_CARRIERMANAGERPHONE + " text," +
            FIELD_ORDER_CUSTOMERCOMPANYCODE + " integer," +
            FIELD_ORDER_CUSTOMERCOMPANYNAME + " text," +
            FIELD_ORDER_CARRIEREQUIPMENTTIME + " integer," +
            FIELD_ORDER_CUSTOMERDEPARTMENTID + " text," +
            FIELD_ORDER_CARRIERDEPARTMENTNAME + " text," +
            FIELD_ORDER_CUSTOMEREQUIPMENTTIME + " integer," +
            FIELD_ORDER_CUSTOMERDEPARTMENTNAME + " text," +
            FIELD_ORDER_INFORM + " text," +
            FIELD_ORDER_ISCHANGED + " integer," +
            FIELD_ORDER_ISNEW + " integer" +
            ");";


    public static ArrayList<Order> getChangedList(){

        String filterOrder = FIELD_ORDER_ISCHANGED + "=1";

        ArrayList<Order> list = getList(filterOrder);

        if (list != null && list.size() > 0){

            for (Order order: list) {

                if (!order.isNew()) continue;

                order.setId(null);

            }
        }

        return list;
    }

    public static Order get(String id){

        String filter = FIELD_ORDER_ID + "='" + id + "'";

        ArrayList<Order> orders = getList(filter);

        if (orders != null && orders.size() > 0){
            return orders.get(0);
        }

        return null;
    }

    public static ArrayList<Order> getList(String filter){

        ArrayList<Order> list = null;

        Cursor cursor = getCursor(filter);

        if (cursor != null && cursor.getCount() > 0){

            list = new ArrayList<>();

            while (cursor.moveToNext()){

                // ORDER DATA
                Order order = new Order();

//                FIELD_ORDER_ID + " ," +
                order.setId(cursor.getString(cursor.getColumnIndex(FIELD_ORDER_ID)));
//                FIELD_ORDER_DATE + " ," +
                order.setDate(cursor.getLong(cursor.getColumnIndex(FIELD_ORDER_DATE)));
//                FIELD_ORDER_GROUP + " ," +
                order.setGroup(cursor.getString(cursor.getColumnIndex(FIELD_ORDER_GROUP)));
//                FIELD_ORDER_PRICE + " ," +
                order.setPrice(cursor.getFloat(cursor.getColumnIndex(FIELD_ORDER_PRICE)));
//                FIELD_ORDER_STATE + " ," +
                order.setState(cursor.getString(cursor.getColumnIndex(FIELD_ORDER_STATE)));
//                FIELD_ORDER_NUMBER + " ," +
                order.setNumber(cursor.getString(cursor.getColumnIndex(FIELD_ORDER_NUMBER)));
//                FIELD_ORDER_RATING + " ," +
                order.setRating(cursor.getFloat(cursor.getColumnIndex(FIELD_ORDER_RATING)));
//                FIELD_ORDER_USERID + " ," +
                order.setUserId(cursor.getString(cursor.getColumnIndex(FIELD_ORDER_USERID)));
//                FIELD_ORDER_COMMENT + " ," +
                order.setComment(cursor.getString(cursor.getColumnIndex(FIELD_ORDER_COMMENT)));
//                FIELD_ORDER_MILEAGE + " ," +
                order.setMileage(cursor.getInt(cursor.getColumnIndex(FIELD_ORDER_MILEAGE)));
//                FIELD_ORDER_MODELID + " ," +
                order.setModelId(cursor.getString(cursor.getColumnIndex(FIELD_ORDER_MODELID)));
//                FIELD_ORDER_VATRATE + " ," +
                order.setVatRate(cursor.getFloat(cursor.getColumnIndex(FIELD_ORDER_VATRATE)));
//                FIELD_ORDER_WITHVAT + " ," +
                order.withVAT(cursor.getInt(cursor.getColumnIndex(FIELD_ORDER_WITHVAT))==1);
//                FIELD_ORDER_BODYTYPE + " ," +
                order.setBodyType(cursor.getString(cursor.getColumnIndex(FIELD_ORDER_BODYTYPE)));
//                FIELD_ORDER_PRIORITY + " ," +
                order.setPriority(cursor.getString(cursor.getColumnIndex(FIELD_ORDER_PRIORITY)));
//                FIELD_ORDER_RENTTIME + " ," +
                order.setRentTime(cursor.getInt(cursor.getColumnIndex(FIELD_ORDER_RENTTIME)));
//                FIELD_ORDER_RENTTYPE + " ," +
                order.setRentType(cursor.getString(cursor.getColumnIndex(FIELD_ORDER_RENTTYPE)));
//                FIELD_ORDER_USERNAME + " ," +
                order.setUserName(cursor.getString(cursor.getColumnIndex(FIELD_ORDER_USERNAME)));
//                FIELD_ORDER_EXTNUMBER + " ," +
                order.setExtNumber(cursor.getString(cursor.getColumnIndex(FIELD_ORDER_EXTNUMBER)));
//                FIELD_ORDER_ISDELETED + " ," +
                order.setDeleted(cursor.getInt(cursor.getColumnIndex(FIELD_ORDER_ISDELETED))==1);
//                FIELD_ORDER_ISPROTEST + " ," +
                order.setProtest(cursor.getInt(cursor.getColumnIndex(FIELD_ORDER_ISPROTEST))==1);
//                FIELD_ORDER_MODELNAME + " ," +
                order.setModelName(cursor.getString(cursor.getColumnIndex(FIELD_ORDER_MODELNAME)));
//                FIELD_ORDER_ORDERTYPE + " ," +
                order.setOrderType(cursor.getString(cursor.getColumnIndex(FIELD_ORDER_ORDERTYPE)));
//                FIELD_ORDER_REGNUMBER + " ," +
                order.setRegNumber(cursor.getString(cursor.getColumnIndex(FIELD_ORDER_REGNUMBER)));
//                FIELD_ORDER_STATENAME + " ," +
                order.setStateName(cursor.getString(cursor.getColumnIndex(FIELD_ORDER_STATENAME)));
//                FIELD_ORDER_VEHICLEID + " ," +
                order.setVehicleId(cursor.getString(cursor.getColumnIndex(FIELD_ORDER_VEHICLEID)));
//                FIELD_ORDER_CREATEDATE + " ," +
                order.setCreateDate(cursor.getLong(cursor.getColumnIndex(FIELD_ORDER_CREATEDATE)));
//                FIELD_ORDER_LOCALITYID + " ," +
                order.setLocalityId(cursor.getString(cursor.getColumnIndex(FIELD_ORDER_LOCALITYID)));
//                FIELD_ORDER_MODIFYDATE + " ," +
                order.setModifyDate(cursor.getLong(cursor.getColumnIndex(FIELD_ORDER_MODIFYDATE)));
//                FIELD_ORDER_OFFERCOUNT + " ," +
                order.setOfferCount(cursor.getInt(cursor.getColumnIndex(FIELD_ORDER_OFFERCOUNT)));
//                FIELD_ORDER_PRIORITYID + " ," +
                order.setPriorityId(cursor.getString(cursor.getColumnIndex(FIELD_ORDER_PRIORITYID)));
//                FIELD_ORDER_CARGOWEIGHT + " ," +
                order.setCargoWeight(cursor.getFloat(cursor.getColumnIndex(FIELD_ORDER_CARGOWEIGHT)));
//                FIELD_ORDER_CARRIERCODE + " ," +
                order.setCarrierCode(cursor.getInt(cursor.getColumnIndex(FIELD_ORDER_CARRIERCODE)));
//                FIELD_ORDER_RENTADDRESS + " ," +
                order.setRentAddress(cursor.getString(cursor.getColumnIndex(FIELD_ORDER_RENTADDRESS)));
//                FIELD_ORDER_RENTENDDATE + " ," +
                order.setRentEndDate(cursor.getLong(cursor.getColumnIndex(FIELD_ORDER_RENTENDDATE)));
//                FIELD_ORDER_TOTALAMOUNT + " ," +
                order.setTotalAmount(cursor.getFloat(cursor.getColumnIndex(FIELD_ORDER_TOTALAMOUNT)));
//                FIELD_ORDER_VEHICLENAME + " ," +
                order.setVehicleName(cursor.getString(cursor.getColumnIndex(FIELD_ORDER_VEHICLENAME)));
//                FIELD_ORDER_CONSUMERTYPE + " ," +
                order.setConsumerType(cursor.getString(cursor.getColumnIndex(FIELD_ORDER_CONSUMERTYPE)));
//                FIELD_ORDER_CONTACTPHONE + " ," +
                order.setContactPhone(cursor.getString(cursor.getColumnIndex(FIELD_ORDER_CONTACTPHONE)));
//                FIELD_ORDER_LOCALITYNAME + " ," +
                order.setLocalityName(cursor.getString(cursor.getColumnIndex(FIELD_ORDER_LOCALITYNAME)));
//                FIELD_ORDER_PRIORITYNAME + " ," +
                order.setPriorityName(cursor.getString(cursor.getColumnIndex(FIELD_ORDER_PRIORITYNAME)));
//                FIELD_ORDER_CONTACTPERSON + " ," +
                order.setContactPerson(cursor.getString(cursor.getColumnIndex(FIELD_ORDER_CONTACTPERSON)));
//                FIELD_ORDER_EQUIPMENTTIME + " ," +
                order.setEquipmentTime(cursor.getInt(cursor.getColumnIndex(FIELD_ORDER_EQUIPMENTTIME)));
//                FIELD_ORDER_ORDERTYPENAME + " ," +
                order.setOrderTypeName(cursor.getString(cursor.getColumnIndex(FIELD_ORDER_ORDERTYPENAME)));
//                FIELD_ORDER_PLANNEDAMOUNT + " ," +
                order.setPlannedAmount(cursor.getFloat(cursor.getColumnIndex(FIELD_ORDER_PLANNEDAMOUNT)));
//                FIELD_ORDER_RENTSTARTDATE + " ," +
                order.setRentStartDate(cursor.getLong(cursor.getColumnIndex(FIELD_ORDER_RENTSTARTDATE)));
//                FIELD_ORDER_VEHICLETYPEID + " ," +
                order.setVehicleTypeId(cursor.getString(cursor.getColumnIndex(FIELD_ORDER_VEHICLETYPEID)));
//                FIELD_ORDER_CARRIERMILEAGE + " ," +
                order.setCarrierMileage(cursor.getInt(cursor.getColumnIndex(FIELD_ORDER_CARRIERMILEAGE)));
//                FIELD_ORDER_CUSTOMERRATING + " ," +
                order.setCustomerRating(cursor.getFloat(cursor.getColumnIndex(FIELD_ORDER_CUSTOMERRATING)));
//                FIELD_ORDER_VEHICLEKMPRICE + " ," +
                order.setVehicleKmPrice(cursor.getFloat(cursor.getColumnIndex(FIELD_ORDER_VEHICLEKMPRICE)));
//                FIELD_ORDER_CAPACITYCLASSID + " ," +
                order.setCapacityClassId(cursor.getString(cursor.getColumnIndex(FIELD_ORDER_CAPACITYCLASSID)));
//                FIELD_ORDER_CARRIERRENTTIME + " ," +
                order.setCarrierRentTime(cursor.getInt(cursor.getColumnIndex(FIELD_ORDER_CARRIERRENTTIME)));
//                FIELD_ORDER_CUSTOMERMILEAGE + " ," +
                order.setCustomerMileage(cursor.getInt(cursor.getColumnIndex(FIELD_ORDER_CUSTOMERMILEAGE)));
//                FIELD_ORDER_VEHICLETYPENAME + " ," +
                order.setVehicleTypeName(cursor.getString(cursor.getColumnIndex(FIELD_ORDER_VEHICLETYPENAME)));
//                FIELD_ORDER_CARRIERCOMPANYID + " ," +
                order.setCarrierCompanyId(cursor.getString(cursor.getColumnIndex(FIELD_ORDER_CARRIERCOMPANYID)));
//                FIELD_ORDER_CARRIERMANAGERID + " ," +
                order.setCarrierManagerId(cursor.getString(cursor.getColumnIndex(FIELD_ORDER_CARRIERMANAGERID)));
//                FIELD_ORDER_CUSTOMERRENTTIME + " ," +
                order.setCustomerRentTime(cursor.getInt(cursor.getColumnIndex(FIELD_ORDER_CUSTOMERRENTTIME)));
//                FIELD_ORDER_CUSTOMERCOMPANYID + " ," +
                order.setCustomerCompanyId(cursor.getString(cursor.getColumnIndex(FIELD_ORDER_CUSTOMERCOMPANYID)));
//                FIELD_ORDER_CARRIERCOMPANYNAME + " ," +
                order.setCarrierCompanyName(cursor.getString(cursor.getColumnIndex(FIELD_ORDER_CARRIERCOMPANYNAME)));
//                FIELD_ORDER_CARRIERMANAGERNAME + " ," +
                order.setCarrierManagerName(cursor.getString(cursor.getColumnIndex(FIELD_ORDER_CARRIERMANAGERNAME)));
//                FIELD_ORDER_CARRIERTOTALAMOUNT + " ," +
                order.setCarrierTotalAmount(cursor.getFloat(cursor.getColumnIndex(FIELD_ORDER_CARRIERTOTALAMOUNT)));
//                FIELD_ORDER_NUMBEROFPASSENGERS + " ," +
                order.setNumberOfPassengers(cursor.getInt(cursor.getColumnIndex(FIELD_ORDER_NUMBEROFPASSENGERS)));
//                FIELD_ORDER_VEHICLEHOURLYPRICE + " ," +
                order.setVehicleHourlyPrice(cursor.getFloat(cursor.getColumnIndex(FIELD_ORDER_VEHICLEHOURLYPRICE)));
//                FIELD_ORDER_CARRIERCOMPANYEMAIL + " ," +
                order.setCarrierCompanyEmail(cursor.getString(cursor.getColumnIndex(FIELD_ORDER_CARRIERCOMPANYEMAIL)));
//                FIELD_ORDER_CARRIERCOMPANYPHONE + " ," +
                order.setCarrierCompanyPhone(cursor.getString(cursor.getColumnIndex(FIELD_ORDER_CARRIERCOMPANYPHONE)));
//                FIELD_ORDER_CARRIERDEPARTMENTID + " ," +
                order.setCarrierDepartmentId(cursor.getString(cursor.getColumnIndex(FIELD_ORDER_CARRIERDEPARTMENTID)));
//                FIELD_ORDER_CARRIERMANAGEREMAIL + " ," +
                order.setCarrierManagerEmail(cursor.getString(cursor.getColumnIndex(FIELD_ORDER_CARRIERMANAGEREMAIL)));
//                FIELD_ORDER_CARRIERMANAGERPHONE + " ," +
                order.setCarrierManagerPhone(cursor.getString(cursor.getColumnIndex(FIELD_ORDER_CARRIERMANAGERPHONE)));
//                FIELD_ORDER_CUSTOMERCOMPANYCODE + " ," +
                order.setCustomerCompanyCode(cursor.getInt(cursor.getColumnIndex(FIELD_ORDER_CUSTOMERCOMPANYCODE)));
//                FIELD_ORDER_CUSTOMERCOMPANYNAME + " ," +
                order.setCustomerCompanyName(cursor.getString(cursor.getColumnIndex(FIELD_ORDER_CUSTOMERCOMPANYNAME)));
//                FIELD_ORDER_CARRIEREQUIPMENTTIME + " ," +
                order.setCarrierEquipmentTime(cursor.getInt(cursor.getColumnIndex(FIELD_ORDER_CARRIEREQUIPMENTTIME)));
//                FIELD_ORDER_CUSTOMERDEPARTMENTID + " ," +
                order.setCustomerDepartmentId(cursor.getString(cursor.getColumnIndex(FIELD_ORDER_CUSTOMERDEPARTMENTID)));
//                FIELD_ORDER_CARRIERDEPARTMENTNAME + " ," +
                order.setCarrierDepartmentName(cursor.getString(cursor.getColumnIndex(FIELD_ORDER_CARRIERDEPARTMENTNAME)));
//                FIELD_ORDER_CUSTOMEREQUIPMENTTIME + " ," +
                order.setCustomerEquipmentTime(cursor.getInt(cursor.getColumnIndex(FIELD_ORDER_CUSTOMEREQUIPMENTTIME)));
//                FIELD_ORDER_CUSTOMERDEPARTMENTNAME + ")" +
                order.setCustomerDepartmentName(cursor.getString(cursor.getColumnIndex(FIELD_ORDER_CUSTOMERDEPARTMENTNAME)));
//                FIELD_ORDER_INFORM
                order.setInform(cursor.getString(cursor.getColumnIndex(FIELD_ORDER_INFORM)));
//                FIELD_ORDER_ISCHANGED + " ," +
                order.setProtest(cursor.getInt(cursor.getColumnIndex(FIELD_ORDER_ISCHANGED))==1);
//                FIELD_ORDER_ISNEW + " ," +
                order.setNew(cursor.getInt(cursor.getColumnIndex(FIELD_ORDER_ISNEW))==1);

                ArrayList<OrderHistory> orderHistory = SQLOrderHistory.getOrderList(order.getId());
                if (orderHistory != null && orderHistory.size() > 0){
                    order.setHistory(orderHistory.toArray(new OrderHistory[0]));
                }

                ArrayList<Offer> offers = SQLOffer.getOrderList(order.getId());
                if (offers != null && offers.size() > 0){
                    order.setOffers(offers.toArray(new Offer[0]));
                }

                Person person = SQLOrderCustomerPerson.getOrderPerson(order.getId());
                if (person != null){
                    order.setCustomerPerson(person);
                }

                list.add(order);
            }

            cursor.close();
        }

        return list;
    }

    public static Cursor getCursor(String filter) {

        String sqlFilter = "";

        String sql = "SELECT " + FIELD_ORDER_ID  + " AS _id, * FROM " + TABLE_NAME;

        if (Utils.stringsNotEmpty(filter)){

            if (Utils.stringsNotEmpty(sqlFilter)){
                sqlFilter += " AND ";
            }

            sqlFilter += filter;
        }

        if (Utils.stringsNotEmpty(sqlFilter)){
            sql += " WHERE " + sqlFilter;
        }

        sql += " ORDER BY " + FIELD_ORDER_DATE + " DESC ";

        return SQLUtils.selectData(sql);
    }

    public static void update(Order order){
        Order[] orders = new Order[1];
        orders[0] = order;
        update(orders);
    }

    public static void update(Order[] orders){

        if (orders == null) return;

        String sql = "INSERT OR REPLACE INTO " + TABLE_NAME + " ("+
                FIELD_ORDER_ID + " ," +
                FIELD_ORDER_DATE + " ," +
                FIELD_ORDER_GROUP + " ," +
                FIELD_ORDER_PRICE + " ," +
                FIELD_ORDER_STATE + " ," +
                FIELD_ORDER_NUMBER + " ," +
                FIELD_ORDER_RATING + " ," +
                FIELD_ORDER_USERID + " ," +
                FIELD_ORDER_COMMENT + " ," +
                FIELD_ORDER_MILEAGE + " ," +
                FIELD_ORDER_MODELID + " ," +
                FIELD_ORDER_VATRATE + " ," +
                FIELD_ORDER_WITHVAT + " ," +
                FIELD_ORDER_BODYTYPE + " ," +
                FIELD_ORDER_PRIORITY + " ," +
                FIELD_ORDER_RENTTIME + " ," +
                FIELD_ORDER_RENTTYPE + " ," +
                FIELD_ORDER_USERNAME + " ," +
                FIELD_ORDER_EXTNUMBER + " ," +
                FIELD_ORDER_ISDELETED + " ," +
                FIELD_ORDER_ISPROTEST + " ," +
                FIELD_ORDER_MODELNAME + " ," +
                FIELD_ORDER_ORDERTYPE + " ," +
                FIELD_ORDER_REGNUMBER + " ," +
                FIELD_ORDER_STATENAME + " ," +
                FIELD_ORDER_VEHICLEID + " ," +
                FIELD_ORDER_CREATEDATE + " ," +
                FIELD_ORDER_LOCALITYID + " ," +
                FIELD_ORDER_MODIFYDATE + " ," +
                FIELD_ORDER_OFFERCOUNT + " ," +
                FIELD_ORDER_PRIORITYID + " ," +
                FIELD_ORDER_CARGOWEIGHT + " ," +
                FIELD_ORDER_CARRIERCODE + " ," +
                FIELD_ORDER_RENTADDRESS + " ," +
                FIELD_ORDER_RENTENDDATE + " ," +
                FIELD_ORDER_TOTALAMOUNT + " ," +
                FIELD_ORDER_VEHICLENAME + " ," +
                FIELD_ORDER_CONSUMERTYPE + " ," +
                FIELD_ORDER_CONTACTPHONE + " ," +
                FIELD_ORDER_LOCALITYNAME + " ," +
                FIELD_ORDER_PRIORITYNAME + " ," +
                FIELD_ORDER_CONTACTPERSON + " ," +
                FIELD_ORDER_EQUIPMENTTIME + " ," +
                FIELD_ORDER_ORDERTYPENAME + " ," +
                FIELD_ORDER_PLANNEDAMOUNT + " ," +
                FIELD_ORDER_RENTSTARTDATE + " ," +
                FIELD_ORDER_VEHICLETYPEID + " ," +
                FIELD_ORDER_CARRIERMILEAGE + " ," +
                FIELD_ORDER_CUSTOMERRATING + " ," +
                FIELD_ORDER_VEHICLEKMPRICE + " ," +
                FIELD_ORDER_CAPACITYCLASSID + " ," +
                FIELD_ORDER_CARRIERRENTTIME + " ," +
                FIELD_ORDER_CUSTOMERMILEAGE + " ," +
                FIELD_ORDER_VEHICLETYPENAME + " ," +
                FIELD_ORDER_CARRIERCOMPANYID + " ," +
                FIELD_ORDER_CARRIERMANAGERID + " ," +
                FIELD_ORDER_CUSTOMERRENTTIME + " ," +
                FIELD_ORDER_CUSTOMERCOMPANYID + " ," +
                FIELD_ORDER_CARRIERCOMPANYNAME + " ," +
                FIELD_ORDER_CARRIERMANAGERNAME + " ," +
                FIELD_ORDER_CARRIERTOTALAMOUNT + " ," +
                FIELD_ORDER_NUMBEROFPASSENGERS + " ," +
                FIELD_ORDER_VEHICLEHOURLYPRICE + " ," +
                FIELD_ORDER_CARRIERCOMPANYEMAIL + " ," +
                FIELD_ORDER_CARRIERCOMPANYPHONE + " ," +
                FIELD_ORDER_CARRIERDEPARTMENTID + " ," +
                FIELD_ORDER_CARRIERMANAGEREMAIL + " ," +
                FIELD_ORDER_CARRIERMANAGERPHONE + " ," +
                FIELD_ORDER_CUSTOMERCOMPANYCODE + " ," +
                FIELD_ORDER_CUSTOMERCOMPANYNAME + " ," +
                FIELD_ORDER_CARRIEREQUIPMENTTIME + " ," +
                FIELD_ORDER_CUSTOMERDEPARTMENTID + " ," +
                FIELD_ORDER_CARRIERDEPARTMENTNAME + " ," +
                FIELD_ORDER_CUSTOMEREQUIPMENTTIME + " ," +
                FIELD_ORDER_CUSTOMERDEPARTMENTNAME + "," +
                FIELD_ORDER_INFORM + "," +
                FIELD_ORDER_ISCHANGED + " ," +
                FIELD_ORDER_ISNEW + " )" +
                " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?" +
                ",?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?" +
                ",?,?,?)";

        try {

            getWriteDb().beginTransactionNonExclusive();

            SQLiteStatement stInsert = getWriteDb().compileStatement(sql);

            for (Order order : orders) {

//                FIELD_ORDER_ID + " ," +
                stInsert.bindString(1, order.getId());
//                FIELD_ORDER_DATE + " ," +
                stInsert.bindLong(2, order.getDate());
//                FIELD_ORDER_GROUP + " ," +
                stInsert.bindString(3, order.getGroup());
//                FIELD_ORDER_PRICE + " ," +
                stInsert.bindDouble(4, order.getPrice());
//                FIELD_ORDER_STATE + " ," +
                stInsert.bindString(5, order.getState());
//                FIELD_ORDER_NUMBER + " ," +
                stInsert.bindString(6, order.getNumber());
//                FIELD_ORDER_RATING + " ," +
                stInsert.bindDouble(7, order.getRating());
//                FIELD_ORDER_USERID + " ," +
                stInsert.bindString(8, order.getUserId());
//                FIELD_ORDER_COMMENT + " ," +
                stInsert.bindString(9, order.getComment());
//                FIELD_ORDER_MILEAGE + " ," +
                stInsert.bindLong(10, order.getMileage());
//                FIELD_ORDER_MODELID + " ," +
                stInsert.bindString(11, order.getModelId());
//                FIELD_ORDER_VATRATE + " ," +
                stInsert.bindDouble(12, order.getVatRate());
//                FIELD_ORDER_WITHVAT + " ," +
                stInsert.bindLong(13, (order.withVAT() ? 1:0));
//                FIELD_ORDER_BODYTYPE + " ," +
                stInsert.bindString(14, order.getBodyType());
//                FIELD_ORDER_PRIORITY + " ," +
                stInsert.bindString(15, order.getPriority());
//                FIELD_ORDER_RENTTIME + " ," +
                stInsert.bindLong(16, order.getRentTime());
//                FIELD_ORDER_RENTTYPE + " ," +
                stInsert.bindString(17, order.getRentType());
//                FIELD_ORDER_USERNAME + " ," +
                stInsert.bindString(18, order.getUserName());
//                FIELD_ORDER_EXTNUMBER + " ," +
                stInsert.bindString(19, order.getExtNumber());
//                FIELD_ORDER_ISDELETED + " ," +
                stInsert.bindLong(20, (order.isDeleted() ? 1:0));
//                FIELD_ORDER_ISPROTEST + " ," +
                stInsert.bindLong(21, (order.isProtest() ? 1:0));
//                FIELD_ORDER_MODELNAME + " ," +
                stInsert.bindString(22, order.getModelName());
//                FIELD_ORDER_ORDERTYPE + " ," +
                stInsert.bindString(23, order.getOrderType());
//                FIELD_ORDER_REGNUMBER + " ," +
                stInsert.bindString(24, order.getRegNumber());
//                FIELD_ORDER_STATENAME + " ," +
                stInsert.bindString(25, order.getStateName());
//                FIELD_ORDER_VEHICLEID + " ," +
                stInsert.bindString(26, order.getVehicleId());
//                FIELD_ORDER_CREATEDATE + " ," +
                stInsert.bindLong(27, order.getCreateDate());
//                FIELD_ORDER_LOCALITYID + " ," +
                stInsert.bindString(28, order.getLocalityId());
//                FIELD_ORDER_MODIFYDATE + " ," +
                stInsert.bindLong(29, order.getModifyDate());
//                FIELD_ORDER_OFFERCOUNT + " ," +
                stInsert.bindLong(30, order.getOfferCount());
//                FIELD_ORDER_PRIORITYID + " ," +
                stInsert.bindString(31, order.getPriorityId());
//                FIELD_ORDER_CARGOWEIGHT + " ," +
                stInsert.bindDouble(32, order.getCargoWeight());
//                FIELD_ORDER_CARRIERCODE + " ," +
                stInsert.bindLong(33, order.getCarrierCode());
//                FIELD_ORDER_RENTADDRESS + " ," +
                stInsert.bindString(34, order.getRentAddress());
//                FIELD_ORDER_RENTENDDATE + " ," +
                stInsert.bindLong(35, order.getRentEndDate());
//                FIELD_ORDER_TOTALAMOUNT + " ," +
                stInsert.bindDouble(36, order.getTotalAmount());
//                FIELD_ORDER_VEHICLENAME + " ," +
                stInsert.bindString(37, order.getVehicleName());
//                FIELD_ORDER_CONSUMERTYPE + " ," +
                stInsert.bindString(38, order.getConsumerType());
//                FIELD_ORDER_CONTACTPHONE + " ," +
                stInsert.bindString(39, order.getContactPhone());
//                FIELD_ORDER_LOCALITYNAME + " ," +
                stInsert.bindString(40, order.getLocalityName());
//                FIELD_ORDER_PRIORITYNAME + " ," +
                stInsert.bindString(41, order.getPriorityName());
//                FIELD_ORDER_CONTACTPERSON + " ," +
                stInsert.bindString(42, order.getContactPerson());
//                FIELD_ORDER_EQUIPMENTTIME + " ," +
                stInsert.bindLong(43, order.getEquipmentTime());
//                FIELD_ORDER_ORDERTYPENAME + " ," +
                stInsert.bindString(44, order.getOrderTypeName());
//                FIELD_ORDER_PLANNEDAMOUNT + " ," +
                stInsert.bindDouble(45, order.getPlannedAmount());
//                FIELD_ORDER_RENTSTARTDATE + " ," +
                stInsert.bindLong(46, order.getRentStartDate());
//                FIELD_ORDER_VEHICLETYPEID + " ," +
                stInsert.bindString(47, order.getVehicleTypeId());
//                FIELD_ORDER_CARRIERMILEAGE + " ," +
                stInsert.bindLong(48, order.getCarrierMileage());
//                FIELD_ORDER_CUSTOMERRATING + " ," +
                stInsert.bindDouble(49, order.getCustomerRating());
//                FIELD_ORDER_VEHICLEKMPRICE + " ," +
                stInsert.bindDouble(50, order.getVehicleKmPrice());
//                FIELD_ORDER_CAPACITYCLASSID + " ," +
                stInsert.bindString(51, order.getCapacityClassId());
//                FIELD_ORDER_CARRIERRENTTIME + " ," +
                stInsert.bindLong(52, order.getCarrierRentTime());
//                FIELD_ORDER_CUSTOMERMILEAGE + " ," +
                stInsert.bindLong(53, order.getCustomerMileage());
//                FIELD_ORDER_VEHICLETYPENAME + " ," +
                stInsert.bindString(54, order.getVehicleTypeName());
//                FIELD_ORDER_CARRIERCOMPANYID + " ," +
                stInsert.bindString(55, order.getCarrierCompanyId());
//                FIELD_ORDER_CARRIERMANAGERID + " ," +
                stInsert.bindString(56, order.getCarrierManagerId());
//                FIELD_ORDER_CUSTOMERRENTTIME + " ," +
                stInsert.bindLong(57, order.getCustomerRentTime());
//                FIELD_ORDER_CUSTOMERCOMPANYID + " ," +
                stInsert.bindString(58, order.getCustomerCompanyId());
//                FIELD_ORDER_CARRIERCOMPANYNAME + " ," +
                stInsert.bindString(59, order.getCarrierCompanyName());
//                FIELD_ORDER_CARRIERMANAGERNAME + " ," +
                stInsert.bindString(60, order.getCarrierManagerName());
//                FIELD_ORDER_CARRIERTOTALAMOUNT + " ," +
                stInsert.bindDouble(61, order.getCarrierTotalAmount());
//                FIELD_ORDER_NUMBEROFPASSENGERS + " ," +
                stInsert.bindLong(62, order.getNumberOfPassengers());
//                FIELD_ORDER_VEHICLEHOURLYPRICE + " ," +
                stInsert.bindDouble(63, order.getVehicleHourlyPrice());
//                FIELD_ORDER_CARRIERCOMPANYEMAIL + " ," +
                stInsert.bindString(64, order.getCarrierCompanyEmail());
//                FIELD_ORDER_CARRIERCOMPANYPHONE + " ," +
                stInsert.bindString(65, order.getCarrierCompanyPhone());
//                FIELD_ORDER_CARRIERDEPARTMENTID + " ," +
                stInsert.bindString(66, order.getCarrierDepartmentId());
//                FIELD_ORDER_CARRIERMANAGEREMAIL + " ," +
                stInsert.bindString(67, order.getCarrierManagerEmail());
//                FIELD_ORDER_CARRIERMANAGERPHONE + " ," +
                stInsert.bindString(68, order.getCarrierManagerPhone());
//                FIELD_ORDER_CUSTOMERCOMPANYCODE + " ," +
                stInsert.bindLong(69, order.getCustomerCompanyCode());
//                FIELD_ORDER_CUSTOMERCOMPANYNAME + " ," +
                stInsert.bindString(70, order.getCustomerCompanyName());
//                FIELD_ORDER_CARRIEREQUIPMENTTIME + " ," +
                stInsert.bindLong(71, order.getCarrierEquipmentTime());
//                FIELD_ORDER_CUSTOMERDEPARTMENTID + " ," +
                stInsert.bindString(72, order.getCustomerDepartmentId());
//                FIELD_ORDER_CARRIERDEPARTMENTNAME + " ," +
                stInsert.bindString(73, order.getCarrierDepartmentName());
//                FIELD_ORDER_CUSTOMEREQUIPMENTTIME + " ," +
                stInsert.bindLong(74, order.getCustomerEquipmentTime());
//                FIELD_ORDER_CUSTOMERDEPARTMENTNAME + ")" +
                stInsert.bindString(75, order.getCustomerDepartmentName());
//                FIELD_ORDER_INFORM
                stInsert.bindString(76, order.getInformString());
//                FIELD_ORDER_ISCHANGED + " ," +
                stInsert.bindLong(77, (order.isChanged() ? 1:0));
//                FIELD_ORDER_ISNEW + " ," +
                stInsert.bindLong(78, (order.isNew() ? 1:0));

                if (order.getHistory() != null && order.getHistory().length > 0){
                    for (OrderHistory history: order.getHistory()){
                        history.setOrderId(order.getId());
                    }
                    SQLOrderHistory.update(order.getHistory());
                }

                if (order.getOffers() != null && order.getOffers().length > 0){
                    for (Offer offer: order.getOffers()){
                        offer.setOrderId(order.getId());
                    }
                    SQLOffer.update(order.getOffers());
                }

                if (order.getCustomerPerson() != null){
                    order.getCustomerPerson().setOrderId(order.getId());
                    SQLOrderCustomerPerson.update(order.getCustomerPerson());

                }

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

            getWriteDb().beginTransaction();

            SQLOrderCustomerPerson.deleteAll();

            SQLOffer.deleteAll();

            SQLOrderHistory.deleteAll();

            getWriteDb().delete(TABLE_NAME, null, null);

            getWriteDb().setTransactionSuccessful();

        } catch (Exception e) {
            LogUtils.debugErrorLog(LOG_PREFIX, e);
        } finally {
            getWriteDb().endTransaction();
        }
    }


    public static void createTable() {
        SQLOffer.createTable();
        SQLOrderHistory.createTable();
        SQLOrderCustomerPerson.createTable();
        SQLUtils.execSQL(CREATE_TABLE_SQL);
    }

    public static void dropTable() {
        SQLOffer.dropTable();
        SQLOrderHistory.dropTable();
        SQLOrderCustomerPerson.dropTable();
        SQLUtils.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME + "; ");
    }
}
