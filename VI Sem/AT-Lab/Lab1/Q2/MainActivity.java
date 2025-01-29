package com.ssc.q2;

import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private EditText email,mobile,pass,cpass;
    private Switch spass;
    private Button sbut;
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
        email=(EditText) findViewById(R.id.editTextTextEmailAddress);
        mobile=(EditText) findViewById(R.id.editTextPhone);
        pass=(EditText) findViewById(R.id.editTextTextPassword);
        cpass=(EditText) findViewById(R.id.editTextTextPassword2);
        spass=(Switch) findViewById(R.id.switch1);
        sbut=(Button) findViewById(R.id.button);

        spass.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
                pass.setInputType(InputType.TYPE_CLASS_TEXT);
                cpass.setInputType(InputType.TYPE_CLASS_TEXT);
            }else{
                pass.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
                cpass.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
            }

        });

        sbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ema = email.getText().toString().trim();
                String ph=mobile.getText().toString().trim();
                String pa=pass.getText().toString().trim();
                String cpa=cpass.getText().toString().trim();

                if(ema.isEmpty()){
                    Toaster("Enter email address");
                }
                else if(!Patterns.EMAIL_ADDRESS.matcher(ema).matches()){
                    Toaster("Enter valid email address");
                }
                else if(ph.isEmpty()){
                    Toaster("Enter mobile number");
                } else if (ph.length()!=10 || !TextUtils.isDigitsOnly(ph)) {
                    Toaster("Enter valid phone number");

                } else if (pa.isEmpty() || cpa.isEmpty()) {
                    Toaster("Please enter password");
                } else if (!pa.equals(cpa)) {
                    Toaster("Passwords donot match");
                }
                else {
                    Toaster("Details input successful");
                    AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Details").setMessage("Email: " + ema + "\nPhone: " + ph + "\nPassword: " + pa).setPositiveButton("OK",null).show();

                }
            }
        });
    }

    void Toaster(String message){
        Toast.makeText(MainActivity.this,message,Toast.LENGTH_SHORT).show();
    }


}