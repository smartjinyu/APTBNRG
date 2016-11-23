package com.smartjinyu.criminalintent;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by smartjinyu on 2016/10/30.
 */
/*
 * A singleton class is a class with a private constructor and a get() method,which returns the instance if it already exists, otherwise it will
 * call the contructor to create it.
 */
public class CrimeLab {
    private static CrimeLab sCrimeLab;
    private List<Crime> mCrimes;

    public static CrimeLab get(Context context){
        if(sCrimeLab == null){
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }
    private CrimeLab(Context context){
        mCrimes = new ArrayList<>();
    }

    public List<Crime> getCrimes(){
        return mCrimes;
    }

    public Crime getCrime(UUID id){
        for(Crime crime:mCrimes){
            if(crime.getId().equals(id)){
                return crime;
            }
        }
        return null;
    }
    public void addCrime(Crime c){
        mCrimes.add(c);
    }
    public void removeCrime(Crime c){
        mCrimes.remove(c);
    }

}
