package com.example.harikareddy.sos;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UpdateProfile extends AppCompatActivity {
    private EditText newFirstName, newLastName, newMobile, newAddress, newEmail;
    private Button save;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        newFirstName = findViewById(R.id.firstNameUpdate);
        newLastName = findViewById(R.id.lastNameUpdate);
        newMobile = findViewById(R.id.mobileUpdate);
        newAddress = findViewById(R.id.addressUpdate);
        newEmail = findViewById(R.id.emailUpdate);
        save = findViewById(R.id.btnSave);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
                newFirstName.setText(userProfile.getFirstName());
                newLastName.setText(userProfile.getLastName());
                newMobile.setText(userProfile.getMobile());
                newAddress.setText(userProfile.getAddress());
                newEmail.setText(userProfile.getEmail());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(UpdateProfile.this,databaseError.getCode(),Toast.LENGTH_SHORT).show();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fname = newFirstName.getText().toString();
                String lname = newLastName.getText().toString();
                String mobile = newMobile.getText().toString();
                String address = newAddress.getText().toString();
                String email = newEmail.getText().toString();
                UserProfile userProfile = new UserProfile(fname,lname,mobile,address,email);
                databaseReference.setValue(userProfile);
                finish();
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
