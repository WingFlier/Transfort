package ru.wdsoft.utils;

import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * Created by slaventii@mail.ru on 13.02.2019.
 */
public class CryptoUtils {

    public static String byteArrayToHex(byte[] a) {

        StringBuilder sb = new StringBuilder(a.length * 2);
        for(byte b: a)
            sb.append(String.format("%02x", b));
        return sb.toString();
    }

    public static String byteArrayToHexReverse(byte[] a) {

        StringBuilder sb = new StringBuilder(a.length * 2);
        for(int i = a.length-1; i>=0; i--) {
            byte b = a[i];
            sb.append(String.format("%02x", b));
        }


        return sb.toString();
    }

    public static String md6(String s){

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(s.getBytes(),0,s.length());
            String hash = new BigInteger(1, md.digest()).toString(16);
            return hash;
        } catch (Exception e) {

        }

        return null;

    }

    public static String md5(String s){

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(s.getBytes());
            String hash = new BigInteger(1, md.digest()).toString(16);
            while (hash.length() < 32) {
                hash = "0" + hash;
            }
            return hash;
        } catch (Exception e) {

        }

        return null;

    }


}
