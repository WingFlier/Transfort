package ru.wdsoft.ui.order;

import android.content.Context;

import java.util.Date;
import java.util.UUID;

import ru.wdsoft.api.models.Order;
import ru.wdsoft.api.models.OrderHistory;
import ru.wdsoft.db.tables.SQLOrder;
import ru.wdsoft.main.WDData;
import ru.wdsoft.utils.Utils;

/**
 * Created by slaventii@mail.ru on 01.03.2019.
 */

public class OrderModel {

    private static final String RENT_TYPE_TIME = "T";
    private static final String RENT_TYPE_ROUTE = "R";

    private static final String CONSUMER_TYPE_NA = "NA";
    private static final String CONSUMER_TYPE_WEB = "Web";
    private static final String CONSUMER_TYPE_MOB = "Mob";
    private static final String CONSUMER_TYPE_CS = "CS";


    // Выполнен
    public static final String ORDER_STATE_RD = "RD";
    // Создан
    public static final String ORDER_STATE_NW = "NW";
    // Выбран исполнитель
    public static final String ORDER_STATE_CC = "CC";
    // Отменен
    public static final String ORDER_STATE_CN = "CN";
    // Водитель прибыл
    public static final String ORDER_STATE_DP = "DP";
    // Водитель в пути
    public static final String ORDER_STATE_DW = "DW";
    // Талон заказчика не принят
    public static final String ORDER_STATE_NO = "NO";
    //Талон заказчика приня
    public static final String ORDER_STATE_OK = "OK";


    private Context mCtx;

    private Order mOrder;

    private boolean mIsNew = false;

    public OrderModel(Context ctx, String id, boolean copy) {

        this.mCtx = ctx;

        if (Utils.stringsNotEmpty(id)){

            loadOrder(id);

            if (copy){
                copyOrder();
            }

        } else {
            createOrder();
        }

    }

    protected WDData getData(){
        return WDData.getInstance();
    }

    private void loadOrder(String id){
        mOrder = SQLOrder.get(id);
    }

