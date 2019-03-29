package ru.wdsoft.main;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;

import java.util.Timer;
import java.util.TimerTask;

import ru.wdsoft.api.ApiClient;
import ru.wdsoft.api.BackTask;
import ru.wdsoft.db.WDDbHelper;
import ru.wdsoft.utils.LogUtils;

public class WDService extends Service {

    private final String LOG_PREFIX = this.getClass().getName() + " -- ";


    // для релиза в гугл сделать значения
    private int NOMENCLATURE_SYNC_TIME = 60000 * 30;

    private int ORDERS_SYNC_TIME = 60000 * 5;


    private Timer mNomenclatureSyncTask, mOrdersSyncTask;

    private boolean mSyncStarted = false;

    private R24Binder mBinder = new R24Binder();

    public WDService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (mNomenclatureSyncTask != null) {
            mNomenclatureSyncTask.cancel();
            mNomenclatureSyncTask.purge();
            mNomenclatureSyncTask = null;
        }

        if (mOrdersSyncTask != null) {
            mOrdersSyncTask.cancel();
            mOrdersSyncTask.purge();
            mOrdersSyncTask = null;
        }


    }

    /**
     * СИСТЕМНЫЕ ВСПОМОГАТЕЛЬНЫЕ МЕТОДЫ
     */


    protected ApiClient getApi() {
        return ApiClient.getInstance(this);
    }

    public WDDbHelper getDb() {
        return WDDbHelper.getInstance(this);
    }

    public WDData getData() {
        return WDData.getInstance();
    }

    /**
     * МЕТОДЫ СИНХРОНИЗАЦИИ ДАННЫХ
     */

    public void startSyncTasks() {

        if (!mSyncStarted) {

            mNomenclatureSyncTask = new Timer();
            mNomenclatureSyncTask.schedule(new NomenclatureSyncTask(), 0L, NOMENCLATURE_SYNC_TIME);

//            mOrdersSyncTask = new Timer();
//            mOrdersSyncTask.schedule(new OrdersSyncTask(), 0L, ORDERS_SYNC_TIME);

            mSyncStarted = true;

        }
    }


//    private void notifyNewWayDocs(Context ctx, long date){
//
//        Intent notificationIntent = new Intent(ctx.getApplicationContext(), WayDocsActivity.class);
//
//        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK
//                | Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//
//        notificationIntent.putExtra(WayDocsActivity.ARG_WORK_DATE, date);
//
//        Utils.notifyUser(ctx, "У вас есть новые путевые листы", Utils.NOTIFICATION_NEW_WAYDOCS,
//                notificationIntent);
//
//    }


    /**
     * ТАЙМЕР СИНХРОНИЗАЦИИ ЗАЯВОК, ТАЛОНОВ
     */

    private class OrdersSyncTask extends TimerTask {

        @Override
        public void run() {

            LogUtils.debugLog(LOG_PREFIX, "OrdersSyncTask started...");

//            new RunTableSyncTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, BackTask.ORDERS);
        }
    }

    /**
     * ТАЙМЕР СИНХРОНИЗАЦИИ СПРАВОЧНИКОВ
     */

    private class NomenclatureSyncTask extends TimerTask {

        @Override
        public void run() {

            LogUtils.debugLog(LOG_PREFIX, "NomenclatureSyncTask started...");

//            new RunTableSyncTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, BackTask.COMPANIES);
//            new RunTableSyncTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, BackTask.MATERIALS);
//            new RunTableSyncTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, BackTask.VEHICLE_MODELS);
//            new RunTableSyncTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, BackTask.WORK_TYPES);
//            new RunTableSyncTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, BackTask.VEHICLES);
//            new RunTableSyncTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, BackTask.ORDERSTATES);

        }
    }


    /**
     * ФОНОВОЕ ЗАДАНИЕ СИНХРОНИЗАЦИИ ЗАЯВОК, ТАЛОНОВ, ФАЙЛОВ, ОБРАЩЕНИЙ, СПРАВОЧНИКОВ
     */

    private class RunTableSyncTask extends AsyncTask<String, Void, Void>{

        @Override
        protected Void doInBackground(String... params) {

            String dataType = params[0];

            BackTask.apiRequest(WDService.this, dataType);

            return null;
        }
    }

    /**
     * БИНДЕР, ИСПОЛЬЗУЕТСЯ ДЛЯ ДОСТУПА К МЕТОДАМ СЕРВИСА
     */

    public class R24Binder extends Binder {
        public WDService getService() {
            return WDService.this;
        }
    }

}
