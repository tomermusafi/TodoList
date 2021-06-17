package com.example.todolist.Fragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.todolist.CallBack.CallBack_Activity;
import com.example.todolist.R;
import com.example.todolist.Model.Task;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;

import static android.app.Activity.RESULT_OK;

public class TaskFragment extends Fragment {

    private CallBack_Activity callBack_activityList;

    private View view;
    private EditText task_EDT_task, task_EDT_date, task_EDT_time, task_EDT_location;
    private Context context;
    private String android_id;
    private Toolbar toolbar;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;
    int PLACE_PICKER_REQUEST = 1;
    private Button btn_check;
    private double latitude, longitude;
    private String dateForDatabase = "";
    private SeekBar seekBar;
    private TextView distance;
    private int mdistance = 0;





    public TaskFragment(){

    }

    public TaskFragment(Context context, String android_id, Toolbar toolbar) {
        this.context = context;
        this.android_id = android_id;
        this.toolbar = toolbar;
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
            view = inflater.inflate(R.layout.task_fragment, container, false);
        }

        findViews(view);
        //callBack_activityList.setCheckToolbar();

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mdistance = progress;
                distance.setText("" + progress + " m");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        task_EDT_task.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                switch (actionId){
                    case EditorInfo.IME_ACTION_DONE:
                        break;
                }
                return false;
            }
        });

        task_EDT_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try{
                    startActivityForResult(builder.build(getActivity()),
                            PLACE_PICKER_REQUEST);

                } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });
        myRef = database.getReference("message");

        btn_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "saved", Toast.LENGTH_SHORT).show();
                String description = task_EDT_task.getText().toString();
                //String date = task_EDT_date.getText().toString();
                String time = task_EDT_time.getText().toString();
                ArrayList<Double> location = new ArrayList<>();
                String id = String.valueOf(System.currentTimeMillis());
                location.add(latitude);
                location.add(longitude);
                boolean isnotify = false;
                boolean isDone = false;

                Task task = new Task(description, dateForDatabase, time, location, id, isnotify, isDone, mdistance);
                myRef.child("Users").child(android_id).child(""+ id).setValue(task);

                task_EDT_task.setText("");
                task_EDT_date.setText("");
                task_EDT_time.setText("");
                task_EDT_location.setText("");

                callBack_activityList.setCheckToolbar();
            }
        });

//        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
////            @Override
////            public boolean onMenuItemClick(MenuItem item) {
////                Toast.makeText(context, "saved", Toast.LENGTH_SHORT).show();
////                String description = task_EDT_task.getText().toString();
////                //String date = task_EDT_date.getText().toString();
////                String time = task_EDT_time.getText().toString();
////                ArrayList<Double> location = new ArrayList<>();
////                String id = String.valueOf(System.currentTimeMillis());
////                location.add(latitude);
////                location.add(longitude);
////                boolean isnotify = false;
////                boolean isDone = false;
////                Task task = new Task(description, dateForDatabase, time, location, id, isnotify, isDone);
////                myRef.child("Users").child(android_id).child(""+ id).setValue(task);
////
////                task_EDT_task.setText("");
////                task_EDT_date.setText("");
////                task_EDT_time.setText("");
////                task_EDT_location.setText("");
////
////                callBack_activityList.setCheckToolbar();
////
////                return true;
////            }
////        });

        task_EDT_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog();
            }
        });
        task_EDT_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimeDialog();
            }
        });

//        final FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("message");
//        ArrayList<Double> location = new ArrayList<>();
//        location.add(2.222);
//        location.add(4.444);
//        Task task = new Task("14.08.20", "16:33", "Tomer's birthday", location);
//
//        myRef.child("Users").child(android_id).child(""+ System.currentTimeMillis()).setValue(task);


        return view;
    }

    private void showTimeDialog() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                context,
                timeSetListener,
                Calendar.getInstance().get(Calendar.HOUR),
                Calendar.getInstance().get(Calendar.MINUTE),
                DateFormat.is24HourFormat(context)
        );
        timePickerDialog.show();
    }

    private void showDateDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                context,
                dateSetListener,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();

    }

    TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            String houre = "";
            String myMinute = "";
            if(hourOfDay < 10){
                houre = "0"+hourOfDay;
            }else{
                houre = ""+hourOfDay;
            }
            if(minute < 10){
                myMinute = "0"+minute;
            }else{
                myMinute = ""+minute;
            }
            String time = "" + houre + ":" + myMinute;
            task_EDT_time.setText(time);
        }
    };

    DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            String myMonth = "";
            String myday = "";
            if((month+1) < 10){
                myMonth = "0"+(month+1);
            }else{
                myMonth = ""+(month+1);
            }
            if(dayOfMonth < 10){
                myday = "0"+dayOfMonth;
            }else{
                myday = ""+dayOfMonth;
            }
            String date = "" + myMonth + "/" + myday + "/" + year;
            dateForDatabase = "" + year + "/" + myMonth + "/" + myday;
            task_EDT_date.setText(date);
        }
    };

    private void goTo() {
        //fragmentTransaction.remove(yourfragment).commit();

    }

    private void findViews(View view) {
        task_EDT_task = view.findViewById(R.id.task_EDT_task);
        task_EDT_date = view.findViewById(R.id.task_EDT_date);
        task_EDT_time = view.findViewById(R.id.task_EDT_time);
        task_EDT_location = view.findViewById(R.id.task_EDT_location);
        btn_check = view.findViewById(R.id.btn_check);
        seekBar = view.findViewById(R.id.seek_bar);
        distance = view.findViewById(R.id.txv_distance);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.toolbar_menu, menu);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == PLACE_PICKER_REQUEST){
            if(resultCode == RESULT_OK){
                Place place = PlacePicker.getPlace(data, context);
                StringBuilder stringBuilder = new StringBuilder();
                latitude = place.getLatLng().latitude;
                longitude = place.getLatLng().longitude;
                String mlatitude = String.valueOf(latitude);
                String mlongitude = String.valueOf(longitude);
                stringBuilder.append("LATITUDE: ");
                stringBuilder.append(mlatitude);
                stringBuilder.append("\n");
                stringBuilder.append("LONGITUDE: ");
                stringBuilder.append(mlongitude);
                task_EDT_location.setText(stringBuilder.toString());
            }
        }
    }
}