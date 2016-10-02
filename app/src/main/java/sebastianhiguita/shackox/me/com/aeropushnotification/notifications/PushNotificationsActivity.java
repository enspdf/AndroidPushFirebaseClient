package sebastianhiguita.shackox.me.com.aeropushnotification.notifications;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import sebastianhiguita.shackox.me.com.aeropushnotification.R;

public class PushNotificationsActivity extends AppCompatActivity {

    String Token;
    boolean thread_running = true;

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

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                while (thread_running) {
                    Token = FirebaseInstanceId.getInstance().getToken();
                    if (Token != null) {
                        System.out.println("Device Token is " + Token);
                        registerToken(Token);

                        thread_running = false;
                    } else {
                        System.out.println(" - Token not loaded -");
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        t.start();

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

    private void registerToken(String token) {

        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("Token",token)
                .build();

        Request request = new Request.Builder()
                .url("https://cloudapp-manzza.rhcloud.com/Push/DeviceRegister.php") // cloud development
                //.url("http://175.35.0.15/fcm/register.php") //local development
                .post(body)
                .build();

        try {
            client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
