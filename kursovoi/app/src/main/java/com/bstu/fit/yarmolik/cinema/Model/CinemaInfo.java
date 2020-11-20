package com.bstu.fit.yarmolik.cinema.Model;

public class CinemaInfo {
    private String Name;
    private String Adress;
    public CinemaInfo(String name, String adress){
        Name=name;
        Adress=adress;
    }

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
}
