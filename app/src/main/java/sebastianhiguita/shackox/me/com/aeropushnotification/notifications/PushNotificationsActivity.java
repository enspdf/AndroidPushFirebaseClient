package sebastianhiguita.shackox.me.com.aeropushnotification.notifications;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.google.firebase.messaging.FirebaseMessaging;

import sebastianhiguita.shackox.me.com.aeropushnotification.R;

public class PushNotificationsActivity extends AppCompatActivity {

    private static final String TAG = PushNotificationsActivity.class.getSimpleName();

    private PushNotificationsFragment mNotificationsFragment;
    private PushNotificationsPresenter mNotificationsPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(getString(R.string.title_activity_notifications));

        mNotificationsFragment = (PushNotificationsFragment) getSupportFragmentManager().findFragmentById(R.id.notifications_container);

        if (mNotificationsFragment == null) {
            mNotificationsFragment = PushNotificationsFragment.newInstance();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.notifications_container, mNotificationsFragment)
                    .commit();
        }

        mNotificationsPresenter = new PushNotificationsPresenter(mNotificationsFragment, FirebaseMessaging.getInstance());
    }

}
