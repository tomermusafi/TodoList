package com.example.todolist;

import java.util.ArrayList;

public class User {
    String uid;
    ArrayList<Task> tasks;

    public User() {
    }

    public User(String uid, ArrayList<Task> tasks) {
        this.uid = uid;
        this.tasks = tasks;
    }

    public String getUid() {
        return uid;
    }

    public User setUid(String uid) {
        this.uid = uid;
        return this;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public User setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
        return this;
    }
}
