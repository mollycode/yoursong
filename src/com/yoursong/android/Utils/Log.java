package com.yoursong.android.Utils;

public class Log {

    private static final String LOG_KEY = "com.yoursong.android";

    public static void d(String message) {
        android.util.Log.d(LOG_KEY, message);
    }

    public static void v(String message) {
        android.util.Log.v(LOG_KEY, message);
    }

    public static void e(String message) {
        android.util.Log.e(LOG_KEY, message);
    }

    public static void e(String message, Throwable throwable) {
        android.util.Log.e(LOG_KEY, message, throwable);
    }
}
