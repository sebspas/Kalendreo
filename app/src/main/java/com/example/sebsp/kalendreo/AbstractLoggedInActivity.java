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
public abstract class AbstractLoggedInActivity extends AbstractAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseUser = firebaseAuth.getCurrentUser();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * If the user is actually not connected, we redirect him to the login page
     */
    protected void redirect() {
        Intent next;
        if (firebaseAuth == null || (firebaseUser = firebaseAuth.getCurrentUser()) == null) {
            // User not logged in, we redirect him to the log in
            Toast.makeText(this, R.string.logout_message, Toast.LENGTH_SHORT).show();
            next = new Intent(this, LoginActivity.class);
            startActivity(next);
            finish();
        }
    }

    /**
     * Disconnect the user, and redirect to the log in activity
     */
    protected void disconnect() {
        firebaseAuth.signOut(); // Firebase log out
        LoginManager.getInstance().logOut(); // Facebook log out
        redirect();
    }
}
