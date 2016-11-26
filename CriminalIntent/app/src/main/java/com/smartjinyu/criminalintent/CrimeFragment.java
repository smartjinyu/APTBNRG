package com.smartjinyu.criminalintent;


import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ShareCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.net.DatagramPacket;
import java.util.Date;
import java.util.UUID;

/**
 * Created by smartjinyu on 2016/10/30.
 */

public class CrimeFragment extends Fragment{
    private Crime mCrime;
    private static final String ARG_CRIME_ID = "crime_id";
    private static final String DIALOG_DATE = "DialogDate";
    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_CONTACT = 1;

    private EditText mTitleField;
    private CheckBox mSolvedCheckBox;
    private Button mDateButton;
    private Button mReportButton;
    private Button mSuspectButton;
    private Button mCallButton;

    public static CrimeFragment newInstance(UUID crimeId){
        Bundle args = new Bundle();
        args.putSerializable(ARG_CRIME_ID,crimeId);
        CrimeFragment fragment = new CrimeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        UUID crimeId = (UUID) getArguments().getSerializable(ARG_CRIME_ID);
        mCrime = CrimeLab.get(getActivity()).getCrime(crimeId);
    }

    @Override
    public void onPause(){
        super.onPause();
        CrimeLab.get(getActivity()).updateCrime(mCrime);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_crime,container,false);
        mTitleField = (EditText) v.findViewById(R.id.crime_title);
        mTitleField.setText(mCrime.getTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //This space is intentionally left blank
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mCrime.setTitle(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //This one too
            }
        });

        mDateButton = (Button) v.findViewById(R.id.crime_date);
        updateDate();
        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = getFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance(mCrime.getDate());
                dialog.setTargetFragment(CrimeFragment.this,REQUEST_DATE);//so DatePickerFragment's Target fragment is CrimeFragement
                dialog.show(manager,DIALOG_DATE);
            }
        });

        mSolvedCheckBox = (CheckBox) v.findViewById(R.id.crime_solved);
        mSolvedCheckBox.setChecked(mCrime.isSolved());
        mSolvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mCrime.setSolved(b);
            }
        });

        mReportButton = (Button) v.findViewById(R.id.crime_report);
        mReportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShareCompat.IntentBuilder intentBuilder = ShareCompat.IntentBuilder.from(getActivity());
                intentBuilder.setType("text/plain");
                intentBuilder.setText(getCrimeReport());
                intentBuilder.startChooser();
            }
        });

        mCallButton = (Button) v.findViewById(R.id.crime_call);
        mCallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri lookupUri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_LOOKUP_URI, mCrime.getSuspect());
                Uri res = ContactsContract.Contacts.lookupContact(getActivity().getContentResolver(), lookupUri);

                String[] queryFields = new String[] {ContactsContract.Contacts._ID};
                Cursor c1 = getActivity().getContentResolver().query(res,queryFields,null, null,null);
                String id = null;

                try{
                    if(c1.getCount() != 0){
                        c1.moveToFirst();
                        id = c1.getString(0);
                    }
                }finally {
                    c1.close();
                }

                Cursor cursor = getActivity().getContentResolver().query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?",
                        new String[]{id}, null);

                cursor.moveToFirst();
                String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                Uri num = Uri.parse("tel:n"+number);
                Intent i =new Intent(Intent.ACTION_DIAL,num);
                startActivity(i);


                cursor.close();


            }
        });

        final Intent pickContact = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        mSuspectButton = (Button) v.findViewById(R.id.crime_suspect);
        mSuspectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(pickContact,REQUEST_CONTACT);
            }
        });

        if(mCrime.getSuspect() != null ){
            //haven't handle runtime permission in SDK23 yet
            Uri lookupUri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_LOOKUP_URI, mCrime.getSuspect());
            Uri res = ContactsContract.Contacts.lookupContact(getActivity().getContentResolver(), lookupUri);

            String[] queryFields = new String[] { ContactsContract.Contacts.DISPLAY_NAME };
            Cursor c1 = getActivity().getContentResolver().query(res,queryFields,null,null,null);
            try{
                if(c1.getCount() != 0){
                    c1.moveToFirst();
                    String suspectName = c1.getString(0);
                    mSuspectButton.setText(suspectName);
                }
            }finally {
                c1.close();
            }
        }else{
            mCallButton.setEnabled(false);
        }

        PackageManager packageManager = getActivity().getPackageManager();
        if(packageManager.resolveActivity(pickContact,PackageManager.MATCH_DEFAULT_ONLY)==null){
            mSuspectButton.setEnabled(false);
        }

        return v;
    }

    @Override
    public void onActivityResult (int requestCode,int resultCode,Intent data){
        if(resultCode != Activity.RESULT_OK){
            return;
        }
        if(requestCode == REQUEST_DATE){
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mCrime.setDate(date);
            updateDate();
        }else if(requestCode == REQUEST_CONTACT && data != null){
            Uri contactUri = data.getData();
            String[] queryFields1 = new String[] { ContactsContract.Contacts.LOOKUP_KEY };

            Cursor c = getActivity().getContentResolver().query(contactUri,queryFields1,null,null,null);
            //query like a where clause
            try{
                if(c.getCount() == 0){
                    return;
                }
                c.moveToFirst();
                String key = c.getString(0);
                mCrime.setSuspect(key);

            }finally {
                c.close();
            }
            //haven't handle runtime permission in SDK23 yet
            Uri lookupUri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_LOOKUP_URI, mCrime.getSuspect());
            Uri res = ContactsContract.Contacts.lookupContact(getActivity().getContentResolver(), lookupUri);

            String[] queryFields = new String[] { ContactsContract.Contacts.DISPLAY_NAME };
            Cursor c1 = getActivity().getContentResolver().query(res,queryFields,null,null,null);
            try{
                if(c1.getCount() == 0){
                    return;
                }
                c1.moveToFirst();
                String suspectName = c1.getString(0);
                mSuspectButton.setText(suspectName);
            }finally {
                c1.close();
            }
            if(!mCallButton.isEnabled()){
                mCallButton.setEnabled(true);
            }



        }
    }


    private void updateDate() {
        mDateButton.setText(mCrime.getDate().toString());
    }

    private String getCrimeReport(){
        String solvedString = null;
        if(mCrime.isSolved()){
            solvedString = getString(R.string.crime_report_solved);
        }else{
            solvedString = getString(R.string.crime_report_unsolved);
        }
        String dateFormat = "EEE,MMM dd";
        String dateString = DateFormat.format(dateFormat,mCrime.getDate()).toString();

        String suspect = mCrime.getSuspect();
        if(suspect == null){
            suspect = getString(R.string.crime_report_no_suspect);
        }else{
            suspect = getString(R.string.crime_report_suspect,suspect);
        }
        String report = getString(R.string.crime_report,mCrime.getTitle(),dateString,solvedString,suspect);
        return report;
    }

}
