package sebastianhiguita.shackox.me.com.aeropushnotification.notifications;

import java.util.ArrayList;

import sebastianhiguita.shackox.me.com.aeropushnotification.BasePresenter;
import sebastianhiguita.shackox.me.com.aeropushnotification.BaseView;
import sebastianhiguita.shackox.me.com.aeropushnotification.data.PushNotification;

/**
 * Created by SHACKOX on 1/10/16.
 */

public class PushNotificationContract {
    interface View extends BaseView<Presenter> {
        void showNotifications(ArrayList<PushNotification> notifications);
        void showEmptyState(boolean empty);
        void popPushNotification(PushNotification pushMessage);
    }

    interface Presenter extends BasePresenter {
        void registerAppClient();
        void loadNotifications();
        void savePushMessage(String title, String description);
    }
}
