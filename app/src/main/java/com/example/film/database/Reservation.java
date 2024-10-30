package com.example.film.database;

import android.graphics.Bitmap;

import java.util.List;

public class Reservation {

    public static final String TABLE_NAME = "reservation";
    public static final String RESERVATION_ID = "id";
    public static final String MOVIE_ID = "movie_id";
    public static final String USER_ID = "user_id";
    public static final String DATE = "date";
    public static final String TIME = "time";
    public static final String SEAT = "seat";
    public static final String CREATE_TABLE = "CREATE TABLE "
            + TABLE_NAME + "("
            + RESERVATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + MOVIE_ID + " INTEGER,"
            + USER_ID + " INTEGER,"
            + DATE + " TEXT,"
            + TIME + " TEXT,"
            + SEAT + " TEXT"
            + ")";
    private Integer id;
    private Integer movieId;
    private Integer userId;
    private String date;
    private String time;
    private List<Integer> seat;

    public Reservation() {
    }

    public Reservation(Integer id, Integer movieId, Integer userId, String date, String time, List<Integer> seat) {
        this.id = id;
        this.movieId = movieId;
        this.date = date;
        this.time = time;
        this.seat = seat;
        this.userId = userId;
    }

    public Reservation(Integer movieId, Integer userId, String date, String time, List<Integer> seat) {
        this.movieId = movieId;
        this.date = date;
        this.userId = userId;
        this.time = time;
        this.seat = seat;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<Integer> getSeat() {
        return seat;
    }

    public void setSeat(List<Integer> seat) {
        this.seat = seat;
    }
}
