package com.android.yoursong.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import com.android.yoursong.Helpers.ContactDataHelper;
import com.android.yoursong.Models.QueryContact;
import com.android.yoursong.R;

public class SearchActivity extends Activity {

    public static final String EXTRA_QUERY_TYPE = "com.android.yoursong.extra.query_type";
    public static final String EXTRA_QUERY = "com.android.yoursong.extra.query";
    public static final String EXTRA_QUERY_PHONE = "com.android.yoursong.extra.query_phone";
    public static final String EXTRA_QUERY_EMAIL = "com.android.yoursong.extra.query_email";

    private boolean titleOnly = false;

    private static final int PICK_CONTACT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search);

        ((EditText)findViewById(R.id.name_query)).setOnEditorActionListener(actionListener);
    }

    private TextView.OnEditorActionListener actionListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                String query = String.valueOf(((EditText)findViewById(R.id.name_query)).getText());
                lookupName(query);
                return true;
            }
            return false;
        }
    };

    public void selectContact(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(intent, PICK_CONTACT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_CONTACT && data != null) {
            ContactDataHelper contactDataHelper = new ContactDataHelper(getContentResolver());
                QueryContact queryContact = contactDataHelper.getQueryContact(data.getData());
                lookupName(queryContact);

        }
    }

    private void lookupName(String query) {
        Intent intent = new Intent(this, ResultsActivity.class);
        intent.putExtra(EXTRA_QUERY_TYPE, titleOnly);
        intent.putExtra(EXTRA_QUERY, query);
        startActivity(intent);
    }

    private void lookupName(QueryContact queryContact) {
        Intent intent = new Intent(this, ResultsActivity.class);
        intent.putExtra(EXTRA_QUERY_TYPE, titleOnly);
        intent.putExtra(EXTRA_QUERY, queryContact.getQueryName());
        intent.putExtra(EXTRA_QUERY_EMAIL, queryContact.getEmailAddress());
        intent.putExtra(EXTRA_QUERY_PHONE, queryContact.getPhoneNumber());
        startActivity(intent);
    }

    public void searchType(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.all_param:
                if (checked)
                    titleOnly = false;
                break;
            case R.id.title_param:
                if (checked)
                    titleOnly = true;
                break;
        }
    }
}
