package com.bstu.fit.yarmolik.cinema.Model;

public class FilmInfo {
    public String Name;
    public Integer Year;
    public String Country;
    public Integer Duration;
    public String Genre;
    public String Description;
    public String Poster;
    public FilmInfo(String name, Integer year, String country, Integer duration, String genre, String description, String poster){
        Name=name;
        Year=year;
        Country=country;
        Duration=duration;
        Genre=genre;
        Description=description;
        Poster=poster;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getName() {
        return Name;
    }

    public void setYear(Integer year) {
        Year = year;
    }

    public Integer getYear() {
        return Year;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getCountry() {
        return Country;
    }

    public void setDuration(Integer duration) {
        Duration = duration;
    }

    public Integer getDuration() {
        return Duration;
    }

    public void setGenre(String genre) {
        Genre = genre;
    }

    public String getGenre() {
        return Genre;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getDescription() {
        return Description;
    }

    public void setPoster(String poster) {
        Poster = poster;
    }

    public String getPoster() {
        return Poster;
    }
}
