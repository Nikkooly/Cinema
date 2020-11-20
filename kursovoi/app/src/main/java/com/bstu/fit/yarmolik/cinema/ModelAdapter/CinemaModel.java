package com.bstu.fit.yarmolik.cinema.ModelAdapter;

public class CinemaModel {
    String cardName;
    int imageResourceId;
    String address;
    public CinemaModel(int imageResourceId, String cardName,String address){
        this.cardName=cardName;
        this.imageResourceId=imageResourceId;
        this.address=address;
    }
    public void setImageResourceId(int imageResourceId){
        this.imageResourceId=imageResourceId;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getCardName() {
        return cardName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
