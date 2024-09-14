package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myapplication.Adapter.UserAdapter;
import com.example.myapplication.Adapter.upload;
import com.example.myapplication.auth.LoginActivity;
import com.example.myapplication.model.User;
import com.example.myapplication.profile.ContactUsActivity;
import com.example.myapplication.profile.ProfileActivity;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdminMainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Toolbar toolbar;
    ProgressBar progressbar;
    RecyclerView recyclerView;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    private CircleImageView nav_user_image;
    private TextView nav_user_name, nav_user_email;
    private DatabaseReference databaseReference;
    private String emailcategory;

    private List<User> userList;
    private UserAdapter userAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        toolbar = findViewById(R.id.toolbars);
        progressbar = findViewById(R.id.progressbars);
        recyclerView = findViewById(R.id.recyclerViews);
        drawerLayout = findViewById(R.id.admindrawer);
        navigationView = findViewById(R.id.anavigationview);


        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                AdminMainActivity.this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);

        userList = new ArrayList<>();
        userAdapter = new UserAdapter(AdminMainActivity.this, userList);

        recyclerView.setAdapter(userAdapter);

        databaseReference = FirebaseDatabase.getInstance().getReference()
                .child("Admin").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        nav_user_name = navigationView.getHeaderView(0).findViewById(R.id.anav_user_name);
        nav_user_email = navigationView.getHeaderView(0).findViewById(R.id.anav_user_email);
        nav_user_image = navigationView.getHeaderView(0).findViewById(R.id.anav_user_image);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String name = snapshot.child("Name").getValue().toString();
                    nav_user_name.setText(name);
                    String email = snapshot.child("Email").getValue().toString();
                    nav_user_email.setText(email);

                    if (snapshot.hasChild("profilepictureurl")) {
                        String imageUrl = snapshot.child("profilepictureurl").getValue().toString();
                        Glide.with(getApplicationContext()).load(imageUrl).into(nav_user_image);
                    } else {
                        nav_user_image.setImageResource(R.drawable.profile);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference Reference = FirebaseDatabase.getInstance().getReference()
                .child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        Reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                readData();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void readData() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("users");
        Query query = reference.orderByChild("type");
        query.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    User user = dataSnapshot.getValue(User.class);
                    userList.add(user);
                }
                userAdapter.notifyDataSetChanged();
                progressbar.setVisibility(View.GONE);
                if (userList.isEmpty()) {
                    Toast.makeText(AdminMainActivity.this, "No recipients", Toast.LENGTH_SHORT).show();
                    progressbar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.aprofile:
                startActivity(new Intent(AdminMainActivity.this, AdminProfileActivity.class));
                break;
            case R.id.alogout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(AdminMainActivity.this, AdminActivity.class));
                finish();
                break;
            case R.id.anotification:
                startActivity(new Intent(AdminMainActivity.this, upload.class));
                break;
            case R.id.acallus:
                startActivity(new Intent(AdminMainActivity.this, ContactUsActivity.class));
                break;

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return false;
    }
}