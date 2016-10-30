package com.smartjinyu.criminalintent;

import android.support.v4.app.Fragment;

/**
 * Created by smartjinyu on 2016/10/30.
 */

public class CrimeListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment(){
        return new CrimeListFragment();
    }
}
