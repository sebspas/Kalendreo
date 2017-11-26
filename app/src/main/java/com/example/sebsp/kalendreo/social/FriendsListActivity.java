package com.example.sebsp.kalendreo.social;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.sebsp.kalendreo.R;
import com.example.sebsp.kalendreo.structure.AbstractLoggedInActivity;

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

        final ArrayList<Friend> friends = new ArrayList<>();
        try {
            JSONArray friendsList = new JSONArray(friendsJsonData);
            for (int i = 0; i < friendsList.length(); i++) {
                String id = friendsList.getJSONObject(i).getString("id");
                String name = friendsList.getJSONObject(i).getString("name");
                friends.add(new Friend(id, name));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, friends);
        ListView listView = findViewById(R.id.friends_list_view);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(FriendsListActivity.this, FriendEvents.class);
                intent.putExtra(getString(R.string.EXTRA_FACEBOOK_USER_ID), friends.get(position).id);
                intent.putExtra(getString(R.string.EXTRA_FACEBOOK_USER_NAME), friends.get(position).name);
                FriendsListActivity.this.startActivity(intent);
            }
        });
        listView.setAdapter(adapter);
    }

    private class Friend {
        public String id;
        public String name;

        private Friend(String id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
