package com.example.kiwi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.util.Log;

public class AlertReceiver extends BroadcastReceiver {
    private static final String TAG = "ALERT_RECEIVER";
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "received alert at " + String.valueOf(System.currentTimeMillis()));
        final MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.beep2);
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mediaPlayer.release();
            }
        });
        mediaPlayer.setVolume(1.0f, 1.0f);
        mediaPlayer.start();
    }
}
