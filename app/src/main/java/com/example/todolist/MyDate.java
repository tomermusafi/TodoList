package com.example.todolist;

import java.util.ArrayList;

public class MyDate {
    String date;
    ArrayList<MyTime> myTimes;

    public MyDate() {
    }

    public MyDate(String date, ArrayList<MyTime> myTimes) {
        this.date = date;
        this.myTimes = myTimes;
    }

    public String getDate() {
        return date;
    }

    public MyDate setDate(String date) {
        this.date = date;
        return this;
    }

    public ArrayList<MyTime> getMyTimes() {
        return myTimes;
    }

    public MyDate setMyTimes(ArrayList<MyTime> myTimes) {
        this.myTimes = myTimes;
        return this;
    }
}
