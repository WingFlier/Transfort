package ru.wdsoft.api;

import android.content.Context;

import com.google.android.gms.common.api.Api;

import java.io.Serializable;
import java.util.ArrayList;

import ru.wdsoft.api.models.CancelationReason;
import ru.wdsoft.api.models.CapacityClass;
import ru.wdsoft.api.models.Company;
import ru.wdsoft.api.models.Country;
import ru.wdsoft.api.models.DocumentType;
import ru.wdsoft.api.models.Filter;
import ru.wdsoft.api.models.FilterCompany;
import ru.wdsoft.api.models.FilterVehicle;
import ru.wdsoft.api.models.FilterVehicleModels;
import ru.wdsoft.api.models.FilteredOrders;
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
import ru.wdsoft.db.DBManager;
import ru.wdsoft.db.tables.SQLOrder;
import ru.wdsoft.db.tables.SQLPriorityType;
import ru.wdsoft.main.WDData;
import ru.wdsoft.db.WDDbHelper;
import ru.wdsoft.utils.CastUtils;
import ru.wdsoft.utils.LogUtils;
import ru.wdsoft.utils.NotifyUtils;

/**
 * Created by slaventii@mail.ru on 10.11.2017.
 */

public class BackTask {

    public final static String CONFIG = "System.GetConfig";
    public final static String ORDERPRIORITYTYPES = "Dictionary.GetOrderPriorityTypes";
    public final static String CANCELATIONREASONS = "Dictionary.GetCancelationReasons";
    public final static String DECLINEREASONS = "Dictionary.GetDeclineReasons";
    public final static String CAPACITYCLASS = "Dictionary.GetCapacityClass";
    public final static String VEHICLECONDITIONS = "Dictionary.GetVehicleConditions";
    public final static String VEHICLEDOCUMENTTYPES = "Dictionary.GetVehicleDocumentTypes";
    public final static String DOCUMENTTYPES = "Dictionary.GetDocumentTypes";
    public final static String COMPANYDOCUMENTTYPES = "Dictionary.GetCompanyDocumentTypes";
    public final static String COUNTRIES = "Dictionary.GetCountries";
    public final static String LOCALITIES = "Geography.GetLocalities";
    public final static String OWNERSHIPTYPES = "Dictionary.GetOwnershipTypes";
    public final static String VEHICLETYPES = "Dictionary.GetVehicleTypes";
    public final static String VEHICLEBRANDS = "Dictionary.GetVehicleBrands";
    public final static String VEHICLE_MODELS = "Dictionary.GetVehicleModels";
    public final static String COMPANIES = "Dictionary.GetCompanies";
    public final static String VEHICLES = "Dictionary.GetVehicles";
    public final static String SET_COMPANIES = "Dictionary.SetCompanies";
    public final static String GET_ORDERS = "Orders.GetOrders";
    public final static String SET_ORDERS = "Orders.SetOrders";
    public final static String SET_TOKEN = "Membership.SetToken";
    public final static String ORDERTYPES = "Dictionary.GetOrderTypes";
    public final static String ORDERSTATES = "Dictionary.GetOrderStates";


    /**
     * Для логирования
     */
    private static final String LOG_PREFIX = "BackTask -- ";
    private static final int NOTIFY_ID_UPD_ERR = 10001;


    /**
     * Получение первоначальных настроек для работы приложения
     *
     * @param ctx
     * @return
     */
    public static ApiResponse getConfig(Context ctx) {
        return getApi(ctx).GetConfig();
    }

    /**
     * Обновление заявок
     *
     * @param ctx
     * @param filter
     * @return
     */
    public static ApiResponse updateOrders(Context ctx, Filter filter) {

        ArrayList<Order> orders = SQLOrder.getChangedList();

        if (orders != null && orders.size() > 0) {

            Order[] arrOrders = orders.toArray(new Order[0]);
            ApiResponse respSetOrders = getApi(ctx).apiRequest(SET_ORDERS, arrOrders);

            if (respSetOrders == null) {
                return ApiResponse.errorResponse("Ошибка синхронизации заявок");
            } else if (respSetOrders.isError()) {
                return respSetOrders;
            }
        }

        return getApi(ctx).apiRequest(GET_ORDERS, CastUtils.toString(filter));
    }

