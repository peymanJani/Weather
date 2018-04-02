package com.example.peyman.jani.weather.JavaClass;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class HelperCalendar {

    public static final int DAY_MS = 86400000;                                                   //24*60*60*1000;
    public static int[]     months = new int[]{ 31, 31, 31, 31, 31, 31, 30, 30, 30, 30, 30, 29 };


    /* from 1348-10-11
    * leap year 1347
    */
    private static long getMilisecondsFrom(String date, String format) {
        if (format == null) {
            format = "yyyy-MM-dd";
        }
        try {
            String givenDateString = date;
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            Date mDate = sdf.parse(givenDateString);
            long timeInMilliseconds = mDate.getTime();
            return timeInMilliseconds;
        }
        catch (ParseException e) {
            e.printStackTrace();
        }

        return 0;
    }


    private static String getJalaliFromMs(long ms) {
        long days = ms / DAY_MS;

        int monthIndex = 10;
        days -= 18;

        int year = 1348;

        while (true) {
            if (days > months[monthIndex]) {
                days -= months[monthIndex];
            } else {
                break;
            }
            monthIndex++;
            if (monthIndex == 12) {
                if (((year - 1347) % 4) == 0) {
                    days -= 1;
                }
                year++;
                monthIndex = 0;
            }
        }

        return year + "/" + (monthIndex + 1) + "/" + days;
    }


    public static String g2j(String date, String format) {
        return getJalaliFromMs(getMilisecondsFrom(date, format));
    }


    public static String g2j(String date) {
        return getJalaliFromMs(getMilisecondsFrom(date, null));
    }
}
