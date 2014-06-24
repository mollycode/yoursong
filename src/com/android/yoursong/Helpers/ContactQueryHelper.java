package com.android.yoursong.Helpers;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import com.android.yoursong.Models.QueryContact;

public class ContactQueryHelper extends ContactDatabaseHelper {

    private String[] contactProjection = {
            ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER,
            ContactsContract.CommonDataKinds.Phone.IS_PRIMARY,
            ContactsContract.CommonDataKinds.Email.ADDRESS,
            ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
            ContactsContract.Data.MIMETYPE
    };

    public ContactQueryHelper(ContentResolver resolver) {
        super(resolver);
    }

    public QueryContact getQueryContact(Uri dataUri) {
        QueryContact queryContact = new QueryContact();

        Cursor contactCursor = getContactCursor(dataUri);

        while (contactCursor.moveToNext()) {

            Cursor dataCursor = getDataCursor(contactCursor, contactProjection);
            while (dataCursor.moveToNext()) {
                setData(dataCursor, queryContact);
            }
            dataCursor.close();
        }
        contactCursor.close();

        return queryContact;
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
