package com.bstu.fit.yarmolik.cinema.ModelAdapter;

public class TicketStaticModel {
    private String cinema;
    private String hall;
    private String filmName;
    private  String date;
    private  String time;
    private  String endTime;
    private String seance_id;

    public TicketStaticModel(String cinema, String hall, String filmName, String date, String time, String endTime, String seance_id) {
        this.cinema = cinema;
        this.hall = hall;
        this.filmName = filmName;
        this.date = date;
        this.time = time;
        this.endTime = endTime;
        this.seance_id = seance_id;
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

    public String getFilmName() {
        return filmName;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName;
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

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getSeance_id() {
        return seance_id;
    }

    public void setSeance_id(String seance_id) {
        this.seance_id = seance_id;
    }
}
