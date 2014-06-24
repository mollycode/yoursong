package com.android.yoursong.Http;

import android.net.Uri;
import com.android.volley.AuthFailureError;
import com.android.volley.Response;

import java.util.HashMap;
import java.util.Map;

public class YourSongRequest extends GsonRequest<QueryResponse> {

    private static final String API_URL = "http://api.musixmatch.com/ws/1.1/track.search";
    private static final String API_KEY_PARAM = "apikey";
    private static final String API_KEY = "fda50ab9fd5e60a97b2f10f8e921c095";

    private static final String TITLE_QUERY_PARAM = "q_track";
    private static final String LYRIC_QUERY_PARAM = "q_lyrics";

    private static final String SORT_ORDER_PARAM = "s_track_rating";
    private static final String SORT_ORDER_DESC = "desc";

    private static final String PAGE_PARAM = "page";
    private static final String PAGE_SIZE_PARAM = "page_size";
    private static final String PAGE_SIZE = "20";


    public YourSongRequest(String url,
                           Response.Listener<QueryResponse> listener,
                           Response.ErrorListener errorListener) {
        super(url, QueryResponse.class, null, listener, errorListener);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Accept-Encoding", "");
        return headers;
    }

    public static String getRequestUrl(String searchTerm, boolean titleOnly, int pageNumber) {
        String searchParam = titleOnly ? TITLE_QUERY_PARAM : LYRIC_QUERY_PARAM;

        Uri uri = Uri.parse(API_URL).buildUpon().appendQueryParameter(API_KEY_PARAM, API_KEY)
                .appendQueryParameter(searchParam, searchTerm)
                .appendQueryParameter(PAGE_PARAM, String.valueOf(pageNumber))
                .appendQueryParameter(PAGE_SIZE_PARAM, PAGE_SIZE)
                .appendQueryParameter(SORT_ORDER_PARAM, SORT_ORDER_DESC)
                .build();
        return uri.toString();
    }
}
