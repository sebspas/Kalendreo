package com.example.sebsp.kalendreo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Gaetan on 01/11/2017.
 * Superclass where user's must be identified and logged in
 */
public abstract class LoggedInAppCompatActivity extends AppCompatActivity {

    // Firebase set up
    protected FirebaseUser user;
    protected FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = (auth =  FirebaseAuth.getInstance()).getCurrentUser();
        redirectIfNotConnected();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    // check if the user is logged in, if so redirect to the mainActivity
    protected void redirectIfNotConnected() {
        // redirect to the mainActivity if the users is already logged
        Intent next;
        if (auth == null || user == null) {
            // User not logged in, we redirect him to the log in
            Toast.makeText(this, "Not connected", Toast.LENGTH_SHORT).show();
            next = new Intent(this, LoginActivity.class);
            startActivity(next);
            finish();
        }
    }

    /**
     * If the user is already connected, we redirect him
     */
    protected void redirectIfConnected() {
        if (auth != null && user != null) {
            Intent intent = new Intent(this, MainActivity.class); // Change LoginActivity to SignupActivity if you are calling ImageActivity from SignupActivity
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }
    }

    /**
     * Disconnect the user, and redirect to the log in activity
     */
    protected void disconnect() {
        auth.signOut();
        LoginManager.getInstance().logOut();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

}
