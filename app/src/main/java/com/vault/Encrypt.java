package com.vault;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Encrypt extends AppCompatActivity {

    EditText enc_input;
    Button btnEncrypt,
           enc_copy,
           enc_clear;
    TextView enc_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encrypt);

        enc_input = (EditText) findViewById(R.id.txtenc);
        btnEncrypt = (Button) findViewById(R.id.btnenc);
        enc_copy = (Button) findViewById(R.id.btnecopy);
        enc_clear = (Button) findViewById(R.id.btneclear);
        enc_result = (TextView) findViewById(R.id.enc_result);


        btnEncrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        enc_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enc_input.setText(null);
                enc_result.setText(null);

                InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(view.getApplicationWindowToken(),0);

            }
        });


    }


}