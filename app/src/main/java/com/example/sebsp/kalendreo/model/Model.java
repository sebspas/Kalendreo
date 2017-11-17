package com.example.sebsp.kalendreo.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Gaetan on 16/11/2017.
 * Model
 */
abstract class Model {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    protected String id = null;

    /**
     * Insert the model in Firebase if it has not id
     * else update the given model according to its id
     *
     * @throws ModelNotValidException if a field is not valid
     */
    public final void save() throws ModelNotValidException {
        this.valid();
        if (this.id == null) {
            this.create();
        } else {
            this.update();
        }
    }

    protected abstract void valid() throws ModelNotValidException;

    public abstract void create();

    public abstract void update();

    public String getId() {
        return id;
    }
}
