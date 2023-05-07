package com.vault;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button decrypt;
    Button encrypt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        decrypt = (Button) findViewById(R.id.btnDecrypt);
        encrypt = (Button) findViewById(R.id.btnEncrypt);

        decrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent dec = new Intent(MainActivity.this, Decrypt.class);
                startActivity(dec);
            }
        });

        encrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent enc = new Intent(MainActivity.this, Encrypt.class);
                startActivity(enc);
            }
        });

    }
}