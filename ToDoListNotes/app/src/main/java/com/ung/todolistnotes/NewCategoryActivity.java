package com.ung.todolistnotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.time.LocalDate;

public class NewCategoryActivity extends AppCompatActivity {

    private EditText mTitleEditText;
    int colorId = R.color.white;

    public static final String COLOR = "com.ung.todolistnotes.color";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_category);

        mTitleEditText = findViewById(R.id.category_title);
        findViewById(R.id.add_category_button).setOnClickListener(view -> addCatClick());
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
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void addCatClick() {
        Intent intent = new Intent();
        intent.putExtra(COLOR, colorId);
        setResult(RESULT_OK, intent);
        finish();
        Toast.makeText(this, "New Category added", Toast.LENGTH_SHORT).show();
    }

    public void onColorSelected(View view) {
        if (view.getId() == R.id.radio_orange) {
            colorId = R.color.orange;
        }
        if (view.getId() == R.id.radio_yellow) {
            colorId = R.color.yellow;
        }
        else if (view.getId() == R.id.radio_red) {
            colorId = R.color.red;
        }
        else if (view.getId() == R.id.radio_green_light) {
            colorId = R.color.green_light;
        }
        else if (view.getId() == R.id.radio_blue) {
            colorId = R.color.blue;
        }
        else if (view.getId() == R.id.radio_teal) {
            colorId = R.color.teal;
        }
        else if (view.getId() == R.id.radio_blue_light) {
            colorId = R.color.blue_light;
        }
        else if (view.getId() == R.id.radio_purple) {
            colorId = R.color.purple;
        }
        else if (view.getId() == R.id.radio_pink) {
            colorId = R.color.pink;
        }
    }
}