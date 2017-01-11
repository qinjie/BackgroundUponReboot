package com.example.zqi2.backgrounduponreboot;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by zqi2 on 11/01/2017.
 */

public class BackgroundService extends Service{
    static final String TAG = BackgroundService.class.getSimpleName();

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        // Release the wakelock holded by BootCompletedReceiver
        Log.i(BootCompletedReceiver.class.getSimpleName(), "Completed service @ " + SystemClock.elapsedRealtime());
        BootCompletedReceiver.completeWakefulIntent(intent);

        // If service get killed, after returning from here, restart
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Toast.makeText(this, "BackgroundService created", Toast.LENGTH_SHORT).show();
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "BackgroundService destroyed", Toast.LENGTH_SHORT).show();
        super.onCreate();
    }
}
