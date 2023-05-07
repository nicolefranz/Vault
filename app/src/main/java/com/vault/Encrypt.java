package com.vault;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class Encrypt extends AppCompatActivity {

    private EditText enc_input;
    private Button btnEncrypt,
            enc_copy,
            enc_clear;
    private TextView enc_result;
    private SecretKey secretKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encrypt);

        enc_input = findViewById(R.id.txtenc);
        btnEncrypt = findViewById(R.id.btnenc);
        enc_copy = findViewById(R.id.btnecopy);
        enc_clear = findViewById(R.id.btneclear);
        enc_result = findViewById(R.id.enc_result);

        btnEncrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (enc_input.getText().toString().isEmpty()) {
                    Toast.makeText(Encrypt.this, "Please input a text", Toast.LENGTH_SHORT).show();
                    enc_input.setError("Empty Field");
                    return;
                } else {
                    String input = enc_input.getText().toString();
                    secretKey = getSecretKey();
                    String encrypted = encrypt(input);
                    enc_result.setText(encrypted);
                    saveSecretKey(); // Save the secret key in SharedPreferences
                    hidekeyboard(view);

                    Toast.makeText(Encrypt.this, "Successfully Encrypt", Toast.LENGTH_SHORT).show();
                }
            }
        });

        enc_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (enc_input.getText().toString().isEmpty()) {
                    Toast.makeText(Encrypt.this, "The field is EMPTY", Toast.LENGTH_SHORT).show();
                    enc_input.setError("Empty Field");
                    return;
                } else {
                    enc_input.setText("");
                    enc_result.setText("");
                    secretKey = null;
                    hidekeyboard(view);
                    Toast.makeText(Encrypt.this, "Successfully Cleared", Toast.LENGTH_SHORT).show();
                }
            }
        });

        enc_copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(enc_result.getText().toString().isEmpty()){
                    Toast.makeText(Encrypt.this, "EMPTY FIELD", Toast.LENGTH_SHORT).show();
                }else {
                    ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("Encrypted Text", enc_result.getText().toString());
                    clipboard.setPrimaryClip(clip);
                    Toast.makeText(Encrypt.this, "RESULT COPIED TO THE CLIPBOARD", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(Encrypt.this, Decrypt.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }

    private SecretKey getSecretKey() {
        if (secretKey == null) {
            try {
                KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    keyGenerator.init(128, SecureRandom.getInstanceStrong());
                }
                secretKey = keyGenerator.generateKey();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        return secretKey;
    }

    private void saveSecretKey() {
        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String encodedKey = Base64.getEncoder().encodeToString(secretKey.getEncoded());
            editor.putString("secretKey", encodedKey);
            editor.apply();
        }
    }

    private String encrypt(String input) {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, getSecretKey());
            byte[] encryptedBytes = cipher.doFinal(input.getBytes());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                return Base64.getEncoder().encodeToString(encryptedBytes);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return input;
    }

    private void hidekeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
