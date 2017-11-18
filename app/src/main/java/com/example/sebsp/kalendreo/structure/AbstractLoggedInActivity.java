package com.example.sebsp.kalendreo.structure;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.example.sebsp.kalendreo.R;
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
        clearCache();
        Toast.makeText(this, R.string.logout_message, Toast.LENGTH_SHORT).show();
        redirect();
    }

    /**
     * Clear the {@link SharedPreferences}
     */
    private void clearCache() {
        SharedPreferences sharedPref = this.getSharedPreferences(getString(R.string.SP_KEY_FACEBOOK_FRIENDS_JSON), Context.MODE_PRIVATE);
        sharedPref.edit().clear().apply();
        sharedPref = this.getSharedPreferences(getString(R.string.SP_FILE_FACEBOOK_FRIENDS), Context.MODE_PRIVATE);
        sharedPref.edit().clear().apply();
    }
}
