package com.taptap.cmcm.taptap;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;

import com.taptap.cmcm.taptap.utils.AlarmFactory;
import com.taptap.cmcm.taptap.utils.BroadcastReceiverAlarm;
import com.taptap.cmcm.taptap.utils.GameNotification;
import com.unity3d.player.UnityPlayerActivity;

/**
 * Created by ywc on 17/4/12.
 */

public class Game extends UnityPlayerActivity {

    public static final int s_fIStartActivityTypeNotify = 1;

    //broad cast receiver
    private GameBroadCastReceiver m_CBroadReceiver;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        //registerReceiver to listen network status changed
        m_CBroadReceiver = new GameBroadCastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(m_CBroadReceiver,filter);


        //check if start by notification
        Intent in = getIntent();
        if(in != null)
        {
            Bundle bn = in.getExtras();
            if (bn != null)
            {
                int StartTypeOfActivity = bn.getInt(GameNotification.g_StrNotifyActivity);
            }
        }
        else
        {
            //direct start up
        }
    }

    @Override
    protected void onDestroy() {

        //unregister receiver
        unregisterReceiver(m_CBroadReceiver);
        super.onDestroy();
    }

    public static void setNotificationConfig(int identifier, int id, int[] days, int time, String content)
    {
        //start service when time up.
        Intent wakeUpIntent = new Intent(GameHelper.s_Context, BroadcastReceiverAlarm.class);
        //set action
        wakeUpIntent.setAction(BroadcastReceiverAlarm.g_StrAlarmAction);
        //identifier
        wakeUpIntent.putExtra(BroadcastReceiverAlarm.g_StrNotifyidentifier, GameNotification.g_INotifyBeginIdentifier + identifier);
        //day in week
        wakeUpIntent.putExtra(BroadcastReceiverAlarm.g_StrNotifyDay, days);
        //content
        wakeUpIntent.putExtra(BroadcastReceiverAlarm.g_StrNotifyContent, content);
        //id
        wakeUpIntent.putExtra(BroadcastReceiverAlarm.g_StrNotifyId, id);
        //time
        wakeUpIntent.putExtra(BroadcastReceiverAlarm.g_StrNotifyTime, time);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(GameHelper.s_Context, 0, wakeUpIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        //awake data config

        AlarmFactory.AlarmInformation info = new AlarmFactory().new AlarmInformation();
        info.m_IAlarmType = AlarmFactory.G_ALARM_TYPE_WAKE_SERVICE_NOTIFICATION;
        info.m_CContext = GameHelper.s_Context;
        info.m_CIntent = pendingIntent;
        info.m_IAlarmHour = time;
        info.m_ListDays = days;
//        info.m_SContent = content;
//        info.m_SExtra = extra;
        //send
        AlarmFactory.GenerateAlarm(info);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null)
        {
            Bundle bun = intent.getExtras();
            if (bun != null)
            {
                int StartTypeOfActivity = bun.getInt(GameNotification.g_StrNotifyActivity);
            }
            else
            {

            }
        }
    }
}
