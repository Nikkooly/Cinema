package com.bstu.fit.yarmolik.cinema.Model;

public class CheckSeance {
    private String FilmId;
    public CheckSeance(String filmId){
        FilmId=filmId;
    }

    public void setFilmId(String filmId) {
        FilmId = filmId;
    }

    public String getFilmId() {
        return FilmId;
    }
}
