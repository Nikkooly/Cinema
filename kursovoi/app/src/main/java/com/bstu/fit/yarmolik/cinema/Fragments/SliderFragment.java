package com.bstu.fit.yarmolik.cinema.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bstu.fit.yarmolik.cinema.Adapters.SlidingImage_Adapter;
import com.bstu.fit.yarmolik.cinema.CheckInternetConnection;
import com.bstu.fit.yarmolik.cinema.Login;
import com.bstu.fit.yarmolik.cinema.R;
import com.bstu.fit.yarmolik.cinema.Remote.IMyApi;
import com.bstu.fit.yarmolik.cinema.Remote.RetrofitClient;
import com.bstu.fit.yarmolik.cinema.Responces.FilmResponse;
import com.daimajia.androidanimations.library.specials.out.TakingOffAnimator;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SliderFragment extends Fragment {
    private static ViewPager mPager;
    FilmResponse filmResponse;
    private static int currentPage = 0;
    Context context;
    private CirclePageIndicator circlePageIndicator;
    private IMyApi iMyApi;
    CheckInternetConnection checkInternetConnection;
    RelativeLayout relativeLayout;
    public Integer roleId;
    public boolean checkInternetState;
    private static int NUM_PAGES = 0;
    Map<String, String> states = new HashMap<String, String>();
    private ArrayList<String> IMAGES,DESCRIPTION,idFilm;
    //private static final Integer[] IMAGES= {R.raw.gigantic,R.raw.wonder_woman,R.raw.onward,R.raw.artemis_fowl,R.raw.sonic,R.raw.mulan};
    //private static final String[] DESCRIPTION={"Гигантик \n 2021","Чудо женщина \n 2020","Вперед \n 2020","Артемис Фаул \n 2020","Соник \n 2020","Мулан \n 2020"};
    private ArrayList<String> ImagesArray = new ArrayList<String>();
    private ArrayList<String> DescriptionArray = new ArrayList<String>();
    private ArrayList<String> IdArray = new ArrayList<String>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_slider, container, false);
        init(view);
        Bundle arguments = getActivity().getIntent().getExtras();
        relativeLayout=view.findViewById(R.id.sliderRelative);
        roleId= Login.userRoleId;
        checkInternetConnection=new CheckInternetConnection();
        if(checkInternetConnection.isOnline(getContext())) {
            checkInternetState=true;
            relativeLayout.setVisibility(View.VISIBLE);
            /*for (int i = 0; i < IMAGES.length; i++)
                ImagesArray.add(IMAGES[i]);
            for (int j = 0; j < DESCRIPTION.length; j++)
                DescriptionArray.add(DESCRIPTION[j]);*/
            loadFilms(circlePageIndicator);
        }
        else{
            checkInternetState=false;
            Toast.makeText(getContext(),"Нет интернета", Toast.LENGTH_SHORT).show();
        }
        return view;
    }
    private void init(View view){
        IMAGES=new ArrayList<>();
        DESCRIPTION=new ArrayList<>();
        mPager = view.findViewById(R.id.pager);
        iMyApi= RetrofitClient.getInstance().create(IMyApi.class);
        circlePageIndicator=view.findViewById(R.id.indicator);
        idFilm=new ArrayList<>();
    }
    private void loadFilms(CirclePageIndicator indicator){
        Call<List<FilmResponse>> call=iMyApi.getFilms();
        call.enqueue(new Callback<List<FilmResponse>>() {
            @Override
            public void onResponse(Call<List<FilmResponse>> call, Response<List<FilmResponse>> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getContext(), response.code(), Toast.LENGTH_LONG).show();
                }
                else {
                    for (FilmResponse post : response.body()) {
                        DESCRIPTION.add(post.getName());
                        IMAGES.add(post.getPoster());
                        idFilm.add(post.getId());
                    }
                    Integer count=5;
                    if(IMAGES.size()>count){
                        int[] result=sampleRandomNumbersWithoutRepetition(0,IMAGES.size(),5);
                        for(int i=0;i<5;i++){
                            ImagesArray.add(IMAGES.get(result[i]));
                            DescriptionArray.add(DESCRIPTION.get(result[i]));
                            IdArray.add(idFilm.get(result[i]));
                        }
                    }
                    else {
                        for (int i = 0; i < IMAGES.size(); i++) {
                            int[] result=sampleRandomNumbersWithoutRepetition(0,IMAGES.size(),IMAGES.size());
                            ImagesArray.add(IMAGES.get(result[i]));
                            DescriptionArray.add(DESCRIPTION.get(result[i]));
                            IdArray.add(idFilm.get(result[i]));
                        }
                    }

                    mPager.setOffscreenPageLimit(2);
                    mPager.setAdapter(new SlidingImage_Adapter(getContext(), ImagesArray, DescriptionArray,IdArray));

                    indicator.setViewPager(mPager);
                    final float density = getResources().getDisplayMetrics().density;
                    indicator.setRadius(5 * density);
                    NUM_PAGES = IMAGES.size();
                    // Pager listener over indicator
                    indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

                        @Override
                        public void onPageSelected(int position) {
                            currentPage = position;
                        }

                        @Override
                        public void onPageScrolled(int pos, float arg1, int arg2) {

                        }

                        @Override
                        public void onPageScrollStateChanged(int pos) {

                        }
                    });
                }
            }
            @Override
            public void onFailure(Call<List<FilmResponse>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
    public static int[] sampleRandomNumbersWithoutRepetition(int start, int end, int count) {
        Random rng = new Random();
        int[] result = new int[count];
        int cur = 0;
        int remaining = end - start;
        for (int i = start; i < end && count > 0; i++) {
            double probability = rng.nextDouble();
            if (probability < ((double) count) / (double) remaining) {
                count--;
                result[cur++] = i;
            }
            remaining--;
        }
        return result;
    }
}
