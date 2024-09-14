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
import com.example.myapplication.auth.LoginActivity;
import com.example.myapplication.profile.ContactUsActivity;
import com.example.myapplication.profile.ProfileActivity;
import com.example.myapplication.model.User;
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

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar;
    RecyclerView recyclerView;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    private CircleImageView nav_user_image;
    private TextView nav_user_name, nav_user_email, nav_user_bloodgroup, nav_user_type;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toobar);
        recyclerView = findViewById(R.id.recyclerView);

        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);

        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                MainActivity.this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);


        databaseReference = FirebaseDatabase.getInstance().getReference()
                        .child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        nav_user_image = navigationView.getHeaderView(0).findViewById(R.id.nav_user_image);
        nav_user_name = navigationView.getHeaderView(0).findViewById(R.id.nav_user_name);
        nav_user_email = navigationView.getHeaderView(0).findViewById(R.id.nav_user_email);
        nav_user_bloodgroup = navigationView.getHeaderView(0).findViewById(R.id.nav_user_bloodgroup);
        nav_user_type = navigationView.getHeaderView(0).findViewById(R.id.nav_user_type);

        databaseReference = FirebaseDatabase.getInstance().getReference()
                        .child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    String name = snapshot.child("Name").getValue().toString();
                    nav_user_name.setText(name);

                    String email = snapshot.child("Email").getValue().toString();
                    nav_user_email.setText(email);

                    String bloodgroup = snapshot.child("Bloodgroup").getValue().toString();
                    nav_user_bloodgroup.setText(bloodgroup);

                    String type = snapshot.child("type").getValue().toString();
                    nav_user_type.setText(type);


                    if (snapshot.hasChild("Profilepictureurl")) {
                        String imageUrl = snapshot.child("Profilepictureurl").getValue().toString();
                        Glide.with(getApplicationContext()).load(imageUrl).into(nav_user_image);
                    } else {
                        nav_user_image.setImageResource(R.drawable.profile);
                    }

                    Menu nav_menu = navigationView.getMenu();

                    if (type.equals("donor")) {
                        nav_menu.findItem(R.id.sendEmail).setTitle("Received Emails");
                        nav_menu.findItem(R.id.notification).setVisible(true);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.profile:
                intent = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(intent);
                finish();
                break;

            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
                break;
            case R.id.about:
                //startActivity();
                break;
            case R.id.contactUs:
                startActivity(new Intent(MainActivity.this, ContactUsActivity.class));
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return false;
    }
}
