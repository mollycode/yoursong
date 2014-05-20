package com.android.yoursong.Http;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.android.yoursong.YourSongApplication;

public class YourSongVolley {

    private static YourSongVolley yourSongVolley;
    private RequestQueue requestQueue;

    private YourSongVolley() {
        requestQueue = Volley.newRequestQueue(YourSongApplication.getInstance());
    }

    private static YourSongVolley getInstance() {
        if (yourSongVolley == null) {
            yourSongVolley = new YourSongVolley();
        }
        return yourSongVolley;
    }

    public static RequestQueue requestQueue() {
        return getInstance().requestQueue;
    }

    public static void addRequest(Request request) {
        requestQueue().add(request);
    }

}
