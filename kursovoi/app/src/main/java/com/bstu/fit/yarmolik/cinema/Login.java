package com.bstu.fit.yarmolik.cinema;

import android.animation.Animator;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.bestsoft32.tt_fancy_gif_dialog_lib.TTFancyGifDialog;
import com.bestsoft32.tt_fancy_gif_dialog_lib.TTFancyGifDialogListener;
import com.bstu.fit.yarmolik.cinema.Fragments.FilmFragment;
import com.bstu.fit.yarmolik.cinema.Fragments.MainActivity;
import com.bstu.fit.yarmolik.cinema.LocalDataBase.DbHelper;
import com.bstu.fit.yarmolik.cinema.LocalDataBase.WorksWithDb;
import com.bstu.fit.yarmolik.cinema.Manager.ManagerActivity;
import com.bstu.fit.yarmolik.cinema.Model.LoginUser;
import com.bstu.fit.yarmolik.cinema.Remote.IMyApi;
import com.bstu.fit.yarmolik.cinema.Remote.RetrofitClient;
import com.bstu.fit.yarmolik.cinema.Responces.FilmResponse;
import com.bstu.fit.yarmolik.cinema.Responces.GuestResponse;
import com.bstu.fit.yarmolik.cinema.Responces.UserResponce;

import java.util.List;

