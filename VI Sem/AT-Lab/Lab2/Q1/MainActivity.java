package com.ssc.q1;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    RecyclerView.Adapter mad;
    RecyclerView.LayoutManager mlay;

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
        recyclerView = findViewById(R.id.recyclerView);
        String[] items={"Apples","Cucumber","Onion","Cauliflower","Wheat Flour","Watermelons"};
        List<String> itemList = Arrays.asList(items);
        mlay=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mlay);
        mad = new MyAdapter(itemList, new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String item) {
                // Show Toast when an item is clicked
                Toast.makeText(MainActivity.this, item, Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setAdapter(mad);


    }
}