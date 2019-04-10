package updatedproject.com.tryprojectui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Build;
import android.widget.Toast;

public class Book_notification_BroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        if(intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            Intent i = new Intent(context, DayCountService.class);
            context.startActivity(i);
        }
        if(Build.VERSION.SDK_INT < 26){
            if(ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
                Toast.makeText(context, "Mobile data is turned off!", Toast.LENGTH_LONG).show();
            }
        }

    }
}
