package com.cskaoyan.utils;


import java.sql.Date;
import java.text.SimpleDateFormat;

public class TransferDateUtils {
    public static String date2String(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String format = simpleDateFormat.format(date);
        return format;
    }
}
