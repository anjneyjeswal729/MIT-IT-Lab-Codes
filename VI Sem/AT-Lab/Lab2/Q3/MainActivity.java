package com.ssc.q3;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private Spinner spinner;
    private View mainlayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        spinner=findViewById(R.id.spinner);
        String[] colors={"Blue","Black","Red","Yellow"};
        mainlayout=findViewById(R.id.main);
        ArrayAdapter<String> adapter=new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,colors);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (parent.getItemAtPosition(position).toString()){
                    case "Blue":
                        mainlayout.setBackgroundColor(Color.BLUE);
                        ((TextView) parent.getChildAt(0)).setTextSize(20);
                        ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
                        break;
                    case "Black":
                        mainlayout.setBackgroundColor(Color.BLACK);
                        ((TextView) parent.getChildAt(0)).setTextSize(20);
                        ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);

                        break;
                    case "Red":
                        ((TextView) parent.getChildAt(0)).setTextSize(20);
                        ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
                        mainlayout.setBackgroundColor(Color.RED);
                        break;
                    case "Yellow":
                        ((TextView) parent.getChildAt(0)).setTextSize(20);
                        mainlayout.setBackgroundColor(Color.YELLOW);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}