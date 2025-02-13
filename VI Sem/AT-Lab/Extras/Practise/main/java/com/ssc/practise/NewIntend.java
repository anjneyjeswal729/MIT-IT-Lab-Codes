package com.ssc.practise;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.InputType;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class NewIntend extends AppCompatActivity {
    ListView lv;
    EditText ed1,ed2,ed3;
    TextView t1;
    Button b,b1;
    Switch s;
    LinearLayout l;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newlayout);

        lv = findViewById(R.id.lv);
        ed1=findViewById(R.id.editTextText);
        ed2=findViewById(R.id.editTextText2);
        ed3=findViewById(R.id.editTextTextPassword);
        t1=findViewById(R.id.textView);
        b=findViewById(R.id.button2);
        s=findViewById(R.id.switch2);
        l=findViewById(R.id.linearLayout);
        b1=findViewById(R.id.button3);
        b.setOnClickListener(v->{
            int k=Integer.parseInt(ed1.getText().toString());
            int m=Integer.parseInt(ed2.getText().toString());
            int n=k+m;
            t1.setText(String.valueOf(n));
        });
        // Retrieve the passed ArrayList from Intent
        ArrayList<String> newarr = getIntent().getStringArrayListExtra("String");
        s.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    ed3.setInputType(InputType.TYPE_CLASS_TEXT);

                }
                else{
                    ed3.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
                }
            }
        });
        b1.setOnClickListener(v -> {
            ImageView iv = new ImageView(this);
            iv.setImageResource(R.drawable.myself);

            // Set LayoutParams to define width and height
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    200,200
            );
            iv.setLayoutParams(layoutParams); // Apply the layout parameters

            l.addView(iv); // Add ImageView to the LinearLayout
        });

        // Null check before setting adapter
        if (newarr == null || newarr.isEmpty()) {
            newarr = new ArrayList<>();
            newarr.add("No data received");
        }

        // Set adapter for ListView
        ArrayAdapter<String> myad = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, newarr);
        lv.setAdapter(myad);
    }
}
