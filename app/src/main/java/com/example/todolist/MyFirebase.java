package com.example.todolist;

import android.content.Context;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyFirebase {

    public static void getUsers(final CallBack_UsersReady callBack_usersReady, Context context) {
        final ArrayList<Task> tasks2 = new ArrayList<>();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        String android_id = "";
        Log.d("kkk", "before");
        android_id = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        Log.d("kkk", "after");
        myRef.child("Users").child(android_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot == null)
                    callBack_usersReady.error();
                Log.d("ggg", ""+ dataSnapshot);
                MyLists l =new MyLists(dataSnapshot);
                tasks2.addAll(l.getTaskList());
//                for (DataSnapshot ds : dataSnapshot.getChildren()) {
//                    Task task = ds.getValue(Task.class);
//                    tasks2.add(task);
//                }


                callBack_usersReady.TasksReady(tasks2);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                callBack_usersReady.error();
            }
        });

    }

    public static void getUsers(Context context){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        String android_id = "";
        Log.d("kkk", "before");
        android_id = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        Log.d("kkk", "after");
        myRef.child("Users").child(android_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Log.d("ggg", ""+ dataSnapshot);
                MyLists l =new MyLists(dataSnapshot);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
