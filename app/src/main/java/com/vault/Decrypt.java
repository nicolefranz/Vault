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
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Decrypt extends AppCompatActivity {

    private EditText dec_input;
    private Button btnDecrypt,
            dec_copy,
            dec_clear;
    private TextView dec_result;
    private SecretKey secretKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decrypt);

        dec_input = findViewById(R.id.txtdec);
        btnDecrypt = findViewById(R.id.btndec);
        dec_copy = findViewById(R.id.btndcopy);
        dec_clear = findViewById(R.id.btndclear);
        dec_result = findViewById(R.id.dec_result);

        btnDecrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dec_input.getText().toString().isEmpty()) {
                    Toast.makeText(Decrypt.this, "Please input a text", Toast.LENGTH_SHORT).show();
                    dec_input.setError("Empty Field");
                    return;
                } else {
                    String input = dec_input.getText().toString();
                    secretKey = getSecretKey();
                    String decrypted = decrypt(input);
                    dec_result.setText(decrypted);
                    hidekeyboard(view);

                    Toast.makeText(Decrypt.this, "Successfully Decrypt", Toast.LENGTH_SHORT).show();
                }
            }
        });

        dec_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dec_input.getText().toString().isEmpty()) {
                    Toast.makeText(Decrypt.this, "The field is EMPTY", Toast.LENGTH_SHORT).show();
                    dec_input.setError("Empty Field");
                    return;
                } else {
                    dec_input.setText("");
                    dec_result.setText("");
                    secretKey = null;
                    hidekeyboard(view);
                    Toast.makeText(Decrypt.this, "Successfully Cleared", Toast.LENGTH_SHORT).show();
                }
            }
        });

        dec_copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dec_result.getText().toString().isEmpty()){
                    Toast.makeText(Decrypt.this, "EMPTY FIELD", Toast.LENGTH_SHORT).show();
                }else{
                    ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("Decrypted Text", dec_result.getText().toString());
                    clipboard.setPrimaryClip(clip);
                    Toast.makeText(Decrypt.this, "RESULT COPIED TO THE CLIPBOARD", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(Decrypt.this,Encrypt.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }

    private SecretKey getSecretKey() {
        if (secretKey == null) {
            SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
            String encodedKey = preferences.getString("secretKey", null);
            if (encodedKey != null) {
                byte[] keyBytes = new byte[0];
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    keyBytes = Base64.getDecoder().decode(encodedKey);
                }
                secretKey = new SecretKeySpec(keyBytes, "AES");
            }
        }
        return secretKey;
    }

    private String decrypt(String dec_input) {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, getSecretKey());
            byte[] decryptedBytes = new byte[0];
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(dec_input));
            }
            return new String(decryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dec_input;
    }

    private void hidekeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}