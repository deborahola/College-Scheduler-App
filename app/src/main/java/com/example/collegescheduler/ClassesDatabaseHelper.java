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

public class ClassesDatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "classes.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "all_classes";

    private static final String COLUMN_ID = "id";

    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_COURSENAME = "course_name";
    private static final String COLUMN_DAYS = "days";
    private static final String COLUMN_TIME = "time";
    private static final String COLUMN_LOCATION = "location";
    private static final String COLUMN_INSTRUCTOR = "instructor";

    public ClassesDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITLE + " TEXT, " +
                COLUMN_COURSENAME + " TEXT, " +
                COLUMN_DAYS + " TEXT, " +
                COLUMN_TIME + " TEXT, " +
                COLUMN_LOCATION + " TEXT, " +
                COLUMN_INSTRUCTOR + " TEXT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    void addClass(String title, String course_name, String days, String time,
                  String location, String instructor) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_TITLE, title);
        cv.put(COLUMN_COURSENAME, course_name);
        cv.put(COLUMN_DAYS, days);
        cv.put(COLUMN_TIME, time);
        cv.put(COLUMN_LOCATION, location);
        cv.put(COLUMN_INSTRUCTOR, instructor);

        long result = db.insert(TABLE_NAME,null, cv);

        if (result == -1) {
            Toast.makeText(context, "Failed to Add Class.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Class Added Successfully!", Toast.LENGTH_SHORT).show();
        }

        db.close();
    }

    public List<ClassModel> getAllClasses() { // like "reading" the data?... CRUD

        List<ClassModel> classList = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
            String title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE));
            String course_name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_COURSENAME));
            String days = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DAYS));
            String time = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TIME));
            String location = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LOCATION));
            String instructor = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_INSTRUCTOR));

            ClassModel classModel = new ClassModel(id, title, course_name, days, time, location, instructor);
            classList.add(classModel);
        }

        cursor.close();
        db.close();

        return classList;
    }

    void updateClass(int id, String title, String course_name, String days, String time,
                     String location, String instructor) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_TITLE, title);
        cv.put(COLUMN_COURSENAME, course_name);
        cv.put(COLUMN_DAYS, days);
        cv.put(COLUMN_TIME, time);
        cv.put(COLUMN_LOCATION, location);
        cv.put(COLUMN_INSTRUCTOR, instructor);

        long result = db.update(TABLE_NAME, cv, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});

        if (result == -1) {
            Toast.makeText(context, "Failed to Update Class.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Class Updated Successfully!", Toast.LENGTH_SHORT).show();
        }

        db.close();
    }

    public ClassModel getClassByID(int id) {

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(id)});

        ClassModel classModel = null;

        if (cursor.moveToFirst()) {
            String title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE));
            String course_name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_COURSENAME));
            String days = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DAYS));
            String time = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TIME));
            String location = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LOCATION));
            String instructor = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_INSTRUCTOR));

            classModel = new ClassModel(id, title, course_name, days, time, location, instructor);
        }

        cursor.close();
        db.close();

        return classModel;
    }

    void deleteClass(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        if (result == -1) {
            Toast.makeText(context, "Failed to Delete Class.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Class Deleted Successfully.", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }

}