package com.example.kiwi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kiwi.utilities.utils;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MAINACTIVITY";
    EditText mHourInput;
    EditText mMinuteInput;
    TextView mMainTv;
    private static final int pendingIntentRequestCode = 677;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mHourInput = (EditText) findViewById(R.id.et_hour_input);
        mMinuteInput = (EditText) findViewById(R.id.et_minute_input);
        mMainTv = (TextView) findViewById(R.id.tv_buzzme);

        mMinuteInput.addTextChangedListener(getMinuteInputWatcher());

        ConstraintLayout layout = (ConstraintLayout) findViewById(R.id.layout_main);
        layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                utils.hideKeyboard(MainActivity.this, v);
                return false;
            }
        });

        Button setAlarmBtn = (Button) findViewById(R.id.btn_set_alarm);
        setAlarmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAlarm(v);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (checkAlarmSet()) {

        }
    }

    private TextWatcher getMinuteInputWatcher() {
        TextWatcher minuteInputWatcher = new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                String minuteInput = mMinuteInput.getText().toString();
                if (minuteInput.length() != 0) {
                    int minute = Integer.parseInt(minuteInput);

                    if (minute > 59)  {
                        mMinuteInput.setText("59");
                        Toast.makeText(MainActivity.this, "Minute cannot exceed 59.",  Toast.LENGTH_SHORT).show();
                    }
                }
            }
        };

        return minuteInputWatcher;
    }


    private void setAlarm(View view) {
        boolean isHourEmpty = utils.isEmpty(mHourInput);
        boolean isMinuteEmpty = utils.isEmpty(mMinuteInput);
        int hour = 0;
        int minute = 0;
        // If no duration has been given, prompt input
        if (isHourEmpty && isMinuteEmpty) {
            Toast.makeText(this, "Please set the timer.", Toast.LENGTH_LONG).show();
            return;
        }
        if (!isHourEmpty) {
            hour = Integer.parseInt(mHourInput.getText().toString());
        }
        if (!isMinuteEmpty) {
            minute = Integer.parseInt(mMinuteInput.getText().toString());
        }

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        if (alarmManager == null) {
            Log.e(TAG, "alarmManager returned null");
            return;
        }

        long alarmTimeInMillis = System.currentTimeMillis() + utils.getDurationInMillis(hour, minute);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, pendingIntentRequestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, alarmTimeInMillis, pendingIntent);
    }

    private void cancelAlarm() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, pendingIntentRequestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(pendingIntent);
    }

    private boolean checkAlarmSet() {
        return (PendingIntent.getBroadcast(this, pendingIntentRequestCode,
                new Intent(this, AlertReceiver.class),
                PendingIntent.FLAG_NO_CREATE) != null);
    }
}
