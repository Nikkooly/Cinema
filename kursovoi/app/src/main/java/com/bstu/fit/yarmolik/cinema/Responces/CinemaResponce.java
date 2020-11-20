package com.bstu.fit.yarmolik.cinema.Responces;

import java.io.Serializable;

public class CinemaResponce implements Serializable {
    public String Id;
    public String Name;
    public String Adress;

       public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAdress() {
        return Adress;
    }

    public void setAdress(String adress) {
        Adress = adress;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }
}
