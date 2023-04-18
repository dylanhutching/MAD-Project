package com.ung.todolistnotes;

import java.util.Date;

public class Task {

    private String desc;
    private Date date;
    private int priority;
    private int category;

    public Task(String desc, Date date, int priority, int category){
        desc = this.desc;
        date = this.date;
        priority = this.priority;
        category = this.category;
    }

    public String getDesc() {
        return desc;
    }

    public Date getDate() {
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

    public void setDate(Date date) {
        this.date = date;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
