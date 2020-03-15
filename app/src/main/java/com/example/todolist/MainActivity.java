package com.example.todolist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private Toolbar toolbar;

    private ToDoListFragment toDoListFragment;
    private TaskFragment taskFragment;
    private CalendarFragment calendarFragment;
    private MapFragment mapFragment;
    private alertFragment alertFragment;
    public static ArrayList<Task> allTasks;
    private DoneFragment doneFragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        allTasks = new ArrayList<>();

        if(!runtime_permissions()){
            Intent i =new Intent(getApplicationContext(),GPS_Service.class);
            startService(i);
        }

        String android_id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        Log.d("pttt", "id:" + android_id);

        toolbar = findViewById(R.id.main_TB_toolbar);
        alertFragment = new alertFragment();
        toDoListFragment = new ToDoListFragment(this, android_id, alertFragment);
        taskFragment = new TaskFragment(this, android_id, toolbar);
        calendarFragment = new CalendarFragment(this);
        mapFragment = new MapFragment();
        doneFragment = new DoneFragment(this);



        toDoListFragment.setCallBack(callBack_activity);
        taskFragment.setCallBack(callBack_activity);
        calendarFragment.setCallBack(callBack_activity);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_FL_mainScreen, toDoListFragment);
        transaction.commit();



        bottomNavigationView =  findViewById(R.id.bottom_navigation);

        setSupportActionBar(toolbar);



//        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                Toast.makeText(MainActivity.this, "checked", Toast.LENGTH_SHORT).show();
//                return false;
//            }
//        });

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_calendar:
                        Toast.makeText(MainActivity.this, "calendar", Toast.LENGTH_SHORT).show();
                        goTo(calendarFragment);
                        break;
                    case R.id.action_list:
                        Toast.makeText(MainActivity.this, "list", Toast.LENGTH_SHORT).show();
                        goTo(toDoListFragment);
                        break;
                    case R.id.action_map:
                        Toast.makeText(MainActivity.this, "map", Toast.LENGTH_SHORT).show();
                        goTo(mapFragment);
                        break;
                    case R.id.action_done:
                        Toast.makeText(MainActivity.this, "done", Toast.LENGTH_SHORT).show();
                        goTo(doneFragment);
                        break;
                }
                return true;
            }
        });

        final Handler ha=new Handler();
        ha.postDelayed(new Runnable() {

            @Override
            public void run() {
                Log.d("ptttLF", "Ticker");

                allTasks = MyLists.taskList;
                Log.d("jjj", "" + allTasks);

                ha.postDelayed(this, 5000);
            }
        }, 1000);


    }

    private void goTo(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_FL_mainScreen, fragment);
        transaction.commit();
    }

    CallBack_Activity callBack_activity = new CallBack_Activity() {
        @Override
        public void goToTaskPage() {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.main_FL_mainScreen, taskFragment);
            transaction.commit();
        }

        @Override
        public void goToDoListPage() {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.main_FL_mainScreen, toDoListFragment);
            transaction.commit();
        }

        @Override
        public void setCheckToolbar() {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.main_FL_mainScreen, toDoListFragment);
            transaction.commit();
        }

        @Override
        public void goToAlert() {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.main_FL_mainScreen, alertFragment);
            transaction.commit();
        }


    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    private boolean runtime_permissions() {
        if(Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){

            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},100);

            return true;
        }
        return false;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 100){
            if( grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                //enable_buttons();
                //start service
                Intent i =new Intent(getApplicationContext(),GPS_Service.class);
                startService(i);
            }else {
                runtime_permissions();
            }
        }
    }

//    @Override
//    protected void onPause() {
//        Log.d("jjj", "in : " + allTasks);
//        Log.d("jjj", "in : " + allTasks.size());
//        GPS_Service.taskWhenAppClose = new ArrayList<>(allTasks);
//
//        //Log.d("lll","" + MyLists.taskList );
//        super.onPause();
//
//    }
}
