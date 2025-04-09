package com.ssc.at;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Invoices.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_INVOICES = "invoices";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_DETAILS = "details";
    private static final String COLUMN_TOTAL = "total";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_INVOICES + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_DETAILS + " TEXT, " +
                COLUMN_TOTAL + " REAL)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INVOICES);
        onCreate(db);
    }

    public void insertInvoice(String details, double total) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DETAILS, details);
        values.put(COLUMN_TOTAL, total);
        db.insert(TABLE_INVOICES, null, values);
        db.close();
    }

    public ArrayList<String> getAllInvoices() {
        ArrayList<String> invoices = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_INVOICES, null);

        if (cursor.moveToFirst()) {
            do {
                String details = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DETAILS));
                double total = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_TOTAL));
                invoices.add(details + "\nTotal: $" + total);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return invoices;
    }
}
