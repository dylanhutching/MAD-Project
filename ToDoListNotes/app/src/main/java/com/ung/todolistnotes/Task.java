package com.ung.todolistnotes;

import java.time.LocalDate;

public class Task {

    private String desc;
    private LocalDate date; //Switched to use LocalDate for ease of use.
    private int priority;
    private int category;

    public Task(String desc, LocalDate date, int priority, int category){
        this.desc = desc;
        this.date = date ;
        this.priority = priority;
        this.category = category;
    }

    public String getDesc() {
        return desc;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getCategory() {
        return category;
    }

    public int getPriority() {
        return priority;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String toString(){
        String result = getDesc() + "____" + getDate() + "____" + getPriority() + "____" + getCategory();
        return result;
    }
}
