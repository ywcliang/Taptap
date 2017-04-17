package com.taptap.cmcm.taptap;

import android.app.Activity;
import android.content.Context;

/**
 * Created by ywc on 17/3/20.
 */

public class GameHelper {
    public static Activity s_GameActivity = null;
    public static Context s_Context = null;

    public static void initGameHelper(Context paramContext){
        s_Context = paramContext.getApplicationContext();
        s_GameActivity = (Activity) paramContext;

        DeviceUuidFactory uuidFac = new DeviceUuidFactory(s_Context);
    }

    //get user id
    public static String getUserUUID()
    {
        String uuid = DeviceUuidFactory.uuid.toString();
        return uuid;
    }

}
