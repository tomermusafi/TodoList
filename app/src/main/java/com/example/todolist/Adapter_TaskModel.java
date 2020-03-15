package com.example.todolist;

import android.content.Context;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Adapter_TaskModel extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<Task> tasks;
    private OnItemClickListener mItemClickListener;



    public Adapter_TaskModel(Context context, ArrayList<Task> tasks) {
        this.context = context;
        this.tasks = tasks;
    }

    public void updateList(ArrayList<Task> tasks) {
        this.tasks = tasks;
        notifyDataSetChanged();

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_task, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ViewHolder) {
            final Task task = getItem(position);
            final ViewHolder genericViewHolder = (ViewHolder) holder;

            String date = task.getDate();
            String [] s = date.split("/");
            String newDate = "";
            if(!task.getDate().isEmpty()){
                newDate = s[1] + "/" + s[2] + "/" + s[0];
            }else{
                newDate = "";
            }



            genericViewHolder.list_task_LBL_date.setText(newDate);
            genericViewHolder.list_task_LBL_time.setText(task.getTime());
            genericViewHolder.list_task_LBL_description.setText(task.getDescription());

        }
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    private Task getItem(int position) {
        return tasks.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView list_task_LBL_date, list_task_LBL_time, list_task_LBL_description;

        public ViewHolder(final View itemView) {
            super(itemView);
            this.list_task_LBL_date = itemView.findViewById(R.id.list_task_LBL_date);
            this.list_task_LBL_time = itemView.findViewById(R.id.list_task_LBL_time);
            this.list_task_LBL_description = itemView.findViewById(R.id.list_task_LBL_description);

//            itemView.setOnLongClickListener(new View.OnLongClickListener() {
//                @Override
//                public boolean onLongClick(View v) {
//                    if(mItemClickListener != null){
//                        String android_id = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
//                        String taskToRemoe  = tasks.get(getAdapterPosition()).getId();
//
//                        final FirebaseDatabase database = FirebaseDatabase.getInstance();
//                        DatabaseReference myRef = database.getReference("message");
//
//                        //DatabaseReference ref = FirebaseDatabase.getInstance().getReference("message");
//                        //Query applesQuery = ref.child("Users").orderByChild("id").equalTo(taskToRemoe);
//
//
//                        myRef.child("Users").child(android_id).orderByChild("id")
//                                .equalTo(taskToRemoe).addListenerForSingleValueEvent(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(DataSnapshot dataSnapshot) {
//                                removeAt(getAdapterPosition());
//                                for (DataSnapshot Snapshot: dataSnapshot.getChildren()) {
//                                    Log.e("pttt", "hiiiii" + Snapshot);
//
//                                    Snapshot.getRef().removeValue();
//
//                                }
//                            }
//
//                            @Override
//                            public void onCancelled(DatabaseError databaseError) {
//                                Log.e("pttt", "onCancelled", databaseError.toException());
//                            }
//                        });
//
//
//                    }
//
//                    return true;
//                }
//            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mItemClickListener.onItemClick(itemView, getAdapterPosition(), getItem(getAdapterPosition()));

                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mItemClickListener.onItemLongClick(itemView, getAdapterPosition(), getItem(getAdapterPosition()));
                    return false;
                }
            });
        }
    }

    public void removeAt(int position) {
        tasks.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, tasks.size());
    }

    public void removeAll(){
        for(int i = 0; i < tasks.size(); i++){
            tasks.remove(i);
        }
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position, Task task);
        void onItemLongClick(View view, int position, Task task);
    }
}