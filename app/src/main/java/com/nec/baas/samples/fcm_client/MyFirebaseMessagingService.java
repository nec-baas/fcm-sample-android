package com.nec.baas.samples.fcm_client;

import android.app.Notification;
import android.app.NotificationManager;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

/**
 * Push メッセージ受信サービス
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();

    @Override
    public void onMessageReceived(RemoteMessage message) {
        // データ取り出し
        Map<String, String> data = message.getData();
        showNotification(data);
    }

    // Notification を表示する
    private void showNotification(Map<String, String> data) {
        Log.i(TAG, "showNotification start.");

        String title = data.get("title");
        String message = data.get("message");
        String uri = data.get("uri");
        Log.d(TAG, "#title  : " + title);
        Log.d(TAG, "#message: " + message);
        Log.d(TAG, "#uri: "+ uri);

        new Notification.Builder(this);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, null);
        builder.setWhen(System.currentTimeMillis())
                .setTicker(title)
                .setContentTitle(title)
                .setContentText(message)
                .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE)
                .setSmallIcon(R.mipmap.ic_launcher);

        //PendingIntent pi = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), 0);
        //builder.addAction(R.drawable.powered_by_google_light, extras.getString("title"), pi);
        //builder.setContentIntent(pi);

        if (Build.VERSION.SDK_INT > 20) {
            // Lollipop 以降。Heads Up Notification を使用。
            builder.setCategory(Notification.CATEGORY_SERVICE)
                    .setPriority(Notification.PRIORITY_HIGH);
        }

        Notification notification = builder.build();

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(0, notification);

        Log.i(TAG, "showNotification end.");
    }
}
