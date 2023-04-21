package com.ung.todolistnotes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;


public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.ViewHolder> {

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM", Locale.US); //formats task date int dd/mm for recycler view
    //Provide direct reference to each of the views within a data item
    public class ViewHolder extends RecyclerView.ViewHolder {

        //items displayed in each row
        public TextView taskTextView;
        public TextView dateTextView;

        //constructor that accepts entire item row
        public ViewHolder(View itemView){
            super(itemView);

            taskTextView = (TextView) itemView.findViewById(R.id.task_desc);
            dateTextView = (TextView) itemView.findViewById(R.id.task_date);
        }
    }

    private List<Task> mTasks;

    public TasksAdapter(List<Task> tasks){
        mTasks = tasks;
    }
    //inflates custom layout from item_tasks.xml that is applied to each row
    @Override
    public TasksAdapter.ViewHolder onCreateViewHolder(ViewGroup view, int viewType){
        Context context = view.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        //inflate custom layout
        View taskView = inflater.inflate(R.layout.item_tasks, view, false);

        //return new holder
        ViewHolder viewHolder = new ViewHolder(taskView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TasksAdapter.ViewHolder holder, int position){
        //get each task from tasklist
        Task task = mTasks.get(position);
        //format LocalDate and change to String
        String date = formatter.format(task.getDate());
        //set values of textviews
        TextView textView = holder.taskTextView;
        TextView textView1 = holder.dateTextView;

        textView.setText(task.getDesc());
        textView1.setText(date);

    }
    @Override
    public int getItemCount(){
        return mTasks.size();
    }


}