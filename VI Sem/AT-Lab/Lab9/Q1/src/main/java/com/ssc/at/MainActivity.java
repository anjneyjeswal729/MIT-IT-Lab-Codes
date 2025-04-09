package com.ssc.at;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText etName, etAge;
    private RadioGroup genderGroup, q1Group;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        etName = findViewById(R.id.etName);
        etAge = findViewById(R.id.etAge);
        genderGroup = findViewById(R.id.genderGroup);
        q1Group = findViewById(R.id.q1Group);
        btnSubmit = findViewById(R.id.btnSubmit);

        // Set up submit button listener
        btnSubmit.setOnClickListener(v -> handleSubmit());
    }

    private void handleSubmit() {
        String name = etName.getText().toString().trim();
        String age = etAge.getText().toString().trim();

        // Validate inputs
        if (name.isEmpty() || age.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        int genderId = genderGroup.getCheckedRadioButtonId();
        if (genderId == -1) {
            Toast.makeText(this, "Please select your gender", Toast.LENGTH_SHORT).show();
            return;
        }

        int q1Id = q1Group.getCheckedRadioButtonId();
        if (q1Id == -1) {
            Toast.makeText(this, "Please answer the survey question", Toast.LENGTH_SHORT).show();
            return;
        }

        // Get selected gender
        RadioButton selectedGender = findViewById(genderId);
        String gender = selectedGender.getText().toString();

        // Get survey answer
        RadioButton selectedQ1 = findViewById(q1Id);
        String q1Answer = selectedQ1.getText().toString();

        // Display the results in a dialog
        showResultsDialog(name, age, gender, q1Answer);
    }

    private void showResultsDialog(String name, String age, String gender, String q1Answer) {
        String message = "Survey Results:\n\n" +
                "Name: " + name + "\n" +
                "Age: " + age + "\n" +
                "Gender: " + gender + "\n" +
                "Q1: Do you like Android Development? - " + q1Answer;

        new AlertDialog.Builder(this)
                .setTitle("Survey Completed")
                .setMessage(message)
                .setPositiveButton("OK", null)
                .show();
    }
}
