package com.example.sebsp.kalendreo;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Gaetan on 01/11/2017.
 * Superclass where firebaseUser's must be identified and logged in
 */
public abstract class AbstractNotLoggedInActivity extends AbstractAppCompatActivity {

    /**
     * If the firebaseUser is already connected, we redirect him to the Main Activity
     */
    @Override
    protected void redirect() {
        if (firebaseAuth != null && firebaseUser != null) {
            Intent intent = new Intent(this, MainActivity.class); // Change LoginActivityAbstract to SignupActivityAbstract if you are calling ImageActivity from SignupActivityAbstract
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }
    }

}
