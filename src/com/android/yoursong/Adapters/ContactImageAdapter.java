package com.android.yoursong.Adapters;

import android.content.ContentResolver;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import com.android.yoursong.Helpers.ImagesQueryHelper;
import com.android.yoursong.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ContactImageAdapter extends BaseAdapter {

   List<Uri> contactImages = new ArrayList<Uri>();

    public void loadContactImages(ContentResolver contentResolver, GridView gridView) {
        ImagesQueryHelper imagesQueryHelper = new ImagesQueryHelper(contentResolver);

        contactImages = imagesQueryHelper.getContactImages();
        notifyDataSetChanged();

    }

    @Override
    public int getCount() {
        if (contactImages.isEmpty()) {
            return 0;
        } else {
            return Integer.MAX_VALUE;
        }
    }

    @Override
    public Object getItem(int i) {
        return contactImages.get(getArrayPosition(i));
    }

    @Override
    public long getItemId(int i) {
        return contactImages.get(getArrayPosition(i)).hashCode();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ImageView imageView;
        if (view == null) {
            imageView = new ImageView(viewGroup.getContext());
            imageView.setLayoutParams(new GridView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 200));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);

        } else {
            imageView = (ImageView) view;
        }
        Picasso.with(viewGroup.getContext()).load((Uri) getItem(i)).placeholder(R.drawable.ic_launcher).into(imageView);
        return imageView;
    }

    private int getArrayPosition(int i) {
        return i % contactImages.size();
    }
}
