package com.example.harikareddy.sos;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ProfileActivity extends AppCompatActivity {
    private TextView FirstName, LastName, Mobile, Address, Emailid;
    private Button ProfileUpdate, ChangePassword;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        FirstName = findViewById(R.id.tvfirstName);
        LastName = findViewById(R.id.tvlastName);
        Mobile = findViewById(R.id.tvmobile);
        Address = findViewById(R.id.tvaddress);
        Emailid = findViewById(R.id.tvemail);
        ProfileUpdate = findViewById(R.id.btnProfileUpdate);
        ChangePassword = findViewById(R.id.btnChangePassword);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();


        DatabaseReference databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid());


        databaseReference.child("UserProfile").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);

                FirstName.setText("First Name: " + userProfile.getFirstName());

                LastName.setText("Last Name: " + userProfile.getLastName());
                Mobile.setText("Mobile: " + userProfile.getMobile());
                Address.setText("Address: " + userProfile.getAddress());
                Emailid.setText("Email ID: " + userProfile.getEmail());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ProfileActivity.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();

            }
        });
        ProfileUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, UpdateProfile.class));
            }
        });
        ChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, changePassword.class));
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();

        }
        return super.onOptionsItemSelected(item);
    }
}
