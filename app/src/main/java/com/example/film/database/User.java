package com.example.film.database;

public class User {

    public static final String TABLE_NAME = "users";
    public static final String USER_ID = "id";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String CREATE_TIME = "create_time";

    // Create table SQL query
    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + USERNAME + " TEXT," + PASSWORD + " TEXT," + CREATE_TIME + " INTEGER" + ")";

    private Integer id = null;
    private String username;
    private String password;
    private Long timestamp = System.currentTimeMillis();

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(Integer id, String username, String password, Long timestamp) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.timestamp = timestamp;
    }

    public User() {
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
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
