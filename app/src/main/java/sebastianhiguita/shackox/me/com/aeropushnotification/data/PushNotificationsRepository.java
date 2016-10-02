package sebastianhiguita.shackox.me.com.aeropushnotification.data;

import android.support.v4.util.ArrayMap;

import java.util.ArrayList;

/**
 * Created by SHACKOX on 1/10/16.
 */

public class PushNotificationsRepository {

    private static ArrayMap<String, PushNotification> LOCAL_PUSH_NOTIFICATIONS = new ArrayMap<>();
    private static PushNotificationsRepository INSTANCE;

    public PushNotificationsRepository() {
    }

    public static PushNotificationsRepository getInstance() {
        if (INSTANCE == null) {
            return new PushNotificationsRepository();
        } else {
            return INSTANCE;
        }
    }

    public void getPushNotifications(LoadCallback callback) {
        callback.onLoaded(new ArrayList<>(LOCAL_PUSH_NOTIFICATIONS.values()));
    }

    public void savePushNotification(PushNotification notification) {
        LOCAL_PUSH_NOTIFICATIONS.put(notification.getId(), notification);
    }

    public interface LoadCallback {
        void onLoaded(ArrayList<PushNotification> notifications);
    }
}
