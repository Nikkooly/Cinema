package com.bstu.fit.yarmolik.cinema.Manager;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bestsoft32.tt_fancy_gif_dialog_lib.TTFancyGifDialog;
import com.bestsoft32.tt_fancy_gif_dialog_lib.TTFancyGifDialogListener;
import com.bstu.fit.yarmolik.cinema.Fragments.FilmFragment;
import com.bstu.fit.yarmolik.cinema.Model.Seance;
import com.bstu.fit.yarmolik.cinema.R;
import com.bstu.fit.yarmolik.cinema.Remote.IMyApi;
import com.bstu.fit.yarmolik.cinema.Remote.RetrofitClient;
import com.bstu.fit.yarmolik.cinema.Responces.HallResponse;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import dmax.dialog.SpotsDialog;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddPlacesSeanceFragment extends Fragment {
private TextView  filmName,cinemaName, hallName, countPlaces,timeStartTextView,timeEndTextView,durationTextView;
private String film, cinema, hall,time,idHallSeance,date,idHallFilm;
private Integer durationOfTheFilm;
private String selectedStartDate="",selectedEndDate="";
private String timeCheckOne, timeCheckTwo;
private Date time1,time2;
private Date newDate;
private Integer countOfPlaces;
private  Integer duration;
private Button addPlaces,timeStart,timeEnd;
private ArrayList<Integer> placesList;
private List<HallResponse> hallResponse;
private FloatingActionButton fab;
private String select="All";
private EditText priceText;
Calendar dateAndTime=Calendar.getInstance();
private RadioButton buttonAll,buttonHalf;
CompositeDisposable compositeDisposable;
IMyApi iMyApi;
RadioGroup radioGroup;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view =inflater.inflate(R.layout.fragment_add_places_seance, container, false);
       init(view);
       filmName.setText(film);
       cinemaName.setText(cinema);
       hallName.setText(hall);
       durationTextView.setText(durationOfTheFilm.toString()+" минут");
      countPlaces.setText(countOfPlaces.toString());
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final CharSequence[] items = {"К сожалению разработчик не успел сделать этот компонент"};

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Список сеансов в этом зале сегодня");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        // Do something with the selection
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
      timeStart.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              new TimePickerDialog(getContext(), t,
                      dateAndTime.get(Calendar.HOUR_OF_DAY),
                      dateAndTime.get(Calendar.MINUTE), true)
                      .show();
          }
      });
      timeEnd.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              new TimePickerDialog(getContext(), s,
                      dateAndTime.get(Calendar.HOUR_OF_DAY),
                      dateAndTime.get(Calendar.MINUTE), true)
                      .show();

          }
      });
      radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
          @Override
          public void onCheckedChanged(RadioGroup radioGroup, int i) {
              switch (i){
                  case R.id.allPlaces:
                      select="All";
                      break;
              }
              Toast.makeText(getContext(),select,Toast.LENGTH_SHORT).show();
          }
      });
      addPlaces.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              if(!selectedStartDate.equals("") && !selectedEndDate.equals("") && !priceText.getText().toString().equals("")){
                  AlertDialog alertDialog = new SpotsDialog.Builder()
                          .setContext(getContext())
                          .build();
                  alertDialog.show();
              Seance seance = new Seance(selectedStartDate, idHallSeance, idHallFilm, selectedEndDate,Double.parseDouble(priceText.getText().toString()));
              compositeDisposable.add(iMyApi.addSeance(seance)
                      .subscribeOn(Schedulers.io())
                      .observeOn(AndroidSchedulers.mainThread())
                      .subscribe(new Consumer<String>() {
                          @Override
                          public void accept(String s) throws Exception {
                              Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
                              if (!s.equals("Сеанс уже существует")) {
                                  alertDialog.dismiss();
                                  Toast.makeText(getContext(), "Сеанс успешно добавлен", Toast.LENGTH_SHORT).show();
                              }
                              else{
                                  alertDialog.dismiss();
                              }
                          }
                      }, new Consumer<Throwable>() {
                          @Override
                          public void accept(Throwable throwable) throws Exception {
                              alertDialog.dismiss();
                              Toast.makeText(getContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                          }
                      })
              );
          }
          else
              {
                  Toast.makeText(getContext(), "Заполните поля!", Toast.LENGTH_SHORT).show();
              }
              }
      });
       return view;
    }
    private void init(View view){
        try {
            filmName = view.findViewById(R.id.textView20);
            cinemaName = view.findViewById(R.id.textView8);
            hallName = view.findViewById(R.id.textView15);
            countPlaces = view.findViewById(R.id.textView17);
            film = getArguments().getString("nameFilm");
            cinema = getArguments().getString("nameCinema");
            hall = getArguments().getString("nameHall");
            countOfPlaces = getArguments().getInt("countPlaces");
            date = getArguments().getString("dateSeance");
            idHallFilm = getArguments().getString("idFilmSeance");
            idHallSeance = getArguments().getString("idHallSeance");
            durationOfTheFilm=getArguments().getInt("durationFilm");
            Toast.makeText(getContext(),durationOfTheFilm.toString(),Toast.LENGTH_SHORT).show();
            addPlaces = view.findViewById(R.id.addPlaces);
            durationTextView=view.findViewById(R.id.textView27);
            buttonAll = view.findViewById(R.id.allPlaces);
            fab= view.findViewById(R.id.fab);
            radioGroup = view.findViewById(R.id.radios);
            compositeDisposable = new CompositeDisposable();
            iMyApi = RetrofitClient.getInstance().create(IMyApi.class);
            priceText = view.findViewById(R.id.editText2);
            placesList = new ArrayList<Integer>();
            timeStart=view.findViewById(R.id.buttonSetStartTimeSeance);
            timeEnd=view.findViewById(R.id.buttonSetEndTimeSeance);
            timeStartTextView=view.findViewById(R.id.textView23);
            timeEndTextView=view.findViewById(R.id.textView24);
        }
        catch (Exception ex){
            Toast.makeText(getContext(), ex.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }
    // отображаем диалоговое окно для выбора времени
    TimePickerDialog.OnTimeSetListener t=new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            dateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            dateAndTime.set(Calendar.MINUTE, minute);
            {
                Calendar instance = Calendar.getInstance();
                Integer hourTest = hourOfDay;
                Integer minuteTest = minute;

                SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                selectedStartDate = date + " " + hourTest.toString() + ":" + minuteTest.toString();
                try {
                    time1 = format1.parse(selectedStartDate);
                     duration=0;
                     duration=durationOfTheFilm+20;
                    instance.setTime(time1);
                    instance.add(Calendar.MINUTE, duration);
                    newDate= instance.getTime();
                    Date newCheckDate=format1.parse(date+" "+"23:59");
                    Date morningDate=format1.parse(date+" "+"7:59");
                    String dates = format1.format(instance.getTime());
                    if(time1.after(morningDate)){
                        if(newDate.before(newCheckDate)){
                            selectedStartDate=format1.format(time1)+":00";
                            timeStartTextView.setText(selectedStartDate);
                            dialogWindow(dates);
                        }
                        else{
                            Toast.makeText(getContext(), "Сеанс должен заканчиваться раньше 23.59", Toast.LENGTH_SHORT).show();
                            selectedStartDate="";
                        }
                    }
                    else{
                        Toast.makeText(getContext(), "Сеанс должен начинаться не раньше 8.00", Toast.LENGTH_SHORT).show();
                        selectedStartDate="";
                    }



                } catch (Exception ex) {
                    Toast.makeText(getContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }
    };
    TimePickerDialog.OnTimeSetListener s=new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            dateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            dateAndTime.set(Calendar.MINUTE, minute);
            {
                Integer hourTest = hourOfDay;
                Integer minuteTest = minute;
                SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                selectedEndDate = date + " " + hourTest.toString() + ":" + minuteTest.toString();
                try {
                    time2 = format1.parse(selectedEndDate);
                    if (time2.before(time1)){
                        Toast.makeText(getContext(), "Нельзя выбрать время конца сеанса раньше его начала!", Toast.LENGTH_SHORT).show();
                        selectedEndDate="";
                    }
                    else
                    {
                        if(newDate.after(time2)){
                            Toast.makeText(getContext(), "Нельзя выбрать время конца сеанса меньше его продолжительности!", Toast.LENGTH_SHORT).show();
                            selectedEndDate="";
                        }
                        else{
                            Calendar instance = Calendar.getInstance();
                            instance.setTime(time2); //устанавливаем дату, с которой будет производить операции
                            selectedEndDate=format1.format(instance.getTime())+":00";
                            timeEndTextView.setText(selectedEndDate);
                        }

                    }
                } catch (Exception ex) {
                    Toast.makeText(getContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }
    };
    private void dialogWindow(String date){
        new TTFancyGifDialog.Builder(getActivity())
                .setTitle("Magnifisent")
                .setMessage("С учетом начала сеанса и продолжительности фильма, а также 20 минут на подготовку к слудющему сеансу, установить время конца сеанса "+date+" ?")
                .setPositiveBtnText("Ok")
                .setPositiveBtnBackground("#22b573")
                .setNegativeBtnText("Cancel")
                .setNegativeBtnBackground("#c1272d")
                .setGifResource(R.drawable.cinema2)      //pass your gif, png or jpg
                .isCancellable(true)
                .OnPositiveClicked(new TTFancyGifDialogListener() {
                    @Override
                    public void OnClick() {
                        selectedEndDate=date+":00";
                        timeEndTextView.setText(selectedEndDate);
                    }
                })
                .OnNegativeClicked(new TTFancyGifDialogListener() {
                    @Override
                    public void OnClick() {
                        selectedEndDate="";
                        timeEndTextView.setText("Время конца сеанса");
                    }
                })
                .build();
    }

}
