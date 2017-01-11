package com.example.zqi2.backgrounduponreboot;

import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by zqi2 on 11/01/2017.
 * Use WakefulBroadcastReceiver instead of BroadcastReceiver.
 * Without a wake lock runs the risk of your Service getting killed before it is finished processing.
 * In the Service, make sure to release the wake lock.
 */

public class BootCompletedReceiver extends WakefulBroadcastReceiver {
    static final String TAG = BootCompletedReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
            Intent service = new Intent(context, BackgroundService.class);
            // Start the service, keeping the device awake while it is launching.
            Log.i("BootCompletedReceiver", "Starting service @ " + SystemClock.elapsedRealtime());
            Toast.makeText(context, "BootCompletedReceiver.onReceive()", Toast.LENGTH_LONG).show();
            startWakefulService(context, service);
        }
    }
}
