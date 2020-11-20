package com.bstu.fit.yarmolik.cinema.Remote;

import io.reactivex.plugins.RxJavaPlugins;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;



public class RetrofitClient {
    private static Retrofit instance;

    public static Retrofit getInstance() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY); //можно также заменить на Level.BODY чтоб увидеть полное тело запроса и ответа
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();
        if(instance==null)
            instance=new Retrofit.Builder()
                    //.baseUrl("http://10.0.2.2:5000/")
                    .baseUrl("http://476072f414a1.ngrok.io/")
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(client)
                    .build();
        return instance;
    }
}
