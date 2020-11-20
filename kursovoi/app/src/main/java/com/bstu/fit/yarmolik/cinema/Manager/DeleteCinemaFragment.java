package com.bstu.fit.yarmolik.cinema.Manager;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bstu.fit.yarmolik.cinema.R;
import com.bstu.fit.yarmolik.cinema.Remote.IMyApi;
import com.bstu.fit.yarmolik.cinema.Remote.RetrofitClient;
import com.bstu.fit.yarmolik.cinema.Responces.CinemaResponce;
import com.bstu.fit.yarmolik.cinema.Responces.FilmResponse;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeleteCinemaFragment extends Fragment {
    private IMyApi iMyApi;
    private MaterialSpinner spinner;
    private List<CinemaResponce> cinema;
    private ArrayList<String> list;
    private ArrayList<String> idList;
    private String choose="";
    private Button deleteButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_delete_cinema, container, false);
        init(view);
        loadCinema();
        spinner.setItems(list);
        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                choose=item;
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!choose.equals("")) {
                    int position = list.indexOf(choose);
                    AlertDialog alertDialog = new SpotsDialog.Builder()
                            .setContext(getContext())
                            .build();
                    alertDialog.show();
                    Call<Void> call = iMyApi.deleteCinema(idList.get(position));
                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            Toast.makeText(getContext(), response.message(), Toast.LENGTH_LONG).show();
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
                    Toast.makeText(getContext(), "Выберите фильм для удаления", Toast.LENGTH_LONG).show();
                }
            }
        });
        return view;
    }
    private void init(View view){
        iMyApi= RetrofitClient.getInstance().create(IMyApi.class);
        spinner= view.findViewById(R.id.spinnerDeleteCinema);
        deleteButton=view.findViewById(R.id.deleteCinema);
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
