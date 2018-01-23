package org.androidtown.studysem2;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private int notificationID = 1002;
    private final String notificationGroup = "FriendGroup";
    final Context context= this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView)findViewById(R.id.listView);
        ArrayList<String> arrayList = new ArrayList();
        for(int i=1; i<=8; i++){
            arrayList.add("Friend" + i);
        }
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
                TextView textView = (TextView)view;
                intent.putExtra("Friend",textView.getText());
                startActivity(intent);
            }
        });
        NotifiThread notifiThread1 = new NotifiThread();
        notifiThread1.start();
    }
    class NotifiThread extends Thread{
        @Override
        public void run() {
            try {
                Thread.sleep(1000);
                Log.i("rrr","rr");
                Intent intent = new Intent(getApplicationContext(),ChatActivity.class);
                TaskStackBuilder builder = TaskStackBuilder.create(getApplicationContext());
                builder.addParentStack(ChatActivity.class);
                builder.addNextIntent(intent);
                intent.putExtra("Friend","Friend" + ((int)(Math.random()*8)+1));
                PendingIntent pendingIntent = builder.getPendingIntent(12,PendingIntent.FLAG_UPDATE_CURRENT);
                Log.i("rrr","rr1");

                NotificationCompat.Builder groupbuilder = new  NotificationCompat.Builder(getApplicationContext());
                groupbuilder.setSmallIcon(R.drawable.ic_announcement_black_24dp);
                groupbuilder.setContentTitle("Ararm2!");
                groupbuilder.setContentText("This is GroupBuilder!");
                groupbuilder.setGroupSummary(true);
                groupbuilder.setGroup(notificationGroup);
                groupbuilder.setContentIntent(pendingIntent);

                NotificationCompat.Builder mbuilder = new  NotificationCompat.Builder(getApplicationContext());
                mbuilder.setSmallIcon(R.drawable.ic_announcement_black_24dp);
                mbuilder.setContentTitle("Ararm!");
                mbuilder.setContentText("This is NotificationCompat!");//pendingintent
                mbuilder.setGroup(notificationGroup);
                mbuilder.setContentIntent(pendingIntent);
                mbuilder.setAutoCancel(true);

                NotificationCompat.Builder mbuilder2 = new  NotificationCompat.Builder(getApplicationContext());
                mbuilder2.setSmallIcon(R.drawable.ic_announcement_black_24dp);
                mbuilder2.setContentTitle("Ararm!");
                mbuilder2.setContentText("This is NotificationCompat!");//pendingintent
                mbuilder2.setGroup(notificationGroup);
                mbuilder2.setContentIntent(pendingIntent);
                mbuilder2.setAutoCancel(true);
                Log.i("rrr","rr2");

                NotificationManagerCompat notiManager = NotificationManagerCompat.from(context);//(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
                Log.i("rrr","rr3");
                notiManager.notify(4123, groupbuilder.build());
                Log.i("rrr","rr4");
                notiManager.notify(5551, mbuilder.build());
                notiManager.notify(5552, mbuilder2.build());
                Log.i("rrr","rr5");
            }catch (Exception e){
                e.getStackTrace();
            }
        }
    }

}
