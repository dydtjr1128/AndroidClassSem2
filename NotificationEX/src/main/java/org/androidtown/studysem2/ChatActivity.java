package org.androidtown.studysem2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by CYSN on 2017-09-08.
 */

public class ChatActivity extends AppCompatActivity {
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        textView = (TextView)findViewById(R.id.textView);
        textView.setText("chat\nchat\nYongSeok");
        String value = getIntent().getStringExtra("Friend");
        if(value != null){
            ActionBar actionBar = getSupportActionBar();
            actionBar.setTitle(value);
        }
    }
}