    /**
     * Отправка заявок на сервер
     *
     * @return
     */
    public static ApiResponse sendCachedOrders() {

        ArrayList<Order> orders = SQLOrder.getChangedList();

        if (orders != null && orders.size() > 0) {

            Order[] arrOrders = orders.toArray(new Order[0]);
            ApiResponse respSetOrders = getApi().apiRequest(SET_ORDERS, arrOrders);

            if (respSetOrders == null) {
                return ApiResponse.errorResponse("Ошибка синхронизации заявок");
            } else if (respSetOrders.isError()) {
                return respSetOrders;
            }
        }

        return ApiResponse.newResponse(ApiResponse.OK, "");
    }


    /**
     * Обновление справочников
     *
     * @param ctx
     */
    public static void updateNomenclatures(Context ctx) {

        BackTask.apiRequest(ctx, ORDERPRIORITYTYPES);
        BackTask.apiRequest(ctx, CANCELATIONREASONS);
        BackTask.apiRequest(ctx, CAPACITYCLASS);
        BackTask.apiRequest(ctx, VEHICLETYPES);
        BackTask.apiRequest(ctx, VEHICLEBRANDS);
        BackTask.apiRequest(ctx, VEHICLEDOCUMENTTYPES);

        FilterVehicleModels filterModel = new FilterVehicleModels();
        filterModel.typeIdIsNotEmpty(true);
        BackTask.apiRequest(ctx, VEHICLE_MODELS, filterModel);

        FilterCompany filterCompany = new FilterCompany();
        filterCompany.setUserId(getData().getUserId());
        BackTask.apiRequest(ctx, COMPANIES, filterCompany);


        FilterVehicle filterVehicle = new FilterVehicle();
        filterVehicle.setRightsOfUserId(getData().getUserId());
        BackTask.apiRequest(ctx, VEHICLES, filterVehicle);

        BackTask.apiRequest(ctx, VEHICLECONDITIONS);
        BackTask.apiRequest(ctx, DOCUMENTTYPES);
        BackTask.apiRequest(ctx, COUNTRIES);
        BackTask.apiRequest(ctx, LOCALITIES);
        BackTask.apiRequest(ctx, OWNERSHIPTYPES);
        BackTask.apiRequest(ctx, ORDERTYPES);
        BackTask.apiRequest(ctx, ORDERSTATES);

    }

    public static int filterOrders(Context ctx, Filter filter) {

        ApiResponse response = getApi(ctx).apiRequest(GET_ORDERS, filter);

        if (response != null) {

            if (response.isError()) {

                String msgNotify = response.getResponseMessage();

                NotifyUtils.notifyUser(ctx, msgNotify, NOTIFY_ID_UPD_ERR);
                LogUtils.debugErrorLog(LOG_PREFIX, response.getResponseMessage());
                return 0;

            } else {
                FilteredOrders orders = response.getValue(FilteredOrders.class);
                if (orders != null) return orders.getCount();
            }
        }

        return 0;

    }

    public static void updateDb() {
        DBManager.doDbCreate();
    }

    public static ApiResponse apiRequest(Context ctx, String dataType) {
        return apiRequest(ctx, dataType, null);
    }

