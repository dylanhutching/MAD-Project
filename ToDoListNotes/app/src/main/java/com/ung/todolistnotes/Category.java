package com.ung.todolistnotes;

import java.time.LocalDate;

public class Category {

    private String title;
    private int colorId;

    public Category(String title, int colorId){
        this.title = title;
        this.colorId = colorId ;
    }

    public String getTitle() {
        return title;
    }

    public int getColorId() { return colorId; }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setColorId(int colorId) {
        this.colorId = colorId;
    }

    public String toString(){
        String result = getTitle() + "____" + getColorId();
        return result;
    }
}
