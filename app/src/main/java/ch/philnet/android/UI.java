package ch.philnet.android;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.app.NotificationCompat;

import ch.philnet.playground.MainActivity;
import ch.philnet.playground.R;

public class UI {
    public static void ShowDialog(Activity act, String title, String message) {
        new AlertDialog.Builder(act).setMessage(message).setTitle(title).setPositiveButton("OK", null).create().show();
    }
    public static void ShowNotification(MainActivity act, String title, String text) {
        Intent resultIntent = new Intent(act, MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(act).addParentStack(MainActivity.class).addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationManager mNotificationManager = (NotificationManager) act.getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(act)
                .setSmallIcon(R.drawable.icon_app)
                .setContentTitle(title)
                .setContentText(text)
                .setContentIntent(resultPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);
        if (Build.VERSION.SDK_INT >= 21) mBuilder.setVibrate(new long[0]);

        mNotificationManager.notify((int) System.currentTimeMillis(), mBuilder.build());
    }

    public static void ShowSnackbar(Activity act, String message) {
        android.support.design.widget.Snackbar.make(act.findViewById(R.id.blank), message, android.support.design.widget.Snackbar.LENGTH_LONG).show();
    }
}
