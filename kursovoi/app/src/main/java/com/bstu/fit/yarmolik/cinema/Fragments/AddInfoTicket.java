package com.bstu.fit.yarmolik.cinema.Fragments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bstu.fit.yarmolik.cinema.Adapters.SeanceAdapter;
import com.bstu.fit.yarmolik.cinema.Model.Seance;
import com.bstu.fit.yarmolik.cinema.Model.SeanceDate;
import com.bstu.fit.yarmolik.cinema.ModelAdapter.SeanceModel;
import com.bstu.fit.yarmolik.cinema.R;
import com.bstu.fit.yarmolik.cinema.Remote.IMyApi;
import com.bstu.fit.yarmolik.cinema.Remote.RetrofitClient;
import com.bstu.fit.yarmolik.cinema.Responces.CinemaResponce;
import com.bstu.fit.yarmolik.cinema.Responces.SeanceDateResponse;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;
import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddInfoTicket extends AppCompatActivity {
private String selectedCinema;
String idFilm;
private Button addTicket;
MaterialSpinner spinner;
Fragment currentFragment = null;
FragmentTransaction ft;
RelativeLayout relativeLayout;
private ArrayList<SeanceModel> seanceModels;
ArrayList<String> idCinema,nameCinema,addressCinema,infoCinema;
private ArrayList<String> hallId,startTime,endTime,seanceId,hallName;
List<CinemaResponce> cinema;
public static String dateFilm="";
public static String cinemaInfo="";
RecyclerView recyclerView;
private String idSeanceCard="";
private SeanceAdapter seanceAdapter;
private List<SeanceDateResponse> dateResponses;
CompositeDisposable compositeDisposable;
private IMyApi iMyApi;
private HorizontalCalendar horizontalCalendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_add_info_ticket);
        init();
        Bundle arguments = getIntent().getExtras();
        idFilm=arguments.get("idFilmFragment").toString();
        loadCinema(idFilm);
        spinner.setItems(infoCinema);
        recyclerView.setLayoutManager(new GridLayoutManager(AddInfoTicket.this,3));
        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                recyclerView.setVisibility(View.GONE);
                selectedCinema=idCinema.get(infoCinema.indexOf(item));
                cinemaInfo=infoCinema.get(infoCinema.indexOf(item));
                relativeLayout.setVisibility(View.VISIBLE);
                //Toast.makeText(AddInfoTicket.this, selectedCinema, Toast.LENGTH_LONG).show();
            }
        });
        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Calendar date, int position) {
                clearLists();
                SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
                date.add(Calendar.DAY_OF_MONTH,1);
                dateFilm = format1.format(date.getTime());
                SeanceDate seanceDate=new SeanceDate(selectedCinema,idFilm,dateFilm);
                Call<List<SeanceDateResponse>> call=iMyApi.infoSeanceLoad(seanceDate);
                call.enqueue(new Callback<List<SeanceDateResponse>>() {
                    @Override
                    public void onResponse(Call<List<SeanceDateResponse>> call, Response<List<SeanceDateResponse>> response) {
                        dateResponses=response.body();
                        if(!dateResponses.toString().equals("[]")) {
                            recyclerView.setVisibility(View.VISIBLE);
                            for (SeanceDateResponse seanceDateResponse : dateResponses) {
                                startTime.add(seanceDateResponse.getStartTime());
                                endTime.add(seanceDateResponse.getEndTime());
                                seanceId.add(seanceDateResponse.getId());
                                hallId.add(seanceDateResponse.getHallId());
                                hallName.add(seanceDateResponse.getHallName());
                                //hall.add(seanceDateResponse.)
                            }
                            for(int i=0; i<startTime.size();i++){
                                SimpleDateFormat format2 = new SimpleDateFormat("HH:mm");
                                    seanceModels.add(new SeanceModel(hallName.get(i),startTime.get(i),endTime.get(i),seanceId.get(i)));
                            }
                            seanceAdapter=new SeanceAdapter(seanceModels);
                            recyclerView.setAdapter(seanceAdapter);
                            //Toast.makeText(AddInfoTicket.this, startTime.get(0), Toast.LENGTH_LONG).show();
                        }
                        else{
                            recyclerView.setVisibility(View.GONE);
                            seanceModels.clear();
                            SeanceAdapter.idSeance="";
                            SeanceAdapter.timeEndSeance="";
                            SeanceAdapter.timeStartSeance="";
                            SeanceAdapter.hallDataSeance="";
                            Toast.makeText(AddInfoTicket.this, "нету сеансов на сегодня", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<SeanceDateResponse>> call, Throwable t) {

                    }
                });
            }
        });
        addTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                idSeanceCard="";
                idSeanceCard=SeanceAdapter.idSeance;
                if(!SeanceAdapter.idSeance.equals("")) {
                    Intent intent=new Intent(AddInfoTicket.this, SelectTickets.class);
                    intent.putExtra("SeanceId",SeanceAdapter.idSeance);
                    intent.putExtra("idFilm",idFilm);
                    intent.putExtra("dateSeance",dateFilm);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(AddInfoTicket.this, "выберите данные", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    private void init(){
        idCinema=new ArrayList<String>();
        nameCinema=new ArrayList<String>();
        addressCinema=new ArrayList<String>();
        spinner=findViewById(R.id.spinnerCinemaBook);
        infoCinema=new ArrayList<String>();
        iMyApi= RetrofitClient.getInstance().create(IMyApi.class);
        relativeLayout=findViewById(R.id.relativeCalendar);
        compositeDisposable=new CompositeDisposable();
        startTime=new ArrayList<String>();
        hallId=new ArrayList<String>();
        hallName=new ArrayList<>();
        endTime=new ArrayList<String>();
        recyclerView=findViewById(R.id.list_g);
        seanceModels=new ArrayList<>();
        seanceId=new ArrayList<>();
        addTicket=findViewById(R.id.btn_book);
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.MONTH, 1);
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.DAY_OF_MONTH, -1);

        horizontalCalendar = new HorizontalCalendar.Builder(this, R.id.calendarView)
                .range(startDate, endDate)
                .datesNumberOnScreen(4)
                .build();
    }
    private void loadCinema(String id){
        Call<List<CinemaResponce>> call=iMyApi.getCinemaId(id);
        call.enqueue(new Callback<List<CinemaResponce>>() {
            @Override
            public void onResponse(Call<List<CinemaResponce>> call, Response<List<CinemaResponce>> response) {
                cinema=response.body();
                for(CinemaResponce post:cinema){
                    idCinema.add(post.getId());
                    nameCinema.add(post.getName());
                    addressCinema.add(post.getAdress());
                    for(int i=0;i<idCinema.size();i++){
                        infoCinema.add(i,nameCinema.get(i)+"\n"+addressCinema.get(i));
                    }
                }
            }

            @Override
            public void onFailure(Call<List<CinemaResponce>> call, Throwable t) {
                Toast.makeText(AddInfoTicket.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
    public void clearLists(){
        startTime.clear();
        endTime.clear();
        seanceModels.clear();
        startTime.clear();
        endTime.clear();
        seanceId.clear();
        hallId.clear();
        hallName.clear();
    }
}
