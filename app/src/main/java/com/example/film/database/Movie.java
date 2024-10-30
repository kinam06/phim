package com.example.film.database;

import com.example.film.utils.StringUtil;

public class Movie {

    private Integer id;
    private String name;
    private String nameEng;
    private String image;
    private String description;
    private String country;
    private String genre;
    private String director;
    private String actor;
    private int duration;
    private String releaseDate;

    public static final String TABLE_NAME = "movies";
    public static final String MOVIE_ID = "id";
    public static final String NAME = "name";
    public static final String NAME_ENG = "name_eng";
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
            + NAME_ENG + " TEXT,"
            + IMAGE + " TEXT,"
            + DESCRIPTION + " TEXT,"
            + COUNTRY + " TEXT,"
            + GENRE + " TEXT,"
            + DIRECTOR + " TEXT,"
            + ACTOR + " TEXT,"
            + DURATION + " INTEGER,"
            + RELEASE + " INTEGER"
            + ")";

    public Movie(String name, String image, String description, String country, String genre, String director, String actor, int duration, String releaseDate) {
        this.name = name;
        this.nameEng = StringUtil.removeAccent(name);
        this.image = image;
        this.description = description;
        this.country = country;
        this.genre = genre;
        this.director = director;
        this.actor = actor;
        this.duration = duration;
        this.releaseDate = releaseDate;
    }

    public Movie(Integer id, String name, String image, String description, String country, String genre, String director, String actor, int duration, String releaseDate) {
        this.id = id;
        this.name = name;
        this.nameEng = StringUtil.removeAccent(name);
        this.image = image;
        this.description = description;
        this.country = country;
        this.genre = genre;
        this.director = director;
        this.actor = actor;
        this.duration = duration;
        this.releaseDate = releaseDate;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", nameEng='" + nameEng + '\'' +
                ", image='" + image + '\'' +
                ", description='" + description + '\'' +
                ", country='" + country + '\'' +
                ", genre='" + genre + '\'' +
                ", director='" + director + '\'' +
                ", actor='" + actor + '\'' +
                ", duration=" + duration +
                ", releaseDate='" + releaseDate + '\'' +
                '}';
    }

    public String getNameEng() {
        return nameEng;
    }

    public void setNameEng(String nameEng) {
        this.nameEng = nameEng;
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

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }
}
