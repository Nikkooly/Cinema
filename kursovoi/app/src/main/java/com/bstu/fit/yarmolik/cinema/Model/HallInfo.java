package com.bstu.fit.yarmolik.cinema.Model;

public class HallInfo {
    private String CinemaId;
    private String Name;
    private Integer Places;
    public  HallInfo(String cinemaId,String name, Integer places){
        CinemaId=cinemaId;
        Name=name;
        Places=places;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getName() {
        return Name;
    }


    public Integer getPlaces() {
        return Places;
    }

    public void setPlaces(Integer places) {
        Places = places;
    }

    public void setCinemaId(String cinemaId) {
        CinemaId = cinemaId;
    }

    public String getCinemaId() {
        return CinemaId;
    }
}
