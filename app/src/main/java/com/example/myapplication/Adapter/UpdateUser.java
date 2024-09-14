package com.example.myapplication.Adapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.AdminMainActivity;
import com.example.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.auth.User;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class UpdateUser extends AppCompatActivity {

    private ImageView updateUserImage;
    private EditText updateUserType,updateUserName,updateUserEmail,updateUserPhoneNumber,updateUserBloodGroup;
    private String type,name,email,phonenumber,bloodgroup,category;
    private Button updateUserBtn,deleteUserBtn;
    private DatabaseReference reference;
    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_user);

        auth = FirebaseAuth.getInstance();
        updateUserType = findViewById(R.id.updateUserType);
        updateUserImage = findViewById(R.id.updateUserImage);
        updateUserName = findViewById(R.id.updateUserName);
        updateUserEmail =  findViewById(R.id.updateUserEmail);
        updateUserPhoneNumber = findViewById(R.id.updateUserPhoneNumber);
        updateUserBloodGroup = findViewById(R.id.updateUserBloodGroup);
        updateUserBtn = findViewById(R.id.updateUserBtn);
        deleteUserBtn = findViewById(R.id.deleteUserBtn);

        type = getIntent().getStringExtra("type");
        name = getIntent().getStringExtra("name");
        email = getIntent().getStringExtra("email");
        phonenumber = getIntent().getStringExtra("phonenumber");
        bloodgroup = getIntent().getStringExtra("bloodgroup");
        reference = FirebaseDatabase.getInstance().getReference();
        category = getIntent().getStringExtra("users");

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        updateUserType.setText(type);
        updateUserName.setText(name);
        updateUserEmail.setText(email);
        updateUserPhoneNumber.setText(phonenumber);
        updateUserBloodGroup.setText(bloodgroup);

        updateUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = updateUserType.getText().toString();
                name = updateUserName.getText().toString();
                email = updateUserEmail.getText().toString();
                phonenumber = updateUserPhoneNumber.getText().toString();
                bloodgroup = updateUserBloodGroup.getText().toString();
                checkValidation();
            }
        });

        deleteUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteData();
            }
        });

    }

    private void deleteData() {
        reference.child(category).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(UpdateUser.this, "User Deleted Sucessfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(UpdateUser.this, AdminMainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UpdateUser.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void checkValidation() {

        if(type.isEmpty()){
            updateUserType.setError("Empty");
            updateUserType.requestFocus();
        }else if(name.isEmpty()){
            updateUserName.setError("Empty");
            updateUserName.requestFocus();
        }else if(email.isEmpty()){
            updateUserEmail.setError("Empty");
            updateUserEmail.requestFocus();
        }
        else if(phonenumber.isEmpty()){
            updateUserPhoneNumber.setError("Empty");
            updateUserPhoneNumber.requestFocus();
        }
        else if(bloodgroup.isEmpty()){
            updateUserBloodGroup.setError("Empty");
            updateUserBloodGroup.requestFocus();
        }
        else{
            updateData();
        }
    }

    private void updateData() {
        Map<String,Object> hp = new HashMap<>();
        hp.put("type",type);
        hp.put("name",name);
        hp.put("email",email);
        hp.put("phonenumber",phonenumber);
        hp.put("bloodgroup",bloodgroup);
        reference.child("users").updateChildren(hp).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(UpdateUser.this, "Updated Sucessfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(UpdateUser.this,AdminMainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UpdateUser.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
}