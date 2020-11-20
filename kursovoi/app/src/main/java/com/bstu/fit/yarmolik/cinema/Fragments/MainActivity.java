package com.bstu.fit.yarmolik.cinema.Fragments;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bstu.fit.yarmolik.cinema.Adapters.TicketAdapter;
import com.bstu.fit.yarmolik.cinema.CheckInternetConnection;
import com.bstu.fit.yarmolik.cinema.Fragments.CinemaFragment;
import com.bstu.fit.yarmolik.cinema.Fragments.FilmFragment;
import com.bstu.fit.yarmolik.cinema.Fragments.FragmentMore;
import com.bstu.fit.yarmolik.cinema.Fragments.SliderFragment;
import com.bstu.fit.yarmolik.cinema.Fragments.TicketFragment;
import com.bstu.fit.yarmolik.cinema.LocalDataBase.DbHelper;
import com.bstu.fit.yarmolik.cinema.LocalDataBase.WorksWithDb;
import com.bstu.fit.yarmolik.cinema.Login;
import com.bstu.fit.yarmolik.cinema.Manager.AddPlacesSeanceFragment;
import com.bstu.fit.yarmolik.cinema.Manager.ManagerActivity;
import com.bstu.fit.yarmolik.cinema.Model.LoginUser;
import com.bstu.fit.yarmolik.cinema.ModelAdapter.TicketModel;
import com.bstu.fit.yarmolik.cinema.R;
import com.bstu.fit.yarmolik.cinema.Remote.IMyApi;
import com.bstu.fit.yarmolik.cinema.Remote.RetrofitClient;
import com.bstu.fit.yarmolik.cinema.Responces.CinemaResponce;
import com.bstu.fit.yarmolik.cinema.Responces.FilmResponse;
import com.bstu.fit.yarmolik.cinema.Responces.PlacesResponse;
import com.bstu.fit.yarmolik.cinema.Responces.UserTicket;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity  {
SliderFragment sliderFragment;
    private Toolbar toolbar;
    Fragment currentFragment = null;
    FragmentTransaction ft;
    private boolean status=true;
    public IMyApi iMyApi;
    public List<FilmResponse> film;
    public ArrayList<String> nameList,countryList,descriptionList,posterList,genreList,idList;
    private ArrayList<String> nameFilm,dateSeance,idSeance,timeSeance,cinema,hall,filmId,endTime,filmName,places;
    public ArrayList<Integer> durationList,yearList;
    private BottomNavigationView bottomNavigationView;
    private boolean stateInternet;
    CheckInternetConnection checkInternetConnection;
    private DbHelper dbHelper;
    private WorksWithDb worksWithDb=new WorksWithDb();
    private SQLiteDatabase database;
    private String query;
    private Cursor c;
    public void Open(String idFilm){
        currentFragment = new InfoFilmFragment();
        Bundle bundle = new Bundle();
        bundle.putString("idFilmAdapter",idFilm);
        currentFragment.setArguments(bundle);
        ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container, currentFragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        init();
        if (checkInternetConnection.isOnline(MainActivity.this)) {
            if(Login.userRoleId!=3) {
                if(status) {
                    loadUserTicketsInfo(Login.userId);
                    status=false;
                }
            }
            else{

            }
        }
        iMyApi= RetrofitClient.getInstance().create(IMyApi.class);
        ft = getSupportFragmentManager().beginTransaction();
        currentFragment = new SliderFragment();
        ft.replace(R.id.container, currentFragment);
        ft.commit();
        bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.bottombaritem_popular:
                                currentFragment = new SliderFragment();
                                ft = getSupportFragmentManager().beginTransaction();
                                ft.replace(R.id.container, currentFragment);
                                ft.addToBackStack(null);
                                ft.commit();
                                return true;
                            case R.id.bottombaritem_films:
                                currentFragment = new FilmFragment();
                                ft = getSupportFragmentManager().beginTransaction();
                                ft.replace(R.id.container, currentFragment);
                                ft.addToBackStack(null);
                                ft.commit();
                                return true;
                            case R.id.bottombaritem_cinemas:
                                currentFragment = new CinemaFragment();
                                ft = getSupportFragmentManager().beginTransaction();
                                ft.replace(R.id.container, currentFragment);
                                ft.addToBackStack(null);
                                ft.commit();
                                return true;
                            case R.id.bottombaritem_tickets:
                                currentFragment = new TicketFragment();
                                ft = getSupportFragmentManager().beginTransaction();
                                ft.replace(R.id.container, currentFragment);
                                ft.addToBackStack(null);
                                ft.commit();
                                return true;
                            case R.id.bottombaritem_more:
                                currentFragment = new FragmentMore();
                                ft = getSupportFragmentManager().beginTransaction();
                                ft.replace(R.id.container, currentFragment);
                                ft.commit();
                                return true;
                        }
                        return false;
                    }
                });
    }

        public void init(){
            iMyApi= RetrofitClient.getInstance().create(IMyApi.class);
            nameList= new ArrayList<String>();
            idList=new ArrayList<String>();
            genreList=new ArrayList<String>();
            descriptionList=new ArrayList<String>();
            yearList=new ArrayList<Integer>();
            durationList=new ArrayList<Integer>();
            posterList=new ArrayList<String>();
            countryList= new ArrayList<String>();
            nameFilm=new ArrayList<>();
            idSeance=new ArrayList<>();
            dateSeance=new ArrayList<>();
            timeSeance=new ArrayList<>();
            cinema=new ArrayList<>();
            hall=new ArrayList<>();
            filmId=new ArrayList<>();
            endTime=new ArrayList<>();
            dbHelper = new DbHelper(MainActivity.this, "project.db", null, 1);
            database=dbHelper.getReadableDatabase();
            filmName=new ArrayList<>();
            checkInternetConnection=new CheckInternetConnection();
        }
    private void loadUserTicketsInfo(String id){
        Call<List<UserTicket>> call=iMyApi.getTicketsLocal(id);
        call.enqueue(new Callback<List<UserTicket>>() {
            @Override
            public void onResponse(Call<List<UserTicket>> call, Response<List<UserTicket>> response) {
                if(!response.body().toString().equals("[]") || !response.body().toString().equals("")){
                    dbHelper.deleteAllTickets();
                    dbHelper.deleteAllSeances();
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
                        dbHelper.insertSeance(idSeance.get(i),cinema.get(i),hall.get(i),nameFilm.get(i),dateSeance.get(i),timeSeance.get(i),endTime.get(i));
                        loadPlaces(idSeance.get(i));
                    }
                }
                else{
                }
            }

            @Override
            public void onFailure(Call<List<UserTicket>> call, Throwable t) {
                Toast.makeText(MainActivity.this,t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void loadPlaces(String seanceId){
        Call<List<PlacesResponse>> call=iMyApi.getPlaces(Login.userId,seanceId);
        ArrayList<Integer> loadPlace=new ArrayList<>();
        ArrayList<String> placeId=new ArrayList<>();
        call.enqueue(new Callback<List<PlacesResponse>>() {
            @Override
            public void onResponse(Call<List<PlacesResponse>> call, Response<List<PlacesResponse>> response) {
                for (PlacesResponse placesResponse : response.body()) {
                    loadPlace.add((placesResponse.getPlace()));
                    placeId.add(placesResponse.getId());
                }
                for(int i=0;i<loadPlace.size();i++){
                    dbHelper.insertTicket(placeId.get(i),seanceId,Login.userId,loadPlace.get(i).toString());
                }
            }
            @Override
            public void onFailure(Call<List<PlacesResponse>> call, Throwable t) {

            }
        });
    }
        public void Finish(){
        finish();
        }
}