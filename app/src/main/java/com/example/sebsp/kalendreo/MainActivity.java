package com.example.sebsp.kalendreo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AbstractLoggedInActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Button createEvent = findViewById(R.id.CreateAnEvent);
        createEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, CreateEventActivity.class));
            }
        });

        Button viewCalendar = findViewById(R.id.ViewCalendar);
        viewCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, CalendarView.class));
            }
        });

        Button logOut = findViewById(R.id.LogOut);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                disconnect();
            }
        });

        Button seeFriends = findViewById(R.id.show_friends);
        seeFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, FriendsListActivity.class));
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        //mAuth.signOut();
    }
}