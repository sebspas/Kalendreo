package com.example.sebsp.kalendreo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Gaetan on 04/11/2017.
 * SuperClass extending all our activities
 */
public abstract class AbstractAppCompatActivity extends AppCompatActivity {

    // Firebase set up
    protected FirebaseUser firebaseUser;
    protected FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();
        redirect();
    }

    /**
     * Redirect if needed the user
     */
    protected void redirect() {
        // By default we don't redirect the user
    }

    /**
     * Launch the new intent and finish the current activity
     *
     * @param intent intent to launch
     */
    protected void launchAndClose(Intent intent) {
        this.startActivity(intent);
        finish();
    }

    /**
     * Fetch the string value passed in the current intent
     *
     * @param rid R id of the string resource
     * @return the String extra value if existing
     */
    protected String fetchIntentString(int rid) {
        return fetchIntentString(getString(rid));
    }

    /**
     * Fetch the string value passed in the current intent
     * @param key Key of the extra
     * @return the String extra value if existing
     */
    protected String fetchIntentString(String key) {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            return bundle.getString(key);
        }
        return null;
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }


}
