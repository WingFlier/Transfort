package ru.wdsoft.api.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

import ru.wdsoft.api.ApiSerializable;

/**
 * Created by slavntii@mail.ru on 23.02.2019.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Filter extends ApiSerializable {

    //"Работает совместно с AllMineOffers и Vehicles. Если задан и true, то заявки со ссылками на  ТС из списка Vehicles будут добавлены на вывод. (bool)
    @JsonProperty("AddVehicles")
    private Boolean AddVehicles;

    //Если задан, то переключает отбор по правам RightsOfUserId в режим отбора откликов. Если True, то будут выбраны все заявки с откликами, доступными пользователю. Если False - такой же отбор + будут выбраны только заявки где исполнитель не выбран или выбран доступный пользователю исполнитель.   (bool)
    @JsonProperty("AllMineOffers")
    private Boolean AllMineOffers;

    //Если задан и True, то отбираются только заявки созданные в подразделениях доступных пользователю. Спец. правила для исполнителя, при этом,  отключаются (bool)
    @JsonProperty("AsCustomer")
    private Boolean AsCustomer;

    //Если задан и = true, то возвращаются только заявки с пустым перевозчиком. Eсли = false - заявки, где задан перевозчик. Если null - то проверка  значения не делается. (bool)
    @JsonProperty("EmptyCarrier")
    private Boolean EmptyCarrier;

    //Признак группы заявок (guid)
    @JsonProperty("Group")
    private UUID Group;

    // Id заявки. Если задан - остальные атрибуты игнорируются. (guid)
    @JsonProperty("Id")
    private UUID Id;

    // Id региона для поиска заявок (guid)
    @JsonProperty("LocalityIN")
    private UUID[] LocalityIN;

    // Дата/время изменения заявки. Если значение параметра указано, то отбираются заявки измененные после указанного времени (timestamp)
    @JsonProperty("ModifyDate")
    private String ModifyDate;

    // Id ТС. Если массив заполнен то будут выбраны только те заявки, в которых задействованы указанные ТС (guid)
    @JsonProperty("Vehicles")
    private UUID[] Vehicles;

    //Включает/выключает вывод офферов конкурентов. По умолчанию вывод разрешен [true], но если задан AllMineOffers, то умолчание меняется на Не разрешен, т.е. чтобы увитдеть предложения конкурентов, надо явно задать WithConcurentOffers = true (bool)
    @JsonProperty("WithConcurentOffers")
    private Boolean WithConcurentOffers;

    // Фильтрует при True -  только заявки с откликами. False - заявки без откликов. Если не задан - заявки возвращаются вне зависимости от наличия откликов (bool)
    @JsonProperty("WithOffer")
    private Boolean WithOffer;

    // Если задан и true то выводятся только те заявки, в которых нет откликов от заданных правами компаний. По умолчанию false. (bool)
    @JsonProperty("WithoutMineOffers")
    private Boolean WithoutMineOffers;

    @JsonProperty("CarrierCompanyIN")
    private UUID[] CarrierCompanyIN;

    @JsonProperty("CarrierDepartmentIN")
    private UUID[] CarrierDepartmentIN;

    @JsonProperty("CarrierDepartmentId")
    private UUID CarrierDepartmentId;

    @JsonProperty("ConsumerType")
    private String ConsumerType;

    @JsonProperty("CreateDateFrom")
    private String CreateDateFrom;

    @JsonProperty("CreateDateTo")
    private String CreateDateTo;

    @JsonProperty("CustomerCompanyIN")
    private UUID[] CustomerCompanyIN;

    @JsonProperty("CustomerDepartmentIN")
    private UUID[] CustomerDepartmentIN;

    @JsonProperty("CustomerDepartmentId")
    private UUID CustomerDepartmentId;

    @JsonProperty("Date")
    private String Date;

    @JsonProperty("DateFrom")
    private String DateFrom;

    @JsonProperty("DateTo")
    private String DateTo;

    @JsonProperty("IsCustomerReason")
    private Boolean IsCustomerReason;

    @JsonProperty("IsValidForCalc")
    private Boolean IsValidForCalc;

    @JsonProperty("Limit")
    private Integer Limit;

    @JsonProperty("Number")
    private String Number;

    @JsonProperty("Offset")
    private Integer Offset;

    @JsonProperty("OrderType")
    private String OrderType;

    @JsonProperty("PriorityId")
    private UUID PriorityId;

    @JsonProperty("RentType")
    private String RentType;

    @JsonProperty("RightsOfUserId")
    private UUID RightsOfUserId;

    @JsonProperty("ShowHistory")
    private Boolean ShowHistory;

    @JsonProperty("ShowOffers")
    private Boolean ShowOffers;

    @JsonProperty("ShowTicket")
    private Boolean ShowTicket;

    @JsonProperty("SortOrder")
    private String SortOrder;

    @JsonProperty("State")
    private String[] State;

    @JsonProperty("UserId")
    private UUID UserId;

    @JsonProperty("VehicleTypeId")
    private UUID VehicleTypeId;

    @JsonProperty("WithCount")
    private Boolean WithCount;

    @JsonIgnore
    public Boolean addVehicles() {
        return fromJsonProp(AddVehicles);
    }
    @JsonIgnore
    public void addVehicles(Boolean addVehicles) {
        AddVehicles = addVehicles;
    }
    @JsonIgnore
    public Boolean allMineOffers() {
        return fromJsonProp(AllMineOffers);
    }
    @JsonIgnore
    public void allMineOffers(Boolean allMineOffers) {
        AllMineOffers = allMineOffers;
    }
    @JsonIgnore
    public Boolean asCustomer() {
        return fromJsonProp(AsCustomer);
    }
    @JsonIgnore
    public void asCustomer(Boolean asCustomer) {
        AsCustomer = asCustomer;
    }
    @JsonIgnore
    public Boolean emptyCarrier() {
        return fromJsonProp(EmptyCarrier);
    }
    @JsonIgnore
    public void emptyCarrier(Boolean emptyCarrier) {
        EmptyCarrier = emptyCarrier;
    }
    @JsonIgnore
    public String getGroup() {
        return fromJsonProp(Group);
    }
    @JsonIgnore
    public void setGroup(String group) {
        Group = toUUIDJsonProp(group);
    }
    @JsonIgnore
    public String getId() {
        return fromJsonProp(Id);
    }
    @JsonIgnore
    public void setId(String id) {
        Id = toUUIDJsonProp(id);
    }
    @JsonIgnore
    public UUID[] getLocalityIN() {
        return LocalityIN;
    }
    @JsonIgnore
    public void setLocalityIN(UUID[] localityIN) {
        LocalityIN = localityIN;
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
    public UUID[] getVehicles() {
        return Vehicles;
    }
    @JsonIgnore
    public void setVehicles(UUID[] vehicles) {
        Vehicles = vehicles;
    }
    @JsonIgnore
    public Boolean withConcurentOffers() {
        return fromJsonProp(WithConcurentOffers);
    }
    @JsonIgnore
    public void withConcurentOffers(Boolean withConcurentOffers) {
        WithConcurentOffers = withConcurentOffers;
    }
    @JsonIgnore
    public Boolean withOffer() {
        return fromJsonProp(WithOffer);
    }
    @JsonIgnore
    public void withOffer(Boolean withOffer) {
        WithOffer = withOffer;
    }
    @JsonIgnore
    public Boolean withoutMineOffers() {
        return fromJsonProp(WithoutMineOffers);
    }
    @JsonIgnore
    public void withoutMineOffers(Boolean withoutMineOffers) {
        WithoutMineOffers = withoutMineOffers;
    }

    @JsonIgnore
    public UUID getCarrierDepartmentId() {
        return CarrierDepartmentId;
    }
    @JsonIgnore
    public void setCarrierDepartmentId(UUID carrierDepartmentId) {
        CarrierDepartmentId = carrierDepartmentId;
    }
    @JsonIgnore
    public String getConsumerType() {
        return ConsumerType;
    }
    @JsonIgnore
    public void setConsumerType(String consumerType) {
        ConsumerType = consumerType;
    }
    @JsonIgnore
    public String getCreateDateFrom() {
        return CreateDateFrom;
    }
    @JsonIgnore
    public void setCreateDateFrom(String createDateFrom) {
        CreateDateFrom = createDateFrom;
    }
    @JsonIgnore
    public String getCreateDateTo() {
        return CreateDateTo;
    }
    @JsonIgnore
    public void setCreateDateTo(String createDateTo) {
        CreateDateTo = createDateTo;
    }
    @JsonIgnore
    public UUID getCustomerDepartmentId() {
        return CustomerDepartmentId;
    }
    @JsonIgnore
    public void setCustomerDepartmentId(UUID customerDepartmentId) {
        CustomerDepartmentId = customerDepartmentId;
    }
    @JsonIgnore
    public String getDate() {
        return Date;
    }
    @JsonIgnore
    public void setDate(String date) {
        Date = date;
    }
    @JsonIgnore
    public Long getDateFrom() {
        return fromDateTimeJsonProp(DateFrom);
    }
    @JsonIgnore
    public void setDateFrom(Long dateFrom) {
        DateFrom = toDateTimeJsonProp(dateFrom);
    }
    @JsonIgnore
    public String getDateTo() {
        return DateTo;
    }
    @JsonIgnore
    public void setDateTo(String dateTo) {
        DateTo = dateTo;
    }
    @JsonIgnore
    public boolean isCustomerReason() {
        return IsCustomerReason;
    }
    @JsonIgnore
    public void setCustomerReason(boolean customerReason) {
        IsCustomerReason = customerReason;
    }
    @JsonIgnore
    public boolean isValidForCalc() {
        return IsValidForCalc;
    }
    @JsonIgnore
    public void setValidForCalc(boolean validForCalc) {
        IsValidForCalc = validForCalc;
    }
    @JsonIgnore
    public Integer getLimit() {
        return Limit;
    }
    @JsonIgnore
    public void setLimit(Integer limit) {
        Limit = limit;
    }
    @JsonIgnore
    public String getNumber() {
        return Number;
    }
    @JsonIgnore
    public void setNumber(String number) {
        Number = number;
    }
    @JsonIgnore
    public Integer getOffset() {
        return Offset;
    }
    @JsonIgnore
    public void setOffset(Integer offset) {
        Offset = offset;
    }
    @JsonIgnore
    public String getOrderType() {
        return OrderType;
    }
    @JsonIgnore
    public void setOrderType(String orderType) {
        OrderType = orderType;
    }
    @JsonIgnore
    public UUID getPriorityId() {
        return PriorityId;
    }
    @JsonIgnore
    public void setPriorityId(UUID priorityId) {
        PriorityId = priorityId;
    }
    @JsonIgnore
    public String getRentType() {
        return RentType;
    }
    @JsonIgnore
    public void setRentType(String rentType) {
        RentType = rentType;
    }
    @JsonIgnore
    public String getRightsOfUserId() {
        return fromJsonProp(RightsOfUserId);
    }
    @JsonIgnore
    public void setRightsOfUserId(String rightsOfUserId) {
        RightsOfUserId = toUUIDJsonProp(rightsOfUserId);
    }
    @JsonIgnore
    public boolean isShowHistory() {
        return ShowHistory;
    }
    @JsonIgnore
    public void setShowHistory(boolean showHistory) {
        ShowHistory = showHistory;
    }

    @JsonIgnore
    public boolean isShowOffers() {
        return ShowOffers;
    }
    @JsonIgnore
    public void setShowOffers(boolean showOffers) {
        ShowOffers = showOffers;
    }

    @JsonIgnore
    public boolean isShowTicket() {
        return ShowTicket;
    }
    @JsonIgnore
    public void setShowTicket(boolean showTicket) {
        ShowTicket = showTicket;
    }
    @JsonIgnore
    public String getSortOrder() {
        return SortOrder;
    }
    @JsonIgnore
    public void setSortOrder(String sortOrder) {
        SortOrder = sortOrder;
    }
    @JsonIgnore
    public String[] getState() {
        return State;
    }
    @JsonIgnore
    public void setState(String[] state) {
        State = state;
    }
    @JsonIgnore
    public UUID getUserId() {
        return UserId;
    }
    @JsonIgnore
    public void setUserId(UUID userId) {
        UserId = userId;
    }
    @JsonIgnore
    public String getVehicleTypeId() {
        return fromJsonProp(VehicleTypeId);
    }
    @JsonIgnore
    public void setVehicleTypeId(String vehicleTypeId) {
        VehicleTypeId = toUUIDJsonProp(vehicleTypeId);
    }
    @JsonIgnore
    public boolean isWithCount() {
        return WithCount;
    }
    @JsonIgnore
    public void setWithCount(boolean withCount) {
        WithCount = withCount;
    }
    @JsonIgnore
    public UUID[] getCarrierCompanyIN() {
        return CarrierCompanyIN;
    }
    @JsonIgnore
    public void setCarrierCompanyIN(UUID[] carrierCompanyIN) {
        CarrierCompanyIN = carrierCompanyIN;
    }
    @JsonIgnore
    public UUID[] getCarrierDepartmentIN() {
        return CarrierDepartmentIN;
    }
    @JsonIgnore
    public void setCarrierDepartmentIN(UUID[] carrierDepartmentIN) {
        CarrierDepartmentIN = carrierDepartmentIN;
    }
    @JsonIgnore
    public UUID[] getCustomerCompanyIN() {
        return CustomerCompanyIN;
    }
    @JsonIgnore
    public void setCustomerCompanyIN(UUID[] customerCompanyIN) {
        CustomerCompanyIN = customerCompanyIN;
    }
    @JsonIgnore
    public UUID[] getCustomerDepartmentIN() {
        return CustomerDepartmentIN;
    }
    @JsonIgnore
    public void setCustomerDepartmentIN(UUID[] customerDepartmentIN) {
        CustomerDepartmentIN = customerDepartmentIN;
    }
}

