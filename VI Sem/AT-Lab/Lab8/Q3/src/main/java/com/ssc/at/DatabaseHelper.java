package com.ssc.at;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Clinic.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_USERS = "users";
    private static final String TABLE_DOCTORS = "doctors";
    private static final String TABLE_APPOINTMENTS = "appointments";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Users Table
        db.execSQL("CREATE TABLE " + TABLE_USERS + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "username TEXT NOT NULL, " +
                "password TEXT NOT NULL)");

        // Doctors Table
        db.execSQL("CREATE TABLE " + TABLE_DOCTORS + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL, " +
                "specialization TEXT NOT NULL)");

        // Appointments Table
        db.execSQL("CREATE TABLE " + TABLE_APPOINTMENTS + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "doctor_id INTEGER NOT NULL, " +
                "time TEXT NOT NULL, " +
                "FOREIGN KEY(doctor_id) REFERENCES doctors(id))");

        // Insert hardcoded users
        db.execSQL("INSERT INTO " + TABLE_USERS + " (username, password) VALUES ('user1', 'password1')");
        db.execSQL("INSERT INTO " + TABLE_USERS + " (username, password) VALUES ('user2', 'password2')");

        // Insert hardcoded doctors
        db.execSQL("INSERT INTO " + TABLE_DOCTORS + " (name, specialization) VALUES ('Dr. Smith', 'Cardiologist')");
        db.execSQL("INSERT INTO " + TABLE_DOCTORS + " (name, specialization) VALUES ('Dr. Emily', 'Dermatologist')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DOCTORS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_APPOINTMENTS);
        onCreate(db);
    }

    public boolean validateUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE username = ? AND password = ?", new String[]{username, password});
        boolean isValid = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return isValid;
    }

    public boolean isDoctorAvailable(int doctorId, String time) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_APPOINTMENTS + " WHERE doctor_id = ? AND time = ?", new String[]{String.valueOf(doctorId), time});
        boolean isAvailable = cursor.getCount() == 0;
        cursor.close();
        db.close();
        return isAvailable;
    }

    public boolean bookAppointment(int doctorId, String time) {
        if (!isDoctorAvailable(doctorId, time)) {
            return false; // Doctor is not available
        }

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("doctor_id", doctorId);
        values.put("time", time);
        long result = db.insert(TABLE_APPOINTMENTS, null, values);
        db.close();
        return result != -1;
    }
}
