package com.example.collegescheduler;

public class ExamModel {

    private int id;
    private String title;
    private String date;
    private String course;
    private String time;
    private String location;

    public ExamModel(int id, String title, String date, String course, String time, String location) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.course = course;
        this.time = time;
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getCourse() {
        return course;
    }

    public String getTime() {
        return time;
    }

    public String getLocation() {
        return location;
    }

}
