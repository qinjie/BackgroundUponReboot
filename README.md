# BackgroundUponReboot
This project shows how to run an app in background upon reboot.

1. Make sure app is install in internal memory <manifest xmlns:android="..." package="..." android:installLocation="internalOnly">

2. Use BroadcastReceiver to detect BOOT_COMPLETED and start Service.

3. Use wake lock to make sure Service is started successfully.

4. Use Service instead of IntentService because IntentService is meant for an one-time running (i.e. not do not run as STICKY).

5. No need to check runtime permission for RECEIVE_BOOT_COMPLETED because it is normal permission.
https://unionassets.com/android-native-plugin/runtime-permissions-511

5. Testing

* How to test BOOT_COMPLETED without restart emulator or real device? From Android Studio Terminal:
> adb shell
> am broadcast -a android.intent.action.BOOT_COMPLETED

* How to get device id? Get list of connected devices with id's:
> adb devices

* Where is adb.exe?
By default, it is in "adt-installation-dir/sdk/platform-tools"
