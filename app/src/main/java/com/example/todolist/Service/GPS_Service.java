package com.example.todolist.Service;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.todolist.R;
import com.example.todolist.Model.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class GPS_Service extends Service {

    private LocationListener listener;
    private LocationManager locationManager;
    private Location taskLocation;
    public static ArrayList<Task> taskWhenAppClose ;
    public static ArrayList<Task> allTasks;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        Log.d("kkk", "onBind");
        return null;
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onCreate() {
        Log.d("kkk", "onCreate");

        allTasks = new DataForService(getApplicationContext()).getTasks();
        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.d("kkk", "onLocationChanged");

                checkDistance(location);
                checkDateTime();
//                Intent i = new Intent("location_update");
//                i.putExtra("coordinates",location.getLongitude()+" "+location.getLatitude());
//                sendBroadcast(i);
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {
                Log.d("kkk", "onStatusChanged");
            }

            @Override
            public void onProviderEnabled(String s) {
                Log.d("kkk", "onProviderEnabled");
            }

            @Override
            public void onProviderDisabled(String s) {
                Log.d("kkk", "onProviderDisabled");
                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        };

        locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);

        //noinspection MissingPermission
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,3000,0,listener);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("kkk", "onDestroy");
        if(locationManager != null){
            //noinspection MissingPermission
            locationManager.removeUpdates(listener);
        }
    }

    private void checkDateTime() {
       // Date currentTime = Calendar.getInstance().getTime();
        Date date = new Date();
        SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat formatter2 = new SimpleDateFormat("HH:mm");
//        System.out.println(formatter1.format(date));
//        System.out.println(formatter2.format(date));
        Log.d("ppp", "date: " + formatter1.format(date));
        Log.d("ppp", "time: " + formatter2.format(date));
        String cdate = formatter1.format(date);
        String ctime = formatter2.format(date);
//        String cDate = "";
//        String cTime = "";
//        String hour = "";
//        String minuet = "";
//        String year = "";
//        String month = "";
//        String day = "";
//        if (currentTime.getDay() < 10) {
//            day = "0" + currentTime.getDay();
//        } else {
//            day = "" + currentTime.getDay();
//        }
//        if (currentTime.getMonth() < 10) {
//            month = "0" + currentTime.getMonth();
//        } else {
//            month = "" + currentTime.getMonth();
//        }
//        year = "" + currentTime.getYear();
//        if (currentTime.getHours() < 10) {
//            hour = "0" + currentTime.getHours();
//        } else {
//            hour = "" + currentTime.getHours();
//        }
//        if (currentTime.getMinutes() < 10) {
//            minuet = "0" + currentTime.getMinutes();
//        } else {
//            minuet = "" + currentTime.getMinutes();
//        }
//        cDate = "" + month + "/" + day + "/" + year;
//        cTime = "" + hour + ":" + minuet;
//
//        Log.d("kkk", "date" + cDate);
//        Log.d("kkk", "time" + cTime);

       // Log.d("nnn", "size: " +allTasks.size() );

        if (allTasks != null){
            Log.d("nnn", "size: " +allTasks.size() );
            Log.d("nnn", "in1");
            for (Task t : allTasks) {
                if (!t.getDate().isEmpty() && !t.getTime().isEmpty()) {
                    Log.d("nnn", "in2");
                    if (t.getTime().equals(ctime) && t.getDate().equals(cdate)&& !t.isIsnotify()) {
                        Log.d("nnn", "in3");
                        notifyMessage(t.getDescription());
                        updateData(t.getId());
                    }
                }
            }
    }


    }

    private void checkDistance(Location location) {
        if (allTasks != null){
            for (Task t : allTasks) {
                if (t.getLocation().get(0) > 0 && t.getLocation().get(1) > 0) {
                    Log.d("nnn", "in4");
                    taskLocation = new Location(location);
                    taskLocation.setLatitude(t.getLocation().get(0));
                    taskLocation.setLongitude(t.getLocation().get(1));
                    Log.d("kkk", "dd: " + taskLocation.distanceTo(location));
                    if (taskLocation.distanceTo(location) <= t.getDistance() && !t.isIsnotify()) {
                        notifyMessage(t.getDescription());
                        updateData(t.getId());

                    }

                }
            }
    }
    }

    private static final String CHANNEL_ID = "CHANNEL_NO_1jghg";

    private void notifyMessage(String taskDescription) {
        // Current version
        Context context = this;
        // Create your notification channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            CharSequence name = "New riddle";
            String description = taskDescription;
            int importance = NotificationManager.IMPORTANCE_HIGH;

            // IMPORTANT: CHANNEL_ID
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,name, importance);
            channel.setDescription(description);


            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }


        // Get an instance of NotificationManager
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.logo)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setContentTitle(taskDescription)


                //IMPORTANT: CHANNEL_ID
                .setChannelId(CHANNEL_ID);

        // It won't show "Heads Up" unless it plays a sound
        if (Build.VERSION.SDK_INT >= 21) mBuilder.setVibrate(new long[0]);


        // Gets an instance of the NotificationManager service//
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);


        mNotificationManager.notify(001, mBuilder.build());
    }

    private void updateData(String id){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef;
        String android_id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        myRef = database.getReference("message");
        myRef.child("Users").child(android_id).child(id).child("isnotify").setValue(true);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        final Handler ha=new Handler();
        ha.postDelayed(new Runnable() {

            @Override
            public void run() {
                Log.d("ptttLF", "Ticker");
                //MyFirebase.getUsers(getApplicationContext());
//                if(MyLists.taskList == null){
//                    Log.d("jjj", "null");
//                    Log.d("jjj", "tasks: " + taskWhenAppClose);
//                    allTasks.addAll(taskWhenAppClose);
//                }else{
//                    Log.d("jjj", "not null");
//                    allTasks = MyLists.taskList;
//                }
                 new DataForService(getApplicationContext()).getTasks();
                checkDateTime();


                ha.postDelayed(this, 5000);
            }
        }, 5000);
        return super.onStartCommand(intent, flags, startId);
    }

    public static void getListOfTasks(ArrayList<Task> taskArrayList){
        allTasks = taskArrayList;
    }


}
