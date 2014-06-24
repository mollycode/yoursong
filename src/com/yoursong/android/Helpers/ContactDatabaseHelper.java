package com.yoursong.android.Helpers;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

public class ContactDatabaseHelper {

    protected final ContentResolver contentResolver;

    public ContactDatabaseHelper(ContentResolver resolver) {
        this.contentResolver = resolver;
    }

    protected Cursor getStrequentCursor() {
        Cursor strequentCursor = contentResolver.query(ContactsContract.Contacts.CONTENT_STREQUENT_URI, null, null, null, null);
        return strequentCursor;
    }

    protected Cursor getContactCursor(Uri dataUri) {
        Uri contactUri = ContactsContract.Contacts.lookupContact(contentResolver, dataUri);
        Cursor contactCursor = contentResolver.query(contactUri, null, null, null, null);

        return contactCursor;
    }

    protected Cursor getDataCursor(Cursor contactCursor, String[] contactProjection) {
        String id = contactCursor.getString(contactCursor.getColumnIndex(ContactsContract.Contacts._ID));
        String nameWhere = ContactsContract.Data.CONTACT_ID + " = " + id;

        Cursor dataCursor = contentResolver.query(ContactsContract.Data.CONTENT_URI, contactProjection, nameWhere, null, null);


        return dataCursor;
    }

}
