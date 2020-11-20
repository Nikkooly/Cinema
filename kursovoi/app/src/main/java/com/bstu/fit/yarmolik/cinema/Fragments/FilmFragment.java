package com.bstu.fit.yarmolik.cinema.Fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bstu.fit.yarmolik.cinema.Adapters.FilmAdapter;
import com.bstu.fit.yarmolik.cinema.CheckInternetConnection;
import com.bstu.fit.yarmolik.cinema.Login;
import com.bstu.fit.yarmolik.cinema.ModelAdapter.FilmModel;
import com.bstu.fit.yarmolik.cinema.R;
import com.bstu.fit.yarmolik.cinema.Remote.IMyApi;
import com.bstu.fit.yarmolik.cinema.Remote.RetrofitClient;
import com.bstu.fit.yarmolik.cinema.Responces.FilmResponse;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FilmFragment extends Fragment {
private IMyApi iMyApi;
ArrayList<FilmModel> listItems=new ArrayList<>();
RecyclerView recyclerView;
FilmAdapter filmAdapter;
ArrayList<FilmModel> film;
private List<FilmResponse> posts;
private ArrayList<String> nameList;
private ArrayList<String> idList;
private String[] nameArray;
private  Integer roleId;
CheckInternetConnection checkInternetConnection;
public boolean checkInternetState;
private ArrayList<String> posterList;
private ArrayList<String> descriptionList;
private ArrayList<Integer> durationList;
private ArrayList<Integer> yearList;
private ArrayList<String> genreList;
private ArrayList<String> countryList;
private ArrayList<Bitmap> imageList;
private ArrayList<Float> ratingList;
private Float rating=2.45F;
private Bitmap testImage;
ImageView imageView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_film, container, false);
        recyclerView=view.findViewById(R.id.recyclerFilmView);
        init(view);
        roleId= Login.userRoleId;
        checkInternetConnection=new CheckInternetConnection();
        if(checkInternetConnection.isOnline(getContext())) {
            checkInternetState=true;
            recyclerView.setVisibility(View.VISIBLE);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            loadFilmsInfo();
        }
        else{
            checkInternetState=false;
            Toast.makeText(getContext(), "Нет подключения", Toast.LENGTH_LONG).show();
        }
        return view;
    }
    private void init(View view){
        iMyApi= RetrofitClient.getInstance().create(IMyApi.class);
        nameList= new ArrayList<String>();
        idList=new ArrayList<String>();
        posterList=new ArrayList<String>();
        descriptionList=new ArrayList<String>();
        durationList=new ArrayList<Integer>();
        yearList=new ArrayList<Integer>();
        genreList=new ArrayList<String>();
        countryList=new ArrayList<String>();
        imageList=new ArrayList<Bitmap>();
        film=new ArrayList<>();
        ratingList=new ArrayList<>();
    }
    public void loadFilmsInfo(){

        Call<List<FilmResponse>> call=iMyApi.getFilms();
        call.enqueue(new Callback<List<FilmResponse>>() {
            @Override
            public void onResponse(Call<List<FilmResponse>> call, Response<List<FilmResponse>> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getContext(), response.code(), Toast.LENGTH_LONG).show();
                }
                posts=response.body();
                for(FilmResponse post : posts){
                    nameList.add(post.getName());
                    idList.add(post.getId());
                    countryList.add(post.getCountry());
                    genreList.add((post.getGenre()));
                    descriptionList.add(post.getDescription());
                    durationList.add(post.getDuration());
                    posterList.add(post.getPoster());
                    yearList.add(post.getYear());
                    ratingList.add(post.getRating());
                }
                for(int i=0;i<posterList.size();i++){
                    try {
                        film.add(new FilmModel(nameList.get(i), yearList.get(i).toString(), ratingList.get(i),posterList.get(i),idList.get(i)));//getBitmapFromURL(posterList.get(i))));
                    }
                    catch (Exception ex){
                        Toast.makeText(getContext(),ex.getMessage()+" Exception",Toast.LENGTH_SHORT).show();
                    }
                }
                filmAdapter=new FilmAdapter(film);
                recyclerView.setAdapter(filmAdapter);
            }
            @Override
            public void onFailure(Call<List<FilmResponse>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
