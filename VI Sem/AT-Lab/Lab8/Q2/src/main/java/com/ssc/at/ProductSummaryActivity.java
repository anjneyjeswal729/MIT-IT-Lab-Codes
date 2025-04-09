package com.ssc.at;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ProductSummaryActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_summary);

        TextView txtSummary = findViewById(R.id.txt_product_summary);

        databaseHelper = new DatabaseHelper(this);

        ArrayList<Product> allProducts = databaseHelper.getAllProducts();
        if (allProducts.isEmpty()) {
            txtSummary.setText("No products available.");
            return;
        }

        int totalProducts = allProducts.size();
        Product maxPricedProduct = allProducts.get(0);
        Product minPricedProduct = allProducts.get(0);

        for (Product product : allProducts) {
            if (product.getPrice() > maxPricedProduct.getPrice()) {
                maxPricedProduct = product;
            }
            if (product.getPrice() < minPricedProduct.getPrice()) {
                minPricedProduct = product;
            }
        }

        StringBuilder summary = new StringBuilder();
        summary.append("Total Products Available: ").append(totalProducts).append("\n\n")
                .append("Maximum Priced Product:\n")
                .append(maxPricedProduct.getName())
                .append(" - $").append(maxPricedProduct.getPrice()).append("\n\n")
                .append("Minimum Priced Product:\n")
                .append(minPricedProduct.getName())
                .append(" - $").append(minPricedProduct.getPrice());

        txtSummary.setText(summary.toString());
    }
}
