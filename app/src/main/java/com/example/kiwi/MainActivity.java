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
    // Request code for identifying PendingIntent
    private static final int PENDING_INTENT_REQUEST_CODE = 677;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mHourInput = (EditText) findViewById(R.id.et_hour_input);
        mMinuteInput = (EditText) findViewById(R.id.et_minute_input);
        mMainTv = (TextView) findViewById(R.id.tv_buzzme);

        mMinuteInput.addTextChangedListener(getMinuteInputWatcher());

        // Hide keyboard when touching outside EditTexts
        ConstraintLayout layout = (ConstraintLayout) findViewById(R.id.layout_main);
        layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                utils.hideKeyboard(MainActivity.this, v);
                return false;
            }
        });

        // Configure "Set Alarm" button to setAlarm function
        Button setAlarmBtn = (Button) findViewById(R.id.btn_set_alarm);
        setAlarmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAlarm();
            }
        });
    }

    /*
     * Inputwatcher for mMinuteInput EditText component
     */
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

            /*
             * Make sure the minute input does not exceed 59. Any input >59 is changed down to 59.
             */
            @Override
            public void afterTextChanged(Editable s) {
                String minuteInput = mMinuteInput.getText().toString();
                if (minuteInput.length() != 0) {
                    int minute = Integer.parseInt(minuteInput);

                    if (minute > 59)  {
                        mMinuteInput.setText("59");
                        Toast.makeText(MainActivity.this, R.string.minute_input_err_msg, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        };

        return minuteInputWatcher;
    }

    /*
     * Sets an alarm
     */
    private void setAlarm() {
        boolean isHourEmpty = utils.isTextViewEmpty(mHourInput);
        boolean isMinuteEmpty = utils.isTextViewEmpty(mMinuteInput);
        int hour = 0;
        int minute = 0;
        // If no duration has been given, prompt input
        if (isHourEmpty && isMinuteEmpty) {
            Toast.makeText(this, R.string.time_input_empty, Toast.LENGTH_LONG).show();
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
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, alarmTimeInMillis, getAlertReceiverPendingIntent());
        Toast.makeText(this, R.string.alarm_set, Toast.LENGTH_LONG).show();
    }

    /*
     * Creates and returns a pending intent for the AlertReceiver
     *
     * @return PendingIntent a pending intent for AlertReceiver
     */
    private PendingIntent getAlertReceiverPendingIntent() {
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, PENDING_INTENT_REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        return pendingIntent;
    }
}
