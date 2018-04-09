package com.android.hackxx2018;

/**
 * Created by Sun on 4/7/2018.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddFriendPage extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("Users");
    String username;
    String language;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final FirebaseCallback callback = new FirebaseCallback() {
            @Override
            public void onCallback(String phone, String username, String value) {

            }
        };
        ref.addChildEventListener(new ChildEventListener() {
            //queryRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot != null && dataSnapshot.getValue() != null) {
                    if (dataSnapshot.getKey().equals("num")) {}
                    for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                        snapshot.getKey();
                        snapshot.getValue();
                    }

                    /*
                    String username = (String)dataSnapshot.getKey();

                    String val = (String)dataSnapshot.getValue();
                    ArrayList<String> pair = new ArrayList<>(2);
                    pair.add(0, username);
                    pair.add(1, val);
                    //callback.onCallback(username, val);
                    dataList.add(pair);*/
                }
                else {
                    Log.e("ERROR", "No children");
                    //list will still be null
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("ERROR", "Cancelled");
            }
        });



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        Button submitFriend = (Button) findViewById(R.id.submit);
        final EditText inputText = (EditText) findViewById(R.id.phonenumber);

        submitFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String phoneNumber = inputText.getText().toString();
                ref.addChildEventListener(new ChildEventListener() {
                    //queryRef.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        if (dataSnapshot != null && dataSnapshot.getValue() != null) {
                            if (dataSnapshot.getKey().equals(phoneNumber)) {
                                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                                    String key = snapshot.getKey();
                                    if (key.equals("Email")) {
                                        username = snapshot.getValue().toString();
                                    }
                                    else if (key.equals("Language")) {
                                        language = snapshot.getValue().toString();
                                    }
                                }
                            }
                        }
                        else {
                            Log.e("ERROR", "No children");
                            //list will still be null
                        }
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e("ERROR", "Cancelled");
                    }
                });
                //TODO: add username based off of phone number given
                String userName = username;
                backToList();
            }
        });

    }

    public void backToList() {
        Intent toFriendsListIntent = new Intent(this, FriendsList.class);

        startActivity(toFriendsListIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

