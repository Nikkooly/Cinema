package com.bstu.fit.yarmolik.cinema.Responces;

import java.io.Serializable;

public class TicketInfoData implements Serializable {
    public String UserId;
    public String SeanceId;
    public Integer Place;

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getSeanceId() {
        return SeanceId;
    }

    public void setSeanceId(String seanceId) {
        SeanceId = seanceId;
    }

    public Integer getPlace() {
        return Place;
    }

    public void setPlace(Integer place) {
        Place = place;
    }
}
