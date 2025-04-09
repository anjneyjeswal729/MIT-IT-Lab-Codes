package com.ssc.at;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Survey.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_QUESTIONS = "questions";
    private static final String TABLE_RESPONSES = "responses";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Questions Table
        db.execSQL("CREATE TABLE " + TABLE_QUESTIONS + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "question TEXT NOT NULL)");

        // Responses Table
        db.execSQL("CREATE TABLE " + TABLE_RESPONSES + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "question_id INTEGER NOT NULL, " +
                "response TEXT NOT NULL, " +
                "FOREIGN KEY(question_id) REFERENCES questions(id))");

        // Insert Hardcoded Questions
        db.execSQL("INSERT INTO " + TABLE_QUESTIONS + " (question) VALUES ('What is your favorite color?')");
        db.execSQL("INSERT INTO " + TABLE_QUESTIONS + " (question) VALUES ('How satisfied are you with our service?')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESPONSES);
        onCreate(db);
    }

    public Cursor getQuestions() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_QUESTIONS, null);
    }

    public void saveResponse(int questionId, String response) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("question_id", questionId);
        values.put("response", response);
        db.insert(TABLE_RESPONSES, null, values);
        db.close();
    }

    public Cursor getResponses(int questionId) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT response, COUNT(*) as count FROM " + TABLE_RESPONSES +
                " WHERE question_id = ? GROUP BY response", new String[]{String.valueOf(questionId)});
    }
}
