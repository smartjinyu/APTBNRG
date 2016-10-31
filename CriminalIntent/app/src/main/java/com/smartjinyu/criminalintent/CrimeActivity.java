package com.smartjinyu.criminalintent;


import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.UUID;

public class CrimeActivity extends SingleFragmentActivity {
    private static String EXTRA_CRIME_ID = "com.smartjinyu.criminalintent.crime_id";
    private static String EXTRA_CRIME_POSITION = "com.smartjinyu.criminalintent.adapter_position";
    private static int mposition;

    public static int getPosition(){
        return mposition;
    }


    @Override
    protected Fragment createFragment(){
        UUID crimeId = (UUID) getIntent().getSerializableExtra(EXTRA_CRIME_ID);
        mposition = getIntent().getIntExtra(EXTRA_CRIME_POSITION,0);
        return CrimeFragment.newInstance(crimeId);
    }

    public static Intent newIntent(Context packageContext, UUID crimeId,int position){
        Intent intent = new Intent(packageContext,CrimeActivity.class);
        intent.putExtra(EXTRA_CRIME_ID,crimeId);
        intent.putExtra(EXTRA_CRIME_POSITION,position);
        return intent;
    }


}
