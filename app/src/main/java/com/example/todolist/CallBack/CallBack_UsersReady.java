package com.example.todolist.CallBack;

import com.example.todolist.Model.Task;

import java.util.ArrayList;

public interface CallBack_UsersReady {
    void TasksReady(ArrayList<Task> tasks);
    void error();
}
