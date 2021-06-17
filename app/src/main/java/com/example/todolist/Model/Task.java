package com.example.todolist.Model;

import java.util.ArrayList;

public class Task {
    private String description;
    private String date;
    private String time;
    private ArrayList <Double> location;
    private String id;
    private boolean isnotify;
    private boolean isDone;
    private int distance;

    public Task() {
    }

    public Task(String description, String date, String time
            , ArrayList <Double> location, String id, boolean isnotify, boolean isDone, int distance) {
        this.description = description;
        this.date = date;
        this.time = time;
        this.location = location;
        this.id = id;
        this.isnotify = isnotify;
        this.isDone = isDone;
        this.distance = distance;
    }

    public String getDescription() {
        return description;
    }

    public Task setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getDate() {
        return date;
    }

    public Task setDate(String date) {
        this.date = date;
        return this;
    }

    public String getTime() {
        return time;
    }

    public Task setTime(String time) {
        this.time = time;
        return this;
    }

    public ArrayList <Double> getLocation() {
        return location;
    }

    public Task setLocation(ArrayList <Double> location) {
        this.location = location;
        return this;
    }

    public String getId() {
        return id;
    }

    public Task setId(String id) {
        this.id = id;
        return this;
    }

    public boolean isIsnotify() {
        return isnotify;
    }

    public Task setIsnotify(boolean isnotify) {
        this.isnotify = isnotify;
        return this;
    }

    public boolean getIsDone() {
        return isDone;
    }

    public Task setIsDone(boolean done) {
        isDone = done;
        return this;
    }

    public int getDistance() {
        return distance;
    }

    public Task setDistance(int distance) {
        this.distance = distance;
        return this;
    }
}
