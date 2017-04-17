package com.taptap.cmcm.taptap.utils;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.taptap.cmcm.taptap.Game;

public class BroadcastReceiverAlarm extends BroadcastReceiver{

    public static final String g_StrAlarmAction = "com.taptap.cmcm.taptap.AlarmAction";
    public static final String g_StrNotifyId = "com.taptap.cmcm.taptap.NotifyId";
    public static final String g_StrNotifyTime = "com.taptap.cmcm.taptap.NotifyTime";
    public static final String g_StrNotifyDay = "com.taptap.cmcm.taptap.NotifyDay";
    public static final String g_StrNotifyContent = "com.taptap.cmcm.taptap.NotifyContent";
    public static final String g_StrNotifyidentifier = "com.taptap.cmcm.taptap.Notifyidentifier";

    @Override
    public void onReceive(Context context, Intent intent) {
        String actionStr = intent.getAction();
        if (actionStr == g_StrAlarmAction)
        {
            int identifier = intent.getIntExtra(g_StrNotifyidentifier, GameNotification.g_INotifyBeginIdentifier);
            int time = intent.getIntExtra(g_StrNotifyTime, 1);
            int[] days = intent.getIntArrayExtra(g_StrNotifyDay);
            int id = intent.getIntExtra(g_StrNotifyId, 1);
            String content = intent.getStringExtra(g_StrNotifyContent);

            GameNotification.GenrateNotification(context, content, identifier);
            //set another alarm
            Game.setNotificationConfig(identifier, id, days, time, content);
        }
    }
}
