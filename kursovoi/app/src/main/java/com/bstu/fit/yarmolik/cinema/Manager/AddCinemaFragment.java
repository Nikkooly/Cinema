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
import com.bstu.fit.yarmolik.cinema.R;
import com.bstu.fit.yarmolik.cinema.Remote.IMyApi;
import com.bstu.fit.yarmolik.cinema.Remote.RetrofitClient;

import dmax.dialog.SpotsDialog;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class AddCinemaFragment extends Fragment {
    private EditText name,adress;
    IMyApi iMyApi;
    private Button addCinemaButton;
    CompositeDisposable compositeDisposable;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_add_cinema, container, false);
        init(view);
        addCinemaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!name.getText().toString().equals("") && !adress.getText().toString().equals("")){
                    AlertDialog alertDialog = new SpotsDialog.Builder()
                            .setContext(getContext())
                            .build();
                    alertDialog.show();
                    CinemaInfo cinema= new CinemaInfo(name.getText().toString(),adress.getText().toString());
                    compositeDisposable.add(iMyApi.addCinema(cinema)
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
        compositeDisposable=new CompositeDisposable();
        name=view.findViewById(R.id.addNameOfCinema);
        adress=view.findViewById(R.id.addAdressOfCinema);
        addCinemaButton=view.findViewById(R.id.addCinema);
    }
}
