package ru.wdsoft.api.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.UUID;

import ru.wdsoft.api.ApiSerializable;
import ru.wdsoft.utils.CastUtils;
import ru.wdsoft.utils.LogUtils;

/**
 * Created by slaventii@mail.ru on 21.02.2019.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Order extends ApiSerializable {
    /**
     * Для логирования
     */
    private final String LOG_PREFIX = this.getClass().getName() + " -- ";

    @JsonProperty("Id")
    private UUID Id;

    @JsonProperty("Date")
    private String Date;

    @JsonProperty("Files")
    private ApiFile[] Files;

    @JsonProperty("Group")
    private String Group;

    @JsonProperty("Price")
    private Float Price;

    @JsonProperty("State")
    private String State;

    @JsonProperty("Inform")
    private Inform[] mInform;

    @JsonProperty("Number")
    private String Number;

    @JsonProperty("Offers")
    private Offer[] Offers;

    @JsonProperty("Rating")
    private Float Rating;

    @JsonProperty("UserId")
    private UUID UserId;

    @JsonProperty("Comment")
    private String Comment;

    @JsonProperty("History")
    private OrderHistory[] History;

    @JsonProperty("Mileage")
    private Integer Mileage;

    @JsonProperty("ModelId")
    private UUID ModelId;

    @JsonProperty("VatRate")
    private Float VatRate;

    @JsonProperty("WithVAT")
    private Boolean WithVAT;

    @JsonProperty("BodyType")
    private String BodyType;

    @JsonProperty("Priority")
    private String Priority;

    @JsonProperty("RentTime")
    private Integer RentTime;

    @JsonProperty("RentType")
    private String RentType;

    @JsonProperty("UserName")
    private String UserName;

    @JsonProperty("ExtNumber")
    private String ExtNumber;

    @JsonProperty("IsDeleted")
    private Boolean IsDeleted;

    @JsonProperty("IsProtest")
    private Boolean IsProtest;

    @JsonProperty("ModelName")
    private String ModelName;

    @JsonProperty("OrderType")
    private String OrderType;

    @JsonProperty("RegNumber")
    private String RegNumber;

    @JsonProperty("StateName")
    private String StateName;

    @JsonProperty("VehicleId")
    private UUID VehicleId;

    @JsonProperty("CreateDate")
    private String CreateDate;

    @JsonProperty("LocalityId")
    private UUID LocalityId;

    @JsonProperty("ModifyDate")
    private String ModifyDate;

    @JsonProperty("OfferCount")
    private Integer OfferCount;

    @JsonProperty("PriorityId")
    private UUID PriorityId;

    @JsonProperty("CargoWeight")
    private Float CargoWeight;

    @JsonProperty("CarrierCode")
    private Integer CarrierCode;

    @JsonProperty("RentAddress")
    private String RentAddress;

    @JsonProperty("RentEndDate")
    private String RentEndDate;

    @JsonProperty("TotalAmount")
    private Float TotalAmount;

    @JsonProperty("VehicleName")
    private String VehicleName;

    @JsonProperty("ConsumerType")
    private String ConsumerType;

    @JsonProperty("ContactPhone")
    private String ContactPhone;

    @JsonProperty("LocalityName")
    private String LocalityName;

    @JsonProperty("PriorityName")
    private String PriorityName;

    @JsonProperty("ContactPerson")
    private String ContactPerson;

    @JsonProperty("EquipmentTime")
    private Integer EquipmentTime;

    @JsonProperty("ExtParameters")
    private ExtParameter[] ExtParameters;

    @JsonProperty("OrderTypeName")
    private String OrderTypeName;

    @JsonProperty("PlannedAmount")
    private Float PlannedAmount;

    @JsonProperty("RentStartDate")
    private String RentStartDate;

    @JsonProperty("VehicleTypeId")
    private UUID VehicleTypeId;

    @JsonProperty("CarrierMileage")
    private Integer CarrierMileage;

    @JsonProperty("CustomerPerson")
    private Person CustomerPerson;

    @JsonProperty("CustomerRating")
    private Float CustomerRating;

    @JsonProperty("VehicleKmPrice")
    private Float VehicleKmPrice;

    @JsonProperty("CapacityClassId")
    private UUID CapacityClassId;

    @JsonProperty("CarrierRentTime")
    private Integer CarrierRentTime;

    @JsonProperty("CustomerMileage")
    private Integer CustomerMileage;

    @JsonProperty("VehicleTypeName")
    private String VehicleTypeName;

    @JsonProperty("CarrierCompanyId")
    private UUID CarrierCompanyId;

    @JsonProperty("CarrierManagerId")
    private UUID CarrierManagerId;

    @JsonProperty("CustomerRentTime")
    private Integer CustomerRentTime;

    @JsonProperty("CustomerCompanyId")
    private UUID CustomerCompanyId;

    @JsonProperty("CarrierCompanyName")
    private String CarrierCompanyName;

    @JsonProperty("CarrierManagerName")
    private String CarrierManagerName;

    @JsonProperty("CarrierTotalAmount")
    private Float CarrierTotalAmount;

    @JsonProperty("NumberOfPassengers")
    private Integer NumberOfPassengers;

    @JsonProperty("VehicleHourlyPrice")
    private Float VehicleHourlyPrice;

    @JsonProperty("CarrierCompanyEmail")
    private String CarrierCompanyEmail;

    @JsonProperty("CarrierCompanyPhone")
    private String CarrierCompanyPhone;

    @JsonProperty("CarrierDepartmentId")
    private UUID CarrierDepartmentId;

    @JsonProperty("CarrierManagerEmail")
    private String CarrierManagerEmail;

    @JsonProperty("CarrierManagerPhone")
    private String CarrierManagerPhone;

    @JsonProperty("CustomerCompanyCode")
    private Integer CustomerCompanyCode;

    @JsonProperty("CustomerCompanyName")
    private String CustomerCompanyName;

    @JsonProperty("RouteRentParameters")
    private RentParameters RouteRentParameters;

    @JsonProperty("CarrierEquipmentTime")
    private Integer CarrierEquipmentTime;

    @JsonProperty("CustomerDepartmentId")
    private UUID CustomerDepartmentId;

    @JsonProperty("CarrierDepartmentName")
    private String CarrierDepartmentName;

    @JsonProperty("CustomerEquipmentTime")
    private Integer CustomerEquipmentTime;

    @JsonProperty("CustomerDepartmentName")
    private String CustomerDepartmentName;

    private Boolean isChanged = false;

    private Boolean isNew = false;



    @JsonIgnore
    public Offer[] getOffers() {
        return Offers;
    }
    @JsonIgnore
    public void setOffers(Offer[] offers) {
        Offers = offers;
    }

    @JsonIgnore
    public ApiFile[] getFiles() {
        return Files;
    }
    @JsonIgnore
    public void setFiles(ApiFile[] files) {
        Files = files;
    }
    @JsonIgnore
    public String getGroup() {
        return fromJsonProp(Group);
    }
    @JsonIgnore
    public void setGroup(String group) {
        Group = toJsonProp(group);
    }
    @JsonIgnore
    public Float getPrice() {
        return fromJsonProp(Price);
    }
    @JsonIgnore
    public void setPrice(Float price) {
        Price = price;
    }
    @JsonIgnore
    public Integer getMileage() {
        return fromJsonProp(Mileage);
    }
    @JsonIgnore
    public void setMileage(Integer mileage) {
        Mileage = mileage;
    }
    @JsonIgnore
    public Float getVatRate() {
        return fromJsonProp(VatRate);
    }
    @JsonIgnore
    public void setVatRate(Float vatRate) {
        VatRate = vatRate;
    }
    @JsonIgnore
    public Boolean withVAT() {
        return fromJsonProp(WithVAT);
    }
    @JsonIgnore
    public void withVAT(Boolean withVAT) {
        WithVAT = withVAT;
    }
    @JsonIgnore
    public String getBodyType() {
        return fromJsonProp(BodyType);
    }
    @JsonIgnore
    public void setBodyType(String bodyType) {
        BodyType = toJsonProp(bodyType);
    }
    @JsonIgnore
    public String getExtNumber() {
        return fromJsonProp(ExtNumber);
    }
    @JsonIgnore
    public void setExtNumber(String extNumber) {
        ExtNumber = toJsonProp(extNumber);
    }
    @JsonIgnore
    public String getRegNumber() {
        return fromJsonProp(RegNumber);
    }
    @JsonIgnore
    public void setRegNumber(String regNumber) {
        RegNumber = toJsonProp(regNumber);
    }
    @JsonIgnore
    public Long getCreateDate() {
        return fromDateTimeJsonProp(CreateDate);
    }
    @JsonIgnore
    public void setCreateDate(Long createDate) {
        CreateDate = toDateTimeJsonProp(createDate);
    }
    @JsonIgnore
    public String getLocalityId() {
        return fromJsonProp(LocalityId);
    }
    @JsonIgnore
    public void setLocalityId(String localityId) {
        LocalityId = toUUIDJsonProp(localityId);
    }
    @JsonIgnore
    public Long getModifyDate() {
        return fromDateTimeJsonProp(ModifyDate);
    }
    @JsonIgnore
    public void setModifyDate(Long modifyDate) {
        ModifyDate = toDateTimeJsonProp(modifyDate);
    }
    @JsonIgnore
    public Integer getOfferCount() {
        return fromJsonProp(OfferCount);
    }
    @JsonIgnore
    public void setOfferCount(Integer offerCount) {
        OfferCount = offerCount;
    }
    @JsonIgnore
    public Integer getCarrierCode() {
        return fromJsonProp(CarrierCode);
    }
    @JsonIgnore
    public void setCarrierCode(Integer carrierCode) {
        CarrierCode = carrierCode;
    }
    @JsonIgnore
    public Float getTotalAmount() {
        return fromJsonProp(TotalAmount);
    }
    @JsonIgnore
    public void setTotalAmount(Float totalAmount) {
        TotalAmount = totalAmount;
    }
    @JsonIgnore
    public String getLocalityName() {
        return fromJsonProp(LocalityName);
    }
    @JsonIgnore
    public void setLocalityName(String localityName) {
        LocalityName = toJsonProp(localityName);
    }
    @JsonIgnore
    public Integer getEquipmentTime() {
        return fromJsonProp(EquipmentTime);
    }
    @JsonIgnore
    public void setEquipmentTime(Integer equipmentTime) {
        EquipmentTime = equipmentTime;
    }
    @JsonIgnore
    public String getOrderTypeName() {
        return fromJsonProp(OrderTypeName);
    }
    @JsonIgnore
    public void setOrderTypeName(String orderTypeName) {
        OrderTypeName = toJsonProp(orderTypeName);
    }
    @JsonIgnore
    public Float getPlannedAmount() {
        return fromJsonProp(PlannedAmount);
    }
    @JsonIgnore
    public void setPlannedAmount(Float plannedAmount) {
        PlannedAmount = plannedAmount;
    }
    @JsonIgnore
    public Integer getCarrierMileage() {
        return fromJsonProp(CarrierMileage);
    }
    @JsonIgnore
    public void setCarrierMileage(Integer carrierMileage) {
        CarrierMileage = carrierMileage;
    }
    @JsonIgnore
    public Person getCustomerPerson() {
        return CustomerPerson;
    }
    @JsonIgnore
    public void setCustomerPerson(Person customerPerson) {
        CustomerPerson = customerPerson;
    }
    @JsonIgnore
    public Float getVehicleKmPrice() {
        return fromJsonProp(VehicleKmPrice);
    }
    @JsonIgnore
    public void setVehicleKmPrice(Float vehicleKmPrice) {
        VehicleKmPrice = vehicleKmPrice;
    }
    @JsonIgnore
    public Integer getCarrierRentTime() {
        return fromJsonProp(CarrierRentTime);
    }
    @JsonIgnore
    public void setCarrierRentTime(Integer carrierRentTime) {
        CarrierRentTime = carrierRentTime;
    }
    @JsonIgnore
    public Integer getCustomerMileage() {
        return fromJsonProp(CustomerMileage);
    }
    @JsonIgnore
    public void setCustomerMileage(Integer customerMileage) {
        CustomerMileage = customerMileage;
    }
    @JsonIgnore
    public String getVehicleTypeName() {
        return fromJsonProp(VehicleTypeName);
    }
    @JsonIgnore
    public void setVehicleTypeName(String vehicleTypeName) {
        VehicleTypeName = toJsonProp(vehicleTypeName);
    }
    @JsonIgnore
    public String getCarrierManagerId() {
        return fromJsonProp(CarrierManagerId);
    }
    @JsonIgnore
    public void setCarrierManagerId(String carrierManagerId) {
        CarrierManagerId = toUUIDJsonProp(carrierManagerId);
    }
    @JsonIgnore
    public Integer getCustomerRentTime() {
        return fromJsonProp(CustomerRentTime);
    }
    @JsonIgnore
    public void setCustomerRentTime(Integer customerRentTime) {
        CustomerRentTime = customerRentTime;
    }
    @JsonIgnore
    public String getCarrierManagerName() {
        return fromJsonProp(CarrierManagerName);
    }
    @JsonIgnore
    public void setCarrierManagerName(String carrierManagerName) {
        CarrierManagerName = toJsonProp(carrierManagerName);
    }
    @JsonIgnore
    public Float getCarrierTotalAmount() {
        return fromJsonProp(CarrierTotalAmount);
    }
    @JsonIgnore
    public void setCarrierTotalAmount(Float carrierTotalAmount) {
        CarrierTotalAmount = fromJsonProp(carrierTotalAmount);
    }
    @JsonIgnore
    public Float getVehicleHourlyPrice() {
        return fromJsonProp(VehicleHourlyPrice);
    }
    @JsonIgnore
    public void setVehicleHourlyPrice(Float vehicleHourlyPrice) {
        VehicleHourlyPrice = vehicleHourlyPrice;
    }
    @JsonIgnore
    public String getCarrierCompanyEmail() {
        return fromJsonProp(CarrierCompanyEmail);
    }
    @JsonIgnore
    public void setCarrierCompanyEmail(String carrierCompanyEmail) {
        CarrierCompanyEmail = toJsonProp(carrierCompanyEmail);
    }
    @JsonIgnore
    public String getCarrierCompanyPhone() {
        return fromJsonProp(CarrierCompanyPhone);
    }
    @JsonIgnore
    public void setCarrierCompanyPhone(String carrierCompanyPhone) {
        CarrierCompanyPhone = toJsonProp(carrierCompanyPhone);
    }
    @JsonIgnore
    public String getCarrierManagerEmail() {
        return fromJsonProp(CarrierManagerEmail);
    }
    @JsonIgnore
    public void setCarrierManagerEmail(String carrierManagerEmail) {
        CarrierManagerEmail = fromJsonProp(carrierManagerEmail);
    }
    @JsonIgnore
    public String getCarrierManagerPhone() {
        return fromJsonProp(CarrierManagerPhone);
    }
    @JsonIgnore
    public void setCarrierManagerPhone(String carrierManagerPhone) {
        CarrierManagerPhone = fromJsonProp(carrierManagerPhone);
    }
    @JsonIgnore
    public Integer getCustomerCompanyCode() {
        return fromJsonProp(CustomerCompanyCode);
    }
    @JsonIgnore
    public void setCustomerCompanyCode(Integer customerCompanyCode) {
        CustomerCompanyCode = customerCompanyCode;
    }
    @JsonIgnore
    public RentParameters getRouteRentParameters() {
        return RouteRentParameters;
    }
    @JsonIgnore
    public void setRouteRentParameters(RentParameters routeRentParameters) {
        RouteRentParameters = routeRentParameters;
    }
    @JsonIgnore
    public Integer getCarrierEquipmentTime() {
        return fromJsonProp(CarrierEquipmentTime);
    }
    @JsonIgnore
    public void setCarrierEquipmentTime(Integer carrierEquipmentTime) {
        CarrierEquipmentTime = carrierEquipmentTime;
    }
    @JsonIgnore
    public Integer getCustomerEquipmentTime() {
        return fromJsonProp(CustomerEquipmentTime);
    }
    @JsonIgnore
    public void setCustomerEquipmentTime(Integer customerEquipmentTime) {
        CustomerEquipmentTime = customerEquipmentTime;
    }







    public Order clone(){

        Order order = new Order();

        order.setOrderType(this.getOrderType());
        order.setCustomerCompanyId(this.getCustomerCompanyId());
        order.setCarrierCompanyId(this.getCarrierCompanyId());
        order.setCarrierDepartmentId(this.getCarrierDepartmentId());
        order.setCustomerDepartmentId(this.getCustomerDepartmentId());
        order.setVehicleTypeId(this.getVehicleTypeId());
        order.setCapacityClassId(this.getCapacityClassId());
        order.setModelId(this.getModelId());
        order.setVehicleId(this.getVehicleId());

        order.setPriority(this.getPriority());
        order.setPriorityId(this.getPriorityId());
        order.setPriorityName(this.getPriorityName());

        order.setRentStartDate(this.getRentStartDate());
        order.setRentEndDate(this.getRentEndDate());
        order.setRentTime(this.getRentTime());
        order.setRentAddress(this.getRentAddress());
        order.setContactPhone(this.getContactPhone());
        order.setNumberOfPassengers(this.getNumberOfPassengers());
        order.setInform(this.getSmsInform());
        order.setComment(this.getComment());
        order.setContactPerson(this.getContactPerson());
        order.setCargoWeight(this.getCargoWeight());


        order.setRentType(this.getRentType());
        order.setUserId(this.getUserId());
        order.setUserName(this.getUserName());
        order.setDeleted(false);
        order.setNumber("");
        order.setConsumerType(this.getConsumerType());
        order.setState(this.getState());
        order.setStateName(this.getStateName());
        order.setExtParameters(this.getExtParameters());






        return order;
    }

    @JsonIgnore
    public void setCapacityClassId(String capacityClassId) {
        CapacityClassId = toUUIDJsonProp(capacityClassId);
    }

    @JsonIgnore
    public void setCargoWeight(Float cargoWeight) {
        CargoWeight = cargoWeight;
    }

    @JsonIgnore
    public void setCarrierCompanyId(String carrierCompanyId) {
        CarrierCompanyId = toUUIDJsonProp(carrierCompanyId);
    }

    @JsonIgnore
    public String getCarrierCompanyName() {
        return fromJsonProp(CarrierCompanyName);
    }

    @JsonIgnore
    public void setCarrierCompanyName(String carrierCompanyName) {
        CarrierCompanyName = toJsonProp(carrierCompanyName);
    }

    @JsonIgnore
    public void setCarrierDepartmentId(String carrierDepartmentId) {
        CarrierDepartmentId = toUUIDJsonProp(carrierDepartmentId);
    }

    @JsonIgnore
    public void setComment(String comment) {
        Comment = toJsonProp(comment);
    }

    @JsonIgnore
    public void setContactPerson(String contactPerson) {
        ContactPerson = toJsonProp(contactPerson);
    }

    @JsonIgnore
    public void setContactPhone(String contactPhone) {
        ContactPhone = toJsonProp(contactPhone);
    }

    @JsonIgnore
    public void setCustomerCompanyId(String customerCompanyId) {
        CustomerCompanyId = toUUIDJsonProp(customerCompanyId);
    }

    @JsonIgnore
    public String getCustomerCompanyName() {
        return fromJsonProp(CustomerCompanyName);
    }

    @JsonIgnore
    public void setCustomerCompanyName(String customerCompanyName) {
        CustomerCompanyName = toJsonProp(customerCompanyName);
    }

    @JsonIgnore
    public void setCustomerDepartmentId(String customerDepartmentId) {
        CustomerDepartmentId = toUUIDJsonProp(customerDepartmentId);
    }

    @JsonIgnore
    public void setCustomerRating(Float customerRating) {
        CustomerRating = customerRating;
    }

    @JsonIgnore
    public void setDate(Long date) {
        Date = toDateTimeJsonProp(date);
    }

    @JsonIgnore
    public void setId(String id) {
        Id = toUUIDJsonProp(id);
    }
    @JsonIgnore
    public void setDeleted(Boolean deleted) {
        IsDeleted = deleted;
    }

    @JsonIgnore
    public void setProtest(Boolean protest) {
        IsProtest = protest;
    }

    @JsonIgnore
    public void setModelId(String modelId) {
        ModelId = toUUIDJsonProp(modelId);
    }

    @JsonIgnore
    public void setNumber(String number) {
        Number = toJsonProp(number);
    }

    @JsonIgnore
    public void setNumberOfPassengers(Integer numberOfPassengers) {
        NumberOfPassengers = numberOfPassengers;
    }

    @JsonIgnore
    public void setOrderType(String orderType) {
        OrderType = toJsonProp(orderType);
    }

    @JsonIgnore
    public void setPriority(String priority) {
        Priority = toJsonProp(priority);
    }

    @JsonIgnore
    public void setRentType(String rentType) {
        RentType = toJsonProp(rentType);
    }

    @JsonIgnore
    public void setState(String state) {
        State = toJsonProp(state);
    }

    @JsonIgnore
    public void setUserId(String userId) {
        UserId = toUUIDJsonProp(userId);
    }

    @JsonIgnore
    public void setVehicleId(String vehicleId) {
        VehicleId = toUUIDJsonProp(vehicleId);
    }

    @JsonIgnore
    public void setVehicleTypeId(String vehicleTypeId) {
        VehicleTypeId = toUUIDJsonProp(vehicleTypeId);
    }

    @JsonIgnore
    public String getCapacityClassId() {
        return fromJsonProp(CapacityClassId);
    }

    @JsonIgnore
    public Float getCargoWeight() {
        return fromJsonProp(CargoWeight);
    }

    @JsonIgnore
    public String getCarrierCompanyId() {
        return fromJsonProp(CarrierCompanyId);
    }

    @JsonIgnore
    public String getCarrierDepartmentId() {
        return fromJsonProp(CarrierDepartmentId);
    }

    @JsonIgnore
    public String getComment() {
        return fromJsonProp(Comment);
    }

    @JsonIgnore
    public String getContactPerson() {
        return fromJsonProp(ContactPerson);
    }

    @JsonIgnore
    public String getContactPhone() {
        return fromJsonProp(ContactPhone);
    }

    @JsonIgnore
    public String getCustomerCompanyId() {
        return fromJsonProp(CustomerCompanyId);
    }

    @JsonIgnore
    public String getCustomerDepartmentId() {
        return fromJsonProp(CustomerDepartmentId);
    }

    @JsonIgnore
    public Float getCustomerRating() {
        return fromJsonProp(CustomerRating);
    }

    @JsonIgnore
    public Long getDate() {
        return fromDateTimeJsonProp(Date);
    }

    @JsonIgnore
    public void clearCreateDate() {
        this.Date = null;
    }

    @JsonIgnore
    public String getId() {
        return fromJsonProp(Id);
    }

    @JsonIgnore
    public Boolean isDeleted() {
        return fromJsonProp(IsDeleted);
    }

    @JsonIgnore
    public Boolean isProtest() {
        return fromJsonProp(IsProtest);
    }

    @JsonIgnore
    public String getModelId() {
        return fromJsonProp(ModelId);
    }

    @JsonIgnore
    public String getNumber() {
        return fromJsonProp(Number);
    }

    @JsonIgnore
    public Integer getNumberOfPassengers() {
        return fromJsonProp(NumberOfPassengers);
    }

    @JsonIgnore
    public String getOrderType() {
        return fromJsonProp(OrderType);
    }

    @JsonIgnore
    public String getPriority() {
        return fromJsonProp(Priority);
    }

    @JsonIgnore
    public String getRentType() {
        return fromJsonProp(RentType);
    }

    @JsonIgnore
    public String getState() {
        return fromJsonProp(State);
    }

    @JsonIgnore
    public String getUserId() {
        return fromJsonProp(UserId);
    }

    @JsonIgnore
    public String getVehicleId() {
        return fromJsonProp(VehicleId);
    }

    @JsonIgnore
    public String getVehicleTypeId() {
        return fromJsonProp(VehicleTypeId);
    }

    @JsonIgnore
    public String getInformString() {

        String str = "";

        ObjectMapper mapper = new ObjectMapper();

        try {
            str = mapper.writeValueAsString(getInform());
        } catch (JsonProcessingException e) {
            LogUtils.debugErrorLog(LOG_PREFIX, e);
        }

        return str;
    }

    @JsonIgnore
    public Inform getSmsInform(){

        if (mInform != null && mInform.length > 0){
            return mInform[0];
        }

        return null;
    }

