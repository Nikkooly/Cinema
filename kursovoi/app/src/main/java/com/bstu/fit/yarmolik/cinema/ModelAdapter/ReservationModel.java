package com.bstu.fit.yarmolik.cinema.ModelAdapter;

public class ReservationModel {
    String id;
    String seanceId;
    Integer places;
    Boolean status;
    public ReservationModel(String id, Integer Places,String seanceId, Boolean status){
        this.id=id;
        this.seanceId=seanceId;
        this.places=places;
        this.status=status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSeanceId() {
        return seanceId;
    }

    public void setSeanceId(String seanceId) {
        this.seanceId = seanceId;
    }

    public Integer getPlaces() {
        return places;
    }

    public void setPlaces(Integer places) {
        this.places = places;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
