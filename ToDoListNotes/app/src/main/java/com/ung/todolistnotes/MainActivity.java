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
    private EditText mItemEditText;
    private TextView mItemListTextView;
    private TextView mItemNumTodayTextView;
    private TextView mItemNumOverdueTextView;
    private TextView mItemNumTextView;
    private LinearLayoutManager llm;
    private RecyclerView rvTasks;
    private TasksAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mItemEditText = findViewById(R.id.todo_item);
        mItemNumTodayTextView = findViewById(R.id.due_today);
        mItemNumOverdueTextView = findViewById(R.id.overdue);
        mItemNumTextView = findViewById(R.id.num_of_tasks);

        findViewById(R.id.add_button).setOnClickListener(view -> addButtonClick());

        mToDoList = new ToDoList(this);
        taskList = mToDoList.getTaskList();

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

                Snackbar.make(rvTasks, deletedTask.getDesc() + " deleted.", Snackbar.LENGTH_LONG).setAction("Undo", view -> {
                    taskList.add(position, deletedTask);
                    adapter.notifyItemInserted(position);
                }).show();

            }
        }).attachToRecyclerView(rvTasks);

        displayNum();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.appbar_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.tasks:
                startActivity(new Intent(this, MainActivity.class)); //B4 changing to startActivity(), swapping activities would delete the list. With this line OnResume() is called automatically, which reads the list like needed.
                //setContentView(R.layout.activity_main);
                Toast.makeText(this, "Tasks", Toast.LENGTH_SHORT).show();
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

    /*ActivityResultLauncher<Intent> mCatResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            int colorId = data.getIntExtra(ColorActivity.COLOR, R.color.white);
                            mShadingColor = ContextCompat.getColor(MainActivity.this, colorId);
                        }
                    }
                }
            });*/

    @Override
    protected void onResume() {
        super.onResume();

        try {
            // Attempt to load a previously saved list
            mToDoList.readFromFile();
            adapter.notifyDataSetChanged();
            //displayList();
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

        mItemNumTodayTextView.setText(numItems);
        mItemNumOverdueTextView.setText(numItems);
        mItemNumTextView.setText(numItems);
    }

    @Override
    protected void onPause() {
        super.onPause();

        try {
            // Save list for later
            mToDoList.saveToFile();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void addButtonClick() {

        // Ignore any leading or trailing spaces
        String taskDescription = mItemEditText.getText().toString().trim();

        // Clear the EditText so it's ready for another item
        mItemEditText.setText("");

        // Add the item to the list and display it
        if (taskDescription.length() > 0) {
            mToDoList.addItem(new Task(taskDescription, LocalDate.of(2000,11,6),1,1));
            //displayList();
            adapter.notifyDataSetChanged();
            displayNum();
        }
    }

    /*private void displayList() {

        // Display a numbered list of items
        StringBuffer itemText = new StringBuffer();
        Task[] items = mToDoList.getItems();
        for (int i = 0; i < items.length; i++) {
            itemText.append(i + 1).append(". ").append(items[i].getDesc()).append("\n");
        }

        mItemListTextView.setText(itemText);
    }*/

    private void clearButtonClick() {
        mToDoList.clear();
        try {
            // Save list for later
            mToDoList.saveToFile();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        //displayList();
        adapter.notifyDataSetChanged();
        displayNum();
    }
}