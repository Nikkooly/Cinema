package com.bstu.fit.yarmolik.cinema.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bstu.fit.yarmolik.cinema.ModelAdapter.SeanceModel;
import com.bstu.fit.yarmolik.cinema.R;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.ArrayList;

public class SeanceAdapter extends RecyclerView.Adapter<SeanceAdapter.SeanceViewHolder> {
    private ArrayList<SeanceModel> list;
    private int counter = 0;
    public static String idSeance="";
    public static String timeStartSeance="";
    public static String timeEndSeance="";
    public static String hallDataSeance="";
    public SeanceAdapter(ArrayList<SeanceModel> seanceModels){ list=seanceModels;}
    public class SeanceViewHolder extends RecyclerView.ViewHolder{
        TextView hall,startTime,endTime;
        LinearLayout linearLayout;
        CardView cardView;
        Button addTicket;
        public SeanceViewHolder(View view){
            super(view);
            idSeance="";
            hall=view.findViewById(R.id.hallSTextView);
            linearLayout=view.findViewById(R.id.linearCardLayout);
            startTime=view.findViewById(R.id.startSTimeTextView);
            endTime=view.findViewById(R.id.endSTimeTextView);
            cardView=view.findViewById(R.id.cardSeance);
            //linearLayout.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.card_edge));

        }
    }

    @Override
    public void onBindViewHolder(final SeanceViewHolder holder, int position) {
        YoYo.with(Techniques.FadeInRight).playOn(holder.cardView);
        holder.hall.setText(list.get(position).getHallName());
        holder.startTime.setText(list.get(position).getStartTime());
        holder.endTime.setText(list.get(position).getEndTime());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                idSeance="";
                counter++;
                if(counter%2==1) {
                    holder.linearLayout.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.card_select));
                    idSeance= list.get(position).getId();
                    timeStartSeance=list.get(position).getStartTime();
                    timeEndSeance=list.get(position).getEndTime();
                    hallDataSeance=list.get(position).getHallName();
                }
                else{
                    holder.linearLayout.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.card_edge));
                }
            }
        });

    }
    @NonNull
    @Override
    public SeanceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_seance,parent,false);
        return new SeanceViewHolder(view);
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
}
