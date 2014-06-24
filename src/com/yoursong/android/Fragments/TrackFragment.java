package com.yoursong.android.Fragments;

import android.app.Fragment;
import android.app.SearchManager;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.yoursong.android.Models.Track;
import com.yoursong.android.R;
import com.squareup.picasso.Picasso;

public class TrackFragment extends Fragment {

    private Track track;

    public TrackFragment(Track track) {
        this.track = track;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_track, container, false);

        if (track.hasSpotifyLink()) {
            View button = view.findViewById(R.id.play_button);
            button.setVisibility(View.VISIBLE);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    playTrack(track);
                }
            });

        }

        setTrackInfo(view);

        return view;
    }

    private void setTrackInfo(View view) {
        ((TextView) view.findViewById(R.id.track_title)).setText(track.getTrackName());
        ((TextView) view.findViewById(R.id.track_artist)).setText(track.getArtist());
        Picasso.with(getActivity()).load(track.getBestAlbumArt()).into((ImageView) view.findViewById(R.id.track_image));
    }

    public void playTrack(Track track) {
        try {
            String uri = "spotify:track:" + track.getSpotifyId();
            Intent launcher = new Intent( Intent.ACTION_VIEW, Uri.parse(uri) );
            startActivity(launcher);
        } catch (ActivityNotFoundException e) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(track.getSpotifyLink()));
            startActivity(browserIntent);
        }
    }
}
