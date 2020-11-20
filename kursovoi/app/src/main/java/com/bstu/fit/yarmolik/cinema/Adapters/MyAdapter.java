package com.bstu.fit.yarmolik.cinema.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bstu.fit.yarmolik.cinema.ModelAdapter.CinemaModel;
import com.bstu.fit.yarmolik.cinema.R;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.CinemaViewHolder> {
    private ArrayList<CinemaModel> list;

    public MyAdapter(ArrayList<CinemaModel> cinemaModels) {
        list = cinemaModels;
    }
    public class CinemaViewHolder extends  RecyclerView.ViewHolder{
        ImageView imageResourceId;
        CardView cardView;
        TextView cardName,address;
            public CinemaViewHolder(View view)
    {
            super(view);
            imageResourceId=view.findViewById(R.id.coverImageView);
            cardName=view.findViewById(R.id.titleTextView);
            cardView=view.findViewById(R.id.card_view);
            address=view.findViewById(R.id.adressTextView);
        }
    }

    @Override
    public void onBindViewHolder(final CinemaViewHolder holder, int position) {
        YoYo.with(Techniques.FadeInRight).playOn(holder.cardView);
        holder.cardName.setText(list.get(position).getCardName());
        holder.imageResourceId.setImageResource(list.get(position).getImageResourceId());
        holder.address.setText(list.get(position).getAddress());
    }
    @NonNull
    @Override
    public CinemaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_items,parent,false);
        return new CinemaViewHolder(view);
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
}