package com.example.zqi2.backgrounduponreboot;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int REQUEST_CODE_PERMISSIONS = 1;
    private static final String MANUFACTURER_XIAOMI = "Xiaomi";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Check for required permissions
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String[] PERMISSIONS = {
                    // Add permission here
                    // No need to add RECEIVE_BOOT_COMPLETED because it is normal permission
                    // https://unionassets.com/android-native-plugin/runtime-permissions-511
            };
            if (!hasPermissions(this, PERMISSIONS)) {
                ActivityCompat.requestPermissions(this, PERMISSIONS, REQUEST_CODE_PERMISSIONS);
            }
        }

        // Check phone manufacturer
        Log.i(TAG, "Manufacturer = " + Build.MANUFACTURER);
        Log.i(TAG, "Model = " + Build.MODEL);
        // If it is XiaoMi phone, guide user to manually grant Auto Start permission
        if(Build.MANUFACTURER.equals(MANUFACTURER_XIAOMI)){
            Intent i = new Intent(this, ManualPermissionActivity.class);
            startActivity(i);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Check if service is running in background
        Boolean isServiceRunning = isServiceRunning(
                MainActivity.this.getApplicationContext(),
                BackgroundService.class);
        ((TextView) findViewById(R.id.textView)).setText("Service is running = " + isServiceRunning);
    }

    public boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(MainActivity.this, "Permission granted.",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Permission denied!",
                            Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public static boolean isServiceRunning(Context context, Class<?> serviceClass){
        final ActivityManager activityManager = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        final List<ActivityManager.RunningServiceInfo> services = activityManager.getRunningServices(Integer.MAX_VALUE);

        for (ActivityManager.RunningServiceInfo runningServiceInfo : services) {
            Log.d(TAG, String.format("Service:%s", runningServiceInfo.service.getClassName()));
            if (runningServiceInfo.service.getClassName().equals(serviceClass.getName())){
                return true;
            }
        }
        return false;
    }
}

