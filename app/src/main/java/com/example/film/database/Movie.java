package com.example.film.database;

public class Movie {

    private Integer id;
    private String name;
    private String image;
    private String description;
    private String country;
    private String genre;
    private String director;
    private String actor;
    private int duration;
    private Long releaseDate;

    public static final String TABLE_NAME = "movies";
    public static final String MOVIE_ID = "id";
    public static final String NAME = "name";
    public static final String IMAGE = "image";
    public static final String DESCRIPTION = "description";
    public static final String COUNTRY = "country";
    public static final String GENRE = "genre";
    public static final String DIRECTOR = "director";
    public static final String ACTOR = "actor";
    public static final String DURATION = "duration";
    public static final String RELEASE = "release";
    public static final String CREATE_TABLE = "CREATE TABLE "
            + TABLE_NAME + "("
            + MOVIE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + NAME + " TEXT,"
            + IMAGE + " TEXT,"
            + DESCRIPTION + " TEXT,"
            + COUNTRY + " TEXT,"
            + GENRE + " TEXT,"
            + DIRECTOR + " TEXT,"
            + ACTOR + " TEXT,"
            + DURATION + " INTEGER,"
            + RELEASE + " INTEGER"
            + ")";

    public Movie(String name, String image, String description, String country, String genre, String director, String actor, int duration, Long releaseDate) {
        this.name = name;
        this.image = image;
        this.description = description;
        this.country = country;
        this.genre = genre;
        this.director = director;
        this.actor = actor;
        this.duration = duration;
        this.releaseDate = releaseDate;
    }

    public Movie(Integer id, String name, String image, String description, String country, String genre, String director, String actor, int duration, Long releaseDate) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.description = description;
        this.country = country;
        this.genre = genre;
        this.director = director;
        this.actor = actor;
        this.duration = duration;
        this.releaseDate = releaseDate;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Long getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Long releaseDate) {
        this.releaseDate = releaseDate;
    }
}