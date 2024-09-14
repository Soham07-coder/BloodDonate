package com.example.myapplication.auth;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.AdminActivity;
import com.example.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;

public class RegisterActivity extends AppCompatActivity {

    private Button donirregistrationBtn;
    Button adminbtn,adminsign,backButton;

    FirebaseAuth auth;
    private static final String TAG = "Registered";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        auth = FirebaseAuth.getInstance();
        donirregistrationBtn = findViewById(R.id.donirregistrationBtn);
        adminbtn = findViewById(R.id.adminbtn);
        backButton = findViewById(R.id.backButton);
        adminsign = findViewById(R.id.adminsign);

        FirebaseMessaging.getInstance().subscribeToTopic("All_Users")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(RegisterActivity.this,TAG,Toast.LENGTH_LONG).show();
                        }
                        else{
                            Log.e(TAG,"Not Subscribed Successfully");
                        }
                    }
                });

        FirebaseMessaging.getInstance().subscribeToTopic("news")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @SuppressLint("Range")
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // User subscribed to notifications
                            FirebaseAnalytics.getInstance(getApplicationContext())
                                    .setUserProperty("user_subscribe_to_notifications", "true");
                        } else {
                            // Failed to subscribe
                        }
                    }
                });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
            }
        });

        adminbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, AdminRegistrationActivity.class));
            }
        });

        donirregistrationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, DonorRegistrationActivity.class));
            }
        });

        adminsign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(RegisterActivity.this, AdminActivity.class));
            }
        });
    }

}