package com.bstu.fit.yarmolik.cinema.LocalDataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

public class DbHelper extends SQLiteOpenHelper {
    public DbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    public void insertUserData(String id, String login,String email, String password){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO user_data VALUES (?, ?, ?, ?)";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1, id);
        statement.bindString(2,login);
        statement.bindString(3, email);
        statement.bindString(4, password);
        statement.executeInsert();
    }
    public void insertSeance(String id, String cinema_info,String hall_info, String film_info,String date, String start_time, String end_time){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO seance VALUES (?, ?, ?, ?, ?, ?, ?)";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1, id);
        statement.bindString(2,cinema_info);
        statement.bindString(3, hall_info);
        statement.bindString(4, film_info);
        statement.bindString(5, date);
        statement.bindString(6, start_time);
        statement.bindString(7, end_time);
        statement.executeInsert();
    }
    public void insertTicket(String id, String seance_id,String user_id, String place){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO tickets VALUES (?, ?, ?, ?)";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1, id);
        statement.bindString(2,seance_id);
        statement.bindString(3, user_id);
        statement.bindString(4, place);
        statement.executeInsert();
    }
    public  void deleteTickets(String seance_id, String user_id) {
        SQLiteDatabase database = getWritableDatabase();
        //
        String sql = "DELETE FROM tickets WHERE seance_id = ? and user_id=?";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1, seance_id);
        statement.bindString(2, user_id);
        statement.execute();
        database.close();
    }
    public void deleteAllTickets() {
        SQLiteDatabase database = getWritableDatabase();
        String sql = "DELETE FROM tickets";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.execute();
        database.close();
    }
    public void deleteAllSeances() {
        SQLiteDatabase database = getWritableDatabase();
        String sql = "DELETE FROM seance";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.execute();
        database.close();
    }


    public void queryData(String sql){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
