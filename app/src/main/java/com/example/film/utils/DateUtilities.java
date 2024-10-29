package com.example.film.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtilities {

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    public static String dateToString(Date d) {
        return dateFormat.format(d);
    }

}