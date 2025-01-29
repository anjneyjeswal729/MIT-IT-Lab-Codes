package com.ssc.qu1;

import android.os.Bundle;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.ScrollView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridLayout gridLayout = findViewById(R.id.gridLayout);

        // Example array of image resource IDs to be displayed in the grid
        int[] imageResIds = {
                R.drawable.sample_image, R.drawable.sample_image, R.drawable.sample_image,
                R.drawable.sample_image, R.drawable.sample_image, R.drawable.sample_image,
                R.drawable.sample_image, R.drawable.sample_image, R.drawable.sample_image,
                R.drawable.sample_image, R.drawable.sample_image, R.drawable.sample_image,
                R.drawable.sample_image, R.drawable.sample_image, R.drawable.sample_image,
                R.drawable.sample_image, R.drawable.sample_image, R.drawable.sample_image,
                R.drawable.sample_image, R.drawable.sample_image
        };

        // Dynamically add ImageViews to GridLayout
        for (int i = 0; i < imageResIds.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(new GridLayout.LayoutParams());
            imageView.setImageResource(imageResIds[i]);

            imageView.setPadding(8, 8, 8, 8);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP); // Optional, to ensure images fit well
            gridLayout.addView(imageView);
        }
    }
}
