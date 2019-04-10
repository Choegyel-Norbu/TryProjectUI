package updatedproject.com.tryprojectui;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import static java.nio.file.Paths.get;

public class DayCountService extends Service {

    public final String TAG = "serviceTAG";

    public NotificationManagerCompat notificationManagerCompat;

    public DayCountService() {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.d(TAG, "Service is started");

        notificationManagerCompat = NotificationManagerCompat.from(this);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                     int count = 2;
                     if(count == 2) {
                         String notify = "Your book is due today";
                         Notification notification = new NotificationCompat.Builder(DayCountService.this,
                                 BaseApplicationNotification.CHANNEL_NOTIFICATION)
                                 .setSmallIcon(R.drawable.ic_add)
                                 .setContentTitle("Library notification")
                                 .setContentText(notify)
                                 .build();

                         notificationManagerCompat.notify(1, notification);
                     }
            }
        });thread.start();
        return START_REDELIVER_INTENT;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
