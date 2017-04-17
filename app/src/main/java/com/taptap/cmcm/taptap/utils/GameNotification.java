package com.taptap.cmcm.taptap.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;


import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.taptap.cmcm.taptap.Game;
import com.taptap.cmcm.taptap.R;

public class GameNotification {

    public static final String g_StrNotifyActivity = "com.frogmind.badland2.NofityActivity";
    public static final int g_INotifyBeginIdentifier = 1011010;

    public GameNotification() {

    }

    public static void GenrateNotification(Context context, String conent, int identifier) {
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context.getApplicationContext())
                        .setContentTitle(context.getResources().getString(R.string.app_name))
                        .setContentText(conent)
                        .setSound(alarmSound);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //orange
            mBuilder.setSmallIcon(R.drawable.app_icon)
                    .setColor(Color.rgb(255,193,37));
        }
        else
        {
            mBuilder.setSmallIcon(R.drawable.app_icon);
        }

        //set the notification priority to highest
        mBuilder.setPriority(Notification.PRIORITY_HIGH);
        //click and cancel
        mBuilder.setAutoCancel(true);

        //start MainActivity when user touches notification bar
        Intent resultIntent = new Intent(context.getApplicationContext(), Game.class);
        resultIntent.putExtra("com.frogmind.badland2.NofityActivity", Game.s_fIStartActivityTypeNotify);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(context.getApplicationContext(), 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);

        NotificationManager mNotificationManager =
                (NotificationManager) context.getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(identifier, mBuilder.build());
    }
}
