package com.example.promise.med_manager;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;

/**
 * Created by Promise on 4/11/2018.
 */

public class AlarmReceiver extends BroadcastReceiver {

    private static final int MY_NOTIFICATION_ID = 4;
    NotificationManager nfm;
    Notification ntf;

    @Override
    public void onReceive(Context context, Intent intent) {
        Vibrator vib=(Vibrator)context.getSystemService(context.VIBRATOR_SERVICE);    //for Vibration
        vib.vibrate(10000);

        Intent myIntent = new Intent(context, DoSomething.class); //SO THIS ACTIVITY IN SETTED ALARM TIME.
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, myIntent, Intent.FLAG_ACTIVITY_NEW_TASK);

        ntf = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.bg) //SHOWED IN STATUS BAR
                .setContentTitle("Med_Manager")
                .setContentText("Please remember to take your medication")
                .setTicker("Notification Head")
                .setWhen(System.currentTimeMillis())
                .setContentIntent(pendingIntent)
                .setAutoCancel(true) // REMOVE ALARM NOTIFICATION JUST BY SWIPE
                .build();

        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        Ringtone r = RingtoneManager.getRingtone(context, notification);
        r.play();

        nfm = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        nfm.notify(MY_NOTIFICATION_ID, ntf);
    }
}