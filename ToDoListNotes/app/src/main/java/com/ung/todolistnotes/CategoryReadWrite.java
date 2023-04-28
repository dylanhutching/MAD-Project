package com.ung.todolistnotes;

import android.content.Context;
import android.util.Log;

import androidx.core.content.ContextCompat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CategoryReadWrite {
    //This file below will store only names associated with the categories, because there are a limited number of colors available we dont need to store the color alongside it.
    //********In the new category activity we can check if a color has already been used before leaving the activity.
    public static final String FILENAME = "categories.txt";

    private Context mContext;
    private List<Category> mCategoryList;

    public CategoryReadWrite(Context context) {
        mContext = context;
        mCategoryList = new ArrayList<>();
    }

    public List<Category> getCategoryList() {
        return mCategoryList;
    }

    public void addItem(String categoryTitle, int color) throws IllegalArgumentException {
        mCategoryList.add(new Category(categoryTitle, color));
        try {
            saveToFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Category[] getCategories() {
        List<Category> tempCategoryList = new ArrayList<>();
        tempCategoryList.add(new Category("Personal", ContextCompat.getColor(mContext, R.color.black))); // add new colors in greyscale
        tempCategoryList.add(new Category("School", ContextCompat.getColor(mContext, R.color.grey_dark)));
        tempCategoryList.add(new Category("Work", ContextCompat.getColor(mContext, R.color.grey)));
        tempCategoryList.addAll(mCategoryList);

        Category[] items = new Category[tempCategoryList.size()];

        //for(Category item: tempCategoryList.toArray(items))
            //Log.i("AndroidRuntime", String.valueOf(item.getColorId())); //this line displays tasks as they are being read. FOR DEBUGGING. pro tip set logcat filter to AndroidRuntime

        return tempCategoryList.toArray(items);
    }

    private void writeListToStream(FileOutputStream outputStream) {
        PrintWriter writer = new PrintWriter(outputStream);
        for (Category category : mCategoryList) {
            writer.println(category.toString());
        }
        writer.close();
    }

    public void saveToFile() throws IOException {
        // Write list to file in internal storage
        FileOutputStream outputStream = mContext.openFileOutput(FILENAME, Context.MODE_PRIVATE);
        writeListToStream(outputStream);
    }

    public void readFromFile() throws IOException {
        mCategoryList.clear();

        // Read in list from file in internal storage
        FileInputStream inputStream = mContext.openFileInput(FILENAME);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

            String line;
            while ((line = reader.readLine()) != null) {
                //Log.i("AndroidRuntime", line);
                String[] fields = line.split("____");

                mCategoryList.add(new Category(fields[0], Integer.parseInt(fields[1])));
            }
        }
        catch (FileNotFoundException ex) {
            // Ignore
        }
    }
}
