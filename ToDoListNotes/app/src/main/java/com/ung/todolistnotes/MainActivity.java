package com.ung.todolistnotes;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private ToDoList mToDoList;
    private EditText mItemEditText;
    private TextView mItemListTextView;
    private TextView mItemNumTodayTextView;
    private TextView mItemNumOverdueTextView;
    private TextView mItemNumTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mItemEditText = findViewById(R.id.todo_item);
        mItemListTextView = findViewById(R.id.item_list);
        mItemNumTodayTextView = findViewById(R.id.due_today);
        mItemNumOverdueTextView = findViewById(R.id.overdue);

        findViewById(R.id.add_button).setOnClickListener(view -> addButtonClick());
        findViewById(R.id.clear_button).setOnClickListener(view -> clearButtonClick());

        mToDoList = new ToDoList(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        try {
            // Attempt to load a previously saved list
            mToDoList.readFromFile();
            displayList();
            displayNum();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
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
        String item = mItemEditText.getText().toString().trim();

        // Clear the EditText so it's ready for another item
        //mItemEditText.setText("");

        // Add the item to the list and display it
        if (item.length() > 0) {
            mToDoList.addItem(item);
            displayList();
            displayNum();
        }
    }

    private void displayNum() {
        String[] items = mToDoList.getItems();
        int u = items.length;
        String numItems = Integer.toString(u);

        mItemNumTodayTextView.setText(numItems);
        mItemNumOverdueTextView.setText(numItems);
    }

    private void displayList() {

        // Display a numbered list of items
        StringBuffer itemText = new StringBuffer();
        String[] items = mToDoList.getItems();
        for (int i = 0; i < items.length; i++) {
            itemText.append(i + 1).append(". ").append(items[i]).append("\n");
        }

        mItemListTextView.setText(itemText);
    }

    private void clearButtonClick() {
        mToDoList.clear();
        displayList();
        displayNum();
    }
}