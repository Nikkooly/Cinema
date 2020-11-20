package com.bstu.fit.yarmolik.cinema.Responces;

import java.io.Serializable;

public class HallResponse implements Serializable {
    private String Id;
    private String Name;
    private Integer Places;

    public void setPlaces(Integer places) {
        Places = places;
    }

    public Integer getPlaces() {
        return Places;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getId() {
        return Id;
    }
}
