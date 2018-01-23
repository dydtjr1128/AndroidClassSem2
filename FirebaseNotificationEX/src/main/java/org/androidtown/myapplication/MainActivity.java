package org.androidtown.myapplication;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;

//액티비티는 UI가있음 서비스는 눈에보이지 않고 뒤에서 돌아가는 것
public class MainActivity extends AppCompatActivity {
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.myTextView);
        Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                textView.setText(msg.getData().getString("message"));
            }
        };
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("MainActivity", "Refreshed token: " + refreshedToken);

        MyFirebaseMessagingService myFirebaseMessagingService = new MyFirebaseMessagingService();
        Intent intent = new Intent(
                getApplicationContext(),//현재제어권자
                MyFirebaseMessagingService.class); // 이동할 컴포넌트
        startService(intent); // 서비스 시작



        MyFirebaseMessagingService.setHandler(handler);
    }

}
