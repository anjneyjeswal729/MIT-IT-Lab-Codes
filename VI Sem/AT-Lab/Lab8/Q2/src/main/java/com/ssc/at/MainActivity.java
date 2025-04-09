package com.ssc.at;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Product> products;
    private ArrayList<Product> selectedProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout productListLayout = findViewById(R.id.product_list_layout);
        Button btnGenerateInvoice = findViewById(R.id.btn_generate_invoice);
        Button btnShowInvoices = findViewById(R.id.btn_show_invoices);

        selectedProducts = new ArrayList<>();
        initializeProducts();
        displayProducts(productListLayout);

        btnGenerateInvoice.setOnClickListener(v -> {
            if (selectedProducts.isEmpty()) {
                Toast.makeText(this, "Please select at least one product with valid quantity.", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(MainActivity.this, InvoiceActivity.class);
                intent.putParcelableArrayListExtra("selectedProducts", selectedProducts);
                startActivity(intent);
            }
        });

        btnShowInvoices.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AllInvoicesActivity.class);
            startActivity(intent);
        });
        Button btnViewProductSummary = findViewById(R.id.btn_view_product_summary);
        btnViewProductSummary.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ProductSummaryActivity.class);
            startActivity(intent);
        });
    }

    private void initializeProducts() {
        products = new ArrayList<>();
        products.add(new Product("Apple", 1.5));
        products.add(new Product("Banana", 0.5));
        products.add(new Product("Milk", 2.0));
        products.add(new Product("Bread", 1.2));
    }

    private void displayProducts(LinearLayout layout) {
        for (Product product : products) {
            View productView = getLayoutInflater().inflate(R.layout.item_product, null);
            CheckBox checkBox = productView.findViewById(R.id.checkbox_product);
            EditText quantityInput = productView.findViewById(R.id.edt_quantity);

            checkBox.setText(product.getName() + " - $" + product.getPrice());
            layout.addView(productView);

            checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    String quantityText = quantityInput.getText().toString();
                    if (quantityText.isEmpty() || Integer.parseInt(quantityText) <= 0) {
                        Toast.makeText(this, "Enter a valid quantity.", Toast.LENGTH_SHORT).show();
                        checkBox.setChecked(false);
                    } else {
                        int quantity = Integer.parseInt(quantityText);
                        product.setQuantity(quantity);
                        selectedProducts.add(product);
                    }
                } else {
                    selectedProducts.remove(product);
                }
            });
        }
    }
}
