package com.example.sebsp.kalendreo;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseUser = (firebaseAuth =  FirebaseAuth.getInstance()).getCurrentUser();
        redirect();
    }

    /**
     * Redirect if needed the user
     */
    protected void redirect() {
        // By default we don't redirect the user
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
