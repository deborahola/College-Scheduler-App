package com.example.collegescheduler;

public class TaskModel {

    private int id;
    private String title;
    private String dueDate;
    // private String dayOfWeek;
    private String course;
    private String moreInfo;
    private String priority;
    private String status;

//    public TaskModel(int id, String title, String dueDate String dayOfWeek, String course, String moreInfo, String priority, String status) {
//        this.id = id;
//        this.title = title;
//        this.dueDate = dueDate;
//        this.dayOfWeek = dayOfWeek;
//        this.course = course;
//        this.moreInfo = moreInfo;
//        this.priority = priority;
//        this.status = status;
//    }

    public TaskModel(int id, String title, String dueDate, String course, String moreInfo, String priority, String status) {
        this.id = id;
        this.title = title;
        this.dueDate = dueDate;
        this.course = course;
        this.moreInfo = moreInfo;
        this.priority = priority;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDueDate() {
        return dueDate;
    }

//    public String getDayOfWeek() {
//        return dayOfWeek;
//    }

    public String getCourse() {
        return course;
    }

    public String getMoreInfo() {
        return moreInfo;
    }

    public String getPriority() {
        return priority;
    }

    public String getStatus() {
        return status;
    }

}