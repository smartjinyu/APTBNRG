package com.smartjinyu.locatr;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesUtil;

public class LocatrActivity extends SingleFragmentActivity {

    private static final int REQUEST_ERROR = 0;

    @Override
    protected Fragment createFragment(){
        return LocatrFragment.newInstance();
    }

    @Override
    protected void onResume(){
        super.onResume();
        int errorCode = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this);
        if(errorCode != ConnectionResult.SUCCESS){
            Dialog errorDialog = GoogleApiAvailability.getInstance()
                    .getErrorDialog(this,errorCode,REQUEST_ERROR,
                            new DialogInterface.OnCancelListener(){
                        @Override
                        public void onCancel(DialogInterface dialogInterface){
                            finish();
                            // Leave if services are unavailable
                        }
                    });
            errorDialog.show();
        }
    }

}
