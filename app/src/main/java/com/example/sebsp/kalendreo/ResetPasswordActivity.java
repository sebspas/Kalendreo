package com.example.sebsp.kalendreo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ResetPasswordActivity extends LoggedInAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
    }

    @Override
    protected void redirectIfNotConnected() {
        // Do nothing
    }
}
