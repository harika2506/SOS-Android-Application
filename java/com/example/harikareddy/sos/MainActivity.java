package com.example.harikareddy.sos;

import android.app.ProgressDialog;
import android.content.Intent;


import android.icu.text.IDNA;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.Console;


public class MainActivity extends AppCompatActivity {
    private int loginCounter = 5;
    private EditText emailId;
    private EditText password;
    private TextView loginInfoCount;
    private Button loginButton;
    private TextView register;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        emailId = (EditText) findViewById(R.id.EmailID);
        password = (EditText) findViewById(R.id.password);
        loginInfoCount = (TextView) findViewById(R.id.attemptNumber);
        loginButton = (Button) findViewById(R.id.loginButton);
        register = (TextView) findViewById(R.id.registerButton);
        loginInfoCount.setText("Remaining Attempts: 5");

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            finish();
            startActivity(new Intent(MainActivity.this, ButtonsActivity.class));
        }

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate(emailId.getText().toString(), password.getText().toString());
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
            }
        });

    }

    private void validate(String emailId, String password) {

        firebaseAuth.signInWithEmailAndPassword(emailId, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    CheckEmailVerification();
                } else {
                    Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                    loginCounter--;
                    loginInfoCount.setText("No. of Remaining Attempts: " + loginCounter);
                    if (loginCounter == 0) {
                        loginButton.setEnabled(false);
                    }
                }
            }
        });
    }

    private void CheckEmailVerification() {

        FirebaseUser firebaseUser = firebaseAuth.getInstance().getCurrentUser();
        Boolean emailflag = firebaseUser.isEmailVerified();

        startActivity(new Intent(MainActivity.this, ButtonsActivity.class));
    }

}