package com.taptap.cmcm.taptap.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class AlarmFactory {
    public static final int G_ALARM_TYPE_DEFAULT = -1;
    public static final int G_ALARM_TYPE_WAKE_SERVICE_NOTIFICATION = 0;

    public AlarmFactory() {};

    public static void GenerateAlarm(AlarmInformation alarmInfo)
    {
        assert(alarmInfo.m_CContext != null && alarmInfo.m_CIntent != null);
        switch (alarmInfo.m_IAlarmType)
        {
            case G_ALARM_TYPE_WAKE_SERVICE_NOTIFICATION:
            {
                Calendar calendarTarget = Calendar.getInstance();
                calendarTarget.setTimeInMillis(System.currentTimeMillis());

                Calendar calendarCurrent = Calendar.getInstance();
                calendarCurrent.setTimeInMillis(System.currentTimeMillis());
                int currentHour = calendarCurrent.get(Calendar.HOUR_OF_DAY);
                //O'clock
                calendarCurrent.set(Calendar.MINUTE, 0);
                calendarCurrent.set(Calendar.SECOND, 0);

                //set clock
                calendarTarget.set(Calendar.HOUR_OF_DAY, alarmInfo.m_IAlarmHour);

                if (alarmInfo.m_ListDays.length != 0)
                {
                    int currentDay = calendarCurrent.get(Calendar.DAY_OF_WEEK);
                    boolean goToNextWeek = false;
                    //make it to 1>2>3...
                    Arrays.sort(alarmInfo.m_ListDays);
                    //first config day
                    int dayInWeek = alarmInfo.m_ListDays[0];

                    for (int i = 0; i < alarmInfo.m_ListDays.length; ++i)
                    {
                        //our day start from monday
                        int targetDay = alarmInfo.m_ListDays[i];
                        if (targetDay == Calendar.SUNDAY)
                        {
                            targetDay = Calendar.SATURDAY + 1;
                        }
                        //make it biggest
                        if (currentDay == Calendar.SUNDAY)
                        {
                            currentDay = Calendar.SATURDAY + 1;
                        }

                        if (currentDay >= targetDay && currentHour >= alarmInfo.m_IAlarmHour)
                        {
                            //get earliest day
                            dayInWeek = Math.min(dayInWeek, targetDay);
                            goToNextWeek = true;
                        }
                        else
                        {
                            dayInWeek = targetDay;
                            goToNextWeek = false;
                        }
                    }

                    if (dayInWeek == Calendar.SATURDAY + 1)
                    {
                        dayInWeek = Calendar.SUNDAY;
                    }

                    if (goToNextWeek)
                    {
                        calendarTarget.add(Calendar.WEEK_OF_MONTH, 1);
                    }
                    else
                    {

                    }
                    calendarTarget.set(Calendar.DAY_OF_WEEK, dayInWeek);
                }
                else
                {
                    //default add one day
                    if (currentHour >= alarmInfo.m_IAlarmHour)
                    {
                        //set to next day
                        calendarTarget.add(Calendar.DAY_OF_MONTH, 1);
                    }
                }

                AlarmManager alarmMgr = (AlarmManager) ( alarmInfo.m_CContext.getSystemService(Context.ALARM_SERVICE) );
                //sdk version > 19 setRepeating will not exact.
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
                {
                    //alarmMgr.setExact(AlarmManager.RTC, calendar.getTimeInMillis() + 10000, alarmInfo.m_CIntent);
                    alarmMgr.setWindow(AlarmManager.RTC, calendarTarget.getTimeInMillis(), calendarTarget.getTimeInMillis() + 5 * 1000, alarmInfo.m_CIntent);
                }
                else
                {
                    alarmMgr.setRepeating(AlarmManager.RTC, calendarTarget.getTimeInMillis(),
                            AlarmManager.INTERVAL_DAY, alarmInfo.m_CIntent);
                }
            }
            break;
            case G_ALARM_TYPE_DEFAULT:
            {

            }
            break;
        }
    }

    public class AlarmInformation
    {
        public AlarmInformation(){

        };

        public int m_IAlarmType = AlarmFactory.G_ALARM_TYPE_DEFAULT;
        //when to trigger the alarm in hour
        public int m_IAlarmHour = 0;
        //when to trigger the alarm in minute
        //public int m_IAlarmMinute = 0;
        public Context m_CContext = null;
        //intent
        public PendingIntent m_CIntent = null;
        //days
        public int[] m_ListDays = null;

//        public static final int sAlarmTimeNoon = 11;
//        public static final int sAlarmTimeEvening = 20;

//        //notify content saved in file
//        public String m_SContent = "";
//        //notify extra saved in file
//        public String m_SExtra = "";
//        //notify id saved in file
//        public int id = 0;
    }
}
