package com.android.yoursong.Models;

public class Track {
    String track_name;
    String artist_name;
    String album_coverart_500x500;
    String album_coverart_800x800;
    String album_coverart_350x350;
    String album_coverart_100x100;
    String track_spotify_id;

    public String getTrackName() {
        return track_name;
    }

    public String getArtist() {
        return artist_name;
    }

    public boolean hasSpotifyLink() {
        return !track_spotify_id.isEmpty();
    }

    public String getSpotifyLink() {
        return "http://open.spotify.com/track/" + track_spotify_id;
    }

    public String getBestAlbumArt() {
        if (!album_coverart_800x800.isEmpty())
            return album_coverart_800x800;
        if (!album_coverart_500x500.isEmpty())
            return album_coverart_500x500;
        if (!album_coverart_350x350.isEmpty())
            return album_coverart_350x350;
        if (!album_coverart_100x100.isEmpty())
            return album_coverart_100x100;
        return null;
    }
}
