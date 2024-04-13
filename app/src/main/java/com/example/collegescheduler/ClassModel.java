package com.example.collegescheduler;

public class ClassModel {

    private int id;
    private String title;
    private String courseName;
    private String days;
    private String time;
    private String location;
    private String instructor;

    public ClassModel(int id, String title, String courseName, String days, String time, String location, String instructor) {
        this.id = id;
        this.title = title;
        this.courseName = courseName;
        this.days = days;
        this.time = time;
        this.location = location;
        this.instructor = instructor;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getDays() {
        return days;
    }

    public String getTime() {
        return time;
    }

    public String getLocation() {
        return location;
    }

    public String getInstructor() {
        return instructor;
    }

}