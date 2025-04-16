package com.ssc.practise2;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    Spinner sp;
    ListView lv;
    String[] words={"Hello","World"};
    Button b;
    DatabaseHelper dbhelp;
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
        sp=findViewById(R.id.spinner);
        lv=findViewById(R.id.list_view);
        b=findViewById(R.id.button);
        dbhelp=new DatabaseHelper(this,null,null,1);
        ArrayAdapter ada=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,words);
        ArrayAdapter drop=new ArrayAdapter(this, android.R.layout.simple_spinner_item,words);
        drop.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(drop);
        lv.setAdapter(ada);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this,words[position],Toast.LENGTH_SHORT).show();
            }
        });
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this,words[position],Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        b.setOnClickListener(v->{
            dbhelp.insert(1,"Hello",100);
            Toast.makeText(MainActivity.this,"select something na",Toast.LENGTH_SHORT).show();
        });
    }

}