package com.example.todolist.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.todolist.CallBack.CallBack_Activity;
import com.example.todolist.CallBack.CallBack_RemoveTask;
import com.example.todolist.R;

public class alertFragment extends Fragment {

    private CallBack_RemoveTask callBack_removeTask;
    private CallBack_Activity callBack_activity;

    private View view;
    private CardView alert_cardview_no, alert_cardview_yes;
    private Context context;


    public alertFragment(){

    }

    public alertFragment(Context context) {
        this.context = context;
    }


    public void setCallBack(CallBack_RemoveTask callBack_removeTask, CallBack_Activity callBack_activity) {
        this.callBack_removeTask = callBack_removeTask;
        this.callBack_activity = callBack_activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState);
        Log.d("pttt", "onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("pttt", "onCreateView");
        if(view==null){
            view = inflater.inflate(R.layout.are_you_sure_fragment, container, false);
        }

        findViews(view);

        alert_cardview_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doYes();
                goTo();
            }
        });

        alert_cardview_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goTo();
            }
        });





        return view;
    }

    private void doYes() {
        callBack_removeTask.yes();

    }
    private void goTo(){
        callBack_activity.goToDoListPage();
    }

    private void findViews(View view) {
        alert_cardview_yes = view.findViewById(R.id.alert_cardview_yes);
        alert_cardview_no = view.findViewById(R.id.alert_cardview_no);
    }




}