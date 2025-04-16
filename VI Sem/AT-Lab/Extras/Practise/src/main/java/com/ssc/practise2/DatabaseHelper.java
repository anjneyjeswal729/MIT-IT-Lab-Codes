package com.ssc.practise2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Invoices.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_INVOICES = "invoices";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_DETAILS = "details";
    private static final String COLUMN_TOTAL = "total";
    public DatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query= "CREATE TABLE "+TABLE_INVOICES+" ("+" id INTEGER PRIMARY KEY AUTOINCREMENT, DETAILS TEXT, TOTAL NUMBER);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INVOICES);
        onCreate(db);
    }

    void insert(int id, String text, int total){
        SQLiteDatabase db =  this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("DETAILS",text);
        cv.put("TOTAL",total);
        db.insert("invoices",null,cv);
//        db.close();
    }
}
