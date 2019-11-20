package com.cskaoyan.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TransferUtils_wx {
    public static String date2String(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String format = simpleDateFormat.format(date);
        return format;
    }
}
