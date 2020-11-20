package com.bstu.fit.yarmolik.cinema.Responces;

public class UserTicket {
    public String SeanceId;
    public String Name;
    public String Date;
    public String Time;
    public String Cinema;
    public String Hall;
    public String FilmId;
    public String EndTime;

    public String getSeanceId() {
        return SeanceId;
    }

    public void setSeanceId(String seanceId) {
        SeanceId = seanceId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getCinema() {
        return Cinema;
    }

    public void setCinema(String cinema) {
        Cinema = cinema;
    }

    public String getHall() {
        return Hall;
    }

    public void setHall(String hall) {
        Hall = hall;
    }

    public String getFilmId() {
        return FilmId;
    }

    public void setFilmId(String filmId) {
        FilmId = filmId;
    }

    public String getEndTime() {
        return EndTime;
    }

    public void setEndTime(String endTime) {
        EndTime = endTime;
    }
}
