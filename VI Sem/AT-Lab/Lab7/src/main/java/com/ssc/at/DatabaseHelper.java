package com.ssc.at;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database info
    private static final String DATABASE_NAME = "StudentManagement.db";
    private static final int DATABASE_VERSION = 1;

    // Table info
    private static final String TABLE_STUDENTS = "students";
    private static final String COLUMN_ROLL_NUMBER = "roll_number";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_MARKS = "marks";

    // Create table query
    private static final String CREATE_TABLE_STUDENTS = "CREATE TABLE " + TABLE_STUDENTS + "("
            + COLUMN_ROLL_NUMBER + " VARCHAR PRIMARY KEY, "
            + COLUMN_NAME + " VARCHAR NOT NULL, "
            + COLUMN_MARKS + " VARCHAR NOT NULL)";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the students table
        db.execSQL(CREATE_TABLE_STUDENTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if it exists and create a new one
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENTS);
        onCreate(db);
    }

    /**
     * Insert a new student into the database
     */
    public boolean insertStudent(String rollNumber, String name, String marks) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ROLL_NUMBER, rollNumber);
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_MARKS, marks);

        // Insert the row
        long result = db.insert(TABLE_STUDENTS, null, values);
        db.close();

        // If result is -1, insertion failed
        return result != -1;
    }

    /**
     * Update an existing student's information
     */
    public boolean updateStudent(String rollNumber, String name, String marks) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_MARKS, marks);

        // Update the row
        int result = db.update(TABLE_STUDENTS, values, COLUMN_ROLL_NUMBER + " = ?",
                new String[]{rollNumber});
        db.close();

        // If result is 0, no rows were updated
        return result > 0;
    }

    /**
     * Delete a student from the database
     */
    public boolean deleteStudent(String rollNumber) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Delete the row
        int result = db.delete(TABLE_STUDENTS, COLUMN_ROLL_NUMBER + " = ?",
                new String[]{rollNumber});
        db.close();

        // If result is 0, no rows were deleted
        return result > 0;
    }

    /**
     * Get all students from the database
     */
    public ArrayList<HashMap<String, String>> getAllStudents() {
        ArrayList<HashMap<String, String>> studentList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Query to get all students
        String selectQuery = "SELECT * FROM " + TABLE_STUDENTS + " ORDER BY " + COLUMN_ROLL_NUMBER;
        Cursor cursor = db.rawQuery(selectQuery, null);

        // Loop through all rows and add to list
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> student = new HashMap<>();

                // Safely get column indices first
                int rollNumberIndex = cursor.getColumnIndex(COLUMN_ROLL_NUMBER);
                int nameIndex = cursor.getColumnIndex(COLUMN_NAME);
                int marksIndex = cursor.getColumnIndex(COLUMN_MARKS);

                // Only add values if the column exists
                if (rollNumberIndex >= 0) {
                    student.put("rollNumber", cursor.getString(rollNumberIndex));
                }
                if (nameIndex >= 0) {
                    student.put("name", cursor.getString(nameIndex));
                }
                if (marksIndex >= 0) {
                    student.put("marks", cursor.getString(marksIndex));
                }

                studentList.add(student);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return studentList;
    }

    /**
     * Check if a roll number already exists in the database
     */
    public boolean checkRollNumberExists(String rollNumber) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_STUDENTS + " WHERE " + COLUMN_ROLL_NUMBER + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{rollNumber});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return exists;
    }

    /**
     * Get a single student by roll number
     */
    public HashMap<String, String> getStudentByRollNumber(String rollNumber) {
        HashMap<String, String> student = new HashMap<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Query to get a specific student
        String selectQuery = "SELECT * FROM " + TABLE_STUDENTS + " WHERE "
                + COLUMN_ROLL_NUMBER + " = ?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{rollNumber});

        if (cursor.moveToFirst()) {
            // Safely get column indices first
            int rollNumberIndex = cursor.getColumnIndex(COLUMN_ROLL_NUMBER);
            int nameIndex = cursor.getColumnIndex(COLUMN_NAME);
            int marksIndex = cursor.getColumnIndex(COLUMN_MARKS);

            // Only add values if the column exists
            if (rollNumberIndex >= 0) {
                student.put("rollNumber", cursor.getString(rollNumberIndex));
            }
            if (nameIndex >= 0) {
                student.put("name", cursor.getString(nameIndex));
            }
            if (marksIndex >= 0) {
                student.put("marks", cursor.getString(marksIndex));
            }
        }

        cursor.close();
        db.close();
        return student;
    }
}