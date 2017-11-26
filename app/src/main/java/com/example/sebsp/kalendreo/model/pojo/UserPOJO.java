package com.example.sebsp.kalendreo.model.pojo;

import com.example.sebsp.kalendreo.model.User;

/**
 * Created by Gaetan on 16/11/2017.
 * Plain Old Java Object for a User
 * Needed to be stored easily in Firebase
 */
public class UserPOJO {

    public String id;
    public String email;
    public String facebookId;

    public UserPOJO() {
    }

    public UserPOJO(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.facebookId = user.getFacebookId();
    }
}
