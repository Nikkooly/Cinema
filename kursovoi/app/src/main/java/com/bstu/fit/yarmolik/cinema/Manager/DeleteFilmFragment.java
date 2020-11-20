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

import com.bstu.fit.yarmolik.cinema.Login;
import com.bstu.fit.yarmolik.cinema.R;
import com.bstu.fit.yarmolik.cinema.Remote.IMyApi;
import com.bstu.fit.yarmolik.cinema.Remote.RetrofitClient;
import com.bstu.fit.yarmolik.cinema.Responces.FilmResponse;
import com.google.android.material.snackbar.Snackbar;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DeleteFilmFragment extends Fragment {
IMyApi iMyApi;
    MaterialSpinner spinner;
    List<FilmResponse> posts;
    TextView textView;
    ArrayList<String> list;
    ArrayList<String> idList;
    String choose="";
    FilmResponse filmResponse;
    Button deleteButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_delete_film, container, false);
        iMyApi= RetrofitClient.getInstance().create(IMyApi.class);
        spinner= view.findViewById(R.id.spinnerDelete);
        deleteButton=view.findViewById(R.id.deleteFilm);
        list= new ArrayList<String>();
        idList=new ArrayList<String>();
        loadFilms();
        spinner.setItems(list);
        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
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
                    Call<Void> call = iMyApi.deletePost(idList.get(position));
                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            Toast.makeText(getContext(), response.message(), Toast.LENGTH_LONG).show();
                            loadFilms();
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
                    list.add(post.getName());
                    idList.add(post.getId());
                }
            }
            @Override
            public void onFailure(Call<List<FilmResponse>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
