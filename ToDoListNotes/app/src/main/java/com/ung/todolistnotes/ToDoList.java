package com.ung.todolistnotes;

import android.content.Context;
import android.util.Log;

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
                String[] taskFields = line.split(",");  //taskFields(description, date, etc.) are separated by commas. Tasks themselves are separated by line.

                //get description
                String description = taskFields[0];
                //parse duedate
                String[] dateStringSplit = taskFields[1].split("-"); //This splits the date into an array of strings. The date is saved in file with a format of "2000-11-06". Indexes equal 0==2000, 1==11, and 2==06.
                LocalDate dueDate = LocalDate.of(Integer.parseInt(dateStringSplit[0]), Integer.parseInt(dateStringSplit[1]), Integer.parseInt(dateStringSplit[2]));   //This line creates a new LocalDate called dueDate by parsing the String[] above as integers.
                //parse priority
                int priority = Integer.parseInt(taskFields[2]);
                //parse category
                int category = Integer.parseInt(taskFields[3]);

                //pass above fields to create new task. Then add task to list.
                Task newTask = new Task(description, dueDate, priority, category);
                mTaskList.add(newTask);
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
            //Log.i("Task Written" ,task.getDesc() + "," + task.getDate() + "," + task.getPriority() + "," + task.getCategory());
        }
        writer.close();
    }
}
