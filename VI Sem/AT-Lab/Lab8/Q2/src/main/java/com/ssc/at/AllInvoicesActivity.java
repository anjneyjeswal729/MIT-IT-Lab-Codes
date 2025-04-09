package com.ssc.at;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class AllInvoicesActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_invoices);

        ListView listViewInvoices = findViewById(R.id.list_view_invoices);

        databaseHelper = new DatabaseHelper(this);

        ArrayList<String> allInvoices = databaseHelper.getAllInvoices();

        if (allInvoices.isEmpty()) {
            Toast.makeText(this, "No invoices found.", Toast.LENGTH_SHORT).show();
        } else {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_list_item_1,
                    allInvoices
            );
            listViewInvoices.setAdapter(adapter);
        }
    }
}
