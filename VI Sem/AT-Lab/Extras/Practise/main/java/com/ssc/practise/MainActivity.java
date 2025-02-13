package com.ssc.practise;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    ListView l;
    String[] list={"Hh","gg","ggh"};
    ArrayList<String> ll=new ArrayList<>();
    ToggleButton tb;
    Switch ss;
    SeekBar sb;
    CheckBox c1,c2,c3;
    int seekpos;
    RadioGroup rg;
    Button b;
    Spinner sp;
    String[] spin={"GG","LL","UU"};
    RatingBar rb;
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
        l=findViewById(R.id.list);
        ss=findViewById(R.id.switch1);
        tb=findViewById(R.id.toggleButton);
        sb=findViewById(R.id.seekBar);
        sb.setMax(100);
        sb.setMin(1);
        c1=findViewById(R.id.checkBox);
        c2=findViewById(R.id.checkBox2);
        c3=findViewById(R.id.checkBox3);
        rg=findViewById(R.id.radioGroup);
        b=findViewById(R.id.button);
        sp=findViewById(R.id.spinner);
        rb=findViewById(R.id.ratingBar);
        rb.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                Toaster(String.valueOf(rating));

            }
        });
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seekpos=progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        b.setOnClickListener(v->{
            Toaster(String.valueOf(seekpos));
            Intent intent=new Intent(MainActivity.this,NewIntend.class);
            intent.putStringArrayListExtra("String",ll);
            startActivity(intent);
        });
        ArrayList<String> jij=new ArrayList<>(Arrays.asList(list));
        ArrayAdapter<String> myad=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,jij);
        ArrayAdapter<String> myadd=new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,spin);
        myadd.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(myadd);
        l.setAdapter(myad);
        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String nam = parent.getItemAtPosition(position).toString();

                // Show Toast with selected item
                Toast.makeText(MainActivity.this, nam, Toast.LENGTH_SHORT).show();

                // Check if item exists in list
                boolean found = false;
                for (String item : ll) { // Enhanced for-loop for better readability
                    if (item.equals(nam)) {
                        found = true;
                        break; // Stop loop once found
                    }
                }

                // Add item if not found, otherwise show "Already Selected"
                if (!found) {
                    ll.add(nam);
                } else {
                    Toast.makeText(MainActivity.this, "Already Selected", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Toaster("ON");
                }
                else {
                    Toaster("OFF");
                }
            }
        });
        c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (c1.isChecked()) Toaster(c1.getText().toString());
            }
        });
        c2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    Toaster(c2.getText().toString());
                }
                else {
                    Toaster("Unchecked");
                }
            }
        });
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb1=group.findViewById(checkedId);
                Toaster(rb1.getText().toString());
            }
        });
        ss.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    Toaster("ON");
                    new AlertDialog.Builder(MainActivity.this).setTitle("Hello")
                            .setMessage("JAva is fucking idiota")
                            .setPositiveButton("YES",(dialog, which) -> {
                                new AlertDialog.Builder(MainActivity.this)
                                        .setTitle("Good").show();
                            })
                            .setNegativeButton("NO",(dialog, which) -> {

                            }).show();

                }
                else {
                    Toaster("OFF");
                }
            }
        });
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String gg=parent.getItemAtPosition(position).toString();
                Toaster(gg);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    void Toaster(String msg){
        Toast.makeText(MainActivity.this,msg,Toast.LENGTH_SHORT).show();
    }
}