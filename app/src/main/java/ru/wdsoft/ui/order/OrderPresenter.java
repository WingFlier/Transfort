package ru.wdsoft.ui.order;

import java.util.Date;
import java.util.UUID;

import ru.wdsoft.api.ApiResponse;
import ru.wdsoft.api.BackTask;
import ru.wdsoft.api.models.Order;
import ru.wdsoft.api.models.Person;
import ru.wdsoft.main.WDData;
import ru.wdsoft.utils.Utils;

/**
 * Created by slaventii@mail.ru on 15.03.2018.
 */

public class OrderPresenter {

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


//    private OrderView mView;

//    private OrderModel mOrderModel;

    private Order mOrder;

    public OrderPresenter(String id, boolean copy) {
        createPresenter(id, copy);
    }

    public OrderPresenter() {
        createPresenter(null, false);
    }

    private void createPresenter(String id, boolean copy){
        if (Utils.stringsNotEmpty(id)){

            loadOrder(id);

            if (copy){
                initNewOrder();
            }

        } else {
            createOrder();
        }
    }

    private void loadOrder(String id){
        mOrder = BackTask.getOrder(id);
    }

    private void createOrder(){

        mOrder = new Order();
        initNewOrder();
    }

    private void initNewOrder(){

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
        mOrder.setChanged(true);

    }

    public void setOrderType(String id){
        if (mOrder == null) return;
        mOrder.setOrderType(id);
    }

    public Long getCreateDate(){
        if (mOrder == null) return null;
        return mOrder.getCreateDate();
    }

    public String getOrderNumber(){
        if (mOrder == null) return null;
        if (Utils.stringsNotEmpty(mOrder.getExtNumber()))
            return mOrder.getExtNumber();
        else return mOrder.getNumber();
    }

    public String getOrderType(){
        if (mOrder == null) return null;
        return mOrder.getOrderType();
    }

    public void setVehicleType(String id){
        if (mOrder == null) return;
        mOrder.setVehicleTypeId(id);
    }

    public String getVehicleType(){
        if (mOrder == null) return null;
        return mOrder.getVehicleTypeId();
    }

    public void setBodyType(String id){
        if (mOrder == null) return;
        mOrder.setBodyType(id);
    }

    public String getBodyType(){
        if (mOrder == null) return null;
        return mOrder.getBodyType();
    }

    public void setModel(String id){
        if (mOrder == null) return;
        mOrder.setModelId(id);
    }

    public String getModel() {
        if (mOrder == null) return null;
        return mOrder.getModelId();
    }

    public void setComment(String comment){
        if (mOrder == null) return;
        mOrder.setComment(comment);
    }

    public String getComment() {
        if (mOrder == null) return null;
        return mOrder.getComment();
    }

    public void setNumberOfPassengers(Integer numberOfPassengers){
        if (mOrder == null) return;
        mOrder.setNumberOfPassengers(numberOfPassengers);
    }

    public Integer getNumberOfPassengers() {
        if (mOrder == null) return null;
        return mOrder.getNumberOfPassengers();
    }

    public void setRentStartDate(Long rentStartDate){
        if (mOrder == null) return;
        mOrder.setRentStartDate(rentStartDate);
    }

    public Long getRentStartDate() {
        if (mOrder == null) return null;
        return mOrder.getRentStartDate();
    }

    public void setRentEndDate(Long rentEndDate){
        if (mOrder == null) return;
        mOrder.setRentEndDate(rentEndDate);
    }

    public Long getRentEndDate() {
        if (mOrder == null) return null;
        return mOrder.getRentEndDate();
    }

    public void setRentTime(Integer rentTime){
        if (mOrder == null) return;
        mOrder.setRentTime(rentTime);
    }

    public Integer getRentTime() {
        if (mOrder == null) return null;
        return mOrder.getRentTime();
    }

    public void setRentAddress(String rentAddress){
        if (mOrder == null) return;
        mOrder.setRentAddress(rentAddress);
    }

    public String getRentAddress() {
        if (mOrder == null) return null;
        return mOrder.getRentAddress();
    }

