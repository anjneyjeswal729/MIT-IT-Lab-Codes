package com.ssc.q1;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private RadioGroup rg1, rg2, rg3, rg4;
    private Button b1;

    // Notification channel ID
    private static final String CHANNEL_ID = "quiz_notification_channel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the radio groups and button
        rg1 = findViewById(R.id.radioGroup1);
        rg2 = findViewById(R.id.radioGroup2);
        rg3 = findViewById(R.id.radioGroup3);
        rg4 = findViewById(R.id.radioGroup4);
        b1 = findViewById(R.id.button);

        // Edge-to-edge setup (optional, depending on your use case)
        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Create Notification Channel (Required for Android 8.0 and above)
        createNotificationChannel();

        // Set onClickListener for the Submit button
        b1.setOnClickListener(v -> {
            int score = 0;

            // Check answer for question 1
            int selectedId1 = rg1.getCheckedRadioButtonId();
            if (selectedId1 != -1) {
                RadioButton selectedAnswer1 = findViewById(selectedId1);
                if (selectedAnswer1.getText().toString().equals("b) Paris")) {
                    score++;
                }
            }

            // Check answer for question 2
            int selectedId2 = rg2.getCheckedRadioButtonId();
            if (selectedId2 != -1) {
                RadioButton selectedAnswer2 = findViewById(selectedId2);
                if (selectedAnswer2.getText().toString().equals("b) Mars")) {
                    score++;
                }
            }

            // Check answer for question 3
            int selectedId3 = rg3.getCheckedRadioButtonId();
            if (selectedId3 != -1) {
                RadioButton selectedAnswer3 = findViewById(selectedId3);
                if (selectedAnswer3.getText().toString().equals("c) William Shakespeare")) {
                    score++;
                }
            }

            // Check answer for question 4
            int selectedId4 = rg4.getCheckedRadioButtonId();
            if (selectedId4 != -1) {
                RadioButton selectedAnswer4 = findViewById(selectedId4);
                if (selectedAnswer4.getText().toString().equals("c) Lion")) {
                    score++;
                }
            }

            // Display score in an AlertDialog
            int finalScore = score;
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Conform")
                    .setMessage("Are you sure??") // Show score
                    .setPositiveButton("OK", (dialog, which) -> {
                        // Send notification with the score
                        sendScoreNotification(finalScore);

                        // Send score to next activity
                        Intent intent = new Intent(MainActivity.this, ExplicitIntend.class);
                        intent.putExtra("SCORE", finalScore);
                        startActivity(intent);
                    })
                    .setNegativeButton("Cancel", (dialog, which) -> {
                    })
                    .show();
        });
    }

    protected void onResume() {
        super.onResume();

        // Clear all selected radio buttons when MainActivity resumes
        rg1.clearCheck();
        rg2.clearCheck();
        rg3.clearCheck();
        rg4.clearCheck();
    }

    // Create Notification Channel (Required for Android 8.0 and above)
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Quiz Notifications";
            String description = "Notifications for quiz scores";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            // Register the channel with the system
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }

    // Send score notification
    private void sendScoreNotification(int score) {
        // Initialize the NotificationManager
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Check if the NotificationManager is not null
        if (notificationManager != null) {
            // Create the notification
            Notification notification = null;

            // Check if Android version is Oreo (API level 26) or higher for notification channel
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                notification = new Notification.Builder(this, CHANNEL_ID)
                        .setContentTitle("Quiz Completed!")
                        .setContentText("Your score is: " + score)
                        .setSmallIcon(R.drawable.ic_launcher_foreground)  // Use a valid icon
                        .setPriority(Notification.PRIORITY_DEFAULT)  // Optional: Set priority
                        .build();
            }

            // If notification is created successfully, send it
            if (notification != null) {
                notificationManager.notify(1, notification);
            } else {
                Log.e("Notification", "Notification creation failed!");
            }
        } else {
            Log.e("Notification", "NotificationManager is null!");
        }
    }

}