//    public void setSmsInform(boolean val, String addr){
//
//        if (val) {
//
//            Inform inform = getSmsInform();
//
//            if (inform == null){
//
//                inform = new Inform();
//                inform.setName(Inform.TYPE_SMS);
//                inform.setType(Inform.TYPE_SMS);
//
//                addInform(inform);
//
//            }
//
//            inform.setAddress(addr);
//
//        } else {
////            if (getSmsInform()) return;
//            deleteInform(Inform.TYPE_SMS);
//        }
//
//    }

    @JsonIgnore
    public Inform[] getInform() {
        return mInform;
    }

    @JsonIgnore
    public void setInform(String inform) {
        this.mInform = CastUtils.getSerializable(inform, Inform[].class);
    }

    @JsonIgnore
    public void setInform(Inform inform) {

        if (inform == null) {
            this.mInform = new Inform[0];
        } else {
            Inform[] informs = new Inform[1];
            informs[0] = inform;

            this.mInform = informs;
        }

    }

    @JsonIgnore
    public void addInform(Inform inform){

        int count = 1;

        if (mInform != null){
            count = mInform.length + 1;
        }

        Inform[] inform2 =  new Inform[count];

        if (mInform != null && mInform.length > 0){
            System.arraycopy(mInform, 0, inform2, 0, mInform.length);
        }

        inform2[count-1] = inform;

        mInform = inform2;
    }

    @JsonIgnore
    public void deleteInform(String type) {

        int pos = 0;

        if (mInform != null){

            for (Inform inform : mInform){

                if (type.equalsIgnoreCase(inform.getType())){
                    deleteInform(pos);
                    break;
                } else {
                    pos++;
                }

            }
        }

    }

    @JsonIgnore
    public void deleteInform(int pos){

        int count = 0;

        if (mInform != null && mInform.length > 0){
            count = mInform.length - 1;
        }

        if (count == 0) {
            mInform = null;
            return;
        }

        if (pos > count){
            return;
        }

        Inform[] inform2 = new Inform[count];

        int idx = 0;
        for (int i = 0; i < mInform.length; i++){

            if (i != pos){
                inform2[idx] = mInform[i];
                idx++;
            }
        }

        mInform = inform2;
    }

    @JsonIgnore
    public void clearExtParams(){
        ExtParameters = null;
    }


    @JsonIgnore
    public void addExtParam(String key, String val){

        ExtParameter prm = new ExtParameter();
        prm.setKey(key);
        prm.setValue(val);

        addExtParam(prm);

    }

    @JsonIgnore
    public void addExtParam(String key, Integer val){

        ExtParameter prm = new ExtParameter();
        prm.setKey(key);
        prm.setValue(val);

        addExtParam(prm);

    }

    @JsonIgnore
    public void addExtParam(String key, Boolean val){
        ExtParameter prm = new ExtParameter();
        prm.setKey(key);
        prm.setValue(val);

        addExtParam(prm);
    }

    @JsonIgnore
    public void addExtParam(ExtParameter prm){

        int count = 1;

        if (ExtParameters != null){
            count = ExtParameters.length + 1;
        }

        ExtParameter[] ExtParameters2 =  new ExtParameter[count];

        if (ExtParameters != null && ExtParameters.length > 0){
            System.arraycopy(ExtParameters, 0, ExtParameters2, 0, ExtParameters.length);
        }

        ExtParameters2[count-1] = prm;

        ExtParameters = ExtParameters2;
    }

    @JsonIgnore
    public String getExtParametersString() {

        String str = "";

        ObjectMapper mapper = new ObjectMapper();

        try {
            str = mapper.writeValueAsString(getExtParameters());
        } catch (JsonProcessingException e) {
            LogUtils.debugErrorLog(LOG_PREFIX, e);
        }

        return str;
    }

    @JsonIgnore
    public ExtParameter[] getExtParameters() {
        return ExtParameters;
    }





    @JsonIgnore
    public void setExtParameters(ExtParameter[] extParameters) {
        ExtParameters = extParameters;
    }

    @JsonIgnore
    public void setExtParameters(String extParameters) {
        this.ExtParameters = CastUtils.getSerializable(extParameters, ExtParameter[].class);
    }



