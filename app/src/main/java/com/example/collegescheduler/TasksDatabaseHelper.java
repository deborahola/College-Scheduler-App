package com.example.collegescheduler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class TasksDatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "tasks.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "all_tasks";

    private static final String COLUMN_ID = "id";

    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_DUEDATE = "due_date";
    // private static final String COLUMN_DAYOFWEEK = "day_of_week";
    private static final String COLUMN_COURSE = "course";
    private static final String COLUMN_MOREINFO = "more_info";
    private static final String COLUMN_PRIORITY = "priority";
    private static final String COLUMN_STATUS = "status";

    public TasksDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

//    @Override
//    public void onCreate(SQLiteDatabase db) {
//        String query = "CREATE TABLE " + TABLE_NAME +
//                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                COLUMN_TITLE + " TEXT, " +
//                COLUMN_DUEDATE + " TEXT, " +
//                COLUMN_DAYOFWEEK + " TEXT, " +
//                COLUMN_COURSE + " TEXT, " +
//                COLUMN_MOREINFO + " TEXT, " +
//                COLUMN_PRIORITY + " TEXT, " +
//                COLUMN_STATUS + " TEXT);";
//        db.execSQL(query);
//    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITLE + " TEXT, " +
                COLUMN_DUEDATE + " TEXT, " +
                COLUMN_COURSE + " TEXT, " +
                COLUMN_MOREINFO + " TEXT, " +
                COLUMN_PRIORITY + " TEXT, " +
                COLUMN_STATUS + " TEXT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

//    void addTask(String title, String due_date, String day_of_week, String course, String more_info, String priority, String status) {
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues cv = new ContentValues();
//
//        cv.put(COLUMN_TITLE, title);
//        cv.put(COLUMN_DUEDATE, due_date);
//        cv.put(COLUMN_DAYOFWEEK, day_of_week);
//        cv.put(COLUMN_COURSE, course);
//        cv.put(COLUMN_MOREINFO, more_info);
//        cv.put(COLUMN_PRIORITY, priority);
//        cv.put(COLUMN_STATUS, status);
//
//        long result = db.insert(TABLE_NAME,null, cv);
//
//        if (result == -1) {
//            Toast.makeText(context, "Failed to Add Task.", Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(context, "Task Added Successfully!", Toast.LENGTH_SHORT).show();
//        }
//
//        db.close();
//    }

    void addTask(String title, String due_date, String course, String more_info, String priority, String status) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_TITLE, title);
        cv.put(COLUMN_DUEDATE, due_date);
        cv.put(COLUMN_COURSE, course);
        cv.put(COLUMN_MOREINFO, more_info);
        cv.put(COLUMN_PRIORITY, priority);
        cv.put(COLUMN_STATUS, status);

        long result = db.insert(TABLE_NAME,null, cv);

        if (result == -1) {
            Toast.makeText(context, "Failed to Add Task.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Task Added Successfully!", Toast.LENGTH_SHORT).show();
        }

        db.close();
    }

    public List<TaskModel> getAllTasks() {

        List<TaskModel> taskList = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
            String title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE));
            String due_date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DUEDATE));
            // String day_of_week = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DAYOFWEEK));
            String course = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_COURSE));
            String more_info = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MOREINFO));
            String priority = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRIORITY));
            String status = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STATUS));

//            TaskModel taskModel = new TaskModel(id, title, due_date, day_of_week, course, more_info, priority, status);
            TaskModel taskModel = new TaskModel(id, title, due_date, course, more_info, priority, status);
            taskList.add(taskModel);
        }

        cursor.close();
        db.close();

        return taskList;
    }

//    void updateTask(int id, String title, String due_date, String day_of_week, String course, String more_info, String priority, String status) {
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues cv = new ContentValues();
//
//        cv.put(COLUMN_TITLE, title);
//        cv.put(COLUMN_DUEDATE, due_date);
//        cv.put(COLUMN_DAYOFWEEK, day_of_week);
//        cv.put(COLUMN_COURSE, course);
//        cv.put(COLUMN_MOREINFO, more_info);
//        cv.put(COLUMN_PRIORITY, priority);
//        cv.put(COLUMN_STATUS, status);
//
//        long result = db.update(TABLE_NAME, cv, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
//
//        if (result == -1) {
//            Toast.makeText(context, "Failed to Update Task.", Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(context, "Task Updated Successfully!", Toast.LENGTH_SHORT).show();
//        }
//
//        db.close();
//    }

    void updateTask(int id, String title, String due_date, String course, String more_info, String priority, String status) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_TITLE, title);
        cv.put(COLUMN_DUEDATE, due_date);
        cv.put(COLUMN_COURSE, course);
        cv.put(COLUMN_MOREINFO, more_info);
        cv.put(COLUMN_PRIORITY, priority);
        cv.put(COLUMN_STATUS, status);

        long result = db.update(TABLE_NAME, cv, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});

        if (result == -1) {
            Toast.makeText(context, "Failed to Update Task.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Task Updated Successfully!", Toast.LENGTH_SHORT).show();
        }

        db.close();
    }

    public TaskModel getTaskByID(int id) {

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(id)});

        TaskModel taskModel = null;

        if (cursor.moveToFirst()) {
            String title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE));
            String due_date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DUEDATE));
            // String day_of_week = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DAYOFWEEK));
            String course = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_COURSE));
            String more_info = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MOREINFO));
            String priority = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRIORITY));
            String status = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STATUS));

//            taskModel = new TaskModel(id, title, due_date, day_of_week, course, more_info, priority, status);
            taskModel = new TaskModel(id, title, due_date, course, more_info, priority, status);
        }

        cursor.close();
        db.close();

        return taskModel;
    }

    void deleteTask(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        if (result == -1) {
            Toast.makeText(context, "Failed to Delete Task.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Task Deleted Successfully.", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }

}
