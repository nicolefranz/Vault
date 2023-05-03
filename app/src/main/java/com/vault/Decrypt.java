package com.vault;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Decrypt extends AppCompatActivity {

    EditText dec_input;
    Button btnDecrypt,
           dec_copy,
           dec_clear;
    TextView dec_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decrypt);

        dec_input = (EditText) findViewById(R.id.txtdec);
        btnDecrypt = (Button) findViewById(R.id.btndec);
        dec_copy = (Button) findViewById(R.id.btndcopy);
        dec_clear = (Button) findViewById(R.id.btndclear);
        dec_result = (TextView) findViewById(R.id.dec_result);








        dec_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dec_input.setText(null);
                dec_result.setText(null);

                InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(view.getApplicationWindowToken(),0);
            }
        });

    }
}