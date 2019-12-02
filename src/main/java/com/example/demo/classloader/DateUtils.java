package com.example.demo.classloader;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    public static String getDateStr(Date date) {
        if(date != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return simpleDateFormat.format(date);
        }
        return null;
    }
}
