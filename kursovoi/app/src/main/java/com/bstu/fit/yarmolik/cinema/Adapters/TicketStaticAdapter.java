package com.bstu.fit.yarmolik.cinema.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bstu.fit.yarmolik.cinema.LocalDataBase.DbHelper;
import com.bstu.fit.yarmolik.cinema.LocalDataBase.WorksWithDb;
import com.bstu.fit.yarmolik.cinema.Login;
import com.bstu.fit.yarmolik.cinema.Model.Rating;
import com.bstu.fit.yarmolik.cinema.ModelAdapter.TicketStaticModel;
import com.bstu.fit.yarmolik.cinema.R;
import com.bstu.fit.yarmolik.cinema.Remote.IMyApi;
import com.bstu.fit.yarmolik.cinema.Remote.RetrofitClient;
import com.bstu.fit.yarmolik.cinema.Responces.PlacesResponse;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

public class TicketStaticAdapter extends RecyclerView.Adapter<TicketStaticAdapter.TicketViewHolder> {
    private ArrayList<TicketStaticModel> list;
    private Fragment context;
    private IMyApi iMyApi;
    private CompositeDisposable compositeDisposable=new CompositeDisposable();
    private Date date=new Date();
    private DbHelper dbHelper;
    private WorksWithDb worksWithDb=new WorksWithDb();
    private SQLiteDatabase database;
    private String query;
    private Cursor c;


    private String idSeance,idUser;
    public TicketStaticAdapter(ArrayList<TicketStaticModel> ticketModels,Fragment context) { list=ticketModels; this.context=context;
    }
    public class TicketViewHolder extends RecyclerView.ViewHolder {
        TextView nameFilm;
        TextView dateSeance;
        TextView timeSeance;
        TextView hall;
        TextView cinema;
        TextView reservedSeats;
        Button button,reviewButton;
        CardView cardView;
        Float valueFloat= Float.valueOf(0);
        Dialog popup;
        public TicketViewHolder(View view) {
            super(view);
            nameFilm=view.findViewById(R.id.film_title_text);
            dateSeance=view.findViewById(R.id.textView31);
            timeSeance=view.findViewById(R.id.starting_time_text);
            cardView=view.findViewById(R.id.cardTickets);
            button=view.findViewById(R.id.detail_button);
            reviewButton=view.findViewById(R.id.button3);
            popup = new Dialog(Objects.requireNonNull(context.getActivity()));
            popup.setContentView(R.layout.activity_user_ticket);
            iMyApi= RetrofitClient.getInstance().create(IMyApi.class);
            reservedSeats=popup.findViewById(R.id.reserved_seats);
            hall=popup.findViewById(R.id.textView40);
            cinema=popup.findViewById(R.id.textView39);
            reviewButton.setVisibility(View.GONE);
            dbHelper= new DbHelper(view.getContext(), "project.db", null, 1);
            database=dbHelper.getReadableDatabase();
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
    }
    @NonNull
    @Override
    public TicketStaticAdapter.TicketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_tickets,parent,false);
        return new TicketStaticAdapter.TicketViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TicketStaticAdapter.TicketViewHolder holder, int position) {

        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat simpleDateFormat1=new SimpleDateFormat("dd MMM");
        Calendar calendar=Calendar.getInstance();

        try {
           // date=simpleDateFormat.parse(list.get(position).getDate());
           // calendar.setTime(date);
        YoYo.with(Techniques.FadeInRight).playOn(holder.cardView);
        holder.nameFilm.setText(list.get(position).getFilmName());
        holder.dateSeance.setText(list.get(position).getDate());
        holder.timeSeance.setText(list.get(position).getTime());
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> loadPlace=new ArrayList<>();
                StringBuilder stringBuilder=new StringBuilder();
                query= "select place from tickets where user_id=" +"'"+Login.userId+"'" +" and seance_id="+"'"+list.get(position).getSeance_id()+"'" ;
                c = database.rawQuery(query, null);
                c.moveToFirst();
                while (!c.isAfterLast()) {
                    loadPlace.add(c.getString(0));
                    c.moveToNext();
                }
                c.close();
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
        });
        } catch (Exception e) {
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
