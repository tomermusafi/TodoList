package com.example.todolist.Model;

import java.util.ArrayList;

public class MyTime {
    String time;
    ArrayList <Task> tasks;

    public MyTime() {
    }

    public MyTime(String time, ArrayList<Task> tasks) {
        this.time = time;
        this.tasks = tasks;
    }

    public String getTime() {
        return time;
    }

    public MyTime setTime(String time) {
        this.time = time;
        return this;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public MyTime setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
        return this;
    }
}
