package com.bstu.fit.yarmolik.cinema.Manager;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.bstu.fit.yarmolik.cinema.MyViewModel;
import com.bstu.fit.yarmolik.cinema.R;
import com.bstu.fit.yarmolik.cinema.Remote.IMyApi;
import com.bstu.fit.yarmolik.cinema.Remote.RetrofitClient;
import com.bstu.fit.yarmolik.cinema.Responces.CinemaResponce;
import com.bstu.fit.yarmolik.cinema.Responces.FilmResponse;
import com.bstu.fit.yarmolik.cinema.Responces.HallResponse;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.jaredrummler.materialspinner.MaterialSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DeleteHallFragment extends Fragment {
private MaterialSpinner spinnerCinema,spinnerHall;
private Button deleteButton;
    private List<CinemaResponce> cinema;
    private List<HallResponse> hall;
    private ArrayList<String> list;
    List<FilmResponse> posts;
    private ArrayList<String> idList;
    private ArrayList<String> listHall;
    private ArrayList<String> idListHall;
    private String chooseCinema="";
    private boolean check=false;
    private String chooseHall="";
    private IMyApi iMyApi;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=  inflater.inflate(R.layout.fragment_delete_hall, container, false);
        iMyApi= RetrofitClient.getInstance().create(IMyApi.class);
        init(view);
        loadCinema();
        spinnerCinema.setItems(list);
        spinnerCinema.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                loadHallInfo(idList.get(list.indexOf(item)));
                spinnerHall.setItems(listHall);
                chooseCinema=item;
            }
        });
        spinnerHall.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                chooseHall=item;
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!chooseCinema.equals("") && !chooseHall.equals("")) {
                    AlertDialog alertDialog = new SpotsDialog.Builder()
                            .setContext(getContext())
                            .build();
                    alertDialog.show();
                    Call<Void> call = iMyApi.deleteHall(idListHall.get(listHall.indexOf(chooseHall)));
                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            Toast.makeText(getContext(), "Успешно удалено", Toast.LENGTH_LONG).show();
                            alertDialog.dismiss();
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                            alertDialog.dismiss();
                        }
                    });
                }
                else{
                    Toast.makeText(getContext(), "Заполните поля", Toast.LENGTH_SHORT).show();
                }
                }
            });
        return view;
    }

    private void init(View view){
        spinnerCinema=view.findViewById(R.id.spinnerSelectCinemaToDeleteHall);
        spinnerHall=view.findViewById(R.id.selectHallToDelete);
        deleteButton=view.findViewById(R.id.deleteHall);
        list= new ArrayList<String>();
        idList=new ArrayList<String>();
        listHall= new ArrayList<String>();
        idListHall=new ArrayList<String>();

    }
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
    public void loadHallInfo(String id){
        Call<List<HallResponse>> call =iMyApi.getHallInfo(id);
        call.enqueue(new Callback<List<HallResponse>>() {
            @Override
            public void onResponse(Call<List<HallResponse>> call, Response<List<HallResponse>> response) {
                hall=response.body();
                for(HallResponse h : hall){
                    listHall.add(h.getName());
                    idListHall.add(h.getId());
                }
            }

            @Override
            public void onFailure(Call<List<HallResponse>> call, Throwable t) {

            }
        });

    }
}