//    @JsonIgnore
//    public String getRentParametersString() {
//
//        String str = "";
//
//        ObjectMapper mapper = new ObjectMapper();
//
//        try {
//            str = mapper.writeValueAsString(getRentParameters());
//        } catch (JsonProcessingException e) {
//            Utils.debugErrorLog(LOG_PREFIX, e);
//        }
//
//        return str;
//    }
//
//    @JsonIgnore
//    public RentParameters getRentParameters() {
//        return mRentParameters;
//    }
//
//    @JsonIgnore
//    public void setRentParameters(RentParameters rentParameters) {
//        this.mRentParameters = rentParameters;
//    }
//
//    @JsonIgnore
//    public void setRentParameters(String rentParameters) {
//        this.mRentParameters = Utils.getSerializable(rentParameters, RentParameters.class);
//    }


    @JsonIgnore
    public String getRentAddress() {
        return fromJsonProp(RentAddress);
    }

    @JsonIgnore
    public void setRentAddress(String address) {
        RentAddress = toJsonProp(address);
    }

    @JsonIgnore
    public Long getRentStartDate() {
        return fromDateTimeJsonProp(RentStartDate);
    }

    @JsonIgnore
    public void setRentStartDate(Long date) {
        RentStartDate = toDateTimeJsonProp(date);
    }

    @JsonIgnore
    public Long getRentEndDate() {
        return fromDateTimeJsonProp(RentEndDate);
    }

    @JsonIgnore
    public void setRentEndDate(Long endDate) {
        RentEndDate = toDateTimeJsonProp(endDate);
    }

    @JsonIgnore
    public Integer getRentTime() {
        return fromJsonProp(RentTime);
    }

    @JsonIgnore
    public void setRentTime(Integer rentTime) {
        RentTime = rentTime;
    }

    @JsonIgnore
    public String getConsumerType() {
        return fromJsonProp(ConsumerType);
    }

    @JsonIgnore
    public void setConsumerType(String consumerType) {
        ConsumerType = toJsonProp(consumerType);
    }

    @JsonIgnore
    public Boolean isChanged() {
        return fromJsonProp(isChanged);
    }

    @JsonIgnore
    public void setChanged(Boolean changed) {
        isChanged = changed;
    }

    @JsonIgnore
    public String getCarrierDepartmentName() {
        return fromJsonProp(CarrierDepartmentName);
    }

    @JsonIgnore
    public void setCarrierDepartmentName(String carrierDepartmentName) {
        CarrierDepartmentName = toJsonProp(carrierDepartmentName);
    }

    @JsonIgnore
    public String getCustomerDepartmentName() {
        return fromJsonProp(CustomerDepartmentName);
    }

    @JsonIgnore
    public void setCustomerDepartmentName(String customerDepartmentName) {
        CustomerDepartmentName = toJsonProp(customerDepartmentName);
    }

    @JsonIgnore
    public String getModelName() {
        return fromJsonProp(ModelName);
    }

    @JsonIgnore
    public void setModelName(String modelName) {
        ModelName = toJsonProp(modelName);
    }

    @JsonIgnore
    public String getPriorityName() {
        return fromJsonProp(PriorityName);
    }

    @JsonIgnore
    public void setPriorityName(String priorityName) {
        PriorityName = toJsonProp(priorityName);
    }

    @JsonIgnore
    public String getPriorityId() {
        return fromJsonProp(PriorityId);
    }

    @JsonIgnore
    public void setPriorityId(String priorityId) {
        PriorityId = toUUIDJsonProp(priorityId);
    }

    @JsonIgnore
    public String getStateName() {
        return fromJsonProp(StateName);
    }

    @JsonIgnore
    public void setStateName(String stateName) {
        StateName = toJsonProp(stateName);
    }

    @JsonIgnore
    public String getUserName() {
        return fromJsonProp(UserName);
    }

    @JsonIgnore
    public void setUserName(String userName) {
        UserName = toJsonProp(userName);
    }

    @JsonIgnore
    public String getVehicleName() {
        return fromJsonProp(VehicleName);
    }

    @JsonIgnore
    public void setVehicleName(String vehicleName) {
        VehicleName = toJsonProp(vehicleName);
    }

    @JsonIgnore
    public Float getRating() {
        return fromJsonProp(Rating);
    }

    @JsonIgnore
    public void setRating(Float rating) {
        Rating = rating;
    }

    @JsonIgnore
    public OrderHistory[] getHistory() {
        return History;
    }

    @JsonIgnore
    public void setHistory(OrderHistory[] history) {
        History = history;
    }

    @JsonIgnore
    public Boolean isNew() {
        return isNew;
    }

    @JsonIgnore
    public void setNew(Boolean aNew) {
        isNew = aNew;
    }



}
