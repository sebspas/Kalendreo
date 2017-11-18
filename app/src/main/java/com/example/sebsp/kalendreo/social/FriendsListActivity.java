package com.example.sebsp.kalendreo.social;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.sebsp.kalendreo.structure.AbstractLoggedInActivity;
import com.example.sebsp.kalendreo.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by Gaetan on 04/11/2017.
 * Display the friends list of the user
 */
public class FriendsListActivity extends AbstractLoggedInActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_friends_list);

        // Display list into listView
        String friendsJsonData = this.getFromSharedPreferences(R.string.SP_FILE_FACEBOOK_FRIENDS, R.string.SP_KEY_FACEBOOK_FRIENDS_JSON, "{}");

        ArrayList<String> friendsName = new ArrayList<>();
        try {
            JSONArray friendsList = new JSONArray(friendsJsonData);
            for (int i = 0; i < friendsList.length(); i++) {
                friendsName.add(friendsList.getJSONObject(i).getString("name"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, friendsName);
        ListView listView = findViewById(R.id.friends_list_view);
        listView.setAdapter(adapter);
    }
}
