package com.bstu.fit.yarmolik.cinema.ModelAdapter;

import android.graphics.Bitmap;

public class FilmModel {
    String name;
     String year;
     Float rating;
     String id;
     //Bitmap image;
    String link;
    public FilmModel(String name, String year, Float rating,String link, String id){// Bitmap image){
        this.name=name;
        //this.image=image;
        this.link=link;
        this.year=year;
        this.rating=rating;
        this.id=id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getYear() {
        return year;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public Float getRating() {
        return rating;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    /*public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }*/

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
