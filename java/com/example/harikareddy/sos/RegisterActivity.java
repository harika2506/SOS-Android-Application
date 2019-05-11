package com.example.harikareddy.sos;

import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.service.autofill.UserData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.Console;

public class RegisterActivity extends AppCompatActivity {
    private EditText FirstName;
    private EditText LastName;
    private EditText Mobile;
    private EditText Address;
    private EditText EmailID;
    private EditText Password;
    private Button register;
    private TextView login;
    private FirebaseAuth firebaseAuth;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    String fName, lName, mob, addr, pass, mail;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        FirstName = (EditText)findViewById(R.id.FirstName);
        LastName = (EditText)findViewById(R.id.lastName);
        Mobile = (EditText)findViewById(R.id.mobile);
        Address = (EditText)findViewById(R.id.address);
        EmailID = (EditText)findViewById(R.id.EmailID);
        Password = (EditText)findViewById(R.id.password);
        register = (Button)findViewById(R.id.register);
        login = (TextView)findViewById(R.id.login);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this,MainActivity.class));
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()) {
                    String email = EmailID.getText().toString().trim();
                    String password = Password.getText().toString().trim();
                    firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                sendUserData();
                                firebaseAuth.signOut();
                                Toast.makeText(RegisterActivity.this, "Registration Success", Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                            } else {
                                Toast.makeText(RegisterActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                            }


                        }
                    });
                }
            }
        });
    }
    private Boolean validate(){
        Boolean result = false;
        fName = FirstName.getText().toString();
        lName = LastName.getText().toString();
        mob = Mobile.getText().toString();
        addr = Address.getText().toString();
        pass = Password.getText().toString();
        mail = EmailID.getText().toString();
        if(fName.isEmpty() || lName.isEmpty() || mob.isEmpty() || addr.isEmpty() || pass.isEmpty() || mail.isEmpty()){
            Toast.makeText(this,"Please Enter all Details", Toast.LENGTH_SHORT).show();
        }
        else{
            result = true;
        }
        return result;
    }
    private void sendUserData(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference(firebaseAuth.getUid());


        UserProfile userProfile = new UserProfile(fName,lName,mob, addr,mail);

        myRef.child("UserProfile").setValue(userProfile);

    }


}
