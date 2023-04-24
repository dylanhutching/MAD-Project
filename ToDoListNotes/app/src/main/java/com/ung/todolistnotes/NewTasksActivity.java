package com.ung.todolistnotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class NewTasksActivity extends AppCompatActivity {

    private EditText mTitleEditText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_tasks);

        mTitleEditText = findViewById(R.id.task_title);
        findViewById(R.id.add_task_button).setOnClickListener(view -> addTaskClick());

        //Below im trying to put the selected task description in mtitleedittext.
        /*int selected_task_index = getIntent().getIntExtra("selected_task_index", -1);

        if(selected_task_index == -1)
            ;
        else{
            //read task from that line in the file.
            mTitleEditText.setText();
        }*/

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
        finish();
        Toast.makeText(this, "New Task added", Toast.LENGTH_SHORT).show();
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