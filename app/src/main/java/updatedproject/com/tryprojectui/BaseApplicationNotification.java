package updatedproject.com.tryprojectui;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class BaseApplicationNotification extends Application {

    public static final String CHANNEL_NOTIFICATION = "notification";
    @Override
    public void onCreate() {
        super.onCreate();

        if(Build.VERSION.SDK_INT >= 26) {
            NotificationChannel notificationChannel = new NotificationChannel(
                    CHANNEL_NOTIFICATION, "Book notification", NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.setDescription("This is channel to notify user about the library due date");

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(notificationChannel);
        }
    }
}