    public static ApiResponse apiRequest(Context ctx, String dataType, Serializable body) {

        try {

            LogUtils.debugLog(LOG_PREFIX, dataType + " started...");

            ApiResponse response = getApi(ctx).apiRequest(dataType, body);

            if (response != null) {

                if (response.isError()) {

                    String msgNotify = response.getResponseMessage();

                    NotifyUtils.notifyUser(ctx, msgNotify, NOTIFY_ID_UPD_ERR);
                    LogUtils.debugErrorLog(LOG_PREFIX, response.getResponseMessage());

                } else {

                    switch (dataType) {

                        case GET_ORDERS:

                            Order[] orders = response.getValue(Order[].class);
                            getDb().update(orders);
                            break;

                        case ORDERPRIORITYTYPES:

                            PriorityType[] priorityTypes = response.getValue(PriorityType[].class);
                            getDb().update(priorityTypes);
                            break;

                        case CANCELATIONREASONS:

                            CancelationReason[] cancelationReasons
                                    = response.getValue(CancelationReason[].class);
                            getDb().update(cancelationReasons);
                            break;

                        case CAPACITYCLASS:

                            CapacityClass[] capacityClasses
                                    = response.getValue(CapacityClass[].class);
                            getDb(ctx).update(capacityClasses);

                            break;

                        case VEHICLETYPES:

                            VehicleType[] vehicleTypes = response.getValue(VehicleType[].class);
                            getDb(ctx).update(vehicleTypes);

                            break;

                        case VEHICLEBRANDS:

                            VehicleBrand[] vehicleBrands = response.getValue(VehicleBrand[].class);
                            getDb(ctx).update(vehicleBrands);

                            break;

                        case VEHICLEDOCUMENTTYPES:

                            VehicleDocumentType[] vehicleDocumentTypes
                                    = response.getValue(VehicleDocumentType[].class);
                            getDb(ctx).update(vehicleDocumentTypes);

                            break;

                        case VEHICLE_MODELS:

                            VehicleModel[] vehicleModels = response.getValue(VehicleModel[].class);
                            getDb(ctx).update(vehicleModels);

                            break;

                        case COMPANIES:

                            Company[] companies = response.getValue(Company[].class);
                            getDb(ctx).update(companies);

                            break;

                        case VEHICLES:

                            Vehicle[] vehicles = response.getValue(Vehicle[].class);
                            getDb(ctx).update(vehicles);

                            break;

                        case VEHICLECONDITIONS:

                            VehicleCondition[] vehicleConditions
                                    = response.getValue(VehicleCondition[].class);
                            getDb(ctx).update(vehicleConditions);

                            break;

                        case DOCUMENTTYPES:

                            DocumentType[] documentTypes
                                    = response.getValue(DocumentType[].class);
                            getDb(ctx).update(documentTypes);
                            break;

                        case COUNTRIES:

                            Country[] countries = response.getValue(Country[].class);
                            getDb(ctx).update(countries);
                            break;

                        case LOCALITIES:

                            Locality[] localities = response.getValue(Locality[].class);
                            getDb(ctx).update(localities);
                            break;

                        case OWNERSHIPTYPES:

                            OwnershipType[] ownershipTypes = response.getValue(OwnershipType[].class);
                            getDb(ctx).update(ownershipTypes);
                            break;

                        case ORDERTYPES:

                            OrderType[] orderTypes = response.getValue(OrderType[].class);
                            getDb(ctx).update(orderTypes);
                            break;

                        case ORDERSTATES:

                            OrderState[] orderStates = response.getValue(OrderState[].class);
                            getDb(ctx).update(orderStates);
                            break;

                        default:
                            break;
                    }
                }
            }

            return response;
        } catch (Exception e) {
            NotifyUtils.notifyUser(ctx, e.getMessage(), NOTIFY_ID_UPD_ERR);
            LogUtils.debugErrorLog(LOG_PREFIX, e);
        }

        return ApiResponse.errorResponse("Что-то пошло не так! Попробуйте еще раз");

    }


    public static void deleteOrders() {
        SQLOrder.deleteAll();
    }

    public static ArrayList<Order> getOrderList() {
        return SQLOrder.getList(null);
    }

    public static Order getOrder(String id) {
        return SQLOrder.get(id);
    }

    public static String saveOrder(Order order) {
        SQLOrder.update(order);
        return order.getId();
    }


    private static ApiClient getApi(Context ctx) {
        return ApiClient.getInstance(ctx);
    }

    private static ApiClient getApi() {
        return ApiClient.getInstance();
    }


    private static WDDbHelper getDb(Context ctx) {
        return WDDbHelper.getInstance(ctx);
    }

    private static WDDbHelper getDb() {
        return WDDbHelper.getInstance();
    }

    public static WDData getData() {
        return WDData.getInstance();
    }


    public static ApiResponse auth(String login, String passw) {
        return getApi().Auth(login, passw);
    }

    public static ApiResponse resetPass(String confirmCode, String newPassword, String email) {
        return getApi().changePassword(confirmCode, newPassword, email);
    }

    public static ApiResponse passwordRecovery(String string) {
        return getApi().passwordRecovery(string);
    }

    public static ApiResponse registerUser(String phone, String name, String pass, String email) {
        return getApi().registerUser(email, name, true, name, pass, phone);
    }

    public static ApiResponse registerCompany(String ownershipType, String name, String legalName, long inn, long kpp,
                                              String legalAddress, String adress, boolean isCustomer, boolean isSupplier, boolean withVat,
                                              String userId, String phone, String email) {
        return getApi().registerCompany(ownershipType, name, legalName, inn, kpp, legalAddress,
                adress, isCustomer, isSupplier, withVat, userId, phone, email);
    }

    public static ApiResponse getTypes() {
        return getApi().getTypes();
    }

    public static ApiResponse getSuggestions(String name) {
        return getApi().getSuggestions(name);
    }
}


