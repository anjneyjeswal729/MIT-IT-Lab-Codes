package com.ssc.q1;


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
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ExplicitIntend extends AppCompatActivity {
    private static final String CHANNEL_ID = "quiz_notification_channel";

    private TextView t1;
    private Button b1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        b1 = findViewById(R.id.button2);
        t1 = findViewById(R.id.textView4);
        int score = getIntent().getIntExtra("SCORE", 0);

        // Ensure the notification channel is created before sending notifications
        createNotificationChannel();

        // Show score in the TextView
        t1.setText(score + " out of 4");

        // Send notification with the score
        sendScoreNotification(score);

        b1.setOnClickListener(v -> {
            // Close the current activity and return to the previous activity (MainActivity)
            finish();
        });
    }

    private void createNotificationChannel() {
        // Create Notification Channel only on devices with Android 8.0 (API 26) or higher
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
                        .setSmallIcon(R.drawable.ic_launcher_foreground)  // Use a valid icon here
                        .setPriority(Notification.PRIORITY_DEFAULT)  // Optional: Set priority
                        .build();
            } else {
                // For devices below Android 8.0, do not use a channel and use the default notification behavior
                notification = new Notification.Builder(this)
                        .setContentTitle("Quiz Completed!")
                        .setContentText("Your score is: " + score)
                        .setSmallIcon(R.drawable.ic_launcher_foreground)  // Use a valid icon here
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
