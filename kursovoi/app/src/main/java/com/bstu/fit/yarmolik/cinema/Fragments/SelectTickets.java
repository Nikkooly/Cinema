package com.bstu.fit.yarmolik.cinema.Fragments;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.bstu.fit.yarmolik.cinema.Adapters.GridArrayAdapter;
import com.bstu.fit.yarmolik.cinema.R;
import com.bstu.fit.yarmolik.cinema.Remote.IMyApi;
import com.bstu.fit.yarmolik.cinema.Remote.RetrofitClient;
import com.bstu.fit.yarmolik.cinema.Responces.SeanceInfo;
import com.bstu.fit.yarmolik.cinema.Responces.TicketInfoData;
import com.bstu.fit.yarmolik.cinema.Responces.TicketResponse;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectTickets extends AppCompatActivity {
private String seanceId,idFilm,dateFilm;
private IMyApi iMyApi;
private ArrayList<Integer> placesList;
public ArrayList<Double> priceList;
private TextView priceTextView;
private ArrayList<String> idTicketsListSelectByUser;
private ArrayList<Boolean> statusList;
private GridView gridView;
private GridArrayAdapter adapter;
private Integer size;
public static Integer countOfPlaces=0;
public static Double priceOfPlace=0.0;
public static Double price=0.0;
public static Integer counter=0;
public static Double finalPrice=0.0;
private Button button;
public static ArrayList<Integer> bookTicket;
private TextView countTextView,finalPriceTextView;

private List<TicketResponse> ticketResponseArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_select_tickets);
        init();
        //placesList.clear();
        //loadTicketsInfo(seanceId);
        loadBookTickets(seanceId);
        loadSeanceInfo(seanceId);
        Timer timer = new Timer();
        long delay = 0;
        long period = 100;
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        counter=GridArrayAdapter.counter;
                        //DecimalFormat df = new DecimalFormat("###.#");
                        finalPrice=counter*priceOfPlace;
                        double roundOff = Math.round(finalPrice * 100.0) / 100.0;
                        countTextView.setText(counter.toString());
                        finalPriceTextView.setText(String.valueOf(roundOff));
                    }
                });
            }
        },delay,period);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (adapter.getSelectedPositions().size() > 0) {
                    for(int i=0;i<adapter.getSelectedPositions().size();i++){
                        Integer poss=adapter.getSelectedPositions().get(i)+1;
                        idTicketsListSelectByUser.add(poss.toString());
                        //Toast.makeText(SelectTickets.this, idTicketsList.get(adapter.getSelectedPositions().get(i)), Toast.LENGTH_LONG).show();
                    }
                    Intent intent = new Intent(SelectTickets.this,TicketInfo.class);
                    intent.putExtra("idFilm",idFilm);
                    intent.putExtra("idSeance",seanceId);
                    intent.putExtra("list",returnIdTickets());
                    intent.putExtra("dateFilm",dateFilm);
                    startActivity(intent);
                    idTicketsListSelectByUser.clear();
                } else {
                    Toast.makeText(SelectTickets.this, "Выберите места", Toast.LENGTH_LONG).show();
                }

            }
        });
    }
    private void init(){
        iMyApi= RetrofitClient.getInstance().create(IMyApi.class);
        Bundle arguments = getIntent().getExtras();
        seanceId=arguments.get("SeanceId").toString();
        idFilm=arguments.getString("idFilm");
        dateFilm=arguments.getString("dateSeance");
        statusList=new ArrayList<>();
        priceList=new ArrayList<>();
        placesList=new ArrayList<>();
        gridView=findViewById(R.id.gridPlaces);
        priceTextView=findViewById(R.id.priceTicket);
        countTextView=findViewById(R.id.countTicketsTextView);
        finalPriceTextView=findViewById(R.id.priceFinal);
        button=findViewById(R.id.reservTicket);
        idTicketsListSelectByUser=new ArrayList<>();
        bookTicket=new ArrayList<>();

    }
    public void loadSeanceInfo(String id){
        Call<List<SeanceInfo>> call=iMyApi.getSeanceInfo(id);
        call.enqueue(new Callback<List<SeanceInfo>>() {
            @Override
            public void onResponse(Call<List<SeanceInfo>> call, Response<List<SeanceInfo>> response) {
                for(SeanceInfo seanceInfo :response.body()){
                    countOfPlaces=seanceInfo.getPlaces();
                    priceOfPlace=seanceInfo.getTicketPrice();
                }
                priceTextView.setText(priceOfPlace.toString());
                for(int i=1;i<=countOfPlaces;i++){
                    placesList.add(i);
                    statusList.add(false);
                }
                for(int i=0;i<bookTicket.size();i++){
                    statusList.set(bookTicket.get(i)-1,true);
                }
                adapter = new GridArrayAdapter(SelectTickets.this);
                adapter.setInfo(statusList, placesList,0);
                gridView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<SeanceInfo>> call, Throwable t) {

            }
        });
    }
    public void loadBookTickets(String id){
        Call<List<TicketInfoData>> call=iMyApi.getTicketInfo(id);
        call.enqueue(new Callback<List<TicketInfoData>>() {
            @Override
            public void onResponse(Call<List<TicketInfoData>> call, Response<List<TicketInfoData>> response) {
                for(TicketInfoData ticketInfoData: response.body()){
                    bookTicket.add(ticketInfoData.getPlace());
                }
            }

            @Override
            public void onFailure(Call<List<TicketInfoData>> call, Throwable t) {

            }
        });
    }
    private ArrayList<String> returnIdTickets(){
        return idTicketsListSelectByUser;
    }
}
