package sebastianhiguita.shackox.me.com.aeropushnotification.data;

import java.util.UUID;

/**
 * Created by SHACKOX on 1/10/16.
 */

public class PushNotification {
    private String id;
    private String mTitle;
    private String mDescription;

    public PushNotification() {
        id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }
}
