package sebastianhiguita.shackox.me.com.aeropushnotification.notifications;

import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;

import sebastianhiguita.shackox.me.com.aeropushnotification.data.PushNotification;
import sebastianhiguita.shackox.me.com.aeropushnotification.data.PushNotificationsRepository;

/**
 * Created by SHACKOX on 1/10/16.
 */

public class PushNotificationsPresenter implements PushNotificationContract.Presenter {
    private final PushNotificationContract.View mNotificationView;
    private final FirebaseMessaging mFCMInteractor;

    public PushNotificationsPresenter(PushNotificationContract.View notificationView, FirebaseMessaging FCMInteractor) {
        mNotificationView = notificationView;
        mFCMInteractor = FCMInteractor;

        notificationView.setPresenter(this);
    }

    @Override
    public void start() {
        registerAppClient();
        loadNotifications();
    }

    @Override
    public void registerAppClient() {
        mFCMInteractor.subscribeToTopic("text");
    }

    @Override
    public void loadNotifications() {
        PushNotificationsRepository.getInstance().getPushNotifications(
                new PushNotificationsRepository.LoadCallback() {
                    @Override
                    public void onLoaded(ArrayList<PushNotification> notifications) {
                        if (notifications.size() > 0) {
                            mNotificationView.showEmptyState(false);
                            mNotificationView.showNotifications(notifications);
                        } else {
                            mNotificationView.showEmptyState(true);
                        }
                    }
                }
        );
    }

    @Override
    public void savePushMessage(String title, String description) {
        PushNotification pushMessage = new PushNotification();
        pushMessage.setmTitle(title);
        pushMessage.setmDescription(description);

        PushNotificationsRepository.getInstance().savePushNotification(pushMessage);

        mNotificationView.showEmptyState(false);
        mNotificationView.popPushNotification(pushMessage);
    }
}
