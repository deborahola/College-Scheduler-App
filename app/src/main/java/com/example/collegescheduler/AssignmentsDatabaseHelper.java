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

public class AssignmentsDatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "assignments.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "all_assignments";

    private static final String COLUMN_ID = "id";

    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_DUEDATE = "due_date";
    private static final String COLUMN_COURSE = "course";
    private static final String COLUMN_DESCRIPTION = "description";

    public AssignmentsDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITLE + " TEXT, " +
                COLUMN_DUEDATE + " TEXT, " +
                COLUMN_COURSE + " TEXT, " +
                COLUMN_DESCRIPTION + " TEXT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    void addAssignment(String title, String due_date, String course, String description) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_TITLE, title);
        cv.put(COLUMN_DUEDATE, due_date);
        cv.put(COLUMN_COURSE, course);
        cv.put(COLUMN_DESCRIPTION, description);

        long result = db.insert(TABLE_NAME,null, cv);

        if (result == -1) {
            Toast.makeText(context, "Failed to Add Assignment.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Assignment Added Successfully!", Toast.LENGTH_SHORT).show();
        }

        db.close();
    }

    public List<AssignmentModel> getAllAssignments() { // like "reading" the data?... CRUD

        List<AssignmentModel> assignmentList = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
            String title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE));
            String due_date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DUEDATE));
            String course = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_COURSE));
            String description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION));

            AssignmentModel assignmentModel = new AssignmentModel(id, title, due_date, course, description);
            assignmentList.add(assignmentModel);
        }

        cursor.close();
        db.close();

        return assignmentList;
    }

    void updateAssignment(int id, String title, String due_date, String course, String description) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_TITLE, title);
        cv.put(COLUMN_DUEDATE, due_date);
        cv.put(COLUMN_COURSE, course);
        cv.put(COLUMN_DESCRIPTION, description);

        long result = db.update(TABLE_NAME, cv, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});

        if (result == -1) {
            Toast.makeText(context, "Failed to Update Assignment.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Assignment Updated Successfully!", Toast.LENGTH_SHORT).show();
        }

        db.close();
    }

    public AssignmentModel getAssignmentByID(int id) {

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(id)});

        AssignmentModel assignmentModel = null;

        if (cursor.moveToFirst()) {
            String title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE));
            String due_date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DUEDATE));
            String course = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_COURSE));
            String description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION));

            assignmentModel = new AssignmentModel(id, title, due_date, course, description);
        }

        cursor.close();
        db.close();

        return assignmentModel;
    }

    void deleteAssignment(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        if (result == -1) {
            Toast.makeText(context, "Failed to Delete Assignment.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Assignment Deleted Successfully.", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }

}
