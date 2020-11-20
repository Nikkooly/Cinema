package com.bstu.fit.yarmolik.cinema.LocalDataBase;

public class WorksWithDb {
    public void userRegister(DbHelper dbHelper) {
        dbHelper.queryData("CREATE TABLE IF NOT EXISTS user_data(" +
                " id BLOB PRIMARY KEY," +
                " login TEXT," +
                " email TEXT," +
                " password TEXT, UNIQUE (id) ON CONFLICT IGNORE)");
    }
    public void seanceData(DbHelper dbHelper) {
        dbHelper.queryData("CREATE TABLE IF NOT EXISTS seance(" +
                " id BLOB PRIMARY KEY," +
                " cinema_info TEXT," +
                " hall_info TEXT," +
                " film_info TEXT," +
                " date TEXT," +
                " start_time TEXT," +
                " end_time TEXT, UNIQUE (id) ON CONFLICT IGNORE)");
    }
    public void ticketInfo(DbHelper dbHelper) {
        dbHelper.queryData("CREATE TABLE IF NOT EXISTS tickets(" +
                " id BLOB PRIMARY KEY," +
                " seance_id BLOB," +
                " user_id BLOB," +
                " place TEXT," +
                " UNIQUE (id) ON CONFLICT IGNORE," +
                "FOREIGN KEY (seance_id) REFERENCES seance(id) on delete cascade,"+
                "FOREIGN KEY (user_id) REFERENCES user_data(id) on delete cascade)");
    }

}
