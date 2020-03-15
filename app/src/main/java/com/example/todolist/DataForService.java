package com.example.todolist;

import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;

public class DataForService {

    Context context;
    ArrayList<Task> tasks;

    public DataForService(Context context) {
        this.context = context;
    }

    public ArrayList<Task> getTasks() {
        tasks = new ArrayList<>();
        Log.d("pttt", "C - Number of tasks111: " + tasks.size());
        MyFirebase.getUsers(new CallBack_UsersReady() {

            @Override
            public void TasksReady(ArrayList<Task> tasks) {
                refreshList(tasks);
                Log.d("pttt", "C - Number of tasks: " + tasks.size());
            }

            @Override
            public void error() {

            }

        }, context);

        Log.d("pttt", "C - Number of taskssss: " + tasks.size());
        return tasks;
    }
    private void refreshList(ArrayList<Task> tasks) {
        this.tasks = tasks;
        GPS_Service.getListOfTasks(tasks);

    }
}
