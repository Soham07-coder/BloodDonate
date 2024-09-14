package com.example.myapplication.Adapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;

import com.google.firebase.FirebaseApp;

import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.R;
import com.example.myapplication.uploadNotice;

public class upload extends AppCompatActivity {


    CardView addNoticej,deleteNotice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_upload);

        FirebaseApp.initializeApp(this);
        deleteNotice = findViewById(R.id.deleteNotice);


        try {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e) {
        }

        addNoticej = findViewById(R.id.addNotice);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });

        addNoticej.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(upload.this, uploadNotice.class);
                startActivity(intent);
            }
        });

        deleteNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(upload.this,deleteNotice.class);
                startActivity(intent);
            }
        });

    }
}