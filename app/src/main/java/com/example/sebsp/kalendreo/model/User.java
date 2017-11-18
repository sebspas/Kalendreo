package com.example.sebsp.kalendreo.model;

import android.util.Log;

import com.example.sebsp.kalendreo.model.pojo.UserPOJO;
import com.google.firebase.auth.FirebaseUser;

public class User extends Model {

    public static String getTableName() { return "users"; }

    private FirebaseUser firebaseUser;


    // -------------- Constructors

    /**
     * Create a user for our model from FirebaseUser
     *
     * @param user firebaseUser
     */
    public User(FirebaseUser user) {
        this.id = user == null ? null : user.getUid();
        this.firebaseUser = user;
    }

    public User(String userId) {
        this.id = userId;
    }

    // -------------- Getters & setters

    public String getEmail() {
        return firebaseUser.getEmail();
    }

    // ------------- Override methods

    @Override
    protected void valid() throws ModelNotValidException {
    }

    @Override
    public void create() {
        this.id = databaseReference.push().getKey();
        databaseReference.child(getTableName()).child(id).setValue(new UserPOJO(this));
    }

    @Override
    public void update() {
        databaseReference.child(getTableName()).child(id).setValue(new UserPOJO(this));
    }

    @Override
    public void delete() {
        // No method to delete an user implemented yet
    }
}
