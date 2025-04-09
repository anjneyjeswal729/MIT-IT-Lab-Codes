package com.ssc.at;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class InvoiceActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice);

        TextView txtInvoiceDetails = findViewById(R.id.txt_invoice_details);
        Button btnSaveInvoice = findViewById(R.id.btn_save_invoice);

        databaseHelper = new DatabaseHelper(this);

        ArrayList<Product> selectedProducts = getIntent().getParcelableArrayListExtra("selectedProducts");

        if (selectedProducts != null && !selectedProducts.isEmpty()) {
            final StringBuilder invoiceDetails = new StringBuilder();
            final double[] totalCost = {0.0}; // Use an array to make it effectively final

            for (Product product : selectedProducts) {
                double productTotal = product.getPrice() * product.getQuantity();
                totalCost[0] += productTotal;

                invoiceDetails.append(product.getName())
                        .append(" - $").append(product.getPrice())
                        .append(" x ").append(product.getQuantity())
                        .append(" = $").append(productTotal)
                        .append("\n");
            }

            invoiceDetails.append("\nTotal: $").append(totalCost[0]);
            txtInvoiceDetails.setText(invoiceDetails.toString());

            btnSaveInvoice.setOnClickListener(v -> {
                databaseHelper.insertInvoice(invoiceDetails.toString(), totalCost[0]);
                Toast.makeText(this, "Invoice saved successfully!", Toast.LENGTH_SHORT).show();
                finish(); // Return to the previous screen
            });
        } else {
            txtInvoiceDetails.setText("No products selected.");
            btnSaveInvoice.setEnabled(false);
        }
    }
}
