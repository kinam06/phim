package com.example.film.utils;

import com.example.film.TimeSelection;

import java.util.ArrayList;
import java.util.List;

public class Const {

    public static List<TimeSelection> availableTime = new ArrayList<>();
    static{
        availableTime.add(new TimeSelection("12:00 AM"));
        availableTime.add(new TimeSelection("2:00 PM"));
        availableTime.add(new TimeSelection("4:00 PM"));
        availableTime.add(new TimeSelection("6:00 PM"));
        availableTime.add(new TimeSelection("8:00 PM"));
    }
}
