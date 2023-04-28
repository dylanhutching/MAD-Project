package com.ung.todolistnotes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;


import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;


public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.ViewHolder> {
    private List<Task> mTasks;
    private String[] priorities = {"Low", "Medium", "High"};
    private CategoryReadWrite categoryReadWrite = MainActivity.mCategoryReadWrite;

    public TasksAdapter(List<Task> tasks){
        mTasks = tasks;
    }
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd", Locale.US); //formats task date int dd/mm for recycler view
    //Provide direct reference to each of the views within a data item
    public class ViewHolder extends RecyclerView.ViewHolder {

        //items displayed in each row
        public TextView taskTextView;
        public TextView dateTextView;
        public TextView priorityTextView;

        //constructor that accepts entire item row
        public ViewHolder(View itemView){
            super(itemView);

            taskTextView = (TextView) itemView.findViewById(R.id.task_desc);
            dateTextView = (TextView) itemView.findViewById(R.id.task_date);
            priorityTextView = (TextView) itemView.findViewById(R.id.task_priority);
        }
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
        String date = formatter.format(task.getDate());;
        String priority = priorities[task.getPriority()];
        //set values of textviews
        TextView textView = holder.taskTextView;
        TextView textView1 = holder.dateTextView;
        TextView textView2 = holder.priorityTextView;

        textView.setText(task.getDesc());
        textView1.setText(date);
        textView2.setText(priority);

        int color = categoryReadWrite.getCategories()[task.getCategory()].getColorId();
        textView.setTextColor(color);
        textView1.setTextColor(color);
        textView2.setTextColor(color);

    }
    @Override
    public int getItemCount(){
        return mTasks.size();
    }


}
