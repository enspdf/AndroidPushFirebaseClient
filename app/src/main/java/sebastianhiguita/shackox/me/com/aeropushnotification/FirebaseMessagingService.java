package sebastianhiguita.shackox.me.com.aeropushnotification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.RemoteMessage;

import sebastianhiguita.shackox.me.com.aeropushnotification.notifications.PushNotificationsActivity;

/**
 * Created by filipp on 5/23/2016.
 */
public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService{

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        showNotification(remoteMessage.getData().get("title"), remoteMessage.getData().get("message"));
    }

    private void showNotification(String title, String message) {

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.airplane_black);

        Intent i = new Intent(this,PushNotificationsActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,i,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setAutoCancel(true)
                .setNumber(1)
                .setContentTitle(title)
                .setLargeIcon(bitmap)
                //.setSubText(message)
                .setContentText(message)
                //.addAction(R.drawable.common_google_signin_btn_icon_light, "Acción", pendingIntent)
                //.addAction(R.drawable.common_google_signin_btn_icon_dark, "Nuevo", pendingIntent)
                //.setStyle(new NotificationCompat.BigTextStyle())
                .setSmallIcon(R.drawable.airplane)
                .setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 })
                //.setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                //.setStyle(new NotificationCompat.BigTextStyle().bigText(title))
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        manager.notify(0,builder.build());
    }


}