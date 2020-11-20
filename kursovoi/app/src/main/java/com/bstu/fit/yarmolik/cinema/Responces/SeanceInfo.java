package com.bstu.fit.yarmolik.cinema.Responces;

import java.io.Serializable;

public class SeanceInfo implements Serializable {
    public Integer Places;
    public Double TicketPrice;

    public Integer getPlaces() {
        return Places;
    }

    public void setPlaces(Integer places) {
        Places = places;
    }

    public Double getTicketPrice() {
        return TicketPrice;
    }

    public void setTicketPrice(Double ticketPrice) {
        TicketPrice = ticketPrice;
    }
}