    public void setCustomer(String customer){
        if (mOrder == null) return;
        mOrder.setCustomerCompanyId(customer);
        mOrder.setCustomerDepartmentId(customer);
    }

    public String getCustomer() {
        if (mOrder == null) return null;
        return mOrder.getCustomerCompanyId();
    }

    public void setContactPersonName(String name) {
        if (mOrder == null) return;
        if (mOrder.getCustomerPerson() == null){
            Person person = new Person();
            mOrder.setCustomerPerson(person);
        }
        mOrder.getCustomerPerson().setName(name);
    }

    public String getContactPersonName() {
        if (mOrder == null) return null;
        if (mOrder.getCustomerPerson() == null) return null;
        return mOrder.getCustomerPerson().getName();
    }

    public void setContactPersonPhone(String phone) {
        if (mOrder == null) return;
        if (mOrder.getCustomerPerson() == null){
            Person person = new Person();
            mOrder.setCustomerPerson(person);
        }
        mOrder.getCustomerPerson().setPhone(phone);
    }

    public String getContactPhone() {
        if (mOrder == null) return null;
        if (mOrder.getCustomerPerson() == null) return null;
        return mOrder.getCustomerPerson().getPhone();
    }

    public void setContactPersonEmail(String email) {
        if (mOrder == null) return;
        if (mOrder.getCustomerPerson() == null){
            Person person = new Person();
            mOrder.setCustomerPerson(person);
        }
        mOrder.getCustomerPerson().setEMail(email);
    }

    public String getContactEmail() {
        if (mOrder == null) return null;
        if (mOrder.getCustomerPerson() == null) return null;
        return mOrder.getCustomerPerson().getEMail();
    }

    public boolean isNew(){
        if (mOrder == null) return true;
        return mOrder.isNew();
    }

    public void setChanged(){
        if (mOrder == null) return;
        mOrder.setChanged(true);
    }

    public String getOrderId() {
        if (mOrder == null) return null;
        return mOrder.getId();
    }

