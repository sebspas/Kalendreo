package com.example.sebsp.kalendreo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AbstractNotLoggedInActivity {
    // INPUTS
    private EditText inputEmail, inputPassword;
    private ProgressBar progressBar;
    private Button btnSignup, btnLogin;

    // FACEBOOK AUTH
    private CallbackManager mCallbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // set the view now
        setContentView(R.layout.activity_login);

        inputEmail = findViewById(R.id.email);
        inputPassword = findViewById(R.id.password);
        progressBar = findViewById(R.id.progressBar);
        btnSignup = findViewById(R.id.btn_signup);
        btnLogin = findViewById(R.id.btn_login);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchAndClose(new Intent(LoginActivity.this, SignupActivity.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = inputEmail.getText().toString();
                final String password = inputPassword.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                //authenticate user
                firebaseAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                progressBar.setVisibility(View.GONE);
                                if (!task.isSuccessful()) {
                                    // there was an error
                                    if (password.length() < 6) {
                                        inputPassword.setError(getString(R.string.minimum_password));
                                    } else {
                                        Toast.makeText(LoginActivity.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    launchAndClose(new Intent(LoginActivity.this, SplashScreenActivity.class));
                                }
                            }
                        });
            }
        });

        // Facebook auth
        facebookAuth();
    }

    /**
     * Manage the authentication with facebook
     */
    private void facebookAuth() {
        // Initialize Facebook Login button
        mCallbackManager = CallbackManager.Factory.create();
        final LoginButton loginButton = findViewById(R.id.fb_login_button);
        loginButton.setReadPermissions("email", "public_profile", "user_friends");
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(Tag.FACEBOOK_AUTH, "facebook:onSuccess:" + loginResult);
                Intent intent = new Intent(LoginActivity.this, SplashScreenActivity.class);
                intent.putExtra(getString(R.string.EXTRA_FACEBOOK_AUTH_TOKEN), loginResult.getAccessToken().getToken());
                launchAndClose(intent);
            }
            @Override
            public void onCancel() {
                Log.d(Tag.FACEBOOK_AUTH, "facebook:onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(Tag.FACEBOOK_AUTH, "facebook:onError", error);
            }
        });

    }


    // Facebook  Auth
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Pass the activity result back to the Facebook SDK
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }

}

