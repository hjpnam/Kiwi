package com.example.kiwi.utilities;


import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

public class utils {
    public static void hideKeyboard(Context context, View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public static boolean isEmpty(TextView view) {
        return view.getText().toString().trim().length() == 0;
    }

    public static long getDurationInMillis(int hour, int minute) {
        return (hour * 60 + minute) * 60000;
    }
}
