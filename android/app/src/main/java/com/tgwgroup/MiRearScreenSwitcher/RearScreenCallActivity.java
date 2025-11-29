package com.tgwgroup.MiRearScreenSwitcher;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telecom.TelecomManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class RearScreenCallActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rear_screen_call);

        String incomingNumber = getIntent().getStringExtra("incoming_number");

        TextView callerInfo = findViewById(R.id.caller_info);
        callerInfo.setText(incomingNumber);

        ImageView answerCall = findViewById(R.id.answer_call);
        answerCall.setOnClickListener(v -> answerCall());

        ImageView rejectCall = findViewById(R.id.reject_call);
        rejectCall.setOnClickListener(v -> rejectCall());
    }

    private void answerCall() {
        TelecomManager telecomManager = (TelecomManager) getSystemService(Context.TELECOM_SERVICE);
        if (telecomManager != null) {
            telecomManager.acceptRingingCall();
        }
        finish();
    }

    private void rejectCall() {
        TelecomManager telecomManager = (TelecomManager) getSystemService(Context.TELECOM_SERVICE);
        if (telecomManager != null) {
            telecomManager.endCall();
        }
        finish();
    }
}
