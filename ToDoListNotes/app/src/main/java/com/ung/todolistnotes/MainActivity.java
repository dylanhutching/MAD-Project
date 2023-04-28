package com.ung.todolistnotes;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import android.view.Menu;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    //private final static String TAG = "MainActivity";
    public static ToDoList mToDoList;
    private List<Task> taskList;
    public static CategoryReadWrite mCategoryReadWrite;
    private EditText mItemEditText;
    private TextView mItemListTextView;
    private TextView mItemNumTodayTextView;
    private TextView mItemNumOverdueTextView;
    private TextView mItemNumTextView;
    private TextView mDueDateTextView;
    private TextView mPriorityTextView;
    private LinearLayoutManager llm;
    private RecyclerView rvTasks;
    private TasksAdapter adapter;
    private boolean isOption1Removed = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //mItemEditText = findViewById(R.id.todo_item);
        mItemNumTodayTextView = findViewById(R.id.due_today);
        mItemNumOverdueTextView = findViewById(R.id.overdue);
        mItemNumTextView = findViewById(R.id.num_of_tasks);

        mPriorityTextView = findViewById(R.id.priority);
        mDueDateTextView = findViewById(R.id.due_date);

        findViewById(R.id.priority).setOnClickListener(view -> SortByPriority());
        findViewById(R.id.due_date).setOnClickListener(view -> SortByDueDate());

        mToDoList = new ToDoList(this);
        taskList = mToDoList.getTaskList();
        mCategoryReadWrite = new CategoryReadWrite(this);

        //starts the recycler view by passing in data through adapter and setting a LayoutManager to position the items
        llm = new LinearLayoutManager(this);
        rvTasks = (RecyclerView) findViewById(R.id.rvTasks);
        adapter = new TasksAdapter(mToDoList.getTaskList());

        rvTasks.setLayoutManager(llm);
        rvTasks.setAdapter(adapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();

                Task deletedTask = taskList.get(position);

                taskList.remove(viewHolder.getAdapterPosition());
                adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                try {
                    mToDoList.saveToFile();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                Snackbar.make(rvTasks, deletedTask.getDesc() + " deleted.", Snackbar.LENGTH_LONG).setAction("Undo", view -> {
                    taskList.add(position, deletedTask);
                    adapter.notifyItemInserted(position);
                    try {
                        mToDoList.saveToFile();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    displayNum();
                }).show();
                displayNum();
            }
        }).attachToRecyclerView(rvTasks);

        displayNum();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.appbar_menu, menu);

        // Hide option 1 if it is selected
        if (isOption1Removed = true) {
            menu.removeItem(R.id.tasks);
        }
        return true;
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.tasks:
                startActivity(new Intent(this, MainActivity.class)); //B4 changing to startActivity(), swapping activities would delete the list. With this line OnResume() is called automatically, which reads the list like needed.
                //setContentView(R.layout.activity_main);
                Toast.makeText(this, "Tasks", Toast.LENGTH_SHORT).show();
                isOption1Removed = true;
                return true;
            case R.id.delete_task:
                clearButtonClick();
                //This code will change with our new tasks list but this is for demo purposes
                return true;
            case R.id.new_category:
                startActivity(new Intent(this, NewCategoryActivity.class));
                //setContentView(R.layout.activity_new_category);
                Toast.makeText(this, "New Category", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.new_task:
                //setContentView(R.layout.activity_new_tasks);
                Toast.makeText(this, "New Task", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(this, NewTasksActivity.class);    //Intents allow us to pass data between activities, and properly call onResume(), OnStart(), etc.
                i.putExtra("selected_task_index", 0); //for testing this extra is set to 0, later it will be set to the selected task index. Additionally, we can check if this value is -1 (the default set inside of NewTasksActivity.java), to determine if we are trying to edit or make a new task in the other activity.
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        try {
            // Attempt to load a previously saved list
            mToDoList.readFromFile();

            mCategoryReadWrite.readFromFile();
            mToDoList.SortByDueDate();
            adapter.notifyDataSetChanged();
            displayNum();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    private void displayNum() {
        Task[] items = mToDoList.getItems();
        int u = items.length;
        String numItems = Integer.toString(u);
        mItemNumTodayTextView.setText(Integer.toString(mToDoList.GetNumDueToday()));
        mItemNumOverdueTextView.setText(Integer.toString(mToDoList.GetNumOverdue()));
        mItemNumTextView.setText(numItems);
    }

    @Override
    protected void onPause() {
        super.onPause();

        try {
            // Save list for laterm
            mCategoryReadWrite.saveToFile();
            mToDoList.saveToFile();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void clearButtonClick() {
        mToDoList.clear();
        try {
            // Save list for later
            mToDoList.saveToFile();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        adapter.notifyDataSetChanged();
        displayNum();
    }

    public void SortByDueDate(){
        mToDoList.SortByDueDate();
        adapter.notifyDataSetChanged();

        mDueDateTextView.setTextColor(ContextCompat.getColor(this, R.color.green_light));
        mPriorityTextView.setTextColor(ContextCompat.getColor(this, R.color.grey));
    }

    public void SortByPriority(){
        mToDoList.SortByPriority();
        adapter.notifyDataSetChanged();

        mPriorityTextView.setTextColor(ContextCompat.getColor(this, R.color.green_light));
        mDueDateTextView.setTextColor(ContextCompat.getColor(this, R.color.grey));
    }

}