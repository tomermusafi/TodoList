package com.example.todolist;

import java.util.ArrayList;

public interface CallBack_UsersReady {
    void TasksReady(ArrayList<Task> tasks);
    void error();
}
