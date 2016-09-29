package sebastianhiguita.shackox.me.com.aeropushnotification;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class MainActivity extends AppCompatActivity {

    String Token;
    boolean thread_running = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseMessaging.getInstance().subscribeToTopic("test");
        String a = FirebaseInstanceId.getInstance().getToken();

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

    }

    private void registerToken(String token) {

        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("Token",token)
                .build();

        Request request = new Request.Builder()
                .url("http://175.35.0.15/fcm/register.php")
                .post(body)
                .build();

        try {
            client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
