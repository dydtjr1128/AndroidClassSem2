package org.androidtown.firebaseex;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by CYSN on 2017-10-24.
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((TextView)findViewById(R.id.idTextView)).setText(getIntent().getStringExtra("user_id") + "\n Login!!");
    }

    @Override
    public void onBackPressed() {
        FirebaseAuth.getInstance().signOut();
        Toast.makeText(getApplicationContext(), "Sign Out!!", Toast.LENGTH_SHORT).show();
        super.onBackPressed();
    }
}
