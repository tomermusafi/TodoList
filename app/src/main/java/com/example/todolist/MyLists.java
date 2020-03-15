package com.example.todolist;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MyLists {

    private DataSnapshot dataSnapshot;
    public static ArrayList<Task> taskList;
    public static ArrayList<Task> tasksByDateList;
    public static ArrayList<Task> doneTasksList;


    public MyLists() {
    }

    public MyLists(DataSnapshot dataSnapshot) {
        this.dataSnapshot = dataSnapshot;
        taskList = makeTaskList();
        doneTasksList = makeDoneList();

    }

    private   ArrayList<Task> makeDoneList(){
        doneTasksList = new ArrayList<>();
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            Task task = ds.getValue(Task.class);
            if(task.getIsDone())
                doneTasksList.add(task);
        }
        sortDoneList();

        return doneTasksList;
    }

    private   ArrayList<Task> makeTaskList(){
        taskList = new ArrayList<>();
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            Task task = ds.getValue(Task.class);
            if(!task.getIsDone())
                taskList.add(task);
        }
        sortList();

        return taskList;
    }

    private void sortList(){
        Collections.sort(taskList, new Comparator<Task>() {
            @Override
            public int compare(Task o1, Task o2) {
                String x1 = o1.getDate();
                String x2 = o2.getDate();
                int sComp = x1.compareTo(x2);
                if(sComp != 0){
                    return sComp;
                }
                String y1 = o1.getTime();
                String y2 = o2.getTime();
                return y1.compareTo(y2);
            }
        });
    }
    private void sortDoneList(){
        Collections.sort(doneTasksList, new Comparator<Task>() {
            @Override
            public int compare(Task o1, Task o2) {
                String x1 = o1.getDate();
                String x2 = o2.getDate();
                int sComp = x1.compareTo(x2);
                if(sComp != 0){
                    return sComp;
                }
                String y1 = o1.getTime();
                String y2 = o2.getTime();
                return y1.compareTo(y2);
            }
        });
    }


    public static ArrayList<Task> makeTimeTasksList(String date){
        tasksByDateList = new ArrayList<>();
        for(Task t : taskList){
            if(t.getDate().equals(date)){
                tasksByDateList.add(t);
            }

        }
        return tasksByDateList;
    }

    public ArrayList<Task> getTaskList(){
        return taskList;
    }

}
