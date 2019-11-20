package com.cskaoyan.utils;


import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class TransferDateUtils {
    public static String date2String(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = simpleDateFormat.format(date);
        return format;
    }

    public static String date2LongString(Date date){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        return df.format(date);
    }
}
