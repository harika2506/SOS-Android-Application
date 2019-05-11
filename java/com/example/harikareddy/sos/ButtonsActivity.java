package com.example.harikareddy.sos;

import android.content.Intent;
import android.graphics.Bitmap;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class ButtonsActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private Button addeditcontacts;
    private Button photocapture;
    private Button audiocapture;
    private Button opNotification;
    private Button callAct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buttons);
        firebaseAuth=FirebaseAuth.getInstance();
        addeditcontacts = (Button)findViewById(R.id.contacts);
        addeditcontacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ButtonsActivity.this,AddContactsActivity.class));
            }
        });
        photocapture = (Button)findViewById(R.id.photoCapture);
        photocapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ButtonsActivity.this, CameraActivity.class));
            }
        });
        audiocapture = (Button)findViewById(R.id.audioCapture);
        audiocapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ButtonsActivity.this, audioActivity.class));
            }
        });
        opNotification = (Button)findViewById(R.id.opNotification);
        opNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ButtonsActivity.this, opActivity.class));
            }
        });
        callAct = (Button)findViewById(R.id.call);
        callAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ButtonsActivity.this,callActivity.class));
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        Bitmap bitmap = (Bitmap) data.getExtras().get("data");


    }
    private void Logout(){
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(ButtonsActivity.this, MainActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case R.id.logoutMenu: {
                Logout();
                break;
            }
            case R.id.profileMenu:
                startActivity(new Intent(ButtonsActivity.this, ProfileActivity.class));
                break;


        }
        return super.onOptionsItemSelected(item);
    }

}
