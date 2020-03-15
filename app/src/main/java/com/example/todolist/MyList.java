package com.example.todolist;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyList {
    private Adapter_TaskModel adapter_taskModel;
    private RecyclerView recyclerView;
    private Context context;
    private ArrayList<Task> tasks;
    private int mposition;
    private String fragmentName ="";
    private CallBack_Activity callBack_activityList;


    private alertFragment alertFragment;

    public MyList() {
    }

    public MyList(RecyclerView recyclerView, Context context, ArrayList<Task> tasks){
        this.recyclerView = recyclerView;
        this.context = context;
        refreshList(tasks);
    }

    public MyList(RecyclerView recyclerView, Context context, ToDoListFragment toDoListFragment) {
        this.recyclerView = recyclerView;
        this.context = context;
        fragmentName = toDoListFragment.getClass().getName();
        Log.d("ooo", "name: " + fragmentName);
        //alertFragment.setCallBack(callBack_removeTask,callBack_activityList);
        initList();


    }

    private void initList() {
        tasks = getNotes();




    }

    private void openNote(Task task) {
        Toast.makeText(context, task.getDate(), Toast.LENGTH_SHORT).show();
    }

    private ArrayList<Task> getNotes() {
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

        Log.d("pttt", "C - Number of tasks: " + tasks.size());
        return tasks;
    }
    private void refreshList(ArrayList<Task> tasks) {
       this.tasks = tasks;
        adapter_taskModel = new Adapter_TaskModel(context, tasks);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter_taskModel);
        adapter_taskModel.SetOnItemClickListener(new Adapter_TaskModel.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, Task task) {
                String l1 = "";
                String l2 = "";
                if(task.getLocation().get(0) !=0 && task.getLocation().get(1) != 0){
                    l1 = "" + task.getLocation().get(0);
                    l2 = "" + task.getLocation().get(1);
                }
                String date = task.getDate();
                if(!date.isEmpty()) {
                    String[] dateArr = date.split("/");
                    String year = "", month = "", day = "";
                    year = dateArr[0];
                    month = dateArr[1];
                    day = dateArr[2];
                    date = month + "/" + day + "/" + year;
                }
                    new AlertDialog.Builder(context)
                            .setTitle("Task")
                            .setMessage("Date: " + date + "\nTime: " + task.getTime() + "\nTask: " + task.getDescription()
                                    + "\nLatitude: " + l1 + "\nLongitude:" + l2)

                            // Specifying a listener allows you to take an action before dismissing the dialog.
                            // The dialog is automatically dismissed when a dialog button is clicked.
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // Continue with delete operation
                                }
                            })

                            // A null listener allows the button to dismiss the dialog and take no further action.
                            .setIcon(R.drawable.logo)
                            .show();

                //openNote(task);
            }

            @Override
            public void onItemLongClick(View view, int position, Task task) {

                mposition = position;
                if(fragmentName.equals("com.example.todolist.ToDoListFragment"))
                    changeToDone();
                //callBack_activityList.goToAlert();
               // removeTask(position);
//                Handler handler = new Handler();
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//
//                    }
//                }, 3000);
//                recyclerView.removeAllViews();
//                initList();
            }
        });
//        recyclerView.removeAllViews();
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setAdapter(adapter_taskModel);
    }

    private void changeToDone(){
        Toast.makeText(context, "done", Toast.LENGTH_SHORT).show();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef;
        String id  = tasks.get(mposition).getId();
        String android_id = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        myRef = database.getReference("message");
        myRef.child("Users").child(android_id).child(id).child("isDone").setValue(true);
        adapter_taskModel.removeAt(mposition);
        ToDoListFragment.updateList();
    }

//    private void removeTask(final int position) {
//
//        String android_id = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
//        String taskToRemoe  = tasks.get(position).getId();
//        for (int i = 0; i < adapter_taskModel.getItemCount(); i++){
//            adapter_taskModel.removeAt(i);
//        }
//        recyclerView.removeAllViews();
//
//        final FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("message");
//
//        //DatabaseReference ref = FirebaseDatabase.getInstance().getReference("message");
//        //Query applesQuery = ref.child("Users").orderByChild("id").equalTo(taskToRemoe);
//
//
//        myRef.child("Users").child(android_id).orderByChild("id")
//                .equalTo(taskToRemoe).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//               // adapter_taskModel.removeAt(position);
//                for (DataSnapshot Snapshot: dataSnapshot.getChildren()) {
//                    Log.e("pttt", "hiiiii" + Snapshot);
//
//                    Snapshot.getRef().removeValue();
//
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Log.e("pttt", "onCancelled", databaseError.toException());
//            }
//        });
//
//    }

//    CallBack_RemoveTask callBack_removeTask = new CallBack_RemoveTask() {
//        @Override
//        public void yes() {
//            removeTask(mposition);
//        }
//
//        @Override
//        public void no() {
//
//        }
//    };

    public  void removall(){
        recyclerView.removeAllViews();
    }

}
