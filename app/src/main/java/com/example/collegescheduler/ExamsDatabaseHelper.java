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

public class ExamsDatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "examss.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "all_exams";

    private static final String COLUMN_ID = "id";

    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_COURSE = "course";
    private static final String COLUMN_TIME = "time";
    private static final String COLUMN_LOCATION = "location";

    public ExamsDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITLE + " TEXT, " +
                COLUMN_DATE + " TEXT, " +
                COLUMN_COURSE + " TEXT, " +
                COLUMN_TIME + " TEXT, " +
                COLUMN_LOCATION + " TEXT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    void addExam(String title, String date, String course, String time,
                  String location) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_TITLE, title);
        cv.put(COLUMN_DATE, date);
        cv.put(COLUMN_COURSE, course);
        cv.put(COLUMN_TIME, time);
        cv.put(COLUMN_LOCATION, location);

        long result = db.insert(TABLE_NAME,null, cv);

        if (result == -1) {
            Toast.makeText(context, "Failed to Add Exam.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Exam Added Successfully!", Toast.LENGTH_SHORT).show();
        }

        db.close();
    }

    public List<ExamModel> getAllExams() {

        List<ExamModel> examList = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
            String title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE));
            String date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE));
            String course = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_COURSE));
            String time = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TIME));
            String location = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LOCATION));

            ExamModel examModel = new ExamModel(id, title, date, course, time, location);
            examList.add(examModel);
        }

        cursor.close();
        db.close();

        return examList;
    }

    void updateExam(int id, String title, String date, String course, String time,
                    String location) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_TITLE, title);
        cv.put(COLUMN_DATE, date);
        cv.put(COLUMN_COURSE, course);
        cv.put(COLUMN_TIME, time);
        cv.put(COLUMN_LOCATION, location);

        long result = db.update(TABLE_NAME, cv, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});

        if (result == -1) {
            Toast.makeText(context, "Failed to Update Exam.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Exam Updated Successfully!", Toast.LENGTH_SHORT).show();
        }

        db.close();
    }

    public ExamModel getExamByID(int id) {

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(id)});

        ExamModel examModel = null;

        if (cursor.moveToFirst()) {
            String title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE));
            String date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE));
            String course = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_COURSE));
            String time = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TIME));
            String location = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LOCATION));

            examModel = new ExamModel(id, title, date, course, time, location);
        }

        cursor.close();
        db.close();

        return examModel;
    }

    void deleteExam(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        if (result == -1) {
            Toast.makeText(context, "Failed to Delete Exam.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Exam Deleted Successfully.", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }

}
