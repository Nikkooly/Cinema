package com.bstu.fit.yarmolik.cinema.Model;

public class Rating {
    public String FilmId;
    public String UserId;
    public String Review;
    public Float Rating1;

    public Rating(String filmId, String userId, String review, Float rating1) {
        FilmId = filmId;
        UserId = userId;
        Review = review;
        Rating1 = rating1;
    }

    public String getFilmId() {
        return FilmId;
    }

    public void setFilmId(String filmId) {
        FilmId = filmId;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getReview() {
        return Review;
    }

    public void setReview(String review) {
        Review = review;
    }

    public Float getRating1() {
        return Rating1;
    }

    public void setRating1(Float rating1) {
        Rating1 = rating1;
    }
}
