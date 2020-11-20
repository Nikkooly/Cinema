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
import com.bstu.fit.yarmolik.cinema.Model.FilmInfo;
import com.bstu.fit.yarmolik.cinema.R;
import com.bstu.fit.yarmolik.cinema.Remote.IMyApi;
import com.bstu.fit.yarmolik.cinema.Remote.RetrofitClient;
import com.bstu.fit.yarmolik.cinema.Responces.CinemaResponce;
import com.bstu.fit.yarmolik.cinema.Responces.FilmResponse;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;
import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UpdateCinemaFragment extends Fragment {
    private ArrayList<String> nameList;
    private ArrayList<String> idList;
    private String choose="";
    private IMyApi iMyApi;
    private EditText address;
    private Button updateButton;
    private MaterialSpinner spinner;
    private List<CinemaResponce> cinema;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_update_cinema, container, false);
        init(view);
        loadCinema();
        spinner.setItems(nameList);
        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                choose = item;
                int chooseIndex = nameList.indexOf(item);
                loadCinemaInfo(idList.get(chooseIndex));
            }
        });
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!address.getText().toString().equals("")){
                    int chooseIndex = nameList.indexOf(choose);
                    updateCinemaInfo(idList.get(chooseIndex),choose);
                    Toast.makeText(getContext(), "Успешно изменено", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getContext(), "Заполните поля", Toast.LENGTH_LONG).show();
                }
            }
        });
        return view;
    }
    private void init(View view){
        iMyApi= RetrofitClient.getInstance().create(IMyApi.class);
        spinner= view.findViewById(R.id.spinnerUpdateCinema);
        updateButton=view.findViewById(R.id.updateCinema);
        nameList= new ArrayList<String>();
        idList=new ArrayList<String>();
        address=view.findViewById(R.id.updateAdressOfCinema);
    }
    private void loadCinema(){
        Call<List<CinemaResponce>> call=iMyApi.getCinema();
        call.enqueue(new Callback<List<CinemaResponce>>() {
            @Override
            public void onResponse(Call<List<CinemaResponce>> call, Response<List<CinemaResponce>> response) {
                cinema=response.body();
                for(CinemaResponce post :  cinema){
                    nameList.add(post.getName());
                    idList.add(post.getId());
                }
            }
            @Override
            public void onFailure(Call<List<CinemaResponce>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
    private void loadCinemaInfo(String id){
        Call<CinemaResponce> call=iMyApi.getCinemaInfo(id);
        call.enqueue(new Callback<CinemaResponce>() {
            @Override
            public void onResponse(Call<CinemaResponce> call, Response<CinemaResponce> response) {
                address.setText(response.body().getAdress());
            }

            @Override
            public void onFailure(Call<CinemaResponce> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
    private void updateCinemaInfo(String id,String name){
        AlertDialog alertDialog = new SpotsDialog.Builder()
                .setContext(getContext())
                .build();
        alertDialog.show();
        CinemaInfo info= new CinemaInfo(name,address.getText().toString());
        Call<CinemaInfo> call= iMyApi.updateCinema(id,info);
        call.enqueue(new Callback<CinemaInfo>() {
            @Override
            public void onResponse(Call<CinemaInfo> call, Response<CinemaInfo> response) {
                alertDialog.dismiss();
            }

            @Override
            public void onFailure(Call<CinemaInfo> call, Throwable t) {
                alertDialog.dismiss();
            }
        });
    }
}
