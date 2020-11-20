package com.bstu.fit.yarmolik.cinema.Model;

import java.util.List;

public class BoughtTicket {
    public String IdUser;
    public String EmailUser;
    public String Date;
    public String StartTime;
    public String EndTime;
    public String Film;
    public String CinemaInfo;
    public String HallName;
    public String SeanceId;
    public List<String> Place;

    public BoughtTicket(String idUser, String emailUser, String date, String startTime, String endTime, String film, String cinemaInfo, String hallName, String seanceId, List<String> place) {
        IdUser = idUser;
        EmailUser = emailUser;
        Date = date;
        StartTime = startTime;
        EndTime = endTime;
        Film = film;
        CinemaInfo = cinemaInfo;
        HallName = hallName;
        SeanceId = seanceId;
        Place = place;
    }

    public String getIdUser() {
        return IdUser;
    }

    public void setIdUser(String idUser) {
        IdUser = idUser;
    }


    public String getEmailUser() {
        return EmailUser;
    }

    public void setEmailUser(String emailUser) {
        EmailUser = emailUser;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getStartTime() {
        return StartTime;
    }

    public void setStartTime(String startTime) {
        StartTime = startTime;
    }

    public String getEndTime() {
        return EndTime;
    }

    public void setEndTime(String endTime) {
        EndTime = endTime;
    }

    public String getCinemaInfo() {
        return CinemaInfo;
    }

    public void setCinemaInfo(String cinemaInfo) {
        CinemaInfo = cinemaInfo;
    }

    public String getHallName() {
        return HallName;
    }

    public void setHallName(String hallName) {
        HallName = hallName;
    }

    public String getSeanceId() {
        return SeanceId;
    }

    public void setSeanceId(String seanceId) {
        SeanceId = seanceId;
    }

    public List<String> getPlace() {
        return Place;
    }

    public void setPlace(List<String> place) {
        Place = place;
    }

    public String getFilm() {
        return Film;
    }

    public void setFilm(String film) {
        Film = film;
    }
}
