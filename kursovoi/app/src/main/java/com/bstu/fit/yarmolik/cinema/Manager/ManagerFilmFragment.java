package com.bstu.fit.yarmolik.cinema.Manager;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bstu.fit.yarmolik.cinema.Model.FilmInfo;
import com.bstu.fit.yarmolik.cinema.R;
import com.bstu.fit.yarmolik.cinema.Remote.IMyApi;
import com.bstu.fit.yarmolik.cinema.Remote.RetrofitClient;

import dmax.dialog.SpotsDialog;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;


public class ManagerFilmFragment extends Fragment {
    EditText nameOfTheFilm,yearOfTheFilm,durationOfTheFilm,countryOfTheFilm,genreOfTheFilm,descriptionOfTheFilm,posterOfTheFilm;
    Button addFilm;
    IMyApi iMyApi;
    CompositeDisposable compositeDisposable;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      View view= inflater.inflate(R.layout.fragment_manager_film, container, false);
     init(view);
        iMyApi= RetrofitClient.getInstance().create(IMyApi.class);
      addFilm.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              try {
                  String name=nameOfTheFilm.getText().toString();
                  String year=yearOfTheFilm.getText().toString();
                  String country=countryOfTheFilm.getText().toString();
                  String genre=genreOfTheFilm.getText().toString();
                  String duration=durationOfTheFilm.getText().toString();
                  String description=descriptionOfTheFilm.getText().toString();
                  String poster=posterOfTheFilm.getText().toString();
                  if(!name.equals("") && !year.equals("") && !country.equals("") && !genre.equals("") && !description.equals("") && !poster.equals("") && !duration.equals("")) {
                      boolean checkPoster=poster.matches("^https?://(?:[a-z0-9\\-]+\\.)+[a-z]{2,6}(?:/[^/#?]+)+\\.(?:jpg|gif|png)$");
                      if(URLUtil.isValidUrl(poster)) {
                          AlertDialog alertDialog = new SpotsDialog.Builder()
                                  .setContext(getContext())
                                  .build();
                          alertDialog.show();
                          FilmInfo film = new FilmInfo(nameOfTheFilm.getText().toString(),
                                  Integer.parseInt(yearOfTheFilm.getText().toString()),
                                  countryOfTheFilm.getText().toString(),
                                  Integer.parseInt(durationOfTheFilm.getText().toString()),
                                  genreOfTheFilm.getText().toString(),
                                  descriptionOfTheFilm.getText().toString(),
                                  posterOfTheFilm.getText().toString()
                          );
                          compositeDisposable.add(iMyApi.filmInfo(film)
                                  .subscribeOn(Schedulers.io())
                                  .observeOn(AndroidSchedulers.mainThread())
                                  .subscribe(s -> {
                                      alertDialog.dismiss();
                                      Toast.makeText(getContext(), s, Toast.LENGTH_LONG).show();
                                  }, throwable -> {
                                      alertDialog.dismiss();
                                      Toast.makeText(getContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                  })
                          );
                      }
                      else{
                          Toast.makeText(getContext(), "Некорректная ссылка!", Toast.LENGTH_SHORT).show();
                      }
                  }
                  else{
                      Toast.makeText(getContext(), "Заполните поля!", Toast.LENGTH_SHORT).show();
                  }
              }
              catch (Exception ex){
                  Toast.makeText(getContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
              }
          }
      });
      return view;
    }
    public void init(View view){
        nameOfTheFilm=view.findViewById(R.id.addNameOfFilm);
        yearOfTheFilm=view.findViewById(R.id.addYearOfFilm);
        durationOfTheFilm=view.findViewById(R.id.addDurationOfFilm);
        countryOfTheFilm=view.findViewById(R.id.addCountryOfFilm);
        genreOfTheFilm=view.findViewById(R.id.addGenreOfFilm);
        posterOfTheFilm=view.findViewById(R.id.addPosterOfFilm);
        descriptionOfTheFilm=view.findViewById(R.id.addDescriptionOfFilm);
        addFilm=view.findViewById(R.id.addFilm);
        posterOfTheFilm=view.findViewById(R.id.addPosterOfFilm);
        compositeDisposable=new CompositeDisposable();
    }
    }
