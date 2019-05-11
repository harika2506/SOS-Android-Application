package com.example.harikareddy.sos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class AddContactsActivity extends AppCompatActivity {
    private EditText Contact1ph;
    private EditText Contact2ph;
    private EditText Contact3ph;
    private Button register;
    private String contact1,contact2,contact3;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contacts);
        Contact1ph = (EditText)findViewById(R.id.ph1);
        Contact2ph = (EditText)findViewById(R.id.ph2);
        Contact3ph = (EditText)findViewById(R.id.ph3);
        register = (Button)findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contact1 = Contact1ph.getText().toString();
                contact2 = Contact2ph.getText().toString();
                contact3 = Contact3ph.getText().toString();
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

                DatabaseReference dbRef = firebaseDatabase.getReference(firebaseAuth.getUid());
                UserProfile userProfile = new UserProfile(contact1,contact2,contact3);
                dbRef.child("EmergencyContacts").setValue(userProfile);

                Toast.makeText(AddContactsActivity.this,"Contacts added succesfully",Toast.LENGTH_SHORT).show();

                Intent intent=new Intent(AddContactsActivity.this, ButtonsActivity.class);
                startActivity(intent);
            }
        });
    }
}
