package com.bstu.fit.yarmolik.cinema.ModelAdapter;

public class TicketModel {
    public String filmName;
    public String seanceId;
    public String date;
    public String time;
    public String cinema;
    public String hall;
    public String filmId;
    public String endTime;

    public TicketModel(String filmName, String seanceId, String date, String time, String cinema, String hall, String filmId, String endTime) {
        this.filmName = filmName;
        this.seanceId = seanceId;
        this.date = date;
        this.time = time;
        this.cinema = cinema;
        this.hall = hall;
        this.filmId = filmId;
        this.endTime = endTime;
    }

    public String getFilmName() {
        return filmName;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }

    public String getSeanceId() {
        return seanceId;
    }

    public void setSeanceId(String seanceId) {
        this.seanceId = seanceId;
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

    public String getCinema() {
        return cinema;
    }

    public void setCinema(String cinema) {
        this.cinema = cinema;
    }

    public String getHall() {
        return hall;
    }

    public void setHall(String hall) {
        this.hall = hall;
    }

    public String getFilmId() {
        return filmId;
    }

    public void setFilmId(String filmId) {
        this.filmId = filmId;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
