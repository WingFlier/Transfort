package ru.wdsoft.utils;

/**
 * Created by Botezatu Veaceslav on 10.03.2016.
 */
public class Utils {

    /**
     * Для логирования
     */
    private static final String LOG_PREFIX = "Utils -- ";

    public static boolean stringsNotEmpty(String... strings) {
        if (strings == null) return false;

        for (String s : strings) {
            if (s == null || s.trim().equalsIgnoreCase("")) return false;
        }

        return true;
    }

    public static boolean hasValue(Integer val){
        if (val == null || val <= 0) return false; else return true;
    }

    public static String formatPhoneNumber(String strPhone, String mask){

        if (!Utils.stringsNotEmpty(strPhone, mask)){
            return strPhone;
        }

        strPhone = strPhone.replaceAll("[^0-9]", "");

        StringBuilder out = new StringBuilder();

        int j=0;
        for (int i = 0; i< mask.length() ;  i++) {

            if (mask.charAt(i) == '+') {

                out.append('+');
                out.append('7');

                if (strPhone.charAt(j) == '7' || strPhone.charAt(j) == '8'){
                    j++;
                }
                i++;

            } else if (mask.charAt(i) == '#') {
                out.append(strPhone.charAt(j));
                j++;
            } else {
                out.append(mask.charAt(i));
            }
            if (j >= strPhone.length()){
                break;
            }
        }

        return out.toString();

    }

}
