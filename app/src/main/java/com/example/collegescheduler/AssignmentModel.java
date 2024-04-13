package com.example.collegescheduler;

public class AssignmentModel {

    private int id;
    private String title;
    private String dueDate;
    private String course;
    private String description;

    public AssignmentModel(int id, String title, String dueDate, String course, String description) {
        this.id = id;
        this.title = title;
        this.dueDate = dueDate;
        this.course = course;
        this.description = description;
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

    public String getCourse() {
        return course;
    }

    public String getDescription() {
        return description;
    }

}
