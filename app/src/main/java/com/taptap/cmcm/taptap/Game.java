package com.taptap.cmcm.taptap;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.PersistableBundle;

import com.unity3d.player.UnityPlayerActivity;

/**
 * Created by ywc on 17/4/12.
 */

public class Game extends UnityPlayerActivity {
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

    }

    @Override
    protected void onDestroy() {

        //unregister receiver
        unregisterReceiver(m_CBroadReceiver);
        super.onDestroy();
    }
}
