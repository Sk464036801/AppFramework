package com.web.spider.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Eric on 10/29/14.
 */
public class AppUtils {

    public static final String timeFormatter = "yyyy-MM-dd HH:mm:ss";

    public static String getDateTimeNow() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat(timeFormatter);

        return format.format(calendar.getTime());
    }
}
