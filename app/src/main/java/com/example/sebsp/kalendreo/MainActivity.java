package com.example.sebsp.kalendreo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends LoggedInAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth =  FirebaseAuth.getInstance();

        Button createEvent = findViewById(R.id.CreateAnEvent);

        createEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, CreateEvent.class));
                finish();
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();

        //mAuth.signOut();
    }
}