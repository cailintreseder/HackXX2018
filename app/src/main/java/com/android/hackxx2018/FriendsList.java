package com.android.hackxx2018;

/**
 * Created by Sun on 4/7/2018.
 */

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class FriendsList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void addFriend(String friendName) {
        final float scale = this.getResources().getDisplayMetrics().density;
        int pixels = (int) (50 * scale + 0.5f);
        int textSize = (int) (10 * scale + 0.5f);
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
