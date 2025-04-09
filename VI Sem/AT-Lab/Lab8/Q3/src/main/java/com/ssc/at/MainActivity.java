package com.ssc.at;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new DatabaseHelper(this);

        EditText edtUsername = findViewById(R.id.edt_username);
        EditText edtPassword = findViewById(R.id.edt_password);
        EditText edtDoctorId = findViewById(R.id.edt_doctor_id);
        EditText edtTime = findViewById(R.id.edt_time);
        Button btnLoginBook = findViewById(R.id.btn_login_book);

        btnLoginBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edtUsername.getText().toString();
                String password = edtPassword.getText().toString();
                int doctorId;
                String time = edtTime.getText().toString();

                try {
                    doctorId = Integer.parseInt(edtDoctorId.getText().toString());
                } catch (NumberFormatException e) {
                    Toast.makeText(MainActivity.this, "Invalid Doctor ID.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (databaseHelper.validateUser(username, password)) {
                    boolean success = databaseHelper.bookAppointment(doctorId, time);
                    if (success) {
                        Toast.makeText(MainActivity.this, "Appointment booked successfully!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Doctor is not available at the selected time.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Invalid login credentials.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
