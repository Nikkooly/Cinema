package com.bstu.fit.yarmolik.cinema.Model;

public class Seance {
    private String StartTime;
    private String HallId;
    private String FilmId;
    private String EndTime;
    private Double TicketPrice;
    public Seance(String start_time, String hallId, String filmId,String end_time,Double ticketPrice){
        StartTime=start_time;
        HallId=hallId;
        FilmId=filmId;
        EndTime=end_time;
        TicketPrice=ticketPrice;
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

    public Double getTicketPrice() {
        return TicketPrice;
    }

    public void setTicketPrice(Double ticketPrice) {
        TicketPrice = ticketPrice;
    }
}
