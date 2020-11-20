package com.bstu.fit.yarmolik.cinema.Responces;

public class SeanceDateResponse {
    private String Id;
    private String StartTime;
    private String HallId;
    private String FilmId;
    private String EndTime;
    private String HallName;

    public String getHallName() {
        return HallName;
    }

    public void setHallName(String hallName) {
        HallName = hallName;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getId() {
        return Id;
    }

    public void setHallId(String hallId) {
        HallId = hallId;
    }

    public void setFilmId(String filmId) {
        FilmId = filmId;
    }

    public String getFilmId() {
        return FilmId;
    }

    public String getHallId() {
        return HallId;
    }

    public String getEndTime() {
        return EndTime;
    }

    public void setEndTime(String endTime) {
        EndTime = endTime;
    }

    public String getStartTime() {
        return StartTime;
    }

    public void setStartTime(String startTime) {
        StartTime = startTime;
    }
}
