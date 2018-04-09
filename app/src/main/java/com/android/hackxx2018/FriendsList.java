package com.android.hackxx2018;

/**
 * Created by Sun on 4/7/2018.
 */

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class FriendsList extends AppCompatActivity {
    Context context;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = getApplicationContext();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        button = null;

        Intent in = getIntent();
        if (in != null) {
            String username = in.getStringExtra("Username");
            String language = in.getStringExtra("Language");
            if (username != null) {
                addFriend(username);
            }
        }

        Button addFriend = findViewById(R.id.addFriend);
        addFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAddFriends();
            }
        });

    }

    public void addFriend(String friendName) {
        ConstraintLayout constraintLayout = findViewById(R.id.layout);
        final float scale = this.getResources().getDisplayMetrics().density;
        int pixels = (int) (50 * scale + 0.5f);
        int textSize = (int) (10 * scale + 0.5f);
        if (context != null) {
            Log.d("NOTNULL", "Not null");
        }
        else { Log.e("NULL", "Is null"); }
        Button button = new Button(this);
        android.support.constraint.ConstraintLayout.LayoutParams params = new
                android.support.constraint.ConstraintLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, pixels);
        button.setId(friendName.hashCode());
        button.setText(friendName);
        button.setBackgroundColor(Color.rgb(230, 230, 230));
        button.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
        button.setTextColor(Color.rgb(89, 89, 89));
        button.setTextSize(TypedValue.COMPLEX_UNIT_DIP, textSize);
        button.setSingleLine(true);
        button.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        button.setMarqueeRepeatLimit(1000);
        //ChatActivity.displayChatMessages();
        constraintLayout.addView(button, params);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    startChatIntent();
            }
        });

        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(constraintLayout);

        constraintSet.connect(
                button.getId(), ConstraintSet.LEFT,
                ConstraintSet.PARENT_ID, ConstraintSet.LEFT, 0);
        constraintSet.connect(
                button.getId(), ConstraintSet.RIGHT,
                ConstraintSet.PARENT_ID, ConstraintSet.RIGHT,0
        );
        if (this.button == null) {
            constraintSet.connect(
                    button.getId(), ConstraintSet.TOP,
                    ConstraintSet.PARENT_ID, ConstraintSet.TOP, 0);
        }
        else {
            constraintSet.connect(
                    button.getId(), ConstraintSet.TOP,
                    this.button.getId(), ConstraintSet.BOTTOM, 0);
        }
        this.button = button;

        constraintSet.constrainDefaultHeight(button.getId(), pixels);
        constraintSet.applyTo(constraintLayout);

    }

    public void startChatIntent() {
        Intent toChat = new Intent(this, ChatActivity.class);

        startActivity(toChat);

    }

    public void startAddFriends() {
        Intent toAddFriends = new Intent(this, AddFriendPage.class);

        startActivity(toAddFriends);

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
