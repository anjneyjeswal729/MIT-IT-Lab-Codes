package com.ssc.l3q2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private RatingBar ratingBar;
    private CheckBox checkBox1, checkBox2, checkBox3, checkBox4, checkBox5;
    private EditText editTextFeedback;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Initialize views
        ratingBar = findViewById(R.id.ratingBar);
        checkBox1 = findViewById(R.id.checkBox);
        checkBox2 = findViewById(R.id.checkBox2);
        checkBox3 = findViewById(R.id.checkBox3);
        checkBox4 = findViewById(R.id.checkBox4);
        checkBox5 = findViewById(R.id.checkBox5);
        editTextFeedback = findViewById(R.id.editTextText);
        submitButton = findViewById(R.id.button);

        // Set up window insets for handling the system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Set the button click listener
        submitButton.setOnClickListener(v -> handleSubmit());
    }

    private void handleSubmit() {
        // Get rating
        float rating = ratingBar.getRating();

        // Get selected checkboxes
        StringBuilder selectedOptions = new StringBuilder();
        if (checkBox1.isChecked()) selectedOptions.append(checkBox1.getText().toString()).append("\n");
        if (checkBox2.isChecked()) selectedOptions.append(checkBox2.getText().toString()).append("\n");
        if (checkBox3.isChecked()) selectedOptions.append(checkBox3.getText().toString()).append("\n");
        if (checkBox4.isChecked()) selectedOptions.append(checkBox4.getText().toString()).append("\n");
        if (checkBox5.isChecked()) selectedOptions.append(checkBox5.getText().toString()).append("\n");

        // Get feedback text
        String feedbackText = editTextFeedback.getText().toString();

        // Create the feedback summary
        String feedbackSummary = "Rating: " + rating + "\n" +
                "Liked the most: \n" + selectedOptions.toString() +
                "Suggestions: \n" + feedbackText;

        // Show an AlertDialog with the feedback summary
        new AlertDialog.Builder(this)
                .setTitle("Feedback Submitted")
                .setMessage(feedbackSummary)
                .setPositiveButton("OK", (dialog, which) -> {
                    // Optionally, clear the form after submission
                    clearForm();
                })
                .show();
    }

    private void clearForm() {
        // Reset the RatingBar, CheckBoxes, and EditText
        ratingBar.setRating(0);
        checkBox1.setChecked(false);
        checkBox2.setChecked(false);
        checkBox3.setChecked(false);
        checkBox4.setChecked(false);
        checkBox5.setChecked(false);
        editTextFeedback.setText("");
    }
}
