package com.ung.todolistnotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.Spinner;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationBarView;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class NewTasksActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, CalendarView.OnDateChangeListener {

    private EditText mTitleEditText;
    private Task task;
    private ToDoList toDoList = MainActivity.mToDoList;
    private CalendarView datePicker;
    private String desc;
    private LocalDate date;
    private int priority;
    private int category;
    private String[] priorities;
    private String[] categories = {"School", "Work", "Other"};
    private Spinner prioritySpinner;
    private Spinner categorySpinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_tasks);

        datePicker = (CalendarView) findViewById(R.id.date_picker);
        datePicker.setOnDateChangeListener(this);


        mTitleEditText = (EditText) findViewById(R.id.task_title);
        findViewById(R.id.add_task_button).setOnClickListener(view -> addTaskClick());

        //Below im trying to put the selected task description in mtitleedittext.
        /*int selected_task_index = getIntent().getIntExtra("selected_task_index", -1);

        if(selected_task_index == -1)
            ;
        else{
            //read task from that line in the file.
            mTitleEditText.setText();
        }*/
        priorities = getResources().getStringArray(R.array.priorities_array);

        prioritySpinner = findViewById(R.id.priority_picker);
        categorySpinner = findViewById(R.id.category_picker);
        prioritySpinner.setOnItemSelectedListener(this);
        categorySpinner.setOnItemSelectedListener(this);

        ArrayAdapter priorityAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, priorities);
        priorityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        prioritySpinner.setAdapter(priorityAdapter);

        ArrayAdapter categoryAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, categories);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoryAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.appbar_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.tasks:
                startActivity(new Intent(this, MainActivity.class)); //B4 changing to this, swapping activities would delete the list. With this line OnResume() is called, which reads the list like needed.
                //setContentView(R.layout.activity_main);
                Toast.makeText(this, "Tasks", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.new_category:
                setContentView(R.layout.activity_new_category);
                Toast.makeText(this, "New Category", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void addTaskClick() {
        desc = String.valueOf(mTitleEditText.getText());
        priority = prioritySpinner.getSelectedItemPosition();
        category = categorySpinner.getSelectedItemPosition();

        task = new Task(desc, date, priority, category);

        toDoList.addItem(task);
        try {
            toDoList.saveToFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        finish();
        Toast.makeText(this, "New Task added", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
        date = LocalDate.of(i, (i1+1), i2);
    }

    /*Spinner spinner = findViewById(R.id.priority_picker);
    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
      R.array.priorities_array, android.R.layout.simple_spinner_item);
adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
spinner.setAdapter(adapter);
spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String item = (String)parent.getItemAtPosition(position);
            Toast.makeText(MainActivity.this, item, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    });*/
}