package com.example.administrator.test;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Bridge on 2018-01-15.
 */

public class DateUtil {

    public static String getTime(String utcDate) {
        StringBuilder stringBuilder = new StringBuilder();

        int position = utcDate.indexOf(":");
        int start = position - 2;
        int mid = position;
        int end = position + 3;

        String tempHour = utcDate.substring(start, mid);
        int hour = Integer.parseInt(tempHour) + 9;

        if (hour < 12)
            stringBuilder.append(utcDate.substring(0, start) + " 오전 " + hour + utcDate.substring(mid, end));
        else {
            hour -= 12;
            stringBuilder.append(utcDate.substring(0, start) + " 오후 " + hour + utcDate.substring(mid, end));
        }

        return stringBuilder.toString();
    }

    public static String getCurrentTime() {
        Date date = new Date();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
        String tempDate = dateFormat.format(date);

        SimpleDateFormat dayFormat = new SimpleDateFormat("hh:mm");
        String tempDay = dayFormat.format(date);

        SimpleDateFormat noonFormat = new SimpleDateFormat("a");
        String tempNoon = noonFormat.format(date);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(tempDate + " " + tempNoon + " " + tempDay);

        return stringBuilder.toString();
    }
}
