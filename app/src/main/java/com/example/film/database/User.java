package com.example.film.database;

import com.example.film.utils.DateUtilities;

import java.util.Date;

public class User {

    public static final String TABLE_NAME = "users";
    public static final String USER_ID = "id";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String CREATE_TIME = "create_time";

    // Create table SQL query
    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + USERNAME + " TEXT," + PASSWORD + " TEXT," + CREATE_TIME + " TEXT" + ")";

    private Integer id = null;
    private String username;
    private String password;
    private String time ;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        time = DateUtilities.dateToString(new Date());
    }

    public User(Integer id, String username, String password, String time) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.time = time;
    }

    public User() {
    }

    public String getTime() {
        return time;
    }

    public void setTimestamp(String timestamp) {
        this.time = timestamp;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
