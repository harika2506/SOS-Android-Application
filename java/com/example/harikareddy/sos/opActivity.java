package com.example.harikareddy.sos;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.app.Activity;

import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;

import android.util.Log;
import android.view.Menu;
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
import com.google.firebase.storage.FirebaseStorage;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit ;


import java.util.ArrayList;

public class opActivity extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =0 ;
    Button sendBtn;
    EditText txtphoneNo;
    EditText txtMessage;
    String phoneNo;
    String message;
    private FirebaseStorage firebaseStorage;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;

    ArrayList<String> contactList = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_op);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();


        DatabaseReference databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid());
        databaseReference.child("EmergencyContacts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
                contactList.add(userProfile.getContact1());
                contactList.add(userProfile.getContact2());
                contactList.add(userProfile.getContact3());

                System.out.println(contactList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("failed");
            }
        });


        sendBtn = (Button) findViewById(R.id.btnSendSMS);


        sendBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                sendSMSMessage();
            }
        });

    }
    protected void sendSMSMessage()  {

        locationManager loc = new locationManager();


        message = "Hi , I am in Trouble, Please follow the location to help.....\n";
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M && checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(new String[] {Manifest.permission.ACCESS_COARSE_LOCATION},1000);
        }else{
            LocationManager locationManager=(LocationManager) getSystemService(Context.LOCATION_SERVICE);
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            try{
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                String address = getAddress(latitude,longitude);
                message+=address;

            } catch (Exception e){

            }

        }


        for(int i = 0; i < contactList.size(); i++) {
            phoneNo = contactList.get(i) ;
            System.out.println(phoneNo);
            if(phoneNo.length() == 10) {
                System.out.println(i);

                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(phoneNo, null, message, null, null);
                Toast.makeText(getApplicationContext(), "SMS sent.",
                        Toast.LENGTH_LONG).show();
            }

        }
    }
    private String getAddress(double lat, double longit) {
        String cityname="";
        String address="";
        if(lat!=0 && longit!=0)
        {
            Geocoder geocoder = new Geocoder(opActivity.this, Locale.getDefault());
            try {
                List<Address> addressList=geocoder.getFromLocation(lat,longit,1);
                address= addressList.get(0).getAddressLine(0);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return address;
    }



}