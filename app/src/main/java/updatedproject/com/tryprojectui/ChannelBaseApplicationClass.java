package updatedproject.com.tryprojectui;

import android.app.Activity;
import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;

public class ChannelBaseApplicationClass extends Application {
    private static final String CHANNEL_NOTIFICATION_ID = "Book notification channel";

    @Override
    public void onCreate() {
        super.onCreate();

        notificatinChanel();
    }

    public void notificatinChanel() {

        if(Build.VERSION.SDK_INT >= 26) {
            NotificationChannel channel_book = new NotificationChannel(
                    CHANNEL_NOTIFICATION_ID,
                    "This is a book notification due channel.",
                    NotificationManager.IMPORTANCE_HIGH
            );

            channel_book.setDescription("Book notification channel");

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel_book);
        }
    }
}
