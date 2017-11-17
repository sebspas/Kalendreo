package com.example.sebsp.kalendreo.login;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.example.sebsp.kalendreo.AbstractAppCompatActivity;
import com.example.sebsp.kalendreo.MainActivity;
import com.example.sebsp.kalendreo.R;
import com.example.sebsp.kalendreo.model.Event;
import com.example.sebsp.kalendreo.model.pojo.EventPOJO;
import com.example.sebsp.kalendreo.utils.ReminderManager;
import com.example.sebsp.kalendreo.utils.Tag;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;

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
            } else { // else, the MainActivity will be launched automatically when the firebase authentication will be done
                // The user is logged in with Facebook, we can fetch his friends while the firebase authentication is loading
                fetchFriends();
            }
        }

        /**
         * Load the facebook friends of the user connected
         */
        private void fetchFriends() {
            /* make the API call */
            new GraphRequest(
                    AccessToken.getCurrentAccessToken(),
                    "/me/friends",
                    null,
                    HttpMethod.GET,
                    new GraphRequest.Callback() {
                        public void onCompleted(GraphResponse response) {
                            try {
                                JSONArray rawName = response.getJSONObject().getJSONArray("data");

                                String keyFile = getString(R.string.SP_FILE_FACEBOOK_FRIENDS);
                                String keySp = getString(R.string.SP_KEY_FACEBOOK_FRIENDS_JSON);
                                SplashScreenActivity.this.storeInSharedPreferences(keyFile, keySp, String.valueOf(rawName));
                            } catch (JSONException e) {
                                Log.e(Tag.FACEBOOK_FRIENDS, e.toString());
                            }
                            Log.e(Tag.FACEBOOK_AUTH,"Friends: " + response.getRawResponse());
                        }
                    }
            ).executeAsync();
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

        /**
         * Create the notification for all the event depending on the categorie
         */
        private void createAlarmForNotificationForEvent() {
            // TODO GM: Call this method
            final HashMap<String, Event> listOfEvents = new HashMap<>();

            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("events").child(firebaseUser.getUid());

            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    Iterable<DataSnapshot> eventIterable = dataSnapshot.getChildren();

                    for (DataSnapshot eventSnap : eventIterable) {
                        Event event = new Event(eventSnap.getValue(EventPOJO.class));
                        listOfEvents.put(event.getId(), event);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.e(Tag.FIREBASE_ERROR, databaseError.getMessage());
                }
            });

            for (Event e : listOfEvents.values()) {
                // for each event of the user we create a new reminder
                ReminderManager.createAReminder(SplashScreenActivity.this, e);
            }
        }
    }
}
