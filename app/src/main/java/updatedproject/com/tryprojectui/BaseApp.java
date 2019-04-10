package updatedproject.com.tryprojectui;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class BaseApp extends Application {

    public static final String CHANNEL_1 = "book due channel";
    public static final String CHANNEL_2 = "your date report";

    @Override
    public void onCreate() {
        super.onCreate();

        creatinfNotificaionChannel();
    }

    public void creatinfNotificaionChannel() {
        if(Build.VERSION.SDK_INT >= 26) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_1,
                    "Book Due",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription("This channel will help to notify user about the book due he/she has got.");

            NotificationChannel channel12 = new NotificationChannel(
                    CHANNEL_2,
                    "The date report channel",
                    NotificationManager.IMPORTANCE_LOW
            );
            channel12.setDescription("This is just a dummy channel ");

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
            manager.createNotificationChannel(channel12);
        }
    }
}
