package com.tgwgroup.MiRearScreenSwitcher;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.IBinder;
import android.provider.ContactsContract;
import android.telecom.TelecomManager;
import android.telephony.TelephonyManager;
import android.util.Log;

public class CallStateService extends Service {
    private static final String TAG = "CallStateService";
    private CallStateReceiver callStateReceiver;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "CallStateService created");
        registerCallStateReceiver();
        getDefaultDialerApp();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "CallStateService destroyed");
        unregisterReceiver(callStateReceiver);
    }

    private void registerCallStateReceiver() {
        callStateReceiver = new CallStateReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(TelephonyManager.ACTION_PHONE_STATE_CHANGED);
        registerReceiver(callStateReceiver, intentFilter);
    }

    private void getDefaultDialerApp() {
        TelecomManager telecomManager = (TelecomManager) getSystemService(Context.TELECOM_SERVICE);
        if (telecomManager != null) {
            String defaultDialer = telecomManager.getDefaultDialerPackage();
            Log.d(TAG, "Default Dialer App: " + defaultDialer);
        }
    }

    private boolean isContact(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            return false;
        }
        Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phoneNumber));
        String[] projection = new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            boolean isContact = cursor.moveToFirst();
            cursor.close();
            return isContact;
        }
        return false;
    }

    public class CallStateReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
            Log.d(TAG, "Call state changed: " + state);

            if (TelephonyManager.EXTRA_STATE_RINGING.equals(state)) {
                String incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);

                Intent callIntent = new Intent(context, RearScreenCallActivity.class);
                callIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                callIntent.putExtra("incoming_number", incomingNumber);
                context.startActivity(callIntent);

                if (isContact(incomingNumber)) {
                    Log.d(TAG, "Incoming call from a contact: " + incomingNumber);
                } else {
                    Log.d(TAG, "Incoming call from an unknown number: " + incomingNumber);
                }
            }
        }
    }
}
