package com.example.kiwi.utilities;


import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.example.kiwi.AlertReceiver;

public class utils {

    /*
     * Hides soft input keyboard from the screen.
     *
     * @param Context context from which the keyboard will be hidden
     * @param View    view for acquiring window token from which the keyboard will be hidden
     */
    public static void hideKeyboard(Context context, View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /*
     * Checks if a TextView is empty.
     *
     * @param  TextView the view to be checked for emptiness
     * @return boolean  whether view contains non-empty string
     */
    public static boolean isTextViewEmpty(TextView view) {
        return view.getText().toString().trim().length() == 0;
    }

    /*
     * Converts hours and minutes to milliseconds
     *
     * @param  int  hour
     * @param  int  minute
     * @return long millisecond conversion of the given hours and minutes
     */
    public static long getDurationInMillis(int hour, int minute) {
        return (hour * 60 + minute) * 60000;
    }

    /*
     * Checks if the PendingIntent for the alarm has been sent
     *
     * @param  Context context from which the intent is sent
     * @param  int     request code used for identifying PendingIntent
     * @return boolean whether alarm has been set already
     */
    public static boolean checkAlarmSet(Context context, int requestCode) {
        return (PendingIntent.getBroadcast(context, requestCode,
                new Intent(context, AlertReceiver.class),
                PendingIntent.FLAG_NO_CREATE) != null);
    }
}
