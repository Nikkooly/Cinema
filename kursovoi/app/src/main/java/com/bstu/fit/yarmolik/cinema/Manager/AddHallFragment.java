package com.bstu.fit.yarmolik.cinema.Manager;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bstu.fit.yarmolik.cinema.Model.CinemaInfo;
import com.bstu.fit.yarmolik.cinema.Model.HallInfo;
import com.bstu.fit.yarmolik.cinema.R;
import com.bstu.fit.yarmolik.cinema.Remote.IMyApi;
import com.bstu.fit.yarmolik.cinema.Remote.RetrofitClient;
import com.bstu.fit.yarmolik.cinema.Responces.CinemaResponce;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AddHallFragment extends Fragment {
    private List<CinemaResponce> cinema;
    private ArrayList<String> list;
    private ArrayList<String> idList;
    private String choose="",chooseCount="";
    private EditText name, countOfPlaces;
    private Button addHallButton;
    private CompositeDisposable compositeDisposable;
    private MaterialSpinner spinner,spinnerCount;
    private IMyApi iMyApi;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_add_hall, container, false);
        init(view);
        loadCinema();
        spinnerCount.setItems("70", "90", "100");
        spinner.setItems(list);
        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                choose=item;
            }
        });
        spinnerCount.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                chooseCount=item;
            }
        });
        addHallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = list.indexOf(choose);
                String nameOfHall=name.getText().toString();
                if(!choose.equals("") && !nameOfHall.equals("") && !chooseCount.equals("")){
                    String cinemaId=idList.get(position);
                    AlertDialog alertDialog = new SpotsDialog.Builder()
                            .setContext(getContext())
                            .build();
                    alertDialog.show();
                    HallInfo hall=new HallInfo(cinemaId,nameOfHall, Integer.parseInt(chooseCount));
                    compositeDisposable.add(iMyApi.addHall(hall)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Consumer<String>() {
                                @Override
                                public void accept(String s) throws Exception {
                                    Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
                                    alertDialog.dismiss();
                                }
                            }, new Consumer<Throwable>() {
                                @Override
                                public void accept(Throwable throwable) throws Exception {
                                    Toast.makeText(getContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }));
                }
                else{
                    Toast.makeText(getContext(), "Заполните поля", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;

    }
    private void init(View view){
        iMyApi= RetrofitClient.getInstance().create(IMyApi.class);
        spinner=view.findViewById(R.id.spinnerSelectCinemaOfHall);
        spinnerCount=view.findViewById(R.id.addCountOfPlacesOfHall);
        name=view.findViewById(R.id.addNameOfHall);
        //countOfPlaces=view.findViewById(R.id.addCountOfPlacesOfHall);
        addHallButton=view.findViewById(R.id.addHall);
        compositeDisposable=new CompositeDisposable();
        list= new ArrayList<String>();
        idList=new ArrayList<String>();
    }
    private void loadCinema(){
        Call<List<CinemaResponce>> call=iMyApi.getCinema();
        call.enqueue(new Callback<List<CinemaResponce>>() {
            @Override
            public void onResponse(Call<List<CinemaResponce>> call, Response<List<CinemaResponce>> response) {
                cinema=response.body();
                for(CinemaResponce post :  cinema){
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
}
