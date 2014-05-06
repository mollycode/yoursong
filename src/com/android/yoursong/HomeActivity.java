package com.android.yoursong;

import android.app.Activity;
import android.app.DownloadManager;
import android.os.Bundle;
import android.widget.TextView;

public class HomeActivity extends Activity {

    /**
     * Called when the activity is first created.
     */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);



        StringRequest request = new StringRequest(
                DownloadManager.Request.Method.GET,
                url,
                listener,
                errorListener);

        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ((TextView)findViewById(R.id.text)).setText(response);
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.networkResponse != null) {
                    L.d("Error Response code: " +  error.networkResponse.statusCode);
                }
            }
        };


        RequestQueue requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        requestQueue.add(request);
    }
}
