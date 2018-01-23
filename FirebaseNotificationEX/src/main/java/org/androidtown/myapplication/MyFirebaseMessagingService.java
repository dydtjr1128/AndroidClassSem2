package org.androidtown.myapplication;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by CYSN on 2017-11-07.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService{
    private final String TAG = "MyFbMessagingService";
    private OnTextListener onTextListener;
    private static Handler handler3;
    public static void setHandler(Handler handler){
        handler3 = handler;
    }
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        if(remoteMessage.getData().size() > 0){
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
        }

        if(remoteMessage.getNotification() != null){
            String msgBody = remoteMessage.getNotification().getBody();
            if(handler3 != null) {
                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putString("message",msgBody);
                message.setData(bundle);
                handler3.sendMessage(message);
            }
            Log.d(TAG, "Message Notification Body: " + msgBody);
        }
    }
    public void setOnTextEventListener(OnTextListener listener) {
        onTextListener = listener;
    }
}
