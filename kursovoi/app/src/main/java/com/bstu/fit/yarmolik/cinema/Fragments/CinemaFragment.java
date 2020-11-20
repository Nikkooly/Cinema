package com.bstu.fit.yarmolik.cinema.Fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bstu.fit.yarmolik.cinema.Adapters.MyAdapter;
import com.bstu.fit.yarmolik.cinema.CheckInternetConnection;
import com.bstu.fit.yarmolik.cinema.Login;
import com.bstu.fit.yarmolik.cinema.ModelAdapter.CinemaModel;
import com.bstu.fit.yarmolik.cinema.R;
import com.bstu.fit.yarmolik.cinema.Remote.IMyApi;
import com.bstu.fit.yarmolik.cinema.Remote.RetrofitClient;
import com.bstu.fit.yarmolik.cinema.Responces.CinemaResponce;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CinemaFragment extends Fragment {
    private ArrayList<CinemaModel> listItems;
    private RecyclerView MyRecyclerView;
    private RecyclerView recyclerView;
    private MyAdapter carAdapter;
    private IMyApi iMyApi;
    private  Integer roleId;
    CheckInternetConnection checkInternetConnection;
    public boolean checkInternetState;
    private List<CinemaResponce> cinema;
    private ArrayList<String> nameList;
    private ArrayList<String> idList;
    private ArrayList<String> adressList;
    //String description[] = {"Минск \nпроспект Александра Лукашенко 15","Минск \nул. Николая Лукашенко 31","Минск \nул. Виктора Лукашенко 21/1","Минск \nул. Дмитрия Лукашенко 5"};
    int  images[] = {R.drawable.cinema7};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_cinema, container, false);
        recyclerView=(RecyclerView) view.findViewById(R.id.recyclerView);
        init(view);
        Bundle arguments = getActivity().getIntent().getExtras();
        roleId= Login.userRoleId;
        checkInternetConnection=new CheckInternetConnection();
        if(checkInternetConnection.isOnline(getContext())) {
            checkInternetState=true;
            recyclerView.setVisibility(View.VISIBLE);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            loadCinema();
        }
        else{
            checkInternetState=false;
            Toast.makeText(getContext(), "Нет подключения", Toast.LENGTH_LONG).show();
        }

        return view;
    }
    private void init(View view){
        iMyApi= RetrofitClient.getInstance().create(IMyApi.class);
        nameList=new ArrayList<>();
        idList= new ArrayList<>();
        adressList= new ArrayList<>();
        listItems=new ArrayList<CinemaModel>();
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
                    adressList.add(post.getAdress());
                }
                for(int i=0;i<nameList.size();i++){
                    try{
                        listItems.add(new CinemaModel(images[0], nameList.get(i),adressList.get(i)));
                    }
                    catch (Exception ex){
                        Toast.makeText(getContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
                carAdapter = new MyAdapter(listItems);
                recyclerView.setAdapter(carAdapter);
            }
            @Override
            public void onFailure(Call<List<CinemaResponce>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
