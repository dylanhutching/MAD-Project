package com.ung.todolistnotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.time.LocalDate;

public class NewCategoryActivity extends AppCompatActivity {

    private CategoryReadWrite categoryReadWrite = MainActivity.mCategoryReadWrite;
    private static Context mContext;

    private EditText mTitleEditText;
    int colorId;

    public static final String COLOR = "com.ung.todolistnotes.color";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;

        setContentView(R.layout.activity_new_category);

        mTitleEditText = findViewById(R.id.category_title);
        findViewById(R.id.add_category_button).setOnClickListener(view -> addCatClick());

        colorId = ContextCompat.getColor(this, R.color.white);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.appbar_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.tasks:
                startActivity(new Intent(this, MainActivity.class));
                //setContentView(R.layout.activity_main);
                Toast.makeText(this, "Tasks", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void addCatClick() {
        /*Intent intent = new Intent();
        intent.putExtra(COLOR, colorId);
        setResult(RESULT_OK, intent);*/

        Log.i("AndroidRuntime", mTitleEditText.getText().toString());

        if(colorId == ContextCompat.getColor(this, R.color.white)){
            Toast.makeText(this, "Please Select a Color.", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(mTitleEditText.getText().toString())){
            Toast.makeText(this, "Please Enter a Title.", Toast.LENGTH_SHORT).show();
        }else{
            categoryReadWrite.addItem(mTitleEditText.getText().toString(), colorId);
            finish();
            Toast.makeText(this, "New Category added", Toast.LENGTH_SHORT).show();
        }
    }

    public void onColorSelected(View view) {
        if (view.getId() == R.id.radio_orange) {
            colorId = ContextCompat.getColor(this, R.color.orange);
        }
        if (view.getId() == R.id.radio_yellow) {
            colorId = ContextCompat.getColor(this, R.color.yellow);
        }
        else if (view.getId() == R.id.radio_red) {
            colorId = ContextCompat.getColor(this, R.color.red);
        }
        else if (view.getId() == R.id.radio_green_light) {
            colorId = ContextCompat.getColor(this, R.color.green_light);
        }
        else if (view.getId() == R.id.radio_blue) {
            colorId = ContextCompat.getColor(this, R.color.blue);
        }
        else if (view.getId() == R.id.radio_teal) {
            colorId = ContextCompat.getColor(this, R.color.teal);
        }
        else if (view.getId() == R.id.radio_blue_light) {
            colorId = ContextCompat.getColor(this, R.color.blue_light);
        }
        else if (view.getId() == R.id.radio_purple) {
            colorId = ContextCompat.getColor(this, R.color.purple);
        }
        else if (view.getId() == R.id.radio_pink) {
            colorId = ContextCompat.getColor(this, R.color.pink);
        }
    }
}