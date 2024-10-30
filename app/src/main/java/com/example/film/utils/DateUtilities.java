package com.example.film.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtilities {

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public static String dateToString(Date d) {
        return dateFormat.format(d);
    }

    public static Date createDate(int day, int month, int year) {
        return new GregorianCalendar(year, month - 1, day).getTime();
    }
}
