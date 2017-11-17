package com.example.sebsp.kalendreo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Gaetan on 04/11/2017.
 * SuperClass extending all our activities
 */
public abstract class AbstractAppCompatActivity extends AppCompatActivity {

    // Firebase set up
    protected FirebaseUser firebaseUser;
    protected FirebaseAuth firebaseAuth;

    public FirebaseUser getFirebaseUser() {
        return firebaseUser;
    }

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
    protected String getStringFromIntent(int rid) {
        return getStringFromIntent(getString(rid));
    }

    /**
     * Fetch the string value passed in the current intent
     *
     * @param key Key of the extra
     * @return the String extra value if existing
     */
    protected String getStringFromIntent(String key) {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            return bundle.getString(key);
        }
        return null;
    }

    /**
     * Store the data in Shared Preferences
     * Warning: this will auto commit, do not use if several editions are needed
     *
     * @param spFile The SP File where to store the data
     * @param spKey  The SP File Key where to store the data in the SP File
     * @param value  String value to store
     */
    protected void storeInSharedPreferences(String spFile, String spKey, String value) {
        SharedPreferences sharedPref = this.getSharedPreferences(spFile, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(spKey, value);
        editor.apply();
    }

    /**
     * Read from the shared preferences a string
     *
     * @param spFile       The SP File where to read the data
     * @param spKey        the SP File Key where to read the data in the SP File
     * @param defaultValue the default value if the SP is not found
     * @return String the value (or default)
     */
    protected String getFromSharedPreferences(String spFile, String spKey, String defaultValue) {
        SharedPreferences sharedPreferences = this.getSharedPreferences(spFile, Context.MODE_PRIVATE);
        return sharedPreferences.getString(spKey, defaultValue);
    }

    /**
     * @param rIdSpFile    R Id of the SP File
     * @param rIdSpKey     R Id of the SP File Key Resource
     * @param defaultValue Default value
     * @return @see.this.getFromSharedPreferences
     * @see this.getFromSharedPreferences
     */
    protected String getFromSharedPreferences(int rIdSpFile, int rIdSpKey, String defaultValue) {
        return this.getFromSharedPreferences(getString(rIdSpFile), getString(rIdSpKey), defaultValue);
    }

}
