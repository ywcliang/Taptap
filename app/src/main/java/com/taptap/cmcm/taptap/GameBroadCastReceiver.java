package com.taptap.cmcm.taptap;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.unity3d.player.UnityPlayer;


/**
 * Created by ywc on 17/4/12.
 */

public class GameBroadCastReceiver extends BroadcastReceiver {
    public static int NOT_REACHABLE = 0;
    public static int WIFI = 1;
    public static int MOBILE = 2;

    private static int mLastStatus;
    public GameBroadCastReceiver() {
        mLastStatus = -1;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService (Context.CONNECTIVITY_SERVICE);
        //get current network info
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();

        int networkStatus = NOT_REACHABLE;

        //have some network info
        if (activeNetInfo != null)
        {
            if (activeNetInfo.isConnected() || activeNetInfo.isConnectedOrConnecting())
            {
                //get network type
                int networkType = activeNetInfo.getType();
                switch (networkType)
                {
                    case ConnectivityManager.TYPE_WIFI:
                    {
                        networkStatus = WIFI;
                        break;
                    }
                    case ConnectivityManager.TYPE_MOBILE:
                    {
                        networkStatus = MOBILE;
                        break;
                    }
                    default:
                    {
                        networkStatus = NOT_REACHABLE;
                        break;
                    }
                }
            }
            else
            {
                networkStatus = NOT_REACHABLE;
            }
        }
        else
        //no net work info at all
        {
            networkStatus = NOT_REACHABLE;
        }

        if (mLastStatus != networkStatus)
        {
            StringBuilder sb = new StringBuilder();
            sb.append(networkStatus);

            //str1 : object name, str2 function , str3 par
            UnityPlayer.UnitySendMessage("GameMain","NotifyNetworkStatusChangedEvent",sb.toString());

            mLastStatus = networkStatus;
        }
    }

}
