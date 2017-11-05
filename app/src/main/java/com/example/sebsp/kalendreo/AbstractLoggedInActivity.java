package com.example.sebsp.kalendreo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.sebsp.kalendreo.login.LoginActivity;
import com.facebook.login.LoginManager;

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
            launchAndClose(new Intent(this, LoginActivity.class));
        }
    }

    /**
     * Disconnect the user, and redirect to the log in activity
     */
    protected void disconnect() {
        firebaseAuth.signOut(); // Firebase log out
        LoginManager.getInstance().logOut(); // Facebook log out
        Toast.makeText(this, R.string.logout_message, Toast.LENGTH_SHORT).show();
        redirect();
    }

}
