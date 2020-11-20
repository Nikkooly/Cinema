package com.bstu.fit.yarmolik.cinema.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bstu.fit.yarmolik.cinema.Fragments.UserTicketActivity;
import com.bstu.fit.yarmolik.cinema.Login;
import com.bstu.fit.yarmolik.cinema.Model.Rating;
import com.bstu.fit.yarmolik.cinema.ModelAdapter.TicketModel;
import com.bstu.fit.yarmolik.cinema.R;
import com.bstu.fit.yarmolik.cinema.Remote.IMyApi;
import com.bstu.fit.yarmolik.cinema.Remote.RetrofitClient;
import com.bstu.fit.yarmolik.cinema.Responces.PlacesResponse;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.TicketViewHolder> {
    private ArrayList<TicketModel> list;
    private Fragment context;
    private IMyApi iMyApi;
    private CompositeDisposable compositeDisposable=new CompositeDisposable();
    private Date date=new Date();
    private SimpleDateFormat simpleDateFormat =new SimpleDateFormat("yyyy-MM-dd HH:mm");


    private String idSeance,idUser;
    public TicketAdapter(ArrayList<TicketModel> ticketModels,Fragment context) { list=ticketModels; this.context=context;
    }
    public class TicketViewHolder extends RecyclerView.ViewHolder {
         TextView nameFilm;
         TextView dateSeance;
         TextView timeSeance;
         TextView hall;
         TextView cinema;
         EditText reviewText;
         RatingBar ratingBar;
         TextView reservedSeats;
         Button button,reviewButton,finalReviewButton;
         CardView cardView;
         Float valueFloat= Float.valueOf(0);
         Dialog popup,review;
        public TicketViewHolder(View view) {
            super(view);
            nameFilm=view.findViewById(R.id.film_title_text);
            dateSeance=view.findViewById(R.id.textView31);
            timeSeance=view.findViewById(R.id.starting_time_text);
            cardView=view.findViewById(R.id.cardTickets);
            button=view.findViewById(R.id.detail_button);
            reviewButton=view.findViewById(R.id.button3);
            popup = new Dialog(Objects.requireNonNull(context.getActivity()));
            review=new Dialog(Objects.requireNonNull(context.getActivity()));
            review.setContentView(R.layout.activity_review);
            popup.setContentView(R.layout.activity_user_ticket);
            iMyApi= RetrofitClient.getInstance().create(IMyApi.class);
            reservedSeats=popup.findViewById(R.id.reserved_seats);
            hall=popup.findViewById(R.id.textView40);
            cinema=popup.findViewById(R.id.textView39);
            reviewText=review.findViewById(R.id.reviewEditText);
            ratingBar=review.findViewById(R.id.ratingBarS);
            finalReviewButton=review.findViewById(R.id.buttonReview);
        }
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        private void openPopup() {

            popup.show();

            Button close = popup.findViewById(R.id.close_popup);

            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popup.dismiss();
                }
            });
        }
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        private void openReview(String filmId) {
            review.show();
            String reviewTextString="";
            String sRating;
            ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                    valueFloat=ratingBar.getRating();
                }
            });
            finalReviewButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!reviewText.getText().toString().equals("") && !valueFloat.equals(Float.valueOf(0))) {
                        //Toast.makeText(view.getContext(), reviewText.getText().toString(), Toast.LENGTH_SHORT).show();
                        postReview(filmId,Login.userId,reviewText.getText().toString(),valueFloat,review.getContext());
                    }
                    else{
                        Toast.makeText(view.getContext(), "Заполните данные!", Toast.LENGTH_SHORT).show();
                    }
                  //ratingBar.getProgress();

                }
            });
        }
    }
    @NonNull
    @Override
    public TicketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_tickets,parent,false);
        return new TicketViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TicketViewHolder holder, int position) {
        YoYo.with(Techniques.FadeInRight).playOn(holder.cardView);
        holder.nameFilm.setText(list.get(position).getFilmName());
        holder.dateSeance.setText(list.get(position).getDate());
        holder.timeSeance.setText(list.get(position).getTime());
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<List<PlacesResponse>> call=iMyApi.getPlaces(Login.userId,list.get(position).getSeanceId());
                ArrayList<Integer> loadPlace=new ArrayList<>();
                StringBuilder stringBuilder=new StringBuilder();
                call.enqueue(new Callback<List<PlacesResponse>>() {
                    @Override
                    public void onResponse(Call<List<PlacesResponse>> call, Response<List<PlacesResponse>> response) {
                        for (PlacesResponse placesResponse : response.body()) {
                            loadPlace.add((placesResponse.getPlace()));
                        }
                        for (int i = 0; i < loadPlace.size(); i++) {
                            stringBuilder.append(loadPlace.get(i)+",");
                        }
                        String places=stringBuilder.toString();
                        String placesFinal=places.substring(0,places.length()-1);
                        holder.reservedSeats.setText(placesFinal);
                        holder.cinema.setText(list.get(position).getCinema());
                        holder.hall.setText(list.get(position).getHall());
                        holder.openPopup();
                    }
                    @Override
                    public void onFailure(Call<List<PlacesResponse>> call, Throwable t) {

                    }
                });

            }
        });
        try {
            Date endDate= simpleDateFormat.parse(list.get(position).getEndTime());
            String s=simpleDateFormat.format(date);
            Date currentDate=simpleDateFormat.parse(s);
           if(currentDate.after(endDate)){
               holder.reviewButton.setVisibility(View.VISIBLE);
               holder.reviewButton.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       holder.openReview(list.get(position).getFilmId());
                   }
               });
           }
           else{
               holder.reviewButton.setVisibility(View.GONE);
           }
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    private void postReview(String filmId, String userId, String review, Float rating1, Context context){
        Rating rating=new Rating(filmId,userId,review,rating1);
        compositeDisposable.add(iMyApi.postReview(rating)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                if(!s.equals("No")){
                    Toast.makeText(context, "Отзыв успешно оставлен", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(context, "Вы уже оставили отзыв на этот фильм!", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Toast.makeText(context, throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }));
    }
}
