package com.ssc.test;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Prize extends AppCompatActivity {
    Button b;
    RadioGroup rg;
    int totalsum = 0;
    int[] price={5,4,3,6};
    int index;
    TextView t;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prize);

        b=findViewById(R.id.button);
        rg=findViewById(R.id.radiogrp);
        t=findViewById(R.id.textView);
        index = getIntent().getIntExtra("INDEX", -1);
        t.setText(String.valueOf(index));
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedid = rg.getCheckedRadioButtonId();
                if(selectedid!=-1){
                    RadioButton rb = findViewById(selectedid);
                    int quant=Integer.parseInt(rb.getText().toString());
                    t.setText(String.valueOf(quant));
                    Toast.makeText(Prize.this,String.valueOf(quant*price[index]),Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
