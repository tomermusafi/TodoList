package com.example.todolist;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DoneFragment extends Fragment {

    private CallBack_Activity callBack_activityList;

    private View view;
    private RecyclerView recyclerView;
    private Context context;


    public DoneFragment(){

    }

    public DoneFragment(Context context) {
        this.context = context;
    }


    public void setCallBack(CallBack_Activity _callBack_activityList) {
        this.callBack_activityList = _callBack_activityList;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState);
        Log.d("pttt", "onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("pttt", "onCreateView");
        if(view==null){
            view = inflater.inflate(R.layout.done_fragment, container, false);
        }

        findViews(view);
        setRecyclerView();

        return view;
    }

    private void goTo() {
        //callBack_activityList.goToTaskPage();

    }

    private void findViews(View view) {
        recyclerView = view.findViewById(R.id.done_RV_tasks);
    }

    private void setRecyclerView(){
        new MyList(recyclerView, context, MyLists.doneTasksList);

    }






}