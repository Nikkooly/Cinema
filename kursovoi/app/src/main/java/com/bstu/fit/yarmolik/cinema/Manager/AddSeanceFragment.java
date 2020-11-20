package com.bstu.fit.yarmolik.cinema.Manager;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.bstu.fit.yarmolik.cinema.Login;
import com.bstu.fit.yarmolik.cinema.Model.Seance;
import com.bstu.fit.yarmolik.cinema.R;
import com.bstu.fit.yarmolik.cinema.Remote.IMyApi;
import com.bstu.fit.yarmolik.cinema.Remote.RetrofitClient;
import com.bstu.fit.yarmolik.cinema.Responces.CinemaResponce;
import com.bstu.fit.yarmolik.cinema.Responces.FilmResponse;
import com.bstu.fit.yarmolik.cinema.Responces.HallResponse;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.chrono.Chronology;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;

import dmax.dialog.SpotsDialog;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AddSeanceFragment extends Fragment {
    TextView currentDateTime;
    private IMyApi iMyApi;
    List<CinemaResponce> cinema;
    List<HallResponse> hall;
    TextView dataTextView;
    List<FilmResponse> posts;
    private ArrayList<String> list, listHall, listFilm;
    private ArrayList<String> idList, idListHall, idListFilm;
    private ArrayList<Integer> countOfPlaces,durationOfTheFilm;
    private String chooseCinema="",chooseFilm="",chooseHall="";
    private MaterialSpinner cinemaSpinner,hallSpinner,filmSpinner;
    CompositeDisposable compositeDisposable;
    Button setDate,addSeanceButton;
    private Integer yearCalendar,month,day;
    private String date="";
    Calendar dateAndTime=Calendar.getInstance();
    private OnFragment1DataListener mListener;
    //Calendar dateAndTime=Calendar.getInstance();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_add_seance, container, false);
        init(view);
        loadCinema();
        loadFilms();
        cinemaSpinner.setItems(list);
        filmSpinner.setItems(listFilm);
        cinemaSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                listHall.clear();
                loadHallInfo(idList.get(list.indexOf(item)));
                hallSpinner.setItems(listHall);
                chooseCinema=item;
            }
        });
        hallSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                chooseHall=item;
            }
        });
        filmSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                chooseFilm=item;
                //Toast.makeText(getContext(),durationOfTheFilm.get(listFilm.indexOf(chooseFilm)).toString(),Toast.LENGTH_SHORT).show();
            }
        });
        setDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getContext(), d,
                        dateAndTime.get(Calendar.YEAR),
                        dateAndTime.get(Calendar.MONTH),
                        dateAndTime.get(Calendar.DAY_OF_MONTH))
                        .show();
            }
        });

        addSeanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!chooseCinema.equals("") && !chooseFilm.equals("") && !chooseHall.equals("") && !date.equals("")){
                    Date currentDate = new Date();
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    String dateText = dateFormat.format(currentDate);
                    try {
                        Date date1 = dateFormat.parse(dateText);
                        Date date2 = dateFormat.parse(date);
                        if (date1.compareTo(date2) < 0) {
                            mListener.onOpenFragment2(chooseCinema,
                                    chooseHall,
                                    countOfPlaces.get(listHall.indexOf(chooseHall)),
                                    chooseFilm,
                                    durationOfTheFilm.get(listFilm.indexOf(chooseFilm)),
                                    date,
                                    idListHall.get(listHall.indexOf(chooseHall)),
                                    idListFilm.get(listFilm.indexOf(chooseFilm)));
                        }
                        else{
                            Toast.makeText(getContext(),"Выберите дату не раньше завтрашнего дня",Toast.LENGTH_SHORT).show();
                        }
                    }
                    catch (Exception ex){
                        Toast.makeText(getContext(),ex.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(getContext(),"Заполните поля",Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }
    public void init(View view){
        iMyApi= RetrofitClient.getInstance().create(IMyApi.class);
        cinemaSpinner=view.findViewById(R.id.spinnerSelectCinemaOfSeance);
        hallSpinner=view.findViewById(R.id.spinnerSelectHallOfSeance);
        filmSpinner=view.findViewById(R.id.spinnerSelectFilmOfSeance);
        dataTextView=view.findViewById(R.id.textView21);
        setDate=view.findViewById(R.id.dateButton);
        addSeanceButton=view.findViewById(R.id.addSeance);
        list= new ArrayList<String>();
        idList=new ArrayList<String>();
        listHall= new ArrayList<String>();
        idListHall=new ArrayList<String>();
        durationOfTheFilm=new ArrayList<Integer>();
        listFilm=new ArrayList<String>();
        idListFilm=new ArrayList<String>();
        compositeDisposable=new CompositeDisposable();
        countOfPlaces= new ArrayList<Integer>();
    }
    private void setInitialDateTime() {

        date=DateUtils.formatDateTime(getContext(),
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR);
    }
    DatePickerDialog.OnDateSetListener d=new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, monthOfYear, dayOfMonth);
            Integer monthCalendar = calendar.get(Calendar.MONTH) + 1;
            Integer dayCalendar = calendar.get(Calendar.DAY_OF_MONTH);
            Integer yearCalendar = calendar.get(Calendar.YEAR);
            //date = dayCalendar.toString() + "." + monthCalendar.toString() + "." + yearCalendar.toString();
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
            date = format1.format(calendar.getTime());
            dataTextView.setText(date);

        }
    };
    private void loadCinema(){

        Call<List<CinemaResponce>> call=iMyApi.getCinema();
        call.enqueue(new Callback<List<CinemaResponce>>() {
            @Override
            public void onResponse(Call<List<CinemaResponce>> call, Response<List<CinemaResponce>> response) {
                cinema=response.body();
                for(CinemaResponce post : cinema){
                    list.add(post.getName());
                    idList.add(post.getId());
                }
            }
            @Override
            public void onFailure(Call<List<CinemaResponce>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
    private void loadHallInfo(String id){

        Call<List<HallResponse>> call =iMyApi.getHallInfo(id);
        call.enqueue(new Callback<List<HallResponse>>() {
            @Override
            public void onResponse(Call<List<HallResponse>> call, Response<List<HallResponse>> response) {
                hall=response.body();
                if(!hall.toString().equals("Зал не найден")) {
                    for (HallResponse h : hall) {
                        listHall.add(h.getName());
                        idListHall.add(h.getId());
                        countOfPlaces.add(h.getPlaces());
                    }
                }
                else{
                    Toast.makeText(getContext(), "Залов в данном кинотеатре не добавлено", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<HallResponse>> call, Throwable t) {
                //Toast.makeText(getContext(),t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                Toast.makeText(getContext(), "Залов в данном кинотеатре не добавлено", Toast.LENGTH_LONG).show();
            }
        });

    }
    private void loadFilms(){
        Call<List<FilmResponse>> call=iMyApi.getFilms();
        call.enqueue(new Callback<List<FilmResponse>>() {
            @Override
            public void onResponse(Call<List<FilmResponse>> call, Response<List<FilmResponse>> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getContext(), response.code(), Toast.LENGTH_LONG).show();
                }
                posts=response.body();
                for(FilmResponse post : posts){
                    listFilm.add(post.getName());
                    idListFilm.add(post.getId());
                    durationOfTheFilm.add(post.getDuration());
                }
            }
            @Override
            public void onFailure(Call<List<FilmResponse>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragment1DataListener) {
            mListener = (OnFragment1DataListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragment1DataListener");
        }
    }

}
