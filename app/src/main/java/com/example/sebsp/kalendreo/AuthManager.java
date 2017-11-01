package com.example.sebsp.kalendreo;

import android.app.Activity;
import android.content.Intent;

import com.google.firebase.auth.FirebaseAuth;

public class AuthManager {

    // this class is the singleton to manage the login
    private static AuthManager INSTANCE;

    // the firebase Auth
    public FirebaseAuth auth;

    // to get the singleton
    public static AuthManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AuthManager();
        }

        return INSTANCE;
    }

    // constructor to get the firebase auth
    private AuthManager() {
        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
    }

    public void RedirectIfLoggedIn(Activity activity) {
        // redirect to the mainActivity if the users is already logged
        if (auth.getCurrentUser() != null) {
            Intent intent = new Intent(activity, MainActivity.class); // Change LoginActivity to SignupActivity if you are calling ImageActivity from SignupActivity
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivity(intent);
            activity.finish();
        }
    }
}
