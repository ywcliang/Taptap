//package com.taptap.cmcm.taptap.utils;
//
//import android.app.IntentService;
//import android.app.PendingIntent;
//import android.content.ComponentName;
//import android.content.Context;
//import android.content.Intent;
//import android.content.ServiceConnection;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.os.IBinder;
//
//import com.taptap.cmcm.taptap.R;
//
//import java.util.Calendar;
//
//public class GameIntentService extends IntentService{
//    private final String m_fStrNotifyTimeRecord = "notifyTimeRecord";
//    public static final int m_INotificationPid = 1011012;
//
//    public static final String g_fStrServiceExtra = "com.taptap.cmcm.taptap.ServicesType";
//    public static final String g_StrNotifyId = "com.taptap.cmcm.taptap.NotifyId";
//    public static final String g_StrNotifyContent = "com.taptap.cmcm.taptap.NotifyContent";
//    public static final String g_StrNotifyExtra = "com.taptap.cmcm.taptap.NotifyExtra";
//
//    public static final int g_fITypeOfServiceWakeUpPlayer = 0;
//    //assistService connection
//    //private AssistServiceConnection m_CAssistConnection;
//
//    private class AssistServiceConnection implements ServiceConnection {
//        @Override
//        public void onServiceDisconnected(ComponentName name) {
//
//        }
//
//        @Override
//        public void onServiceConnected(ComponentName name, IBinder binder) {
////            Service assistService = ((AssistIntentService.AssistBinder)binder).getAssistBinderInstance();
////            GameIntentService.this.startForeground(m_INotificationPid, new NotificationCompat.Builder(GameIntentService.this).build());
////            assistService.startForeground(m_INotificationPid, new NotificationCompat.Builder(GameIntentService.this).build());
////            assistService.stopForeground(true);
////
////            GameIntentService.this.unbindService(m_CAssistConnection);
//            //m_CAssistConnection = null;
//        }
//    }
//
//    @Override
//    public void onStart(Intent intent, int startId) {
//        super.onStart(intent, startId);
//    }
//
//    @Override
//    public void onCreate() {
//        super.onCreate();
//        //Log.i(m_fStrNotifyTimeRecord,  "onCreate Badland2 Service");
//        // sdk < 18 have no notification bar.
////        if (Build.VERSION.SDK_INT < 18){
////            Notification noti = new NotificationCompat.Builder(this).build();
////            startForeground(m_INotificationPid, noti);
////            return;
////        }
////
////        if (m_CAssistConnection == null)
////        {
////            m_CAssistConnection = new AssistServiceConnection();
////        }
////
////        boolean binded = bindService(new Intent(this, AssistIntentService.class), m_CAssistConnection, Service.BIND_AUTO_CREATE);
//
////        IntentFilter itentF = new IntentFilter("android.intent.action.USER_PRESENT");
////        itentF.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
////        BroadcastReceiverAlarm rc = new BroadcastReceiverAlarm();
////        registerReceiver(rc, itentF);
//    }
//
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        //Log.i(m_fStrNotifyTimeRecord,  "onStartCommand");
//        return super.onStartCommand(intent, START_STICKY, startId);
//    }
//
//    public GameIntentService()
//    {
//        super("GameIntentService");
//    }
//
//    @Override
//    protected void onHandleIntent(Intent intent) {
//        if (intent == null) return;
//        Bundle b = intent.getExtras();
//        //we have to have some extra data, inorder to determine next we should do.
//        if (b == null) return;
//        int typeOfService = b.getInt(g_fStrServiceExtra);
//        if (typeOfService == g_fITypeOfServiceWakeUpPlayer)
//        {
//            //************make sure we will keep our service as long as possible...************
//            triggerAlarmForLaterUsed();
//            //**********************************************************************************
//
//            //load our configuration data.
//            SharedPreferences sp = getApplicationContext().getSharedPreferences("notify", Context.MODE_PRIVATE);
//            SharedPreferences.Editor editor = sp.edit();
//            //record notification information locally.
//            if (!sp.contains(m_fStrNotifyTimeRecord)
//                //&& !sp.contains("notifyWeekRecord")
//                    )
//            {
//                //record notification state 11.am and 8.pm today.    at byte system from right to left, first digit represent 11.am,  second digit represent 8.pm.  example: 0000 0011
//                editor.putInt(m_fStrNotifyTimeRecord, 0);
//                //record notification chose state this week
//                //editor.putInt("notifyWeekRecord", 0);
//                editor.commit();
//            }
//            int byteModeOfTimeRecord = sp.getInt(m_fStrNotifyTimeRecord, 0);
//
//            //Log.d(m_fStrNotifyTimeRecord, "byteModeOfTimeRecord     " + byteModeOfTimeRecord);
//
//            boolean sleep = false;
//            while (true) {
//                synchronized (this) {
//                    try {
//                        if (sleep)
//                        {
//                            try {
//                                final long m_LCheckCoolDown = 5 * 1000;
//                                Thread.sleep(m_LCheckCoolDown);
//                            } catch (InterruptedException e) {
//
//                            }
//                            //Log.d(m_fStrNotifyTimeRecord, "sleep 5 seconds~~~~~~");
//                        }
//                        else
//                        {
//                            //from trigger moment to timeBuffer millisecond passed notification will be enable.
//                            int timeBufferMinute = 5;
//                            //check time record  11.am
//                            boolean recordednoon = (byteModeOfTimeRecord & 0x01) == 0x01;
//                            //check time record  8.pm
//                            boolean recordedEvening = (byteModeOfTimeRecord & 0x02) == 0x02;
//
//                            //when record finished then today is over.
//                            if (recordedEvening && recordednoon)
//                            {
//                                long currentTime = System.currentTimeMillis();
//                                Calendar currentCalendar = Calendar.getInstance();
//                                //get current time
//                                currentCalendar.setTimeInMillis(currentTime);
//                                if (currentCalendar.get(Calendar.HOUR_OF_DAY) < 20)
//                                {
//                                    //reset yesterday record
//                                    byteModeOfTimeRecord = byteModeOfTimeRecord & 0x00;
//                                    editor.putInt(m_fStrNotifyTimeRecord, byteModeOfTimeRecord);
//                                    editor.commit();
//                                    continue;
//                                }
//                                //rewrite record data, today is over.
//                                byteModeOfTimeRecord = byteModeOfTimeRecord | 0x03;
//                                editor.putInt(m_fStrNotifyTimeRecord, byteModeOfTimeRecord);
//                                editor.commit();
//
//                                //stop self to call onDestroy, to prepare for tomorrow.
//                                triggerAlarmForLaterUsed();
//                                //Log.i(m_fStrNotifyTimeRecord,  "stop self to call onDestroy, to prepare for tomorrow");
//                                return;
//                            }
//
//                            if (!recordedEvening || !recordednoon)
//                            {
//                                long currentTime = System.currentTimeMillis();
//                                Calendar currentCalendar = Calendar.getInstance();
//                                //get current time
//                                currentCalendar.setTimeInMillis(currentTime);
//
//                                //have not notify 11.am today
//                                if (!recordednoon)
//                                {
//                                    if (currentCalendar.get(Calendar.HOUR_OF_DAY) == AlarmFactory.AlarmInformation.sAlarmTimeNoon)
//                                    {
//                                        if (currentCalendar.get(Calendar.MINUTE) <= timeBufferMinute)
//                                        {
//                                            //Resources.getSystem().getString(R.string.NOTIFICATION_DAILY_CHALLENGE)
//                                            //GameNotification.GenrateNotification(this, getResources().getString(R.string.NOTIFICATION_DAILY_CHALLENGE));
//                                        }
//                                        //clear yesterday record and record 11.am notification passed.
//                                        byteModeOfTimeRecord = (byteModeOfTimeRecord & 0x00) | 0x01;
//                                        editor.putInt(m_fStrNotifyTimeRecord, byteModeOfTimeRecord);
//                                        editor.commit();
//                                    }
//                                    //notification is useless when player play our game after 11:05,so we prepared for 8.pm notification..
//                                    else if (currentCalendar.get(Calendar.HOUR_OF_DAY) > AlarmFactory.AlarmInformation.sAlarmTimeNoon &&
//                                            currentCalendar.get(Calendar.HOUR_OF_DAY) < AlarmFactory.AlarmInformation.sAlarmTimeEvening)
//                                    {
//                                        //************make sure we will keep our service as long as possible...************
//                                        triggerAlarmForLaterUsed();
//                                        return;
//                                        //**********************************************************************************
//                                    }
//                                }
//                                //have not notify 8.pm today
//                                if (!recordedEvening)
//                                {
//                                    if (currentCalendar.get(Calendar.HOUR_OF_DAY) == AlarmFactory.AlarmInformation.sAlarmTimeEvening)
//                                    {
//                                        if (currentCalendar.get(Calendar.MINUTE) <= timeBufferMinute)
//                                        {
//                                            int[] listOfStr = {
//                                                    R.string.abc_action_bar_home_description,
//                                            };
//
//                                            //get random num from gStrArrayNightMessage.size
//                                            final double d = Math.random();
//                                            final int i = (int)(d * listOfStr.length);
//
//                                            GameNotification.GenrateNotification(this, getResources().getString(listOfStr[i]));
//                                        }
//
//                                        //not only record 8.pm notification passed but also record 11.am was finished.
//                                        byteModeOfTimeRecord = byteModeOfTimeRecord | 0x03;
//                                        editor.putInt(m_fStrNotifyTimeRecord, byteModeOfTimeRecord);
//                                        editor.commit();
//
//                                        //************make sure we will keep our service as long as possible...************
//                                        triggerAlarmForLaterUsed();
//                                        return;
//                                        //**********************************************************************************
//                                    }
//                                    //notification is useless when player play our game after 20:05,so we think today is over.
//                                    else if (currentCalendar.get(Calendar.HOUR_OF_DAY) > AlarmFactory.AlarmInformation.sAlarmTimeEvening)
//                                    {
//                                        byteModeOfTimeRecord = byteModeOfTimeRecord | 0x03;
//                                        editor.putInt(m_fStrNotifyTimeRecord, byteModeOfTimeRecord);
//                                        editor.commit();
//
//                                        //************make sure we will keep our service as long as possible...************
//                                        triggerAlarmForLaterUsed();
//                                        return;
//                                        //**********************************************************************************
//                                    }
//                                }
//                            }
//                        }
//
//                        sleep = !sleep;
//                    }
//                    catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }
//    }
//
//
//
//    @Override
//    public void onDestroy() {
//        //************make sure we will keep our service as long as possible...************
//        triggerAlarmForLaterUsed();
//        //**********************************************************************************
//
//        //send broadcast to BroadcastReceiverAlarm to wakeup my self when destroyed by the system.
////        Intent intent = new Intent();
////        intent.setClass(getApplicationContext(), BroadcastReceiverAlarm.class);
////        intent.setAction("com.badland2.cmcm.destroy");
////        intent.setFlags(Intent.FLAG_EXCLUDE_STOPPED_PACKAGES);
////        sendBroadcast(intent);
//
//        //Log.i(m_fStrNotifyTimeRecord,  "onDestroy sendBroadcast");
//        super.onDestroy();
//    }
//
//    public static void startBadland2IntentService(Context con)
//    {
//        Intent serviceIntent = new Intent();
//        serviceIntent.setComponent(new ComponentName("com.frogmind.badland2.utils", "GameIntentService"));
//        serviceIntent.setClass(con, GameIntentService.class);
//        serviceIntent.setPackage(con.getPackageName());
//        serviceIntent.putExtra(GameIntentService.g_fStrServiceExtra, GameIntentService.g_fITypeOfServiceWakeUpPlayer);
//        con.startService(serviceIntent);
//    }
//
//    private void triggerAlarmForLaterUsed()
//    {
//        //start service when time up.
//        Intent wakeUpIntent = new Intent(getApplicationContext(), GameIntentService.class);
//        wakeUpIntent.putExtra(GameIntentService.g_fStrServiceExtra, GameIntentService.g_fITypeOfServiceWakeUpPlayer);
//        PendingIntent pendingIntent = PendingIntent.getService(getApplicationContext(), 0, wakeUpIntent, PendingIntent.FLAG_CANCEL_CURRENT);
//        //awake data config
//        AlarmFactory.AlarmInformation info = new AlarmFactory().new AlarmInformation();
//        info.m_IAlarmType = AlarmFactory.G_ALARM_TYPE_WAKE_SERVICE_NOTIFICATION;
//        info.m_CContext = this;
//        info.m_CIntent = pendingIntent;
//        //send
//        AlarmFactory.GenerateAlarm(info);
//    }
//}
