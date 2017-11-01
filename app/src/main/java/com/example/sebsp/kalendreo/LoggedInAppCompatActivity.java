package com.example.sebsp.kalendreo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Gaetan on 01/11/2017.
 * Superclass where user's must be identified and logged in
 */
public abstract class LoggedInAppCompatActivity extends AppCompatActivity {

    // Firebase set up
    protected FirebaseUser user;
    protected FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth =  FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user ==  null) {
            Toast.makeText(this, "Not connected", Toast.LENGTH_SHORT).show();
            // User not logged in, we redirect him to the log in page
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        } // else, the user is authenticated
    }

}
