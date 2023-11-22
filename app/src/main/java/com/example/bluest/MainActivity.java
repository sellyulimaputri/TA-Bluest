package com.example.bluest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
TextView nama;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nama=findViewById(R.id.nama);

        String username= getIntent().getStringExtra("user");
        nama.setText("Selamat Datang "+username+"!!!");
    }
}