    private void createOrder(){

        mIsNew = true;

        mOrder = new Order();

//        mOrder.setId();
//        mOrder.setDate();
//        mOrder.setGroup();
//        mOrder.setPrice();
//        mOrder.setState();
//        mOrder.setNumber();
//        mOrder.setRating();
//        mOrder.setUserId();
//        mOrder.setComment();
//        mOrder.setMileage();
//        mOrder.setModelId();
//        mOrder.setVatRate();
//        mOrder.withVAT();
//        mOrder.setBodyType();
//        mOrder.setPriority();
//        mOrder.setRentTime();
//        mOrder.setRentType();
//        mOrder.setUserName();
//        mOrder.setExtNumber();
//        mOrder.setDeleted();
//        mOrder.setProtest();
//        mOrder.setModelName();
//        mOrder.setOrderType();
//        mOrder.setRegNumber();
//        mOrder.setStateName();
//        mOrder.setVehicleId();
//        mOrder.setCreateDate();
//        mOrder.setLocalityId();
//        mOrder.setModifyDate();
//        mOrder.setOfferCount();
//        mOrder.setPriorityId();
//        mOrder.setCargoWeight();
//        mOrder.setCarrierCode();
//        mOrder.setRentAddress();
//        mOrder.setRentEndDate();
//        mOrder.setTotalAmount();
//        mOrder.setVehicleName();
//        mOrder.setConsumerType();
//        mOrder.setContactPhone();
//        mOrder.setLocalityName();
//        mOrder.setPriorityName();
//        mOrder.setContactPerson();
//        mOrder.setEquipmentTime();
//        mOrder.setOrderTypeName();
//        mOrder.setPlannedAmount();
//        mOrder.setRentStartDate();
//        mOrder.setVehicleTypeId();
//        mOrder.setCarrierMileage();
//        mOrder.setCustomerRating();
//        mOrder.setVehicleKmPrice();
//        mOrder.setCapacityClassId();
//        mOrder.setCarrierRentTime();
//        mOrder.setCustomerMileage();
//        mOrder.setVehicleTypeName();
//        mOrder.setCarrierCompanyId();
//        mOrder.setCarrierManagerId();
//        mOrder.setCustomerRentTime();
//        mOrder.setCustomerCompanyId();
//        mOrder.setCarrierCompanyName();
//        mOrder.setCarrierManagerName();
//        mOrder.setCarrierTotalAmount();
//        mOrder.setNumberOfPassengers();
//        mOrder.setVehicleHourlyPrice();
//        mOrder.setCarrierCompanyEmail();
//        mOrder.setCarrierCompanyPhone();
//        mOrder.setCarrierDepartmentId();
//        mOrder.setCarrierManagerEmail();
//        mOrder.setCarrierManagerPhone();
//        mOrder.setCustomerCompanyCode();
//        mOrder.setCustomerCompanyName();
//        mOrder.setCarrierEquipmentTime();
//        mOrder.setCustomerDepartmentId();
//        mOrder.setCarrierDepartmentName();
//        mOrder.setCustomerEquipmentTime();
//        mOrder.setCustomerDepartmentName();
//        mOrder.setProtest();
//        mOrder.setNew();



        mOrder.setDate(new Date().getTime());
        mOrder.setRentType(RENT_TYPE_TIME);
        mOrder.setUserId(getData().getUserId());
        mOrder.setContactPhone(getData().getUserPhone());
        mOrder.setUserName(getData().getUserName());
        mOrder.setDeleted(false);
        mOrder.setNumber("");
        mOrder.setConsumerType(CONSUMER_TYPE_MOB);
//        mOrder.setRentTime(480);
        mOrder.setState(ORDER_STATE_NW);
        mOrder.setNew(true);
    }

//    private Long getDefaultStartDate(){
//
//        Date date = new Date();
//
//        String start = Utils.toUIDate(date.getTime()) + " 08:00";
//        Date d1 = Utils.toDateTime(start);
//
//        start = Utils.addDaysHoursMins(d1, 1);
//
//        return Utils.toLong(start);
//    }
//
//    private String getDefaultEndDate(){
//
//        Date date = new Date();
//
//        String end = fldOrderStartDate.getValue();
//
//        if (!Utils.stringsNotEmpty(end)) {
//            end = Utils.toUIDate(date.getTime()) + " 17:00";
//        } else {
//            end = end.substring(0, end.length()-6);
//            end += " 17:00";
//        }
//
//        Date d2 = Utils.toDateTime(end);
//        String endDate = Utils.addDaysHoursMins(d2, 0);
//
//        return endDate;
//    }


    private void copyOrder(){

        mIsNew = true;

        mOrder.setDate(new Date().getTime());

        mOrder.setUserId(getData().getUserId());
        mOrder.setContactPhone(getData().getUserPhone());
        mOrder.setUserName(getData().getUserName());
        mOrder.setDeleted(false);
        mOrder.setNumber("");
        mOrder.setConsumerType(CONSUMER_TYPE_MOB);
//        mOrder.setRentTime(480);

        // Состояние заявки
        mOrder.setState(ORDER_STATE_NW);
        mOrder.setNew(true);

    }

    public boolean isNew(){
        return mIsNew;
    }

    public String saveOrder(){

        if (isNew()){
            mOrder.setId(UUID.randomUUID().toString());

            OrderHistory[] histories = new OrderHistory[1];
            histories[0] = getOrderCreatedState(mOrder.getId());

            mOrder.setHistory(histories);
        }

        mOrder.setNew(isNew());

        mOrder.setChanged(true);

        SQLOrder.update(mOrder);

        return mOrder.getId();

    }

    public boolean isReadOnly(){

        return false;

//        if (mOrder == null) {
//            return true;
//        } else if (mOrder.getState().equalsIgnoreCase(ORDER_STATE_NW)) {
//            return false;
//        } else {
//            return true;
//        }

    }

    public Order getOrder(){
        return mOrder;
    }

    private OrderHistory getOrderCreatedState(String id){


        OrderHistory history = new OrderHistory();

        history.setOrderId(id);
        history.setConsumerType(CONSUMER_TYPE_MOB);
        history.setState(ORDER_STATE_NW);

        history.setUserName(getData().getUserName());
        history.setUserId(getData().getUserId());
        history.setChanged(true);

        return history;

    }

}
