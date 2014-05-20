package com.android.yoursong.Helpers;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ImagesQueryHelper extends ContactDatabaseHelper {

    public ImagesQueryHelper(ContentResolver resolver) {
        super(resolver);
    }

    public List<Uri> getContactImages() {
        Cursor cursor = getStrequentCursor();

        Set<Uri> contactImages = new HashSet<Uri>();
        while (cursor.moveToNext()) {

            Long contactId = cursor.getLong(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            Uri contactUri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, contactId);
            Uri fullUri = Uri.withAppendedPath(contactUri, ContactsContract.Contacts.Photo.CONTENT_DIRECTORY);
            contactImages.add(fullUri);
        }
        List<Uri> contactImageList = new ArrayList<Uri>(contactImages);
        return contactImageList;
    }

}
