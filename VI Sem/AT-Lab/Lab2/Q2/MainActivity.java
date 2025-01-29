package com.ssc.q2;

import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class MainActivity extends AppCompatActivity {

    private EditText editTextInput;
    private Button buttonEncrypt;
    private TextView textViewEncrypted;

    private static final String ALGORITHM = "AES";
    private static final String KEY = "1234567890123456"; // 16 byte key for AES (128-bit key)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextInput = findViewById(R.id.editTextInput);
        buttonEncrypt = findViewById(R.id.buttonEncrypt);
        textViewEncrypted = findViewById(R.id.textViewEncrypted);

        // Set button click listener
        buttonEncrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputText = editTextInput.getText().toString();
                if (!inputText.isEmpty()) {
                    try {
                        // Encrypt the input text
                        String encryptedText = encrypt(inputText);
                        // Show the encrypted text
                        textViewEncrypted.setText("Encrypted Text: " + encryptedText);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    // AES encryption method
    private String encrypt(String inputText) throws Exception {
        // Create SecretKeySpec from the predefined key
        SecretKeySpec secretKey = new SecretKeySpec(KEY.getBytes(), ALGORITHM);

        // Create a Cipher instance for AES encryption
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        // Encrypt the input text
        byte[] encryptedBytes = cipher.doFinal(inputText.getBytes());

        // Return the encrypted bytes as Base64 string for readability
        return Base64.encodeToString(encryptedBytes, Base64.DEFAULT);
    }
}
