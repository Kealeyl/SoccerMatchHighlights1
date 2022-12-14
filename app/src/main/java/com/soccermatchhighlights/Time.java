package com.soccermatchhighlights;

import android.annotation.SuppressLint;
import android.util.Log;
import java.util.TimeZone;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Time { private static final String TAG = Time.class.getName();private static final String LOCAL_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";private static final String UTC_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss+0000";

    @SuppressLint("SimpleDateFormat")
    public static String utcToLocalTime(String time) {
        SimpleDateFormat utcFormat = new SimpleDateFormat(UTC_TIME_FORMAT);
        utcFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        SimpleDateFormat localFormat = new SimpleDateFormat(LOCAL_TIME_FORMAT);
        Date date = null;
        try {
            date = utcFormat.parse(time);
            if (date != null) {
                String res = localFormat.format(date);
                Log.i(TAG, "utcToLocalTime: before -> " + time + " after -> " + res);return res;
            } else {
                Log.i(TAG, "utcToLocalTime: before -> " + time + " after -> error data null");return "-";
            }
        } catch (Exception e) {
            Log.i(TAG, "utcToLocalTime: before -> " + time + " after -> error" + e.getMessage());
            return "-";
        }
    }
}
