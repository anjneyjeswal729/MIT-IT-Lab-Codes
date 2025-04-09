package com.ssc.at;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Invoices.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_INVOICES = "invoices";
    private static final String TABLE_PRODUCTS = "products";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_DETAILS = "details";
    private static final String COLUMN_TOTAL = "total";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createInvoicesTable = "CREATE TABLE " + TABLE_INVOICES + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_DETAILS + " TEXT, " +
                COLUMN_TOTAL + " REAL)";
        db.execSQL(createInvoicesTable);

        String createProductsTable = "CREATE TABLE IF NOT EXISTS "+TABLE_PRODUCTS+" (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL, " +
                "price REAL NOT NULL)";
        db.execSQL(createProductsTable);

        // Add initial hardcoded products
        db.execSQL("INSERT INTO products (name, price) VALUES ('Apple', 1.5)");
        db.execSQL("INSERT INTO products (name, price) VALUES ('Banana', 0.5)");
        db.execSQL("INSERT INTO products (name, price) VALUES ('Milk', 2.0)");
        Log.d("dd", "Creatd anf inserted ");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INVOICES);
        db.execSQL("DROP TABLE IF EXISTS products");
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
    public ArrayList<Product> getAllProducts() {
        ArrayList<Product> products = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_PRODUCTS, null);

        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                double price = cursor.getDouble(cursor.getColumnIndexOrThrow("price"));
                products.add(new Product(name, price));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return products;
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
