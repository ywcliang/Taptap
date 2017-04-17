//package com.taptap.cmcm.taptap.utils;
//
//import android.app.IntentService;
//import android.content.Intent;
//import android.os.Binder;
//import android.os.IBinder;
//
//public class AssistIntentService extends IntentService{
//
//    class AssistBinder extends Binder
//    {
//        public AssistIntentService getAssistBinderInstance() {
//            return AssistIntentService.this;
//        }
//    }
//
//    public AssistIntentService() {
//        super("AssistIntentService");
//    }
//
//    @Override
//    public IBinder onBind(Intent intent) {
//        return new AssistBinder();
//    }
//
//    @Override
//    protected void onHandleIntent(Intent intent) {
//
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        //Log.i("GameIntentService",  "~~~onDestroy");
//    }
//}