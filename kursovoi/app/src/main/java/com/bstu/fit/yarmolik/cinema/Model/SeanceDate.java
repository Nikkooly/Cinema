package com.bstu.fit.yarmolik.cinema.Model;

public class SeanceDate {
    private String CinemaId;
    private String FilmId;
    private String Date;
    public SeanceDate(String cinemaId, String filmId, String date){
        CinemaId=cinemaId;
        FilmId=filmId;
        Date=date;
    }

    public void setCinemaId(String cinemaId) {
        CinemaId = cinemaId;
    }

    public String getCinemaId() {
        return CinemaId;
    }

    public String getFilmId() {
        return FilmId;
    }

    public void setFilmId(String filmId) {
        FilmId = filmId;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getDate() {
        return Date;
    }
}
