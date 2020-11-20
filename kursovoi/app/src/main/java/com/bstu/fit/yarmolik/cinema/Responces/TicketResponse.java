package com.bstu.fit.yarmolik.cinema.Responces;

public class TicketResponse {
    private String Id;
    private Integer Place;
    private Double Price;
    private Boolean Status;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public Integer getPlace() {
        return Place;
    }

    public void setPlace(Integer place) {
        Place = place;
    }

    public Double getPrice() {
        return Price;
    }

    public void setPrice(Double price) {
        Price = price;
    }

    public Boolean getStatus() {
        return Status;
    }

    public void setStatus(Boolean status) {
        Status = status;
    }
}
