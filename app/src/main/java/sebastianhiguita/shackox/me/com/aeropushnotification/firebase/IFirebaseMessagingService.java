package sebastianhiguita.shackox.me.com.aeropushnotification.firebase;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import sebastianhiguita.shackox.me.com.aeropushnotification.data.PushNotification;
import sebastianhiguita.shackox.me.com.aeropushnotification.notifications.PushNotificationsActivity;
import sebastianhiguita.shackox.me.com.aeropushnotification.R;
import sebastianhiguita.shackox.me.com.aeropushnotification.notifications.PushNotificationsFragment;

/**
 * Created by SHACKOX on 1/10/16.
 */

public class IFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = IFirebaseMessagingService.class.getSimpleName();

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "¡Mensaje recibido!");
        displayNotification(remoteMessage.getData().get("title"), remoteMessage.getData().get("message"));
        sendNotificationBroadcast(remoteMessage);
    }

    private void sendNotificationBroadcast(RemoteMessage remoteMessage) {
        Intent intent = new Intent(PushNotificationsFragment.ACTION_NOTIFY_NEW_NOTIFICATION);
        intent.putExtra("title", remoteMessage.getData().get("title"));
        intent.putExtra("description", remoteMessage.getData().get("message"));
        LocalBroadcastManager.getInstance(getApplicationContext())
                .sendBroadcast(intent);
    }

    private void displayNotification(String title, String message) {
        Intent intent = new Intent(this, PushNotificationsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.airplane_black);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.airplane)
                .setLargeIcon(bitmap)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 })
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());
    }
}
