package com.bstu.fit.yarmolik.cinema.Fragments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bstu.fit.yarmolik.cinema.Adapters.SeanceAdapter;
import com.bstu.fit.yarmolik.cinema.LocalDataBase.DbHelper;
import com.bstu.fit.yarmolik.cinema.LocalDataBase.WorksWithDb;
import com.bstu.fit.yarmolik.cinema.Login;
import com.bstu.fit.yarmolik.cinema.Model.BoughtTicket;
import com.bstu.fit.yarmolik.cinema.R;
import com.bstu.fit.yarmolik.cinema.Registration;
import com.bstu.fit.yarmolik.cinema.Remote.IMyApi;
import com.bstu.fit.yarmolik.cinema.Remote.RetrofitClient;
import com.bstu.fit.yarmolik.cinema.Responces.FilmResponse;
import com.bstu.fit.yarmolik.cinema.Responces.PlacesNumber;
import com.bstu.fit.yarmolik.cinema.Responces.PlacesResponse;
import com.bstu.fit.yarmolik.cinema.Responces.TicketStaticResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/*import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;*/

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import dmax.dialog.SpotsDialog;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TicketInfo extends AppCompatActivity {
private ArrayList<String> selectedTickets;
private Fragment currentFragment = null;
private FragmentTransaction ft;
private String seanceId,idFilm,dateFilm;
private List<FilmResponse> posts;
private ArrayList<Integer> places;
private IMyApi iMyApi;
public String nameFilm="";
private String date,startTime,endTime,cinemaInfo,hallInfo,count,finalPrice;
private TextView nameFilmTextView, genreTextView,dateTimeTextView,dateTextView,cinemaInfoTextView,hallNameTextView,priceTextView,countPlacesTextView;
private ConstraintLayout constraintLayout;
private EditText emailEditText;
private Button button;
private DbHelper dbHelper;
private String query;
private Cursor c;
private WorksWithDb worksWithDb=new WorksWithDb();
private SQLiteDatabase database;
private CompositeDisposable compositeDisposable;
private ImageView imageView,imageView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_ticket_info);
        init();
        AlertDialog alertDialog = new SpotsDialog.Builder()
                .setContext(TicketInfo.this)
                .build();
        alertDialog.show();
        if(Login.userRoleId==1){
            emailEditText.setText(Login.userEmail);
        }
        selectedTickets=(ArrayList<String>) getIntent().getSerializableExtra("list");
        loadFilms(idFilm);
        alertDialog.dismiss();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checkEmail = emailEditText.getText().toString().matches("^([A-Za-z0-9_-]+\\.)*[A-Za-z0-9_-]+@[a-z0-9_-]+(\\.[a-z0-9_-]+)*\\.[a-z]{2,6}$");
                if (checkEmail) {
                    BoughtTicket boughtTicket = new BoughtTicket(
                            Login.userId,
                            emailEditText.getText().toString(),
                            date,
                            startTime,
                            endTime,
                            nameFilmTextView.getText().toString(),
                            cinemaInfo,
                            hallInfo,
                            seanceId,
                            selectedTickets
                    );
                    AlertDialog alertDialog = new SpotsDialog.Builder()
                            .setContext(TicketInfo.this)
                            .build();
                    alertDialog.show();
                    compositeDisposable.add(iMyApi.publishTickets(boughtTicket)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Consumer<String>() {
                                @Override
                                public void accept(String s) throws Exception {
                                    alertDialog.dismiss();
                                    //Toast.makeText(TicketInfo.this, s, Toast.LENGTH_SHORT).show();
                                    if (!s.equals("No")) {
                                        try {
                                            //dbHelper.insertSeance(seanceId, cinemaInfo, hallInfo, nameFilmTextView.getText().toString(), date, startTime, endTime);
                                            //dbHelper.deleteTickets(seanceId,Login.userId);
                                            Call<List<PlacesResponse>> call = iMyApi.getPlaces(Login.userId, seanceId);
                                            ArrayList<Integer> loadPlace = new ArrayList<>();
                                            ArrayList<String> idPlace=new ArrayList<>();
                                            call.enqueue(new Callback<List<PlacesResponse>>() {
                                                @Override
                                                public void onResponse(Call<List<PlacesResponse>> call, Response<List<PlacesResponse>> response) {
                                                    for (PlacesResponse placesResponse : response.body()) {
                                                        loadPlace.add((placesResponse.getPlace()));
                                                        idPlace.add(placesResponse.getId());
                                                    }
                                                   /* for (int i = 0; i < loadPlace.size(); i++) {
                                                        dbHelper.insertTicket(idPlace.get(i),seanceId,Login.userId,loadPlace.get(i).toString());
                                                    }*/
                                                    Toast.makeText(TicketInfo.this, "Успешно добавлено", Toast.LENGTH_SHORT).show();
                                                    Intent intent = new Intent(TicketInfo.this, MainActivity.class);
                                                    startActivity(intent);
                                                    /*Toast.makeText(TicketInfo.this, s, Toast.LENGTH_SHORT).show();*/
                                                }

                                                @Override
                                                public void onFailure(Call<List<PlacesResponse>> call, Throwable t) {

                                                }
                                            });
                                        }
                                        catch(Exception ex){
                                            Toast.makeText(TicketInfo.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Toast.makeText(TicketInfo.this, "Непридвиденная ошибка, приносим свои извинения!", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            }, new Consumer<Throwable>() {
                                @Override
                                public void accept(Throwable throwable) throws Exception {
                                    Toast.makeText(TicketInfo.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            })
                    );
                }
                else{
                    Toast.makeText(TicketInfo.this, "Почта введена неверно", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void init(){
        selectedTickets=new ArrayList<>();
        iMyApi= RetrofitClient.getInstance().create(IMyApi.class);
        Bundle arguments = getIntent().getExtras();
        seanceId=arguments.get("idSeance").toString();
        idFilm=arguments.getString("idFilm");
        dateFilm=arguments.getString("dateFilm");
        imageView=findViewById(R.id.imageView3);
        imageView2=findViewById(R.id.imageView4);
        constraintLayout=findViewById(R.id.infoFinalTicket);
        nameFilmTextView=findViewById(R.id.textView28);
        genreTextView=findViewById(R.id.textView29);
        dateTimeTextView=findViewById(R.id.textView30);
        dateTextView=findViewById(R.id.textView33);
        cinemaInfoTextView=findViewById(R.id.textView34);
        hallNameTextView=findViewById(R.id.textView35);
        priceTextView=findViewById(R.id.textView37);
        countPlacesTextView=findViewById(R.id.textView38);
        emailEditText=findViewById(R.id.editText);
        button=findViewById(R.id.button_add_tickets);
        date=AddInfoTicket.dateFilm;
        startTime=SeanceAdapter.timeStartSeance;
        endTime=SeanceAdapter.timeEndSeance;
        cinemaInfo=AddInfoTicket.cinemaInfo;
        hallInfo=SeanceAdapter.hallDataSeance;
        count=SelectTickets.counter.toString();
        double roundOff = Math.round(SelectTickets.finalPrice * 100.0) / 100.0;
        //finalPrice=SelectTickets.finalPrice.toString();
        dateTimeTextView.setText(SeanceAdapter.timeStartSeance+"-"+SeanceAdapter.timeEndSeance);
        dateTextView.setText(AddInfoTicket.dateFilm);
        cinemaInfoTextView.setText(AddInfoTicket.cinemaInfo);
        hallNameTextView.setText(SeanceAdapter.hallDataSeance);
        priceTextView.setText(String.valueOf(roundOff)+" руб.");
        countPlacesTextView.setText(SelectTickets.counter.toString()+" м.");
        compositeDisposable=new CompositeDisposable();
        places=new ArrayList<>();
        dbHelper = new DbHelper(this, "project.db", null, 1);
        database=dbHelper.getWritableDatabase();
    }
    private void loadFilms(String id){
        Call<FilmResponse> call=iMyApi.getFilm(id);
        call.enqueue(new Callback<FilmResponse>() {
            @Override
            public void onResponse(Call<FilmResponse> call, Response<FilmResponse> response) {
                Picasso.get().load(response.body().getPoster().toString()).into(imageView);
                Picasso.get().load(response.body().getPoster().toString()).into(imageView2);
                nameFilm=response.body().getName().toString();
                nameFilmTextView.setText(response.body().getName());
                genreTextView.setText(response.body().getGenre());
            }

            @Override
            public void onFailure(Call<FilmResponse> call, Throwable t) {
                Toast.makeText(TicketInfo.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
