package com.example.sebsp.kalendreo;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;

/**
 * Created by Gaetan on 04/11/2017.
 * Splash Activity between LoginActivity & MainActivity
 * Used to load the resources & data needed for the application
 */
public class SplashScreenActivity extends AbstractAppCompatActivity {

    private String facebookToken = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        facebookToken = fetchIntentString(R.string.EXTRA_FACEBOOK_AUTH_TOKEN);
        new PrefetchData().execute();
    }

    /**
     * Async Task to make http call
     */
    private class PrefetchData extends AsyncTask<Void, Void, Void> {

        private boolean userAuthenticatedWithFacebook = false;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // before making http calls
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            if (userAuthenticatedWithFacebook = (facebookToken != null)) handleFacebookAccessToken(facebookToken);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // After completing http call, we will close this activity and lauch main activity

            // If the user is not authenticated with facebook, then we launch directly the MainActivity
            if (!userAuthenticatedWithFacebook) {
                launchMainActivity();
            } // else, the MainActivity will be launched automatically when the firebase authentication will be done

        }

        /**
         * Handle Facebook success authentication
         *
         * @param token Facebook token
         */
        private void handleFacebookAccessToken(String token) {
            Log.d(Tag.FACEBOOK_AUTH, "handleFacebookAccessToken:" + token);

            AuthCredential credential = FacebookAuthProvider.getCredential(token);
            firebaseAuth.signInWithCredential(credential)
                    .addOnCompleteListener(SplashScreenActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(Tag.FACEBOOK_AUTH, "signInWithCredential:success");
                                launchMainActivity();

                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(Tag.FACEBOOK_AUTH, "signInWithCredential:failure", task.getException());
                                Toast.makeText(SplashScreenActivity.this, "Authentication with facebook failed.",
                                        Toast.LENGTH_SHORT).show();
                                SplashScreenActivity.super.launchAndClose(new Intent(SplashScreenActivity.this, LoginActivity.class));

                            }
                        }
                    });
        }

        /**
         * Launch the main activity
         */
        private void launchMainActivity() {
            // From here, the firebase authentication is done
            SplashScreenActivity.super.launchAndClose(new Intent(SplashScreenActivity.this, MainActivity.class));
        }
    }
}
