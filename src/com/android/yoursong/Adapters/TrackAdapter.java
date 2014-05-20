package com.android.yoursong.Adapters;


import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.support.v13.app.FragmentPagerAdapter;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.yoursong.Fragments.TrackFragment;
import com.android.yoursong.Http.YourSongRequest;
import com.android.yoursong.Http.YourSongVolley;
import com.android.yoursong.Http.QueryResponse;
import com.android.yoursong.Models.Track;
import com.android.yoursong.Utils.Log;
import com.android.yoursong.YourSongApplication;

import java.util.ArrayList;
import java.util.List;


public class TrackAdapter extends FragmentPagerAdapter {

    public static final String NO_TRACKS_ACTION = "com.android.your_song.action.NO_TRACKS";

    List<Track> tracks = new ArrayList<Track>();
    int maxTracks;
    int pageNumber;
    String query;
    boolean titleOnly;

    public TrackAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return tracks.size();
    }

    @Override
    public Fragment getItem(int i) {
        if (shouldLoadNextBatch(i)) {
            loadBatch(query, titleOnly);
        }
        return new TrackFragment(tracks.get(i));
    }

    private boolean shouldLoadNextBatch(int position) {
        return pageNumber < maxTracks && position == tracks.size() - 1;
    }

    public void loadFirstBatch(String query, boolean titleOnly) {
        this.pageNumber = 1;
        this.query = query;
        this.titleOnly = titleOnly;
        loadBatch(query, titleOnly);
    }

    private void loadBatch(String query, boolean titleOnly) {
        String url = YourSongRequest.getRequestUrl(query, titleOnly, pageNumber);

        final Response.Listener<QueryResponse> listener = new Response.Listener<QueryResponse>() {
            @Override
            public void onResponse(QueryResponse response) {
                maxTracks = response.message.header.available;

                if (maxTracks == 0) {
                    notifyEmptyResults();
                } else {
                    for (QueryResponse.Message.ResponseBody.TrackInfo info : response.message.body.track_list) {
                        tracks.add(info.track);
                    }
                    notifyDataSetChanged();
                    pageNumber++;
                }
            }
        };

        final Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(error.getMessage());
            }
        };

        YourSongRequest request = new YourSongRequest(url, listener, errorListener);

        YourSongVolley.addRequest(request);
    }

    public Track getTrack(int position) {
        return tracks.get(position);
    }

    private void notifyEmptyResults() {
        Intent intent = new Intent(NO_TRACKS_ACTION);
        YourSongApplication.getInstance().sendBroadcast(intent);
    }

}
