package com.yoursong.android.Activities;

import android.app.Activity;
import android.content.*;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.*;

import com.yoursong.android.Adapters.TrackAdapter;
import com.yoursong.android.Models.*;
import com.yoursong.android.R;

import java.util.ArrayList;
import java.util.List;

import static com.yoursong.android.Activities.SearchActivity.*;


public class ResultsActivity extends Activity {

    private String query;
    private boolean titleOnly;
    private String emailAddress = "";
    private String phoneNumber = "";

    private TrackAdapter trackAdapter;
    private BroadcastReceiver noTrackBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            findViewById(R.id.no_track_fragment).setVisibility(View.VISIBLE);
            findViewById(R.id.send_button).setVisibility(View.GONE);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        findViewById(R.id.no_track_fragment).setVisibility(View.GONE);

        parseIntentData();

        setActionBar();

        loadViewPager();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(noTrackBroadcastReceiver, new IntentFilter(TrackAdapter.NO_TRACKS_ACTION));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(noTrackBroadcastReceiver);
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onMenuItemSelected(featureId, item);
    }

    private void parseIntentData() {
        Intent intentData = getIntent();

        this.query = intentData.getStringExtra(EXTRA_QUERY);

        this.titleOnly = intentData.getBooleanExtra(EXTRA_QUERY_TYPE, false);
        if (intentData.hasExtra(EXTRA_QUERY_EMAIL)) {
            this.emailAddress = intentData.getStringExtra(EXTRA_QUERY_EMAIL);
        }
        if (intentData.hasExtra(EXTRA_QUERY_PHONE)) {
            this.phoneNumber = intentData.getStringExtra(EXTRA_QUERY_PHONE);
        }
    }

    private void setActionBar() {
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setTitle(query);
    }

    private void loadViewPager() {
        this.trackAdapter = new TrackAdapter(getFragmentManager());
        ViewPager viewPager = (ViewPager) findViewById(R.id.track_pager);
        viewPager.setAdapter(trackAdapter);
        trackAdapter.loadFirstBatch(query, titleOnly);
    }

    public void shareSongOnClick(View view) {

        Intent emailIntent = getEmailIntent();
        Intent[] extraSmsIntents = getExtraSmsIntents();

        Intent chooserIntent = Intent.createChooser(emailIntent, getString(R.string.share_dialog));
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, extraSmsIntents);

        startActivity(chooserIntent);
    }

    private Intent getEmailIntent() {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto:" + emailAddress));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.send_title));
        emailIntent.putExtra(Intent.EXTRA_TEXT, getFormattedMessage());
        return emailIntent;
    }

    private Intent getSmsIntent() {
        Uri smsUri = Uri.fromParts("sms", phoneNumber, null);
        Intent smsIntent = new Intent(Intent.ACTION_VIEW, smsUri);
        smsIntent.putExtra("sms_body", getFormattedMessage());
        return smsIntent;
    }

    private Intent[] getExtraSmsIntents() {
        PackageManager packageManager = getPackageManager();
        if (packageManager == null) {
            return null;
        }

        Intent baseIntent = getSmsIntent();
        List<ResolveInfo> resolveInfoList = packageManager.queryIntentActivities(baseIntent, PackageManager.MATCH_DEFAULT_ONLY);

        List<Intent> extraSmsIntents = new ArrayList<Intent>();

        for (ResolveInfo info : resolveInfoList) {
            ActivityInfo activityInfo = info.activityInfo;
            if (activityInfo != null) {
                Intent smsIntent = getSmsIntent();
                smsIntent.setComponent(new ComponentName(activityInfo.packageName, activityInfo.name));
                extraSmsIntents.add(smsIntent);
            }
        }

        return extraSmsIntents.toArray(new Intent[extraSmsIntents.size()]);
    }

    private String getFormattedMessage() {
        int position = ((ViewPager) findViewById(R.id.track_pager)).getCurrentItem();
        Track track = trackAdapter.getTrack(position);
        String message = String.format(getString(R.string.send_message), query, track.getTrackName(), track.getArtist());
        if (track.hasSpotifyLink()) {
            message += '\n' + getString(R.string.send_link) + " " + track.getSpotifyLink();
        }
        return message;
    }
}


