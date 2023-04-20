package com.ung.todolistnotes;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ToDoList {

    public static final String FILENAME = "todolist.txt";

    private Context mContext;
    private List<Task> mTaskList;

    public ToDoList(Context context) {
        mContext = context;
        mTaskList = new ArrayList<>();
    }

    public void addItem(Task item) throws IllegalArgumentException {
        mTaskList.add(item);
    }

    public Task[] getItems() {
        Task[] items = new Task[mTaskList.size()];
        return mTaskList.toArray(items);
    }

    public void clear() {
        mTaskList.clear();
    }

    public void saveToFile() throws IOException {

        // Write list to file in internal storage
        FileOutputStream outputStream = mContext.openFileOutput(FILENAME, Context.MODE_PRIVATE);
        writeListToStream(outputStream);
    }

    public void readFromFile() throws IOException {

        // Read in list from file in internal storage
        FileInputStream inputStream = mContext.openFileInput(FILENAME);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            mTaskList.clear();

            String line;
            while ((line = reader.readLine()) != null) {
                String[] taskFields = line.split(",");  //taskFields(description, etc.) are separated by commas. Tasks themselves are separated by line.

                //Task newTask = new Task(taskFields[0], taskFields[1], taskFields[2], taskFields[3]);
                mTaskList.add(new Task("task", LocalDate.of(2000,1,1),1,1)); //exchange this once code works properly

                /*String fieldsConcatenated = ""; //for testing.
                for(int i = 0; i < taskFields.length; i++)
                    fieldsConcatenated += taskFields[i] + "    ";
                System.out.println(fieldsConcatenated);*/
            }
        }
        catch (FileNotFoundException ex) {
            // Ignore
        }
    }

    private void writeListToStream(FileOutputStream outputStream) {
        PrintWriter writer = new PrintWriter(outputStream);
        for (Task task : mTaskList) {
            writer.println(task.getDesc() +","+ task.getDate()+","+ task.getPriority()+","+ task.getCategory());
            System.out.println(task.getDesc() +","+ task.getDate()+","+ task.getPriority()+","+ task.getCategory());
        }
        writer.close();
    }
}
