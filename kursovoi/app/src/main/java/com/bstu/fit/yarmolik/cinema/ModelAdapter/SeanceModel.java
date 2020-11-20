package com.bstu.fit.yarmolik.cinema.ModelAdapter;
public class SeanceModel {
    String hallName;
    String startTime;
    String endTime;
    String id;
    public SeanceModel(String hallName, String startTime, String endTime,String id){
        this.hallName=hallName;
        this.startTime=startTime;
        this.endTime=endTime;
        this.id=id;
    }

    public String getHallName() {
        return hallName;
    }

    public void setHallName(String hallName) {
        this.hallName = hallName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
