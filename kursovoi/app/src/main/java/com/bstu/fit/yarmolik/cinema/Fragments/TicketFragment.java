package com.bstu.fit.yarmolik.cinema.Fragments;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.Toolbar;

import com.bstu.fit.yarmolik.cinema.Adapters.TicketAdapter;
import com.bstu.fit.yarmolik.cinema.Adapters.TicketStaticAdapter;
import com.bstu.fit.yarmolik.cinema.CheckInternetConnection;
import com.bstu.fit.yarmolik.cinema.LocalDataBase.DbHelper;
import com.bstu.fit.yarmolik.cinema.LocalDataBase.WorksWithDb;
import com.bstu.fit.yarmolik.cinema.Login;
import com.bstu.fit.yarmolik.cinema.ModelAdapter.TicketModel;
import com.bstu.fit.yarmolik.cinema.ModelAdapter.TicketStaticModel;
import com.bstu.fit.yarmolik.cinema.R;
import com.bstu.fit.yarmolik.cinema.Registration;
import com.bstu.fit.yarmolik.cinema.Remote.IMyApi;
import com.bstu.fit.yarmolik.cinema.Remote.RetrofitClient;
import com.bstu.fit.yarmolik.cinema.Responces.UserTicket;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TicketFragment extends Fragment {
    CheckInternetConnection checkInternetConnection;
    private ConstraintLayout constraintLayout;
    private RecyclerView recyclerView;
    private IMyApi iMyApi;
    public boolean checkInternetState;
    private TicketAdapter ticketAdapter;
    private TicketStaticAdapter ticketStaticAdapter;
    private ArrayList<TicketModel> ticketModels;
    private ArrayList<TicketStaticModel> ticketStaticModels;
    private ArrayList<String> nameFilm,dateSeance,idSeance,timeSeance,cinema,hall,filmId,endTime,places;
    private boolean status=false;
    private DbHelper dbHelper;
    private WorksWithDb worksWithDb=new WorksWithDb();
    private SQLiteDatabase database;
    private String query;
    private Cursor c;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ticket, container, false);
        init(view);
        try {
            if (checkInternetConnection.isOnline(getContext())) {
                checkInternetState = true;
                if (Login.userRoleId == 1) {
                    constraintLayout.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    loadUserTicketsInfo(Login.userId);
                } else if (Login.userRoleId == 3) {
                    constraintLayout.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }
            } else {
                checkInternetState = false;
                try {
                    query = "select distinct s.cinema_info,s.hall_info,s.film_info,s.date,s.start_time,s.end_time,s.id from tickets t inner join seance s on t.seance_id=s.id " +
                            "where t.user_id=" + "'" + Login.userId + "'";
                    c = database.rawQuery(query, null);
                    c.moveToFirst();
                    while (!c.isAfterLast()) {
                        cinema.add( c.getString(0).toString());
                        hall.add( c.getString(1).toString());
                        nameFilm.add(c.getString(2).toString());
                        dateSeance.add( c.getString(3).toString());
                        timeSeance.add( c.getString(4).toString());
                        endTime.add( c.getString(5).toString());
                        idSeance.add(c.getString(6).toString());
                        c.moveToNext();
                    }
                    c.close();
                    if (nameFilm.size()!=0) {
                        constraintLayout.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        for(int i=0;i<nameFilm.size();i++) {
                            ticketStaticModels.add(new TicketStaticModel(cinema.get(i),hall.get(i),nameFilm.get(i),dateSeance.get(i),timeSeance.get(i),endTime.get(i),idSeance.get(i)));
                           // ticketStaticModels.add(new TicketStaticModel("fggbfbgf","bgfbgfbgf","fgbbgfbgf","bfgfgbbg","bfgbfgbg","fgbfgbfgb","Fgbbfgbgf"));
                        }
                        ticketStaticAdapter = new TicketStaticAdapter(ticketStaticModels, TicketFragment.this);
                        recyclerView.setAdapter(ticketStaticAdapter);
                    } else {
                        constraintLayout.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    }
                }
                catch (Exception ex){
                    Toast.makeText(getContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }
        catch (Exception ex){
            Toast.makeText(getContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
        }

        return view;
    }
    private void init(View view){
        constraintLayout=view.findViewById(R.id.ticketsConstraintLayoutNot);
        recyclerView=view.findViewById(R.id.recyclerTicketView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        iMyApi= RetrofitClient.getInstance().create(IMyApi.class);
        nameFilm=new ArrayList<>();
        idSeance=new ArrayList<>();
        dateSeance=new ArrayList<>();
        ticketModels=new ArrayList<>();
        ticketStaticModels =new ArrayList<>();
        timeSeance=new ArrayList<>();
        cinema=new ArrayList<>();
        hall=new ArrayList<>();
        filmId=new ArrayList<>();
        endTime=new ArrayList<>();
        checkInternetConnection=new CheckInternetConnection();
        dbHelper = new DbHelper(getContext(), "project.db", null, 1);
        database=dbHelper.getReadableDatabase();
        places=new ArrayList<>();
    }
    private void loadUserTicketsInfo(String id){
        Call<List<UserTicket>> call=iMyApi.getTickets(id);
        call.enqueue(new Callback<List<UserTicket>>() {
            @Override
            public void onResponse(Call<List<UserTicket>> call, Response<List<UserTicket>> response) {
                if(!response.body().toString().equals("[]") || !response.body().toString().equals("")){
                    status=true;
                    for(UserTicket userTicket: response.body()){
                        nameFilm.add(userTicket.getName());
                        idSeance.add(userTicket.getSeanceId());
                        dateSeance.add(userTicket.getDate());
                        timeSeance.add(userTicket.getTime());
                        cinema.add(userTicket.getCinema());
                        hall.add(userTicket.getHall());
                        filmId.add(userTicket.getFilmId());
                        endTime.add(userTicket.getEndTime());
                    }
                    for(int i=0;i<nameFilm.size();i++){
                        try{
                        ticketModels.add(new TicketModel(nameFilm.get(i),
                                idSeance.get(i),
                                dateSeance.get(i),
                                timeSeance.get(i),
                                cinema.get(i),
                                hall.get(i),
                                filmId.get(i),
                                endTime.get(i)));
                        }
                        catch (Exception ex){
                            Toast.makeText(getContext(),ex.getMessage()+" Exception",Toast.LENGTH_SHORT).show();
                        }
                    }
                    if(ticketModels.size()==0){
                        constraintLayout.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    }
                    else {
                        ticketAdapter = new TicketAdapter(ticketModels, TicketFragment.this);
                        recyclerView.setAdapter(ticketAdapter);
                    }
                }
                else{
                    status=false;
                }
            }

            @Override
            public void onFailure(Call<List<UserTicket>> call, Throwable t) {
                constraintLayout.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            }
        });
    }
}
