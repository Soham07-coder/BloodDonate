package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.auth.AdminRegistrationActivity;
import com.example.myapplication.auth.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.checkerframework.checker.units.qual.A;

public class AdminActivity extends AppCompatActivity {
    TextInputEditText Aemail,Apassword;
    private Button loginbtn;
    TextView forgotPassword, backButton;

    private FirebaseAuth auth;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        auth = FirebaseAuth.getInstance();
        Aemail = findViewById(R.id.inputAEmail);
        Apassword = findViewById(R.id.inputAPassword);
        loginbtn = findViewById(R.id.loginButton);
        forgotPassword = findViewById(R.id.forgotPassword);
        backButton = findViewById(R.id.backButton);
        progressDialog = new ProgressDialog(this);

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adminLogin();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminActivity.this, AdminRegistrationActivity.class));
                finish();
            }
        });
    }

    private void adminLogin() {
        String email = Aemail.getText().toString();
        String password = Apassword.getText().toString();

        if(email.isEmpty()){
            Aemail.setError("Enter Email");
            Aemail.requestFocus();
            return;
        }

        if(password.isEmpty()){
            Apassword.setError("Enter Password");
            Apassword.requestFocus();
            return;
        }
        else{
            progressDialog.setMessage("Admin Login....");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(getApplicationContext(),"Login Successful",Toast.LENGTH_SHORT);
                        startActivity(new Intent(AdminActivity.this, AdminMainActivity.class));
                        finish();
                    }
                    else{
                        Toast.makeText(getApplicationContext(), task.getException().toString(), Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();
                }
            });
        }

    }

    @Override
    protected void onStart() {
        super.onStart();

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            Intent intent = new Intent(AdminActivity.this, AdminMainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }
}