package com.android.yoursong.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.yoursong.Models.Track;
import com.android.yoursong.R;
import com.squareup.picasso.Picasso;

public class TrackFragment extends Fragment {

    private Track track;

    public TrackFragment(Track track) {
        this.track = track;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_track, container, false);

        setTrackInfo(view);

        return view;
    }

    private void setTrackInfo(View view) {
        ((TextView) view.findViewById(R.id.track_title)).setText(track.getTrackName());
        ((TextView) view.findViewById(R.id.track_artist)).setText(track.getArtist());
        Picasso.with(getActivity()).load(track.getBestAlbumArt()).into((ImageView) view.findViewById(R.id.track_image));
    }
}
