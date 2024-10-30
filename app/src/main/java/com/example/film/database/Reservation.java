package com.example.film.database;

import android.graphics.Bitmap;

import java.util.List;

public class Reservation {
    private Movie film;
    private String startTime;
    private List<Integer> places;
    private Bitmap codeQR;

    public Reservation() {}

    public Reservation(Movie film, String startTime, List<Integer> places, Bitmap codeQR) {
        this.film = film;
        this.startTime = startTime;
        this.places = places;
        this.codeQR = codeQR;
    }

    public Movie getFilm() {
        return film;
    }

    public void setFilm(Movie film) {
        this.film = film;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public List<Integer> getPlaces() {
        return places;
    }

    public void setPlaces(List<Integer> places) {
        this.places = places;
    }

    public Bitmap getCodeQR() {
        return codeQR;
    }

    public void setCodeQR(Bitmap codeQR) {
        this.codeQR = codeQR;
    }
}
