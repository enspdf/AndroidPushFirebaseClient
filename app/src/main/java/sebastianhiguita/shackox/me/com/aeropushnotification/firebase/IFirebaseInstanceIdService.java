package sebastianhiguita.shackox.me.com.aeropushnotification.firebase;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by SHACKOX on 1/10/16.
 */

public class IFirebaseInstanceIdService extends FirebaseInstanceIdService {
    private static final String TAG = IFirebaseInstanceIdService.class.getSimpleName();

    public IFirebaseInstanceIdService() {
    }

    @Override
    public void onTokenRefresh() {
        String Token = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "FCM TOKEN: " + Token);

        registerToken(Token);
    }

    private void registerToken(String Token) {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("Token",Token)
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
