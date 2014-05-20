package com.android.yoursong;

import android.app.Application;

public class YourSongApplication extends Application {

    private static YourSongApplication yourSongApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        initialize();
    }

    private void initialize() {
        yourSongApplication = this;
    }

    public static Application getInstance() {
        return yourSongApplication;
    }
}
