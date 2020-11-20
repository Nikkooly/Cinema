package com.bstu.fit.yarmolik.cinema.Remote;

import com.bstu.fit.yarmolik.cinema.Model.BoughtTicket;
import com.bstu.fit.yarmolik.cinema.Model.CheckSeance;
import com.bstu.fit.yarmolik.cinema.Model.CinemaInfo;
import com.bstu.fit.yarmolik.cinema.Model.FilmInfo;
import com.bstu.fit.yarmolik.cinema.Model.HallInfo;
import com.bstu.fit.yarmolik.cinema.Model.LoginUser;
import com.bstu.fit.yarmolik.cinema.Model.Rating;
import com.bstu.fit.yarmolik.cinema.Model.ReviewModel;
import com.bstu.fit.yarmolik.cinema.Model.Seance;
import com.bstu.fit.yarmolik.cinema.Model.SeanceDate;
import com.bstu.fit.yarmolik.cinema.Model.UserData;
import com.bstu.fit.yarmolik.cinema.Responces.CinemaResponce;
import com.bstu.fit.yarmolik.cinema.Responces.FilmResponse;
import com.bstu.fit.yarmolik.cinema.Responces.GuestResponse;
import com.bstu.fit.yarmolik.cinema.Responces.HallResponse;
import com.bstu.fit.yarmolik.cinema.Responces.PlacesNumber;
import com.bstu.fit.yarmolik.cinema.Responces.PlacesResponse;
import com.bstu.fit.yarmolik.cinema.Responces.ReviewResponse;
import com.bstu.fit.yarmolik.cinema.Responces.SeanceDateResponse;
import com.bstu.fit.yarmolik.cinema.Responces.SeanceInfo;
import com.bstu.fit.yarmolik.cinema.Responces.TicketInfoData;
import com.bstu.fit.yarmolik.cinema.Responces.TicketResponse;
import com.bstu.fit.yarmolik.cinema.Responces.TicketStaticResponse;
import com.bstu.fit.yarmolik.cinema.Responces.UserResponce;
import com.bstu.fit.yarmolik.cinema.Responces.UserTicket;

import java.util.List;

import io.reactivex.Observable;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface IMyApi {
    //http://localhost:5000/api/register
    @POST("api/register")
    Observable<String> registerUser(@Body UserData user);
    @POST("api/login")
    Observable<String> loginUser(@Body LoginUser user);
    @POST("api/films")
    Observable<String> filmInfo(@Body FilmInfo film);
    @GET("api/films")
    Call<List<FilmResponse>> getFilms();
    @DELETE("api/films/{id}")
    Call<Void> deletePost(@Path("id") String id);
    @GET("api/films/{id}")
    Call<FilmResponse> getFilm(@Path("id") String id);
    @PUT("api/films/{id}")
    Call<FilmInfo> updateFilm(@Path("id") String id, @Body FilmInfo film);
    @POST("api/cinema")
    Observable<String> addCinema(@Body CinemaInfo cinema);
    @DELETE("api/cinema/{id}")
    Call<Void> deleteCinema(@Path("id") String id);
    @GET("api/cinema")
    Call<List<CinemaResponce>> getCinema();
    @GET("api/cinema/{id}")
    Call<CinemaResponce> getCinemaInfo(@Path("id") String id);
    @PUT("api/cinema/{id}")
    Call<CinemaInfo> updateCinema(@Path("id") String id, @Body CinemaInfo cinema);
    @POST("api/hall")
    Observable<String> addHall(@Body HallInfo hall);
    @GET("api/hall/{id}")
    Call<List<HallResponse>> getHallInfo(@Path("id") String id);
    @DELETE("api/hall/{id}")
    Call<Void> deleteHall(@Path("id") String id);
    @POST("api/seance")
    Observable<String> addSeance(@Body Seance seance);
    @POST("api/check")
    Observable<String> checkSeance(@Body CheckSeance check);
    @GET("api/seance/{id}")
    Call<List<CinemaResponce>> getCinemaId(@Path("id") String id);
    @POST("api/data")
    Call<List<SeanceDateResponse>> infoSeanceLoad(@Body SeanceDate seanceDate);
    @GET("api/tickets/{id}")
    Call<List<UserTicket>> getTickets(@Path("id") String id);
    @GET("api/localtickets/{id}")
    Call<List<UserTicket>> getTicketsLocal(@Path("id") String id);
    @POST("api/login")
    Call<List<UserResponce>> checkLogin(@Body LoginUser user);
    @POST("api/publishtickets")
    Observable<String> publishTickets(@Body BoughtTicket boughtTicket);
    @GET("api/seance/loadDataSeance/{id}")
    Call<List<SeanceInfo>> getSeanceInfo(@Path("id") String id);
    @GET("api/seance/loadDataTicket/{id}")
    Call<List<TicketInfoData>> getTicketInfo(@Path("id") String id);
    @GET("api/guest/{id}")
    Call<List<GuestResponse>> getGuestInfo(@Path("id") Integer id);
    @GET("api/tickets/placesData/{user_id}&{seance_id}")
    Call<List<PlacesResponse>> getPlaces(@Path("user_id") String user_id,@Path("seance_id") String seance_id);
    @POST("api/review")
    Observable<String> postReview(@Body Rating rating);
    @GET("api/review/{id}")
    Call<List<ReviewResponse>> getReviews(@Path("id") String id);
}
