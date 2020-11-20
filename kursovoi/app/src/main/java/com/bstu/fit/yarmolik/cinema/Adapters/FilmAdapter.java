package com.bstu.fit.yarmolik.cinema.Adapters;

import android.os.Bundle;
import android.telecom.Call;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;


import com.bstu.fit.yarmolik.cinema.CheckInternetConnection;
import com.bstu.fit.yarmolik.cinema.Fragments.InfoFilmFragment;
import com.bstu.fit.yarmolik.cinema.Fragments.OnFragmentBookListener;
import com.bstu.fit.yarmolik.cinema.ModelAdapter.FilmModel;
import com.bstu.fit.yarmolik.cinema.R;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FilmAdapter  extends RecyclerView.Adapter<FilmAdapter.FilmViewHolder> {
    private ArrayList<FilmModel> list;
    private OnFragmentBookListener mListener;
    CheckInternetConnection checkInternetConnection=new CheckInternetConnection();
    public FilmAdapter(ArrayList<FilmModel> filmModels) {
        list=filmModels;
    }
    public class FilmViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        CardView cardView;
        TextView cardName;
        TextView cardYear;
        RatingBar cardRating;
        public FilmViewHolder(View view){
            super(view);
            image=view.findViewById(R.id.imageView2);
            cardName=view.findViewById(R.id.nameFilmTextView);
            cardView=view.findViewById(R.id.card_films);
            cardYear=view.findViewById(R.id.yearFilmTextView);
            cardRating=view.findViewById(R.id.rating);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }
    @Override
    public void onBindViewHolder(final FilmViewHolder holder, int position){
        YoYo.with(Techniques.FadeInRight).playOn(holder.cardView);
        holder.cardName.setText(list.get(position).getName());
        holder.cardYear.setText(list.get(position).getYear());
        Picasso.get().load(list.get(position).getLink()).into(holder.image);
        //holder.image.setImageBitmap(list.get(position).getImage());
        holder.cardRating.setRating(list.get(position).getRating());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkInternetConnection.isOnline(view.getContext())) {
                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    Fragment myFragment = new InfoFilmFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("idFilmAdapter", list.get(position).getId());
                    myFragment.setArguments(bundle);
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.recyclerFragmentFilm, myFragment).addToBackStack(null).commit();
                    //mListener.onOpenFragmentS(list.get(position).getName());
                }
                else{
                    Toast.makeText(view.getContext(), "Нет подключения", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    @NonNull
    @Override
    public FilmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_films,parent,false);
        return new FilmViewHolder(view);
    }
    @Override
    public int getItemCount(){
        return list.size();
    }
}
