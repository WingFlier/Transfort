package ru.wdsoft.ui.datafield;

import android.database.Cursor;
import android.os.Bundle;

/**
 * Created by slaventii@mail.ru on 23.03.2018.
 */

public interface IUIFieldDataModel {

    Cursor getCursor(String filter);
    Cursor getCursor();
    Bundle getDefaultSelected();
    void updateDataFilter(String[] params);
    void udpateItemFilter(String[] params);

    void setDataFilter(DataFilter dataFilter);

    interface DataFilter {
        String updateDataFilter(String[] params);
        String getNoDataFilter();
        String updateItemFilter(String[] params);
    }

}



