package ru.wdsoft.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

/**
 * Created by slaventii@mail.ru on 13.02.2019.
 */
public class DateTimeUtils {

    public static final String UI_DATE_TIME_PATTERN_LONG = "dd-MM-yyyy HH:mm";


    /**
     * КОНВЕРТОРЫ ДАТА/ВРЕМЯ
     */

    public static String getDateTimeAsString(String formatSrc, String timeZone) {

        return getDateTimeAsString(new Date(), formatSrc, timeZone);

    }

    public static String getDateTimeAsString(Date date, String formatDest, String timeZone) {

        if (date == null) date = new Date();

        try {

            SimpleDateFormat destFormat = new SimpleDateFormat(formatDest);
            if (Utils.stringsNotEmpty(timeZone)){
                destFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
            }

            return destFormat.format(date);

        } catch (NullPointerException e) {
//            Utils.debugErrorLog(LOG_PREFIX, e);
        }

        return null;
    }

    public static long getDateNow(){

        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTimeInMillis();
    }

    public static String addDays(Date date, int days){

        Calendar c = Calendar.getInstance();
        c.setTime(date);

        c.add(Calendar.DATE, days);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date resultdate = new Date(c.getTimeInMillis());
        return sdf.format(resultdate);
    }

    public static String addHours(Date date, int hours){

        Calendar c = Calendar.getInstance();
        c.setTime(date);

        c.add(Calendar.HOUR, hours);

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        Date resultdate = new Date(c.getTimeInMillis());
        return sdf.format(resultdate);
    }

    public static String addDaysHoursMins(Date date, int days){

        Calendar c = Calendar.getInstance();
        c.setTime(date);

        c.add(Calendar.DATE, days);

        SimpleDateFormat sdf = new SimpleDateFormat(UI_DATE_TIME_PATTERN_LONG);
        Date resultdate = new Date(c.getTimeInMillis());
        return sdf.format(resultdate);
    }

    public static String getTimeFromMinutes(long mins) {

        long hours = mins / 60;
        long min = mins % 60;

        return String.format("%1$02d:%2$02d", hours, min);
    }

    public static int getMinutesFromTime(int hours, int min){

        int mins = hours * 60 + min;

        return mins;

    }

    public static int getMinutesFromTimeStr(String time){

        String val = time;
        String vals[] = val.split(":");

        if (vals.length < 2){return 0;}

        int hour = Integer.valueOf(vals[0]);
        int min = Integer.valueOf(vals[1]);

        return getMinutesFromTime(hour, min);
    }

    public static Date getTimeFromStr(String str) {

        if (str == null) return null;

        SimpleDateFormat sourceFormat;

        sourceFormat = new SimpleDateFormat("HH:mm");

        try {

            Date parsed = sourceFormat.parse(str);

            return parsed;


        } catch (ParseException | NullPointerException e) {
            e.printStackTrace();
        }

        return new Date();
    }

    public static String getTimerAsString(long secs) {


        int days = (int) TimeUnit.SECONDS.toDays(secs);
        long hours = TimeUnit.SECONDS.toHours(secs) - (days *24);
        long minutes = TimeUnit.SECONDS.toMinutes(secs) - (TimeUnit.SECONDS.toHours(secs)* 60);
        long seconds = TimeUnit.SECONDS.toSeconds(secs) - (TimeUnit.SECONDS.toMinutes(secs) *60);

        String sdays = days > 0 ? String.valueOf(days) + "д:" : "";
        String shours = hours > 0 ? (((hours > 9) ? "" : "0") + String.valueOf(hours) + "ч:") : "";
        String sminutes = ((minutes > 9) ? "" : "0") + String.valueOf(minutes) + "м:";
        String sseconds = ((seconds > 9) ? "" : "0") + String.valueOf(seconds);

        String total = sdays + shours + sminutes + sseconds;

        return total;
    }

}
