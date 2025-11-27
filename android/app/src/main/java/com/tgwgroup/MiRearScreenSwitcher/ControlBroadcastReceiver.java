package com.tgwgroup.MiRearScreenSwitcher;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

/**
 * Manifest-declared receiver to ensure actions reach the app in Release.
 * It starts NotificationService (so dynamic receivers are registered)
 * then re-emits the same action scoped to our package after a short delay.
 */
public class ControlBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "ControlBroadcastReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null) return;
        final String action = intent.getAction();
        if (action == null) return;

        // Avoid re-processing our own re-emitted intents
        if (intent.getBooleanExtra("fromBootstrap", false)) {
            Log.d(TAG, "Ignoring re-emitted internal broadcast: " + action);
            return;
        }

        Log.d(TAG, "Received action: " + action + ", bootstrapping NotificationService");

        try {
            // Start NotificationService to ensure onCreate runs and receivers are registered
            Intent serviceIntent = new Intent(context, NotificationService.class);
            context.startService(serviceIntent);
        } catch (Throwable t) {
            Log.w(TAG, "Failed to start NotificationService: " + t.getMessage());
        }

        // Re-emit the action scoped to our package after a small delay
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            try {
                Intent internal = new Intent(action);
                internal.setPackage(context.getPackageName());
                internal.putExtra("fromBootstrap", true);
                context.sendBroadcast(internal);
                Log.d(TAG, "Re-emitted internal broadcast: " + action);
            } catch (Throwable t) {
                Log.w(TAG, "Failed to re-emit broadcast: " + t.getMessage());
            }
        }, 300);
    }
}