    public Boolean saveOrder(){

        if (!Utils.stringsNotEmpty(mOrder.getId())){
            mOrder.setId(UUID.randomUUID().toString());
        }

        BackTask.saveOrder(mOrder);

        ApiResponse response = BackTask.sendCachedOrders();

        if (response == null){
            return false;
        } else if (response.isError()) {
            return false;
        } else {
            return true;
        }

    }

//    public void attachView(OrderView view){
//        mView = view;
//        initView();
//    }
//
//    public void detachView(){
//        mView = null;
//    }

//    private void initView(){
//
//        if (mView == null) return;
//
//        Order order = getOrder();
//
//        if (order == null) return;
//
//        mView.setOrderTitle(order.getNumber(), order.getDate());
//
//        mView.setOrderType(getOrderModel().getOrderTypeDataModel());
//        mView.setCustomer(getOrderModel().getCustomerDataModel());
//
//        mView.setOrg(getOrderModel().getOrgDataModel());
//
//        mView.setOrgDepts(getOrderModel().getOrgDepsDataModel());
//        mView.setCustomerDept(getOrderModel().getCustomerDepsDataModel());
//        mView.setCarType(getOrderModel().getVihicleTypeDataModel());
//        mView.setCapacityClass(getOrderModel().getCapacityClassDataModel());
//
//        mView.setModel(getOrderModel().getVehicleModelDataModel());
//        mView.setCar(getOrderModel().getVehicleDataModel());
//        mView.setPriority(getOrderModel().getPriorityDataModel());
//
//        mView.setOrderState(order.getStateName());
//        mView.setOrderStart(order.getRentStartDate());
//        mView.setOrderFinish(order.getRentEndDate());
//        mView.setTimeOfUse(order.getRentTime());
//        mView.setOrderAdress(order.getRentAddress());
//        mView.setPhone(order.getContactPhone());
//        mView.setPassengerCount(order.getNumberOfPassengers());
//
//        mView.setSmsInform(order.getSmsInform() != null);
//
//        mView.setComment(order.getComment());
//        mView.setContactPerson(order.getContactPerson());
//        mView.setMotorcade(order.getCarrierDepartmentId());
//        mView.setCargoWeight(order.getCargoWeight());
//
//        String shippingName = null, slingers = null, areaOfWork= null, natureOfWork = null,
//                route = null, responsible = null;
//        boolean workNearPowerLines = false, isLongDuration = false;
//        Integer orderService = 1;
//
//        String key;
//        if (order.getExtParameters() != null && order.getExtParameters().length > 0){
//
//            for (ExtParameter extParameter: order.getExtParameters()){
//
//                if (extParameter != null) {
//
//                    key = extParameter.getKey();
//
//                    switch (key){
//
//                        case ExtParameter.PARAM_ShippingName:
//                            shippingName = extParameter.toString();
//                            break;
//
//                        case ExtParameter.PARAM_Slingers:
//                            slingers = extParameter.toString();
//                            break;
//
//                        case ExtParameter.PARAM_AreaOfWork:
//                            areaOfWork = extParameter.toString();
//                            break;
//
//                        case ExtParameter.PARAM_NatureOfWork:
//                            natureOfWork = extParameter.toString();
//                            break;
//
//                        case ExtParameter.PARAM_WorkNearPowerLines:
//                            workNearPowerLines = extParameter.toBoolean();
//                            break;
//
//                        case ExtParameter.PARAM_Route:
//                            route = extParameter.toString();
//                            break;
//
//                        case ExtParameter.PARAM_IsLongDuration:
//                            isLongDuration = extParameter.toBoolean();
//                            break;
//
//                        case ExtParameter.PARAM_Responsible:
//                            responsible = extParameter.toString();
//                            break;
//
//                        case ExtParameter.PARAM_Service:
//                            orderService = extParameter.toInteger();
//                            if (orderService == null) {
//                                orderService = 1;
//                            }
//                            break;
//
//                        default:
//                            break;
//                    }
//
//                }
//            }
//        }
//
//        mView.setOrderService(orderService);
//        mView.setResponsible(responsible);
//        mView.setBussinesTrip(isLongDuration);
//        mView.setRoute(route);
//        mView.setWorkNearPowerLines(workNearPowerLines);
//        mView.setNatureOfWork(natureOfWork);
//        mView.setAreaOfWork(areaOfWork);
//        mView.setSlingers(slingers);
//        mView.setShippingName(shippingName);
//
//        mView.setReadOnly(isReadOnly());
//    }

//    private Order getOrder(){
//        return mOrderModel.getOrder();
//    }
//
//    private OrderModel getOrderModel(){
//        return mOrderModel;
//    }
//
//    public boolean isNew(){
//        return getOrderModel().isNew();
//    }
//
//    public boolean isChanged(){
//        return getOrder().isChanged();
//    }
//
//    public boolean isReadOnly(){
//        return getOrderModel().isReadOnly();
//    }
//
//    public String getConsumerType(){
//        return getOrder().getConsumerType();
//    }
//
//    public String saveOrder(ArrayList<Bundle> dates){
//
//        if (isReadOnly()) return null;
//
//        setOrderData();
//
//        if (dates == null){
//            return getOrderModel().saveOrder();
//        } else {
//            return getOrderModel().saveOrders(dates);
//        }
//
//    }
//
//    private Order setOrderData(){
//
//        if (mView == null) return null;
//
//        Order order = getOrder();
//
//        if (order == null) return null;
//
//        order.setOrderType(mView.getOrderType());
//        order.setCustomerCompanyId(mView.getCustomer());
//        order.setCarrierCompanyId(mView.getOrg());
//        order.setCarrierDepartmentId(mView.getOrgDepts());
//        order.setCustomerDepartmentId(mView.getCustomerDept());
//        order.setVehicleTypeId(mView.getCarType());
//        order.setCapacityClassId(mView.getCapacityClass());
//        order.setModelId(mView.getModel());
//        order.setVehicleId(mView.getCar());
//        order.setPriorityId(mView.getPriority());
//        order.setRentStartDate(mView.getOrderStart());
//        order.setRentEndDate(mView.getOrderFinish());
//        order.setRentTime(mView.getTimeOfUse());
//        order.setRentAddress(mView.getOrderAdress());
//        order.setContactPhone(mView.getPhone());
//        order.setContactPerson(mView.getContactPerson());
//        order.setNumberOfPassengers(mView.getPassengerCount());
//        order.setInform(mView.getSmsInform());
//        order.setComment(mView.getComment());
//        order.setCargoWeight(mView.getCargoWeight());
//
//        order.clearExtParams();
//        order.addExtParam(ExtParameter.PARAM_ShippingName, mView.getShippingName());
//        order.addExtParam(ExtParameter.PARAM_IsLongDuration, mView.getBussinesTrip());
//        order.addExtParam(ExtParameter.PARAM_WorkNearPowerLines, mView.getWorkNearPowerLines());
//        order.addExtParam(ExtParameter.PARAM_Slingers, mView.getSlingers());
//        order.addExtParam(ExtParameter.PARAM_AreaOfWork, mView.getAreaOfWork());
//        order.addExtParam(ExtParameter.PARAM_NatureOfWork, mView.getNatureOfWork());
//        order.addExtParam(ExtParameter.PARAM_Responsible, mView.getResponsible());
//        order.addExtParam(ExtParameter.PARAM_Route, mView.getRoute());
//        order.addExtParam(ExtParameter.PARAM_Service, mView.getOrderService());
//
//        return order;
//    }

//    public interface OrderView {
//
//        void setOrderTitle(String num, Long date);
//        void setOrderType(IUIFieldDataModel IUIFieldDataModel);
//        void setCustomer(IUIFieldDataModel IUIFieldDataModel);
//        void setOrg(IUIFieldDataModel IUIFieldDataModel);
//        void setOrgDepts(IUIFieldDataModel IUIFieldDataModel);
//        void setCustomerDept(IUIFieldDataModel IUIFieldDataModel);
//        void setCarType(IUIFieldDataModel IUIFieldDataModel);
//        void setCapacityClass(IUIFieldDataModel IUIFieldDataModel);
//
//        void setModel(IUIFieldDataModel IUIFieldDataModel);
//        void setCar(IUIFieldDataModel IUIFieldDataModel);
//        void setPriority(IUIFieldDataModel IUIFieldDataModel);
//
//        void setOrderState(String id);
//        void setOrderStart(Long date);
//        void setOrderFinish(Long date);
//        void setTimeOfUse(int time);
//        void setOrderAdress(String addr);
//        void setPhone(String phone);
//        void setPassengerCount(int count);
//        void setSmsInform(boolean val);
//        void setComment(String comment);
//        void setContactPerson(String person);
//        void setMotorcade(String id);
//        void setCargoWeight(float weight);
//        void setShippingName(String shipping);
//        void setBussinesTrip(Boolean val);
//        void setWorkNearPowerLines(Boolean val);
//        void setAreaOfWork(String val);
//        void setSlingers(String val);
//        void setNatureOfWork(String val);
//        void setResponsible(String val);
//        void setRoute(String val);
//        void setOrderService(int id);
//
//
//        String getOrderType();
//        String getCustomer();
//        String getOrg();
//        String getOrgDepts();
//        String getCustomerDept();
//        String getCarType();
//        String getCapacityClass();
//        String getModel();
//        String getCar();
//        String getPriority();
//        Long getOrderStart();
//        Long getOrderFinish();
//        int getTimeOfUse();
//        String getOrderAdress();
//        String getPhone();
//        int getPassengerCount();
//        Inform getSmsInform();
//        String getComment();
//        String getContactPerson();
//        float getCargoWeight();
//        String getShippingName();
//        Boolean getBussinesTrip();
//        Boolean getWorkNearPowerLines();
//        String getSlingers();
//        String getNatureOfWork();
//        String getAreaOfWork();
//        String getResponsible();
//        String getRoute();
//        int getOrderService();
//
//        void setReadOnly(boolean readOnly);
//    }

    protected WDData getData(){
        return WDData.getInstance();
    }

}
