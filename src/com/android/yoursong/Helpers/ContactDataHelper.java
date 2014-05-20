package com.android.yoursong.Helpers;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import com.android.yoursong.Models.QueryContact;

public class ContactDataHelper {

    private final ContentResolver contentResolver;

    private String[] contactProjection =  {
            ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER,
            ContactsContract.CommonDataKinds.Phone.IS_PRIMARY,
            ContactsContract.CommonDataKinds.Email.ADDRESS,
            ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
            ContactsContract.Data.MIMETYPE
    };

    public ContactDataHelper(ContentResolver resolver) {
        this.contentResolver = resolver;
    }

    public QueryContact getQueryContact(Uri dataUri) {
        QueryContact queryContact = new QueryContact();

        Cursor contactCursor = getContacCursor(dataUri);

        while (contactCursor.moveToNext()) {

            Cursor dataCursor = getDataCursor(contactCursor);
            while (dataCursor.moveToNext()) {
                setData(dataCursor, queryContact);
            }
            dataCursor.close();
        }
        contactCursor.close();

        return queryContact;
    }

    private Cursor getContacCursor(Uri dataUri) {
        Uri contactUri = ContactsContract.Contacts.lookupContact(contentResolver, dataUri);
        Cursor contactCursor = contentResolver.query(contactUri, null, null, null, null);

        return contactCursor;
    }

    private Cursor getDataCursor(Cursor contactCursor) {
        String id = contactCursor.getString(contactCursor.getColumnIndex(ContactsContract.Contacts._ID));
        String nameWhere = ContactsContract.Data.CONTACT_ID + " = " + id;

        Cursor dataCursor = contentResolver.query(ContactsContract.Data.CONTENT_URI, contactProjection, nameWhere, null, null);

        return dataCursor;
    }


    private void setData(Cursor cursor, QueryContact queryContact) {
        String mimetype = cursor.getString(cursor.getColumnIndex(ContactsContract.Data.MIMETYPE));

        if (mimetype.equals(ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)) {
            String queryName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME));

            queryContact.setQueryName(queryName);
        }
        if (mimetype.equals(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)) {
            int isPrimary = cursor.getInt(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.IS_PRIMARY));

            if (isPrimary == 1 || queryContact.getPhoneNumber() == null) {
                String phoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                queryContact.setPhoneNumber(phoneNumber);
            }
        }
        if (mimetype.equals(ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)) {
            int isPrimary = cursor.getInt(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.IS_PRIMARY));

            if (isPrimary == 1 || queryContact.getEmailAddress() == null) {
                String emailAddress = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.ADDRESS));

                queryContact.setEmailAddress(emailAddress);
            }
        }
    }
}
