package com.example.sebsp.kalendreo.model;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Gaetan on 16/11/2017.
 * Model Not Valid Exception
 */
public class ModelNotValidException extends Exception {

    private final int messageId;

    ModelNotValidException(int rStringId) {
        this.messageId = rStringId;
    }

    /**
     * Display the message in a Toast
     * @param context needed to display the toast
     */
    public void displayMessage(Context context) {
        Toast.makeText(context, context.getResources().getString(messageId), Toast.LENGTH_SHORT).show();
    }
}