import dmax.dialog.SpotsDialog;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class Login extends AppCompatActivity {

    private ImageView bookIconImageView;
    private TextView bookITextView;
    private EditText login,password;
    Button btn_login;
    List<FilmResponse> posts;
    String response="";
    public boolean stateInternet;
    private ProgressBar loadingProgressBar;
    private RelativeLayout rootView, afterAnimationView;
    IMyApi iMyApi;
    private SharedPreferences sharedPreferences;
    public static Integer userRoleId,guestRoleId;
    private CheckBox checkBox;
    private DbHelper dbHelper;
    private String query;
    private String checkBoxChoose="";
    private Cursor c;
    public static String userId="",guestId,userEmail="",guestEmail,userLogin="",guestLogin;
    Intent intent;
    private WorksWithDb worksWithDb=new WorksWithDb();
    CompositeDisposable compositeDisposable;
    public static final String APP_PREFERENCES = "user";
    private  String APP_PREFERENCES_LOGIN="login";
    private  String APP_PREFERENCES_PASSWORD="password";
    private SQLiteDatabase database;
    CheckInternetConnection checkInternetConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        compositeDisposable=new CompositeDisposable();
        iMyApi= RetrofitClient.getInstance().create(IMyApi.class);
        // requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        initViews();
        try {
            dbHelper = new DbHelper(this, "project.db", null, 1);
            worksWithDb.userRegister(dbHelper);
            worksWithDb.ticketInfo(dbHelper);
            worksWithDb.seanceData(dbHelper);
            database=dbHelper.getWritableDatabase();
        }
        catch (Exception ex){
            Toast.makeText(Login.this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }

        new CountDownTimer(3000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                bookITextView.setVisibility(VISIBLE);
                rootView.setBackgroundColor(ContextCompat.getColor(Login.this, R.color.colorBackground));
                bookIconImageView.setImageResource(R.drawable.app_icon);
            }

            @Override
            public void onFinish() {
                bookITextView.setVisibility(GONE);
                rootView.setBackgroundColor(ContextCompat.getColor(Login.this, R.color.btnColor));
                startAnimation();
                if(checkInternetConnection.isOnline(Login.this)){
                    stateInternet=true;
                    if(sharedPreferences.contains(APP_PREFERENCES_LOGIN)) {
                        loadWithSharedPreferences(sharedPreferences.getString(APP_PREFERENCES_LOGIN,""),sharedPreferences.getString(APP_PREFERENCES_PASSWORD,""));
                    }
                }
                else{
                    stateInternet=false;
                    AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                    builder.setTitle("Важное сообщение!")
                            .setMessage("Отсутствует подключение к интернету, многие функции будут не доступны. Включите интернет и перезайдите в приложение!")
                            .setIcon(R.drawable.app_icon)
                            .setPositiveButton("ОК", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // Закрываем окно
                                    dialog.cancel();
                                    if(sharedPreferences.contains(APP_PREFERENCES_LOGIN)) {
                                        new TTFancyGifDialog.Builder(Login.this)
                                                .setTitle("Magnifisent")
                                                .setMessage("Желаете войти под ранее сохраненным логином и паролем?")
                                                .setPositiveBtnText("Ok")
                                                .setPositiveBtnBackground("#22b573")
                                                .setNegativeBtnText("Cancel")
                                                .setNegativeBtnBackground("#c1272d")
                                                .setGifResource(R.drawable.cinema2)      //pass your gif, png or jpg
                                                .isCancellable(true)
                                                .OnPositiveClicked(new TTFancyGifDialogListener() {
                                                    @Override
                                                    public void OnClick() {
                                                        loadLocalWithSharedPreferences(sharedPreferences.getString(APP_PREFERENCES_LOGIN, ""),sharedPreferences.getString(APP_PREFERENCES_PASSWORD, ""));
                                                    }
                                                })
                                                .OnNegativeClicked(new TTFancyGifDialogListener() {
                                                    @Override
                                                    public void OnClick() {
                                                        //Toast.makeText(MainActivity.this,"Cancel",Toast.LENGTH_SHORT).show();
                                                    }
                                                })
                                                .build();
                                    }
                                }
                            });
                    builder.create();
                    builder.show();
                }
            }
        }.start();
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String loginValue = login.getText().toString();
                String passwordValue = password.getText().toString();
                boolean checkLogin = loginValue.matches("^[a-zA-Z][a-zA-Z0-9-_\\.]{1,20}$");
                boolean checkPassword = passwordValue.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).{8,}$");
                if (checkPassword == true && checkLogin == true) {
                    if(stateInternet) {
                        AlertDialog alertDialog = new SpotsDialog.Builder()
                                .setContext(Login.this)
                                .build();
                        alertDialog.show();
                        LoginUser user = new LoginUser(login.getText().toString(), Registration.md5(password.getText().toString()));
                        Call<List<UserResponce>> call = iMyApi.checkLogin(user);
                        call.enqueue(new Callback<List<UserResponce>>() {
                            @Override
                            public void onResponse(Call<List<UserResponce>> call, Response<List<UserResponce>> response) {
                                    for (UserResponce userResponce : response.body()) {
                                        //Toast.makeText(Login.this, response.body().size(), Toast.LENGTH_LONG).show();
                                        //Toast.makeText(Login.this, userResponce.toString(), Toast.LENGTH_LONG).show();
                                        userId = userResponce.getId();
                                        userRoleId = userResponce.getRoleId();
                                        userEmail = userResponce.getEmail();
                                        userLogin = userResponce.getLogin();
                                        //Toast.makeText(Login.this, userRoleId.toString(), Toast.LENGTH_LONG).show();
                                        if (userRoleId == 1) {
                                            alertDialog.dismiss();
                                            //Toast.makeText(Login.this, "Уже заходим...", Toast.LENGTH_LONG).show();
                                            intent = new Intent(Login.this, MainActivity.class);
                                            intent.putExtra("stateInternetConnection", stateInternet);
                                            chekLocalUser();
                                            if(checkBox.isChecked())
                                                checkBoxChoose="OK";
                                            else
                                                checkBoxChoose="No";
                                            if(checkBoxChoose.equals("OK")){
                                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                                editor.putString(APP_PREFERENCES_LOGIN, login.getText().toString());
                                                editor.putString(APP_PREFERENCES_PASSWORD,Registration.md5(password.getText().toString()));
                                                editor.apply();
                                            }
                                            startActivity(intent);
                                            clear();
                                        } else if (userRoleId == 2) {
                                            alertDialog.dismiss();
                                            intent = new Intent(Login.this, ManagerActivity.class);
                                            startActivity(intent);
                                            clear();
                                        }
                                        else if (userRoleId == 4) {
                                            alertDialog.dismiss();
                                            Toast.makeText(Login.this, "Неверный логин или пароль. Проверьте введенные данные!", Toast.LENGTH_SHORT).show();
                                            userRoleId=0;
                                        }
                                    }

                            }

                            @Override
                            public void onFailure(Call<List<UserResponce>> call, Throwable t) {
                                alertDialog.dismiss();
                                Toast.makeText(Login.this, "Некорректные данные", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                    else
                        {
                            try {
                                query = "select * from user_data where login=" + "'" + login.getText().toString() + "'" + " and password=" + "'" + Registration.md5(password.getText().toString()) + "'";
                                c = database.rawQuery(query, null);
                                c.moveToFirst();
                                while (!c.isAfterLast()) {
                                    userId = c.getString(0).toString();
                                    userLogin = c.getString(1).toString();
                                    userEmail = c.getString(2).toString();
                                    userRoleId=3;
                                    c.moveToNext();
                                }
                                c.close();
                                if (!userId.equals("")) {
                                    intent = new Intent(Login.this, MainActivity.class);
                                    startActivity(intent);
                                    clear();
                                } else {
                                    Toast.makeText(Login.this, "Неверный логин или пароль. Проверьте введенные данные!", Toast.LENGTH_SHORT).show();
                                }
                            }
                            catch (Exception ex){
                                Toast.makeText(Login.this, ex.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        //Toast.makeText(Login.this, "Нет интернета", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Login.this, "Некорректные данные", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void Skip(View view){
        if(stateInternet) {
            userRoleId = 3;
            getGuestInfo(3);
            intent = new Intent(Login.this, MainActivity.class);
            startActivity(intent);
            clear();
        }
        else{
            Toast.makeText(Login.this, "Отсутствует интернет подключение, вход возможен только авторизованным пользователям! ", Toast.LENGTH_SHORT).show();
        }
    }
    public void SignUp(View view){
        if(stateInternet) {
            Intent intent = new Intent(this, Registration.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(Login.this, "Регистрация в оффлайн-режиме не доступна! ", Toast.LENGTH_SHORT).show();
        }
    }

    private void initViews() {
        bookIconImageView = findViewById(R.id.bookIconImageView);
        bookITextView = findViewById(R.id.bookITextView);
        rootView = findViewById(R.id.rootView);
        afterAnimationView = findViewById(R.id.afterAnimationView);
        login=findViewById(R.id.loginEditText);
        password=findViewById(R.id.passwordEditText);
        btn_login=findViewById(R.id.loginButton);
        checkInternetConnection=new CheckInternetConnection();
        checkBox=findViewById(R.id.checkBox);
        sharedPreferences = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
    }

    private void startAnimation() {
        ViewPropertyAnimator viewPropertyAnimator = bookIconImageView.animate();
        viewPropertyAnimator.x(50f);
        viewPropertyAnimator.y(100f);
        viewPropertyAnimator.setDuration(1000);
        viewPropertyAnimator.setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                afterAnimationView.setVisibility(VISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }
    public void getGuestInfo(Integer id){
        Call<List<GuestResponse>> call=iMyApi.getGuestInfo(id);
        call.enqueue(new Callback<List<GuestResponse>>() {
            @Override
            public void onResponse(Call<List<GuestResponse>> call, Response<List<GuestResponse>> response) {
                for(GuestResponse guestResponse:response.body()){
                    userId=guestResponse.getId();
                    userLogin=guestResponse.getLogin();
                    userEmail=guestResponse.getEmail();
                }
            }

            @Override
            public void onFailure(Call<List<GuestResponse>> call, Throwable t) {

            }
        });
    }
    private void clear(){
        login.setText(null);
        password.setText(null);
    }
    private void chekLocalUser(){
        String id="";
        try {
            query = "select * from user_data where id=" + "'" + userId + "'";
            c = database.rawQuery(query, null);
            c.moveToFirst();
            while (!c.isAfterLast()) {
                id = c.getString(0).toString();
                c.moveToNext();
            }
            c.close();
            if (id.equals("")) {
                dbHelper.insertUserData(userId,userLogin,userEmail,Registration.md5(password.getText().toString()));
            }
        }
        catch (Exception ex){
            Toast.makeText(Login.this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
    private void loadWithSharedPreferences(String login,String password){
        LoginUser user = new LoginUser(login, password);
        Call<List<UserResponce>> call = iMyApi.checkLogin(user);
        call.enqueue(new Callback<List<UserResponce>>() {
            @Override
            public void onResponse(Call<List<UserResponce>> call, Response<List<UserResponce>> response) {
                for (UserResponce userResponce : response.body()) {
                    userId = userResponce.getId();
                    userRoleId = userResponce.getRoleId();
                    userEmail = userResponce.getEmail();
                    userLogin = userResponce.getLogin();
                        intent = new Intent(Login.this, MainActivity.class);
                        startActivity(intent);
                        clear();
                }
            }
            @Override
            public void onFailure(Call<List<UserResponce>> call, Throwable t) {
                Toast.makeText(Login.this, "Некорректные данные", Toast.LENGTH_LONG).show();
            }
        });
    }
    private void loadLocalWithSharedPreferences(String login, String password){
        query = "select * from user_data where login=" + "'" + login + "'" + " and password=" + "'" + password + "'";
        c = database.rawQuery(query, null);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            userId = c.getString(0).toString();
            userLogin = c.getString(1).toString();
            userEmail = c.getString(2).toString();
            c.moveToNext();
        }
        c.close();
        if (!userId.equals("")) {
            intent = new Intent(Login.this, MainActivity.class);
            startActivity(intent);
            clear();
        } else {
            Toast.makeText(Login.this, "Ошибка системы", Toast.LENGTH_SHORT).show();
        }
    }

}
